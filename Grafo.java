import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {
    private final int numVertices;
    private int numArestas;
    private final ArrayList<ArrayList<Integer>> listaAdjacencia;

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.listaAdjacencia = new ArrayList<>();

        // Inicializa as listas para cada vértice
        for (int i = 0; i <= numVertices; i++) {
            this.listaAdjacencia.add(new ArrayList<>());
        }
    }

    public void adicionarAresta(int u, int v) {
        // Adiciona v à lista de vizinhos de u
        this.listaAdjacencia.get(u).add(v);
        // Adiciona u à lista de vizinhos de v
        this.listaAdjacencia.get(v).add(u);
    }

    public void removerAresta(int u, int v) {
 
    boolean removeuV = this.listaAdjacencia.get(u).remove(Integer.valueOf(v));
    
    boolean removeuU = this.listaAdjacencia.get(v).remove(Integer.valueOf(u));

    if (removeuV && removeuU) {
        this.numArestas--;
    }
}

    public int getGrau(int vertice) {
        return this.listaAdjacencia.get(vertice).size();
    }

    public ArrayList<Integer> getVizinhos(int vertice) {
        return this.listaAdjacencia.get(vertice);
    }
    
    public int getNumVertices() {
        return this.numVertices;
    }

     private void setNumArestas(int numArestas) {
        this.numArestas = numArestas;
    }

    public List<Aresta> getArestas() {
    // Cria um Set que irá ignorar duplicatas automaticamente
    Set<Aresta> arestasSet = new HashSet<>();

    for (int verticeU = 1; verticeU <= this.numVertices; verticeU++) {
        for (int verticeV : this.getVizinhos(verticeU)) {
            arestasSet.add(new Aresta(verticeU, verticeV));
        }
    }
    return new ArrayList<>(arestasSet);
}

    public static Grafo carregarDeArquivo(String path) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        
        String linha = br.readLine();
        if (linha == null) {
            throw new IOException("O arquivo está vazio ou em formato inválido.");
        }
        String[] partes = linha.trim().split("\\s+");
        int numVertices = Integer.parseInt(partes[0]);
        int numArestasDeclaradas = Integer.parseInt(partes[1]);


        Grafo grafo = new Grafo(numVertices);

        grafo.setNumArestas(numArestasDeclaradas);

        while ((linha = br.readLine()) != null) {
            String[] partesAresta = linha.trim().split("\\s+");
            
            if (partesAresta.length >= 2) {
                int u = Integer.parseInt(partesAresta[0]);
                int v = Integer.parseInt(partesAresta[1]);
                
                grafo.adicionarAresta(u, v);
            }
        }

        return grafo;
    }
}

}