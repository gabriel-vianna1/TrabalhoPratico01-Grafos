package codigos;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Fonte que usamos como inspiração para a implementação
 * https://www.geeksforgeeks.org/dsa/fleurys-algorithm-for-printing-eulerian-path/
 */


public class Fleury {
    public static List<Integer> FleuryAlgorthm(Grafo grafo, BuscadorDePontes buscador){

        long inicio = System.nanoTime();

         int comeco = 1; // Se houver alguma vértice de grau ímpar começar por ele, se não começar por 1.
         int contadorImpares = 0;
         List<Integer> verticesGrauImpar = new ArrayList<>();
         Grafo copia = grafo.copiar();
         List<Integer> caminho = new ArrayList<>();
         //Verifica o grau de todos os vértices
         for(int i = 1; i <= grafo.getNumVertices(); i++){
            int grau = grafo.getGrau(i);
            
            if(grau % 2 != 0){
                contadorImpares++;
                verticesGrauImpar.add(i);
            }
         }

         if(contadorImpares > 2 || !grafo.isConexo()){
            return caminho; 
        }
        else if(contadorImpares == 2){
            comeco = verticesGrauImpar.get(0);
            System.out.println("Tipo : Grafo Semi-Euleriano");
        }
        else if(contadorImpares == 0){
            System.out.println("Tipo : Grafo Euleriano");
        }

        caminho.add(comeco);

        while(copia.getNumArestas() > 0){
           
            int verticeAtual = caminho.getLast();
            int proximoVertice = -1; //Valor inválido somenta para indicar "vazio"
            List<Integer> vizinhos = copia.getVizinhos(verticeAtual);



            if(copia.getGrau(verticeAtual) > 1){
                List<Aresta> pontes = buscador.findPontes(copia);
                Set<Aresta> pontesSet = new HashSet<>(pontes);
        
                for(int i = 0; i < vizinhos.size(); i++){
                    int candidato = vizinhos.get(i);
                    Aresta arestaCandidata = new Aresta(verticeAtual, candidato);
                    boolean isPonte;

                   isPonte = pontesSet.contains(arestaCandidata);

                    if(!isPonte){
                        proximoVertice = candidato;
                        break;
                    } 
                }
                if(proximoVertice == -1){
                    //Isso indica que todos os vizinho são pontes, então pega a primeira para facilitar.
                    proximoVertice = vizinhos.get(0);
                }
             }else{
                //Nesse caso o vérticeAtual só tem 1 vizinho, então basta seguir o caminho
                proximoVertice = vizinhos.get(0);
             }
        //Caminha para o vértice remove a aresta. 
        caminho.add(proximoVertice);
        copia.removerAresta(verticeAtual, proximoVertice);   
            }

            long fim = System.nanoTime();

            double duracaoMs = (fim - inicio) / 1_000_000.0;
            
            System.out.println("Tempo de execução: " + duracaoMs + "ms");

            return caminho;
    }
   
}
