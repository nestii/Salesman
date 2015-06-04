package graph;

import graph.vertex.Vertex;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Created by Ewa on 04/06/2015.
 */
public interface Graph {

    void addVertex(Vertex vertex);
    void addEdge(Vertex v1, Vertex v2, DefaultWeightedEdge weightedEdge);
}
