import graph.WeightedGraph;


/**
 * Created by Ewa on 04/06/2015.
 */
public class Main {

    public static void main(String[] args) {

        WeightedGraph graph = new WeightedGraph(3,2);
        graph.goThroughGraph(graph.getGraph());
    }
}
