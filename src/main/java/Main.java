import algorithm.Algorithm;
import graph.WeightedGraph;
import gui.GraphVisualization;

import javax.swing.*;

/**
 * Created by Ewa on 04/06/2015.
 */
public class Main {

    public static void main(String[] args) {

//        WeightedGraph graph = new WeightedGraph(6,2);
        GraphVisualization visualization = new GraphVisualization();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                visualization.createAndShowGui();
            }
        });

    }
}
