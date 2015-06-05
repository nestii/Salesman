package car;

import graph.vertex.Vertex;

/**
 * Created by Ewa on 04/06/2015.
 */
public class Car {

    private int capacity;
    private int currentStage;
    private Vertex vertex;

    public Car(int capacity, int currentStage, Vertex vertex) {
        this.capacity = capacity;
        this.currentStage = currentStage;
        this.vertex = vertex;
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


}
