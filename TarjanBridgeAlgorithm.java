import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Esta classe foi ADAPTADA para funcionar com a sua classe Grafo.
 * Ela agora trabalha com vértices indexados de 1 a N.
 */
public class TarjanBridgeAlgorithm {

    private final Grafo grafo;
    private final boolean[] visited;
    private final int[] discovery, low, parent;
    private final List<int[]> pontes;
    private int timer;
    private final int vertices;

    private TarjanBridgeAlgorithm(Grafo grafo) {
        this.grafo = grafo;

        this.vertices = grafo.getNumVertices(); 
        this.timer = 0;
        this.pontes = new ArrayList<>();

        this.visited = new boolean[vertices + 1];
        this.discovery = new int[vertices + 1];
        this.low = new int[vertices + 1];
        this.parent = new int[vertices + 1];
        Arrays.fill(parent, -1);
    }

    public static List<int[]> findpontes(Grafo grafo) {
        TarjanBridgeAlgorithm finder = new TarjanBridgeAlgorithm(grafo);
        return finder.run();
    }

    private List<int[]> run() {
        for (int i = 1; i <= vertices; i++) {
            if (!visited[i]) {
                bridgeDFS(i);
            }
        }
        return pontes;
    }

    private void bridgeDFS(int u) {
        visited[u] = true;
        discovery[u] = low[u] = ++timer;

        for (int v : grafo.getVizinhos(u)) { 
            if (!visited[v]) {
                parent[v] = u;
                bridgeDFS(v);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > discovery[u]) {
                    pontes.add(new int[]{Math.min(u, v), Math.max(u, v)});
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], discovery[v]);
            }
        }
    }
}