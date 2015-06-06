package algorithm;

import graph.WeightedGraph;
import graph.vertex.Client;
import graph.vertex.Magazine;
import graph.vertex.Vertex;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;

import car.Car;

public class Algorithm {
	
	private WeightedGraph graph;
	private int totalTransportCost;
	
	public Algorithm(WeightedGraph graph) {
		this.graph = graph;
		this.totalTransportCost = 0;
	}

	public void iterate(WeightedGraph graph) {
		List<Car> cars = graph.getAllCars();
		List<Client> clients = graph.getAllClients();
		List<Vertex> gonnaBeVisitedInThisIteration = new ArrayList<>();
		
		for (Car c : cars) {
			List<Vertex> possibilitiesToVisitForCurrentCar = graph
					.getPossibleVerticesToVisit(c, graph.getGraph());
			//possibilitiesToVisitForCurrentCar.removeAll(gonnaBeVisitedInThisIteration);
			
			Vertex vertexToVisit = c.chooseDestination(possibilitiesToVisitForCurrentCar, graph);
			
			if (vertexToVisit != null) {
				List<DefaultWeightedEdge> road = graph.findShortestPath(c.getVertex(), vertexToVisit, graph.getGraph());
				List<Vertex> vertexesOnRoad = new ArrayList<>();
				vertexesOnRoad.add(graph.getGraph().getEdgeSource(road.get(road.size() - 1)));
				road.stream().forEach(edge -> vertexesOnRoad.add(graph.getGraph().getEdgeTarget(((DefaultWeightedEdge)edge))));
				gonnaBeVisitedInThisIteration.addAll(vertexesOnRoad);
				Vertex firstVertexOnTheRoad = null;
				firstVertexOnTheRoad = graph.getGraph().getEdgeSource(road.get(0));
				if(firstVertexOnTheRoad == c.getVertex()) {
					firstVertexOnTheRoad = graph.getGraph().getEdgeTarget(road.get(0));
				}
				
				c.setVertex(firstVertexOnTheRoad); //moze sie posypac TODO sprawdzic czy sie nie sypie ;]	
				if(firstVertexOnTheRoad instanceof Magazine) {
					if(c.getCurrentStage() + ((Magazine)firstVertexOnTheRoad).getAvailableGoods() <= c.getCapacity() ) {
						c.setCurrentStage(c.getCurrentStage() + (int) ((Magazine)firstVertexOnTheRoad).getAvailableGoods());
						((Magazine)firstVertexOnTheRoad).setAvailableGoods(0);
					} else {
						int resourcesFromMagaizneCount = c.getCapacity() - c.getCurrentStage();
						((Magazine)firstVertexOnTheRoad).setAvailableGoods(((Magazine)firstVertexOnTheRoad).getAvailableGoods() - resourcesFromMagaizneCount );
						c.setCurrentStage(c.getCapacity());
					}
				} else {
					Client visitatedClient = ((Client)firstVertexOnTheRoad);
					c.setCurrentStage((int) (c.getCurrentStage() - visitatedClient.getNeed()));
					visitatedClient.setNeed(0);
				}
				this.totalTransportCost += graph.getGraph().getEdgeWeight(road.get(0));
			} //jak jest null to auto pauzuje
		}
	}
	
	public void beginAlgorithm() {
		int iterations = 0 ;
		while(!graph.isEverythingDelivered(graph.getGraph())) {
			iterate(this.graph);
			iterations++;
		}
		for(Car c : graph.getAllCars()) {
			totalTransportCost += graph.getGraph().getEdgeWeight(graph.getGraph().getEdge(c.getVertex(), c.getMagazine()));
		}
		System.out.println("done after: "+iterations+" iterations, with total transport cost: "+this.totalTransportCost);
	}
}
