package car;

import graph.WeightedGraph;
import graph.vertex.Client;
import graph.vertex.Magazine;
import graph.vertex.Vertex;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Created by Ewa on 04/06/2015.
 */
public class Car {

    private int capacity;
    private int currentStage;
    private Vertex vertex;
    private Magazine myMagazine;

    public Car(int capacity, int currentStage, Vertex vertex, Magazine myMagazine) {
        this.capacity = capacity;
        this.currentStage = currentStage;
        this.vertex = vertex;
        this.myMagazine = myMagazine;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }
    
    public Magazine getMagazine() {
    	return this.myMagazine;
    }
    
    public Vertex chooseDestination(List<Vertex> listOfVertexes, WeightedGraph graph) {
    	boolean backToMagazine = listOfVertexes.stream().allMatch(vertex -> ((Vertex)vertex) instanceof Client && ((Client)vertex).getNeed() > currentStage );
    	if(backToMagazine) {
    		Magazine bestMagazine = null;
    		double currentBestRoad = Double.POSITIVE_INFINITY;
    		for(Magazine m : graph.getAllFreeMagazines(this)) {
    			List<Vertex> road = graph.findShortestPath(this.vertex, m, graph.getGraph());
    			int newPotentialShortestRoad = graph.calculeteRoadBetween(road);
    			if(newPotentialShortestRoad < currentBestRoad) {
    				bestMagazine = m;
    			}
    		}
			return bestMagazine;
		} else {
			Vertex currentBestSibling = null;
			double currentBestRoad = Double.POSITIVE_INFINITY;
			for(Vertex v : listOfVertexes) {
				List<Vertex> road = new ArrayList<Vertex>();
				List listFromDijkstra = graph.findShortestPath(this.vertex, v, graph.getGraph());
				DefaultWeightedEdge firstEdge = (DefaultWeightedEdge) listFromDijkstra.get(listFromDijkstra.size()-1);
				
				road.add(graph.getGraph().getEdgeSource(firstEdge));
				road.add(graph.getGraph().getEdgeTarget(firstEdge));
				if(road.size() > 1 ) {
					for(int i = listFromDijkstra.size()-2 ; i >= 0 ; ) {
                        DefaultWeightedEdge nextEdge = (DefaultWeightedEdge) listFromDijkstra.get(i);
						road.add(graph.getGraph().getEdgeTarget(nextEdge));
						i--;
					}
				}
				int newPotentialShortestRoad = graph.calculeteRoadBetween(road);
    			if(newPotentialShortestRoad < currentBestRoad) {
    				currentBestSibling = v;
    				currentBestRoad = newPotentialShortestRoad;
    			}
			}
			return currentBestSibling;
		}	
    }


}
