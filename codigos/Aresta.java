package codigos;


import java.util.Objects;

public class Aresta {
    final int u;
    final int v;

    public Aresta(int u, int v) {
        // PADRONIZAÇÃO: Garante que a aresta (u,v) seja sempre igual a (v,u)
        // O menor vértice é sempre armazenado em 'u'
        this.u = Math.min(u, v);
        this.v = Math.max(u, v);
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aresta aresta = (Aresta) o;
        return u == aresta.u && v == aresta.v;
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v);
    }

    @Override
    public String toString() {
        return "(" + u + ", " + v + ")";
    }

}