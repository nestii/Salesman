package gui;

import algorithm.Algorithm;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import graph.WeightedGraph;
import graph.edge.WeightedEdge;
import graph.vertex.Vertex;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import utils.Generator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Ewa on 07/06/2015.
 */
public class GraphVisualization implements ActionListener{

    private  JButton calculateBtn;
    private JTextField clients;
    private JTextField magazines;
    private JLabel result;
    private JPanel panel;
    private ListenableUndirectedWeightedGraph<Vertex, WeightedEdge> g;
    private mxGraphComponent mxGraph;

    public void createAndShowGui() {
        JFrame frame = new JFrame("DemoGraph");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBounds(0, 0, 100, 100);

        calculateBtn = new JButton("Calculate");
        calculateBtn.setBounds(10, 10, 100, 50);
        calculateBtn.addActionListener(this);
        JLabel clientsLabel = new JLabel("C: ");
        JLabel magazinesLabel = new JLabel("M: ");
        clients = new JTextField(4);
        clients.setBounds(400, 10, 100, 50);

        magazines = new JTextField(4);
        magazines.setBounds(400, 20, 100, 50);
        JLabel resultLabel = new JLabel("result: ");
        result = new JLabel();

        panel.add(clientsLabel);
        panel.add(clients);
        panel.add(magazinesLabel);
        panel.add(magazines);
        panel.add(resultLabel);
        panel.add(result);
        panel.add(calculateBtn);

        frame.add(panel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private void drawGraph(JPanel panel, WeightedGraph graph, int cost) {

        result.setText(String.valueOf(cost));
        if(g != null) {
            panel.remove(mxGraph);
            panel.repaint();
        }
        g = buildGraph(graph);
        JGraphXAdapter<Vertex, WeightedEdge> graphAdapter =
                new JGraphXAdapter<>(g);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());
        mxGraph = new mxGraphComponent(graphAdapter);
        panel.add(mxGraph);
    }

    public static ListenableUndirectedWeightedGraph<Vertex, WeightedEdge> buildGraph(WeightedGraph graph) {
        ListenableUndirectedWeightedGraph<Vertex, WeightedEdge> g =
                new ListenableUndirectedWeightedGraph<>(WeightedEdge.class);

        SimpleWeightedGraph<Vertex, DefaultWeightedEdge> simpleWeightedGraph = graph.getGraph();
        Set<Vertex> vertices = simpleWeightedGraph.vertexSet();
        List<Vertex> convertedVertices = new ArrayList<>(vertices);

        for(Vertex vertex : vertices) {
            g.addVertex(vertex);
        }

        for (int i = 0; i < convertedVertices.size(); i++) {
            for (int j = i; j < convertedVertices.size(); j++) {
                if (i != j) {
                    for(WeightedEdge edge: WeightedGraph.edges) {
                        g.addEdge(convertedVertices.get(i), convertedVertices.get(j), edge);
                        g.setEdgeWeight(edge, edge.getWeight());
                    }
                }
            }
        }

        return g;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if(source == calculateBtn) {
            panel.repaint();
            WeightedGraph graph = new WeightedGraph(Integer.parseInt(clients.getText()), Integer.parseInt(magazines.getText()));
            Algorithm a = new Algorithm(graph);
            a.beginAlgorithm();
            int cost = a.getTotalTransportCost();

            drawGraph(panel, graph, cost);
        }
    }
}
