import java.util.ArrayList;
import java.util.List;

public class ArbolB {
    private Nodo raiz;
    private int t;

    public ArbolB(int t) {
        this.raiz = null;
        this.t = t;
    }

    public void insertar(int valor) {
        if (raiz == null) {
            raiz = new Nodo(t, true);
            raiz.claves[0] = valor;
            raiz.numClaves = 1;
        } else {
            if (raiz.numClaves == (2 * t) - 1) {
                Nodo nuevaRaiz = new Nodo(t, false);
                nuevaRaiz.hijos[0] = raiz;
                nuevaRaiz.dividirHijo(0, raiz);
                int i = 0;
                if (nuevaRaiz.claves[0] < valor)
                    i++;
                nuevaRaiz.hijos[i].insertarNoLleno(valor);
                raiz = nuevaRaiz;
            } else {
                raiz.insertarNoLleno(valor);
            }
        }
    }

    public List<Integer> buscarNodoPorClave(int clave) {
        if (raiz == null) {
            return null;
        } else {
            return raiz.buscarClave(clave);
        }
    }

    public List<Integer> recorridoCamino(int clave) {
        if (raiz == null) {
            return null;
        } else {
            return raiz.recorrerCamino(clave);
        }
    }

    public List<Integer> buscarNodoYRetornarClaves(int valor) {
        if (raiz == null) {
            return null;
        } else {
            return raiz.buscarValor(valor);
        }
    }

    public int obtenerValorMaximo() {
        if (raiz == null) {
            return -1;
        } else {
            return raiz.obtenerValorMaximo();
        }
    }

    public void eliminar(int valor) {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return;
        }

        raiz.eliminar(valor);

        if (raiz.numClaves == 0) {
            if (raiz.esHoja())
                raiz = null;
            else
                raiz = raiz.hijos[0];
        }
    }

    public void mostrar() {
        if (raiz != null) {
            raiz.mostrar();
        }
    }


    private class Nodo {
        private int[] claves;
        private Nodo[] hijos;
        private int numClaves;
        private boolean esHoja;

        private Nodo(int t, boolean esHoja) {
            this.claves = new int[(2 * t) - 1];
            this.hijos = new Nodo[2 * t];
            this.numClaves = 0;
            this.esHoja = esHoja;
        }

        private void insertarNoLleno(int valor) {
            int i = numClaves - 1;
            if (esHoja) {
                while (i >= 0 && claves[i] > valor) {
                    claves[i + 1] = claves[i];
                    i--;
                }
                claves[i + 1] = valor;
                numClaves++;
                System.out.println("Insertando... valor " + valor);
            } else {
                while (i >= 0 && claves[i] > valor)
                    i--;
                if (hijos[i + 1].numClaves == (2 * t) - 1) {
                    dividirHijo(i + 1, hijos[i + 1]);
                    if (claves[i + 1] < valor)
                        i++;
                }
                hijos[i + 1].insertarNoLleno(valor);
            }
        }

        private void dividirHijo(int indice, Nodo hijo) {
            Nodo nuevoNodo = new Nodo(t, hijo.esHoja);
            nuevoNodo.numClaves = t - 1;
            for (int j = 0; j < t - 1; j++)
                nuevoNodo.claves[j] = hijo.claves[j + t];
            if (!hijo.esHoja) {
                for (int j = 0; j < t; j++)
                    nuevoNodo.hijos[j] = hijo.hijos[j + t];
            }
            hijo.numClaves = t - 1;
            for (int j = numClaves; j > indice; j--)
                hijos[j + 1] = hijos[j];
            hijos[indice + 1] = nuevoNodo;
            for (int j = numClaves - 1; j >= indice; j--)
                claves[j + 1] = claves[j];
            claves[indice] = hijo.claves[t - 1];
            numClaves++;
        }

        private List<Integer> buscarClave(int clave) {
            int i = 0;
            while (i < numClaves && clave > claves[i])
                i++;
            if (claves[i] == clave)
                return recorrerCamino(clave);
            if (esHoja)
                return null;
            return hijos[i].buscarClave(clave);
        }

        private List<Integer> buscarValor(int valor) {
            int i = 0;
            while (i < numClaves && valor > claves[i])
                i++;
            if (claves[i] == valor)
                return obtenerValores();
            if (esHoja)
                return null;
            return hijos[i].buscarValor(valor);
        }

        private List<Integer> recorrerCamino(int clave) {
            List<Integer> camino = new ArrayList<>();
            int i = 0;
            while (i < numClaves && clave > claves[i]) {
                camino.add(claves[i]);
                i++;
            }
            if (clave == claves[i])
                camino.add(claves[i]);

            if (esHoja)
                return camino;
            return hijos[i].recorrerCamino(clave);
        }

        private List<Integer> obtenerValores() {
            List<Integer> valores = new ArrayList<>();
            for (int i = 0; i < numClaves; i++) {
                valores.add(claves[i]);
            }
            return valores;
        }

        private int obtenerValorMaximo() {
            if (esHoja)
                return claves[numClaves - 1];
            else
                return hijos[numClaves].obtenerValorMaximo();
        }

        
        private void eliminar(int valor) {
            int indice = buscarClaveIndex(valor);
            if (indice < numClaves && claves[indice] == valor) {
                if (esHoja)
                    eliminarEnHoja(indice);
                else
                    eliminarEnNoHoja(indice);
            } else {
                if (esHoja) {
                    System.out.println("El valor " + valor + " no se encuentra en el árbol.");
                    return;
                }
                boolean estaEnUltimoHijo = (indice == numClaves);
                if (hijos[indice].numClaves < t)
                    llenar(indice);
                if (estaEnUltimoHijo && indice > numClaves)
                    hijos[indice - 1].eliminar(valor);
                else
                    hijos[indice].eliminar(valor);
            }
        }

        private void eliminarEnHoja(int indice) {
            for (int i = indice + 1; i < numClaves; i++)
                claves[i - 1] = claves[i];
            numClaves--;
        }

        private void eliminarEnNoHoja(int indice) {
            int valor = claves[indice];
            if (hijos[indice].numClaves >= t) {
                int predecesor = obtenerPredecesor(indice);
                claves[indice] = predecesor;
                hijos[indice].eliminar(predecesor);
            } else if (hijos[indice + 1].numClaves >= t) {
                int sucesor = obtenerSucesor(indice);
                claves[indice] = sucesor;
                hijos[indice + 1].eliminar(sucesor);
            } else {
                fusionar(indice);
                hijos[indice].eliminar(valor);
            }
        }

        private int obtenerPredecesor(int indice) {
            Nodo actual = hijos[indice];
            while (!actual.esHoja)
                actual = actual.hijos[actual.numClaves];
            return actual.claves[actual.numClaves - 1];
        }

        private int obtenerSucesor(int indice) {
            Nodo actual = hijos[indice + 1];
            while (!actual.esHoja)
                actual = actual.hijos[0];
            return actual.claves[0];
        }

        private void llenar(int indice) {
            if (indice != 0 && hijos[indice - 1].numClaves >= t)
                prestarDeAnterior(indice);
            else if (indice != numClaves && hijos[indice + 1].numClaves >= t)
                prestarDeSiguiente(indice);
            else {
                if (indice != numClaves)
                    fusionar(indice);
                else
                    fusionar(indice - 1);
            }
        }

        private void prestarDeAnterior(int indice) {
            Nodo hijo = hijos[indice];
            Nodo hermano = hijos[indice - 1];

            for (int i = hijo.numClaves - 1; i >= 0; i--)
                hijo.claves[i + 1] = hijo.claves[i];

            if (!hijo.esHoja) {
                for (int i = hijo.numClaves; i >= 0; i--)
                    hijo.hijos[i + 1] = hijo.hijos[i];
            }

            hijo.claves[0] = claves[indice - 1];

            if (!hijo.esHoja)
                hijo.hijos[0] = hermano.hijos[hermano.numClaves];

            claves[indice - 1] = hermano.claves[hermano.numClaves - 1];

            hijo.numClaves++;
            hermano.numClaves--;
        }

        private void prestarDeSiguiente(int indice) {
            Nodo hijo = hijos[indice];
            Nodo hermano = hijos[indice + 1];

            hijo.claves[hijo.numClaves] = claves[indice];

            if (!hijo.esHoja)
                hijo.hijos[hijo.numClaves + 1] = hermano.hijos[0];

            claves[indice] = hermano.claves[0];

            for (int i = 1; i < hermano.numClaves; i++)
                hermano.claves[i - 1] = hermano.claves[i];

            if (!hermano.esHoja) {
                for (int i = 1; i <= hermano.numClaves; i++)
                    hermano.hijos[i - 1] = hermano.hijos[i];
            }

            hijo.numClaves++;
            hermano.numClaves--;
        }

        private void fusionar(int indice) {
            Nodo hijo = hijos[indice];
            Nodo hermano = hijos[indice + 1];

            hijo.claves[t - 1] = claves[indice];

            for (int i = 0; i < hermano.numClaves; i++)
                hijo.claves[i + t] = hermano.claves[i];

            if (!hijo.esHoja) {
                for (int i = 0; i <= hermano.numClaves; i++)
                    hijo.hijos[i + t] = hermano.hijos[i];
            }

            for (int i = indice + 1; i < numClaves; i++)
                claves[i - 1] = claves[i];

            for (int i = indice + 2; i <= numClaves; i++)
                hijos[i - 1] = hijos[i];

            hijo.numClaves += hermano.numClaves + 1;
            numClaves--;
        }

        private int buscarClaveIndex(int valor) {
            int indice = 0;
            while (indice < numClaves && claves[indice] < valor)
                indice++;
            return indice;
        }

        private boolean esHoja() {
            return esHoja;
        }

        private void mostrar() {
            int i;
            for (i = 0; i < numClaves; i++) {
                if (!esHoja)
                    hijos[i].mostrar();
                System.out.print(" " + claves[i]);
            }

            if (!esHoja)
                hijos[i].mostrar();
        }
        public Nodo obtenerNodoMinimo() {
            if (esHoja)
                return this;
            else
                return hijos[0].obtenerNodoMinimo();
        }
    }
    
    public Nodo obtenerNodoMinimoRaiz() {
        if (raiz == null) {
            return null;
        } else {
            return raiz.obtenerNodoMinimo();
        }
    }
    
   
    public static void main(String[] args) {
        ArbolB arbol = new ArbolB(3);
        arbol.insertar(3);
        arbol.insertar(8);
        arbol.insertar(12);
        arbol.insertar(15);
        arbol.insertar(18);
        arbol.insertar(21);
        arbol.insertar(25);
        arbol.insertar(28);
        arbol.insertar(32);
        arbol.insertar(36);
        arbol.insertar(40);
        arbol.insertar(45);
        arbol.insertar(48);
        arbol.insertar(50);
        arbol.mostrar();

        System.out.println("\nEliminando 18...");
        arbol.eliminar(18);
        arbol.mostrar();

        System.out.println("\nEliminando 45...");
        arbol.eliminar(45);
        arbol.mostrar();

        System.out.println("\nBuscando nodo con clave 32:");
        List<Integer> nodo32 = arbol.buscarNodoPorClave(32);
        if (nodo32 != null) {
            System.out.println("Nodo encontrado. Claves: " + nodo32);
        } else {
            System.out.println("Nodo no encontrado.");
        }

        System.out.println("\nBuscando nodo con clave 50:");
        List<Integer> nodo50 = arbol.buscarNodoPorClave(50);
        if (nodo50 != null) {
            System.out.println("Nodo encontrado. Claves: " + nodo50);
        } else {
            System.out.println("Nodo no encontrado.");
        }

        System.out.println("\nBuscando nodo con clave 100:");
        List<Integer> nodo100 = arbol.buscarNodoPorClave(100);
        if (nodo100 != null) {
            System.out.println("Nodo encontrado. Claves: " + nodo100);
        } else {
            System.out.println("Nodo no encontrado.");
        }
    }
}







