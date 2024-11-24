/**
 Author: David Cameron (302449225)
 Date Created: 11/24/2024
 Project Name: CSC140 - Assignment 5: Graph Algorithms
 Class: CSUS / FALL 2024/ CSC 140-01: Advanced Algorithm Design and Analysis

 Description:
 This program implements Dijkstra's algorithm to find the shortest path from a source vertex
 to all other vertices in a weighted directed graph. The input graph is provided through a file,
 and the output consists of the shortest distance from the source to each vertex.

 Usage:
 By default, the program reads input from "input.txt" and writes output to "output.txt".
 Command-line arguments can be provided to specify custom input and output files:
 If one argument is provided, it is treated as the input file path.
 If two arguments are provided, the second argument is treated as the output file path.

 Input Format:
 The first line of the input file should contain two integers: the number of vertices and the number of edges.
 Each subsequent line represents an edge with three integers: source vertex, destination vertex, and weight.

 Output Format:
 The output file contains the shortest distance from the source vertex (vertex 0) to all other vertices.
 Unreachable vertices are marked as "INF".

 Classes:
 Main: Handles input/output and invokes Dijkstra's algorithm.
 Graph: Represents the graph structure with vertices and edges.
 Vertex: Represents a vertex with its properties like distance and predecessor.
 Dijkstra: Implements the Dijkstra's algorithm logic.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {
    public static void main(String[] args) {
        final String DEFAULT_INPUT = "input.txt";
        final String DEFAULT_OUTPUT = "output.txt";
        final int DEFAULT_SRC = 0;

        // If no command-line arguments are provided, use the default input file "input.txt".
        // If one argument is provided, treat it as the custom input file path.
        // If two arguments are provided, treat the second one as the custom output file path.

        String inputFileName = (args.length > 0) ? args[0] : DEFAULT_INPUT;
        String outputFileName = (args.length > 1) ? args[1] : DEFAULT_OUTPUT;

        try (Scanner scanner = new Scanner(new File(inputFileName))) {
            //System.out.println("Reading Input File: " + inputFileName);
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
            //System.out.println("Starting dijkstra");
            dijkstra(graph, source);
            //System.out.println("Finished dijkstra");
            printGraph(graph, outputFileName);
            //System.out.println("Output: " + outputFileName);

        } catch (FileNotFoundException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }

    }

    private static void dijkstra(Graph graph, Vertex source) {
        source.setDistance(0);

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        // The PriorityQueue in Java uses a binary heap to efficiently maintain elements with the lowest distance at the
        // head. Instead of adding all vertices to the queue initially, vertices are added and removed dynamically as
        // they are visited. This approach is more efficient and appropriate given Java's PriorityQueue limitations,
        // which do not support direct key updates.
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

    private static void printGraph(Graph graph, String outputFileName) {
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

    private static class Graph {
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


    private static class Vertex implements Comparable<Vertex>{
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
}

