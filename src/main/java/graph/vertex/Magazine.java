package graph.vertex;

import java.util.List;

import car.Car;

/**
 * Created by Ewa on 04/06/2015.
 */
public class Magazine extends Vertex {

    private double availableGoods;
    private List<Car> carsFleet;

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
    
    public List<Car> getCars() {
    	return this.carsFleet;
    }
    
    public void setCars(List<Car> cars) {
    	this.carsFleet = cars;
    }
}
