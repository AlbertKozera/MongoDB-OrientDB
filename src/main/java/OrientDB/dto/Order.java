package OrientDB.dto;

import java.util.UUID;

public class Order {

    private UUID id;
    private String type_of_order;
    private String order_description;
    private Car car = new Car();
    private PersonData personData = new PersonData();

    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType_of_order() {
        return type_of_order;
    }

    public void setType_of_order(String type_of_order) {
        this.type_of_order = type_of_order;
    }

    public String getOrder_description() {
        return order_description;
    }

    public void setOrder_description(String order_description) {
        this.order_description = order_description;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public PersonData getPersonData() {
        return personData;
    }

    public void setPersonData(PersonData personData) {
        this.personData = personData;
    }

    @Override
    public String toString() {
        return "Zlecenie{" +
                "id='" + getId() + '\'' +
                ", rodzaj zlecenia='" + getType_of_order() + '\'' +
                ", opis zlecenia='" + getOrder_description() + '\'' +
                ", " + getCar().toString() +
                ", " + getPersonData().toString() +
                '}';
    }

}
