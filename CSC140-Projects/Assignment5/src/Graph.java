import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final List<Vertex> vertices;
    private final List<Edge> edges;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addEdge(Vertex source, Vertex destination, int weight) {
        edges.add(new Edge(source, destination, weight));
    }

    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.source().equals(vertex)) {
                neighbors.add(edge.destination());
            }
        }
        return neighbors;
    }

    public int getWeight(Vertex source, Vertex destination) {
        for (Edge edge : edges) {
            if (edge.source().equals(source) && edge.destination().equals(destination)) {
                return edge.weight();
            }
        }
        return Integer.MAX_VALUE; // No edge exists
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public Vertex getVertex(int index) {
        return vertices.get(index);
    }

    public record Edge(Vertex source, Vertex destination, int weight) {
    }
}
