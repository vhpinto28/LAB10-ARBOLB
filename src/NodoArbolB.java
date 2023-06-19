import java.util.ArrayList;
import java.util.List;

public class NodoArbolB {
    private int n; // número de claves almacenadas en el nodo
    private boolean leaf; // Si el nodo es hoja (nodo hoja=true; nodo interno=false)
    private int[] key; // almacena las claves en el nodo
    private NodoArbolB[] child; // arreglo con referencias a los hijos

    // Constructores
    public NodoArbolB(int t) {
        n = 0;
        leaf = true;
        key = new int[(2 * t) - 1];
        child = new NodoArbolB[2 * t];
    }

    public List<Integer> recorridoCamino(int k) {
        List<Integer> camino = new ArrayList<>();
        recorridoCaminoAux(k, camino);
        return camino;
    }

    public void showNode() {
        for (int i = 0; i < n; i++) {
            System.out.print(key[i] + " ");
        }

        if (!leaf) {
            for (int i = 0; i <= n; i++) {
                child[i].showNode();
            }
        }
    }

    public void imprimir() {
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            if (i < n - 1) {
                System.out.print(key[i] + " | ");
            } else {
                System.out.print(key[i]);
            }
        }
        System.out.print("]");
    }

    public int find(int k) {
        for (int i = 0; i < n; i++) {
            if (key[i] == k) {
                return i;
            }
        }
        return -1;
    }

    private void recorridoCaminoAux(int k, List<Integer> camino) {
        int i = 0;
        while (i < n && k > key[i]) {
            camino.add(key[i]);
            i++;
        }
        if (!leaf) {
            child[i].recorridoCaminoAux(k, camino);
        }
    }
}

