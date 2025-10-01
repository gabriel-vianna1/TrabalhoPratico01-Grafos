import java.util.ArrayList;
import java.util.List;

public class naiveMehod {

    public static void acharPontes(Grafo grafo){

    List<Aresta> arestas = grafo.getArestas();
    List<Aresta> pontes = new ArrayList<>(); 

    boolean isConexo = grafo.isConexo();

    if(!isConexo){
        System.out.println("Grafo desconexo");
        //Se o grafo já é desconexo, não tem necessidade de procurar por pontes.
        return;
    }

    for(Aresta a: arestas){

        grafo.removerAresta(a.u, a.v);

        if(!grafo.isConexo()){
            //Se o grafo passou a ser desconexo, significa que achamos uma ponte
            pontes.add(a);
        }

        grafo.adicionarAresta(a.u, a.v);
         
    }
  
    if(pontes.isEmpty()){
        System.out.println("Não existem pontes no grafo");
    }
    else{
        System.out.println("As seguintes arestas são pontes: "+ pontes);
    }

    }
    
}
