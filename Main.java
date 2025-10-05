import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
    // Crie grafos com um número razoável de vértices para ver a diferença de tempo
    int numVertices = 20;
    Grafo grafoEuleriano = FabricaDeGrafos.criarGrafoEuleriano(numVertices);
    Grafo grafoSemiEuleriano = FabricaDeGrafos.criarGrafoSemiEuleriano(numVertices);
    Grafo grafoNaoEuleriano = FabricaDeGrafos.criarGrafoNaoEuleriano(numVertices);

    BuscadorDePontes naive = new NaiveMehod();
    BuscadorDePontes tarjan = new TarjanBridgeAlgorithm();
    
    System.out.println("--- INICIANDO TESTES COM O GRAFO EULERIANO ---");
    System.out.println("Número de Vértices: " + grafoEuleriano.getNumVertices());
    System.out.println("Número de Arestas: " + grafoEuleriano.getNumArestas());

    System.out.println("\n1) Testando com o Buscador de Pontes NAIVE:");
    List<Integer> caminhoNaive = Fleury.FleuryAlgorthm(grafoEuleriano, naive);
    System.out.println("Tamanho do caminho encontrado: " + caminhoNaive.size());
    // Validação básica
    if (!caminhoNaive.isEmpty()) {
        System.out.println("Vértice inicial: " + caminhoNaive.get(0));
        System.out.println("Vértice final: " + caminhoNaive.getLast());
    }

    System.out.println("\n2) Testando com o Buscador de Pontes TARJAN:");
    List<Integer> caminhoTarjan = Fleury.FleuryAlgorthm(grafoEuleriano, tarjan);
    System.out.println("Tamanho do caminho encontrado: " + caminhoTarjan.size());
     if (!caminhoTarjan.isEmpty()) {
        System.out.println("Vértice inicial: " + caminhoTarjan.get(0));
        System.out.println("Vértice final: " + caminhoTarjan.getLast());
    }

    /* 
    System.out.println("\n\n--- TESTANDO GRAFO SEMI-EULERIANO (com Tarjan) ---");
    List<Integer> caminhoSemi = Fleury.FleuryAlgorthm(grafoSemiEuleriano, tarjan);
    System.out.println("Tamanho do caminho: " + caminhoSemi.size());
    if (!caminhoSemi.isEmpty()) {
        System.out.println("Vértice inicial: " + caminhoSemi.get(0));
        System.out.println("Vértice final: " + caminhoSemi.getLast());
    }

    System.out.println("\n\n--- TESTANDO GRAFO NÃO EULERIANO (com Tarjan) ---");
    List<Integer> caminhoNao = Fleury.FleuryAlgorthm(grafoNaoEuleriano, tarjan);
    System.out.println("Resultado: O caminho está vazio? " + caminhoNao.isEmpty()); */

}
}
