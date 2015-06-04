package graph;

import car.Car;
import graph.vertex.Client;
import graph.vertex.Magazine;
import graph.vertex.Vertex;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import utils.Generator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ewa on 04/06/2015.
 */
public class WeightedGraph implements Graph {

    private SimpleWeightedGraph<Vertex, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>((DefaultWeightedEdge.class));

    private static final int MIN_CLIENT_NEEDS = 10;
    private static final int MAX_CLIENT_NEEDS = 40;
    private static final int MIN_MAGAZINE_GOODS = 20;
    private static final int MAX_MAGAZINE_GOODS = 70;
    private static final int MIN_COST = 10;
    private static final int MAX_COST = 100;

    public SimpleWeightedGraph<Vertex, DefaultWeightedEdge> getGraph() {
        return graph;
    }

    public WeightedGraph(int noOfClients, int noOfMagazines) {
        buildGraph(noOfClients, noOfMagazines);
    }

    @Override
    public void addVertex(Vertex vertex) {
        graph.addVertex(vertex);
    }

    @Override
    public void addEdge(Vertex v1, Vertex v2, DefaultWeightedEdge weightedEdge) {
        graph.addEdge(v1, v2, weightedEdge);

    }

    private void generateClients(int noOfClients) {
        for (int i = 0; i < noOfClients; i++) {
            graph.addVertex(new Client(Generator.generateRandomInt(MIN_CLIENT_NEEDS, MAX_CLIENT_NEEDS)));
        }
    }

    private void generateMagazines(int noOfMagazines) {
        for (int i = 0; i < noOfMagazines; i++) {
            graph.addVertex(new Magazine(Generator.generateRandomInt(MIN_MAGAZINE_GOODS, MAX_MAGAZINE_GOODS)));
        }
    }

    private void buildGraph(int noOfClients, int noOfMagazines) {
        generateClients(noOfClients);
        generateMagazines(noOfMagazines);

        Set<Vertex> vertices = graph.vertexSet();
        List<Vertex> convertedVertices = new ArrayList<>(vertices);

        for (int i = 0; i < convertedVertices.size(); i++) {
            for (int j = i; j < convertedVertices.size(); j++) {
                if (i != j) {
                    DefaultWeightedEdge weightedEdge = new DefaultWeightedEdge();
                    graph.setEdgeWeight(weightedEdge, Generator.generateRandomInt(MIN_COST, MAX_COST));
                    graph.addEdge(convertedVertices.get(i), convertedVertices.get(j), weightedEdge);
                }
            }
        }
    }

    public void goThroughGraph(SimpleWeightedGraph<Vertex, DefaultWeightedEdge> graph) {
        Iterator<Vertex> iter = new DepthFirstIterator<>(graph);
        Vertex vertex;
        while (iter.hasNext()) {
            vertex = iter.next();
            System.out.println(
                    "Vertex " + vertex.toString() + " is connected to: "
                            + graph.edgesOf(vertex).toString());
        }
    }

    public List findShortestPath(Vertex v1, Vertex v2, SimpleWeightedGraph<Vertex, DefaultWeightedEdge> graph) {

        return DijkstraShortestPath.findPathBetween(graph, v1, v2);
    }


    public boolean isEverythingDelivered(SimpleWeightedGraph<Vertex, DefaultWeightedEdge> graph) {

        Set<Vertex> vertices = graph.vertexSet();
        return vertices.stream().allMatch(vertex -> (vertex instanceof Client && ((Client) vertex).getNeed() == 0));
    }

    // TODO: consider if we have to visit clients, whose needs are <= our current stage -> it means that only one car can deliver needs to this particular client -> more than one car cannot deliver goods to one client
    public boolean isVertexPossibleToVisit(Vertex vertex, Car car) {

        return (vertex instanceof Client) && ((Client) vertex).getNeed() <= car.getCurrentStage();
    }

    public List<Vertex> getPossibleVerticesToVisit(Car car, SimpleWeightedGraph<Vertex, DefaultWeightedEdge> graph) {

        Set<Vertex> vertices = graph.vertexSet();
        return vertices.stream().filter(vertex -> isVertexPossibleToVisit(vertex, car)).collect(Collectors.toList());
    }


    public boolean isMagazineNotEmpty(Vertex vertex) {

        return vertex instanceof Magazine && ((Magazine) vertex).getAvailableGoods() != 0;
    }




}
