import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String DEFAULT_INPUT = "input.txt";
        final String DEFAULT_OUTPUT = "output.txt";
        final int DEFAULT_SRC = 0;

        String inputFileName = (args.length > 0) ? args[0] : DEFAULT_INPUT;
        String outputFileName = (args.length > 1) ? args[1] : DEFAULT_OUTPUT;

        try (Scanner scanner = new Scanner(new File(inputFileName))) {
            System.out.println("Input file found: ");
            int numVertices = scanner.nextInt();
            int numEdges = scanner.nextInt();

            Graph graph = new Graph();

            for (int i = 0; i < numVertices; i++) {
                graph.addVertex(new Vertex(i));
            }

            for (int i = 0; i < numEdges; i++) {
                int source = scanner.nextInt();
                int destination = scanner.nextInt();
                int weight = scanner.nextInt();
                graph.addEdge(graph.getVertex(source), graph.getVertex(destination), weight);
            }

            Vertex source = graph.getVertex(DEFAULT_SRC);
            Dijkstra.dijkstra(graph, source);
            Dijkstra.printGraph(graph, outputFileName);
            System.out.println("Output file complete");

        } catch (FileNotFoundException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}
