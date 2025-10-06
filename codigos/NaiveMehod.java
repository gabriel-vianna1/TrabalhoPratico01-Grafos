package codigos;
import java.util.ArrayList;
import java.util.List;

public class NaiveMehod implements BuscadorDePontes {

    @Override
    public  List<Aresta> findPontes(Grafo grafo){

    List<Aresta> arestas = grafo.getArestas();
    List<Aresta> pontes = new ArrayList<>(); 

    boolean isConexo = grafo.isConexo();

    if(!isConexo){
        System.out.println("Grafo desconexo");
        //Se o grafo já é desconexo, não tem necessidade de procurar por pontes.
        return pontes;
    }

    for(Aresta a: arestas){

        grafo.removerAresta(a.u, a.v);

        if(!grafo.isConexo()){
            //Se o grafo passou a ser desconexo, significa que achamos uma ponte
            pontes.add(a);
        }

        grafo.adicionarAresta(a.u, a.v);
         
    }
    //Vai retornar as arestas ponte, se a lista estiver vazia,significa que não existem pontes no grafo  
    return pontes;

    }
    
}
