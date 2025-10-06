import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fonte: https://www.finalroundai.com/articles/finding-bridges-in-graph
 */
public class TarjanBridgeAlgorithm implements BuscadorDePontes {

    private Grafo grafo;
    private boolean[] visited;
    private int[] discovery, low, parent;
    private List<Aresta> pontes;
    private int timer;
    private int vertices;

    public TarjanBridgeAlgorithm() {
        // Construtor vazio
    }

    @Override
    public List<Aresta> findPontes(Grafo grafo) {
        this.grafo = grafo;
        this.vertices = grafo.getNumVertices();
        this.timer = 0;
        this.pontes = new ArrayList<>();
        this.visited = new boolean[vertices + 1];
        this.discovery = new int[vertices + 1];
        this.low = new int[vertices + 1];
        this.parent = new int[vertices + 1];
        Arrays.fill(parent, -1);

        for (int i = 1; i <= vertices; i++) {
            if (!visited[i] && grafo.getGrau(i) > 0) { // só inicia DFS se o vértice tiver arestas
                bridgeDFS(i);
            }
        }

        return pontes;
    }

    private void bridgeDFS(int u) {
        visited[u] = true;
        discovery[u] = low[u] = ++timer;

        List<Integer> vizinhos = grafo.getVizinhos(u); // evita múltiplas chamadas
        for (int v : vizinhos) {
            if (!visited[v]) {
                parent[v] = u;
                bridgeDFS(v);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > discovery[u]) {
                    pontes.add(new Aresta(u, v));
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], discovery[v]);
            }
        }
    }
}