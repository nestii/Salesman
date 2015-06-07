package graph.edge;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Created by Ewa on 07/06/2015.
 */
public class WeightedEdge extends DefaultWeightedEdge {
    private int weight;
    public String toString() {
        return String.valueOf(getWeight());
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
