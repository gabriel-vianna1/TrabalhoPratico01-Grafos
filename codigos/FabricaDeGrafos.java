package codigos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class FabricaDeGrafos {

    private static final Random random = new Random();

    /**
     * Cria um grafo simples aleatório com uma densidade "média" de arestas.
     * A densidade é controlada por uma probabilidade interna.
     *
     * @param V O número de vértices.
     * @return Um Grafo simples e conexo (na maioria dos casos).
     */
    public static Grafo criarGrafoSimples(int V) {
        if (V <= 0) return new Grafo(V);
        double p = 2.5 * Math.log(V) / V;
        if (p > 1.0) p = 1.0;
        return GeradorDeGrafos.simples(V, p);
    }

    /**
     * Cria um Grafo que GARANTIDAMENTE é Euleriano.
     * (Conexo e todos os vértices com grau par).
     *
     * @param V O número de vértices (deve ser >= 3 para um resultado interessante).
     * @return Um Grafo Euleriano.
     */
    public static Grafo criarGrafoEuleriano(int V) {
        if (V <= 2) {
            return GeradorDeGrafos.ciclo(V);
        }
        Grafo g = GeradorDeGrafos.ciclo(V);
        int numTriangulos = random.nextInt(V / 2 + 1);
        for (int i = 0; i < numTriangulos; i++) {
            int u = random.nextInt(V) + 1;
            int v = random.nextInt(V) + 1;
            int w = random.nextInt(V) + 1;
            if (u == v || v == w || u == w) {
                continue;
            }

    
boolean arestaUV_existe = g.getVizinhos(u).contains(v);
boolean arestaVW_existe = g.getVizinhos(v).contains(w);
boolean arestaWU_existe = g.getVizinhos(w).contains(u);


if (!arestaUV_existe && !arestaVW_existe && !arestaWU_existe) {
    g.adicionarAresta(u, v);
    g.adicionarAresta(v, w);
    g.adicionarAresta(w, u);
}

        }
        
        return g;
    }

    
    
    public static Grafo criarGrafoSemiEuleriano(int V) {
        if (V < 2) {
            // Não é possível ter 2 vértices de grau ímpar com menos de 2 vértices.
            return new Grafo(V);
        }

        // 1. Começa com uma base Euleriana garantida.
        Grafo g = criarGrafoEuleriano(V);

        // 2. Procura dois vértices que não são vizinhos para adicionar uma aresta.
        List<Integer> vertices = new ArrayList<>();
        for (int i = 1; i <= V; i++) vertices.add(i);
        Collections.shuffle(vertices); // Embaralha para não testar sempre os mesmos pares

        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                int u = vertices.get(i);
                int v = vertices.get(j);

                // Se u e v NÃO são vizinhos, adiciona a aresta e termina.
                if (!g.getVizinhos(u).contains(v)) {
                    g.adicionarAresta(u, v);
                    return g; // Retorna o grafo modificado
                }
            }
        }

        
        return GeradorDeGrafos.caminho(V);
    }

   
    public static Grafo criarGrafoNaoEuleriano(int V) {
        if (V <= 1) {
            return new Grafo(V);
        }
        Grafo g = criarGrafoSimples(V);
        boolean temGrauImpar = false;
        for (int i = 1; i <= V; i++) {
            if (g.getGrau(i) % 2 != 0) {
                temGrauImpar = true;
                break;
            }
        }
        if (!temGrauImpar) {
            List<Integer> vertices = new ArrayList<>();
            for (int i = 1; i <= V; i++) vertices.add(i);
            Collections.shuffle(vertices);
            for (int i = 0; i < V; i++) {
                boolean arestaAdicionada = false;
                for (int j = i + 1; j < V; j++) {
                    int u = vertices.get(i);
                    int v = vertices.get(j);
                    if (!g.getVizinhos(u).contains(v)) {
                        g.adicionarAresta(u, v);
                        arestaAdicionada = true;
                        break;
                    }
                }
                if (arestaAdicionada) break;
            }
        }
        return g;
    }
}