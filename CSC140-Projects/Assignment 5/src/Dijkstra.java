import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Dijkstra {
    public static void dijkstra(Graph graph, Vertex source) {
        source.setDistance(0);

        PriorityQueue<Vertex> queue = new PriorityQueue<>(); // The PriorityQueue class in Java uses a binary heap to maintain the elements.
        queue.add(source);

        while (!queue.isEmpty()) {
            Vertex u = queue.poll();
            for (Vertex v : graph.getNeighbors(u)) {
                int weight = graph.getWeight(u, v);
                int newDist = u.getDistance() + weight;
                if (newDist < v.getDistance()) {
                    queue.remove(v);  // Remove old entry if it exists
                    v.setDistance(newDist);
                    v.setPredecessor(u);
                    queue.add(v);  // Insert the updated vertex
                }
            }
        }
    }

    public static void printGraph(Graph graph, String outputFileName) {
        try (PrintWriter writer = new PrintWriter(outputFileName)) {
            writer.print("Vertex   ");
            for (Vertex v : graph.getVertices()) {
                writer.print(v.getId() + "\t");
            }
            writer.println();

            writer.print("Distance ");
            for (Vertex v : graph.getVertices()) {
                if (v.getDistance() == Integer.MAX_VALUE) {
                    writer.print("INF\t");
                } else {
                    writer.print(v.getDistance() + "\t");
                }
            }
            writer.println();
        } catch (FileNotFoundException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }
}
