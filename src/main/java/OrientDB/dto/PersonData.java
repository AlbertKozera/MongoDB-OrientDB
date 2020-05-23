package OrientDB.dto;

public class PersonData {

    private String name;
    private String last_name;
    private int phone_number;

    public PersonData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Dane osobowe{" +
                "imie='" + getName() + '\'' +
                ", nazwisko='" + getLast_name() + '\'' +
                ", numer telefonu=" + getPhone_number() +
                '}';
    }

}
