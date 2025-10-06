package codigos;

import java.util.List;

public interface BuscadorDePontes {
 
    List<Aresta> findPontes(Grafo grafo);
    
}
