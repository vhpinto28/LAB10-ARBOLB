import java.util.List;

public class TestArbolB {

    public static void imprimirCamino(List<Integer> camino) {
        if (camino == null) {
            System.out.println("El elemento no existe en el árbol.");
        } else {
            System.out.println("Camino recorrido:");
            for (int i = 0; i < camino.size(); i++) {
                System.out.print(camino.get(i));
                if (i != camino.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int t = 3;
        ArbolB arbolB = new ArbolB(t);

        int[] valoresUno = {20, 10, 50, 30, 40};
        for (int i = 0; i < valoresUno.length; i++) {
            int valor = valoresUno[i];
            System.out.println("Insertando... valor " + valor);
            arbolB.insertar(valor);
            List<Integer> camino = arbolB.recorridoCamino(valor);
            imprimirCamino(camino);
        }

        System.out.println("ESTADO ARBOL B");
        arbolB.mostrar();
        System.out.println();

        System.out.println("Insertando... valor 60");
        arbolB.insertar(60);
        System.out.println("ESTADO ARBOL B");
        arbolB.mostrar();
        System.out.println();

        System.out.println("Insertando... valor 80");
        arbolB.insertar(80);
        System.out.println("Insertando... valor 70");
        arbolB.insertar(70);
        System.out.println("Insertando... valor 90");
        arbolB.insertar(90);

        System.out.println("ESTADO ARBOL B");
        arbolB.mostrar();
        System.out.println();

        int valorBuscado = 80;
        System.out.println("\nBuscando el nodo con el valor " + valorBuscado + " en el árbol B:");
        List<Integer> clavesEncontradas = arbolB.buscarNodoYRetornarClaves(valorBuscado);
        if (clavesEncontradas.isEmpty()) {
            System.out.println("No se encontró un nodo con el valor " + valorBuscado + ".");
        } else {
            System.out.println(clavesEncontradas);
        }

        int valorEliminar = 30;
        System.out.println("Eliminando... valor " + valorEliminar);
        arbolB.eliminar(valorEliminar);
        List<Integer> caminoEliminar = arbolB.recorridoCamino(valorEliminar);
        imprimirCamino(caminoEliminar);

        System.out.println("");
        int valorMaximo = arbolB.obtenerValorMaximo();
        System.out.println("El valor máximo del árbol B es: " + valorMaximo);

        System.out.println("");
        System.out.println("Nodo mínimo de la raíz del árbol B: " + arbolB.obtenerNodoMinimoRaiz());
        
        System.out.println("");
        System.out.println("-- FIN --");
    }
}







