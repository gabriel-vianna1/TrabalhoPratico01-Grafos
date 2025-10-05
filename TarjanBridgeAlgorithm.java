import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**

 * Nossa implementação foi adaptada da seguitne fonte

 * https://www.finalroundai.com/articles/finding-bridges-in-graph

 */ 
public class TarjanBridgeAlgorithm implements BuscadorDePontes {

    // Os atributos continuam aqui para serem acessados por todos os métodos da classe.
    private Grafo grafo;
    private boolean[] visited;
    private int[] discovery, low, parent;
    private List<Aresta> pontes;
    private int timer;
    private int vertices;

    /**
     * PASSO 1: O construtor agora é público e vazio.
     * Sua única função é permitir a criação do objeto "buscador".
     * Ex: BuscadorDePontes tarjan = new TarjanBridgeAlgorithm();
     */
    public TarjanBridgeAlgorithm() {
        // O construtor fica vazio. A inicialização ocorrerá no método findPontes.
    }

    /**
     * PASSO 2: O método findPontes agora faz todo o trabalho.
     * Ele inicializa as variáveis, executa o algoritmo e retorna o resultado.
     */
    @Override
    public List<Aresta> findPontes(Grafo grafo) {
        // 1. INICIALIZAÇÃO: A lógica que estava no construtor antigo foi movida para cá.
        // Isso garante que cada chamada a findPontes comece com um estado limpo.
        this.grafo = grafo;
        this.vertices = grafo.getNumVertices();
        this.timer = 0;
        this.pontes = new ArrayList<>();
        this.visited = new boolean[vertices + 1];
        this.discovery = new int[vertices + 1];
        this.low = new int[vertices + 1];
        this.parent = new int[vertices + 1];
        Arrays.fill(parent, -1);

        // 2. EXECUÇÃO: O loop que estava no método run() agora está aqui.
        for (int i = 1; i <= vertices; i++) {
            if (!visited[i]) {
                bridgeDFS(i);
            }
        }
        
        // 3. RETORNO: Retorna a lista de pontes encontradas.
        return pontes;
    }

    /**
     * O método recursivo bridgeDFS permanece o mesmo, pois ele depende dos atributos
     * da classe que são inicializados no findPontes.
     */
    private void bridgeDFS(int u) {
        visited[u] = true;
        discovery[u] = low[u] = ++timer;

        for (int v : grafo.getVizinhos(u)) {
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

    // O método run() não é mais necessário, pois sua lógica foi incorporada ao findPontes.
}