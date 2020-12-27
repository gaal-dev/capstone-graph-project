package graph.algorithm.girvan_newman;

public class Edge {
    private int v, e;

    public Edge(int v, int e) {
        this.v = v;
        this.e = e;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Edge) {
            return v == ((Edge) obj).v && e == ((Edge) obj).e;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return v * e;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", v, e);
    }
}
