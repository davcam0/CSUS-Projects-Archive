public class Vertex implements Comparable<Vertex>{
    private final int id;
    private int distance;
    Vertex predecessor;

    public Vertex(int id) {
        this.id = id;
        this.distance = Integer.MAX_VALUE; // Equivalent to infinity
        this.predecessor = null;
    }

    public int getId() {
        return id;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setPredecessor(Vertex predecessor) {
        this.predecessor = predecessor;
    }


    @Override
    public int compareTo(Vertex other) {
        return Integer.compare(this.distance, other.distance);
    }
}
