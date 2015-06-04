package graph.vertex;

/**
 * Created by Ewa on 04/06/2015.
 */
public class Magazine extends Vertex {

    private double availableGoods;

    public Magazine(double goods) {
        this.availableGoods = goods;
    }

    public double getAvailableGoods() {
        return availableGoods;
    }

    public void setAvailableGoods(double availableGoods) {
        this.availableGoods = availableGoods;
    }

    public String toString() {
        return "Magazine goods: " + this.getAvailableGoods();
    }
}
