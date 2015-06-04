package graph.vertex;

/**
 * Created by Ewa on 04/06/2015.
 */
public class Client extends Vertex {

    private double need;

    public Client(double need) {
        this.need = need;
    }


    public double getNeed() {
        return need;
    }

    public void setNeed(double need) {
        this.need = need;
    }

    public String toString() {
        return "client need: " + this.getNeed();
    }
}
