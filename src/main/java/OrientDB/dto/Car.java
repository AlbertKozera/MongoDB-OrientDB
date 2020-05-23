package OrientDB.dto;

public class Car {

    private String brand;
    private String model;
    private int production_date;

    public Car() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProduction_date() {
        return production_date;
    }

    public void setProduction_date(int production_date) {
        this.production_date = production_date;
    }

    @Override
    public String toString() {
        return "Samochod{" +
                "marka='" + getBrand() + '\'' +
                ", model='" + getModel() + '\'' +
                ", rok produkcji=" + getProduction_date() +
                '}';
    }

}
