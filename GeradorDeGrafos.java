import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

/**
 * A classe GeradorDeGrafos fornece métodos estáticos para criar
 * vários tipos de grafos, adaptada para usar a implementação específica
 * das classes Grafo.java e Aresta.java do usuário.
 * (Esta classe não precisou de alterações lógicas)
 */
public class GeradorDeGrafos {

    private static final Random random = new Random();

    // Esta classe não pode ser instanciada
    private GeradorDeGrafos() {
    }

    /**
     * Retorna um grafo simples aleatório com V vértices e E arestas.
     * @param V O número de vértices.
     * @param E O número de arestas.
     * @return Um Grafo simples.
     */
    public static Grafo simples(int V, int E) {
        if (E > (long) V * (V - 1) / 2) throw new IllegalArgumentException("Muitas arestas");
        if (E < 0) throw new IllegalArgumentException("Número de arestas não pode ser negativo");

        Grafo G = new Grafo(V);
        Set<Aresta> setDeArestas = new HashSet<>();
        int arestasAdicionadas = 0;

        while (arestasAdicionadas < E) {
            // Gera vértices no intervalo [1, V]
            int v = random.nextInt(V) + 1;
            int w = random.nextInt(V) + 1;

            Aresta e = new Aresta(v, w);
            if (v != w && !setDeArestas.contains(e)) {
                setDeArestas.add(e);
                G.adicionarAresta(v, w);
                arestasAdicionadas++;
            }
        }
        G.setNumArestas(arestasAdicionadas); // Define o número de arestas no final
        return G;
    }

    /**
     * Retorna um grafo aleatório (modelo Erdos-Renyi).
     * Uma aresta entre quaisquer dois vértices existe com probabilidade p.
     * @param V O número de vértices.
     * @param p A probabilidade de uma aresta existir.
     * @return Um Grafo simples.
     */
    public static Grafo simples(int V, double p) {
        if (p < 0.0 || p > 1.0)
            throw new IllegalArgumentException("A probabilidade deve estar entre 0 e 1");

        Grafo G = new Grafo(V);
        int arestasAdicionadas = 0;
        for (int v = 1; v <= V; v++) {
            for (int w = v + 1; w <= V; w++) {
                if (random.nextDouble() < p) {
                    G.adicionarAresta(v, w);
                    arestasAdicionadas++;
                }
            }
        }
        G.setNumArestas(arestasAdicionadas);
        return G;
    }

    /**
     * Retorna um grafo caminho com V vértices.
     * @param V O número de vértices.
     * @return Um Grafo em forma de caminho.
     */
    public static Grafo caminho(int V) {
        Grafo G = new Grafo(V);
        List<Integer> vertices = new ArrayList<>();
        for (int i = 1; i <= V; i++) {
            vertices.add(i);
        }
        Collections.shuffle(vertices);
        for (int i = 0; i < V - 1; i++) {
            G.adicionarAresta(vertices.get(i), vertices.get(i + 1));
        }
        G.setNumArestas(V > 0 ? V - 1 : 0);
        return G;
    }

    /**
     * Retorna um grafo ciclo com V vértices.
     * @param V O número de vértices.
     * @return Um Grafo em forma de ciclo.
     */
    public static Grafo ciclo(int V) {
        Grafo G = new Grafo(V);
        List<Integer> vertices = new ArrayList<>();
        for (int i = 1; i <= V; i++) {
            vertices.add(i);
        }
        Collections.shuffle(vertices);
        for (int i = 0; i < V - 1; i++) {
            G.adicionarAresta(vertices.get(i), vertices.get(i + 1));
        }
        if (V > 1) {
            G.adicionarAresta(vertices.get(V - 1), vertices.get(0));
        }
        G.setNumArestas(V > 0 ? V : 0);
        return G;
    }

    /**
     * Retorna uma árvore binária completa com V vértices.
     * @param V O número de vértices.
     * @return Um Grafo em forma de árvore binária.
     */
    public static Grafo arvoreBinaria(int V) {
        Grafo G = new Grafo(V);
        List<Integer> vertices = new ArrayList<>();
        for (int i = 1; i <= V; i++) {
            vertices.add(i);
        }
        Collections.shuffle(vertices);
        // O loop começa em 1 pois o nó 0 (raiz) não tem pai.
        for (int i = 1; i < V; i++) {
            // Conecta o nó i ao seu pai (i-1)/2
            G.adicionarAresta(vertices.get(i), vertices.get((i - 1) / 2));
        }
        G.setNumArestas(V > 0 ? V - 1 : 0);
        return G;
    }

    /**
     * Retorna uma árvore aleatória com V vértices usando uma sequência de Prüfer.
     * @param V O número de vértices.
     * @return Um Grafo em forma de árvore aleatória.
     */
    public static Grafo arvoreAleatoria(int V) {
        Grafo G = new Grafo(V);
        if (V <= 1) return G;

        // Gera a sequência de Prüfer com rótulos de 1 a V
        int[] prufer = new int[V - 2];
        for (int i = 0; i < V - 2; i++) {
            prufer[i] = random.nextInt(V) + 1;
        }

        // Calcula o grau de cada vértice. Usa um array de tamanho V+1 para indexação de 1 a V.
        int[] degree = new int[V + 1];
        for (int v = 1; v <= V; v++) {
            degree[v] = 1;
        }
        for (int i = 0; i < V - 2; i++) {
            degree[prufer[i]]++;
        }

        // Usa uma fila de prioridade para encontrar as folhas (grau 1)
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int v = 1; v <= V; v++) {
            if (degree[v] == 1) {
                pq.add(v);
            }
        }
        
        // Constrói a árvore
        for (int i = 0; i < V - 2; i++) {
            int v = pq.poll(); // Pega a folha de menor índice
            G.adicionarAresta(v, prufer[i]);
            degree[v]--; // Desnecessário, pois já foi removido
            degree[prufer[i]]--;
            if (degree[prufer[i]] == 1) {
                pq.add(prufer[i]);
            }
        }
        // Adiciona a última aresta entre os dois vértices restantes na fila
        G.adicionarAresta(pq.poll(), pq.poll());
        
        G.setNumArestas(V - 1);
        return G;
    }
}