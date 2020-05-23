package OrientDB;

import OrientDB.dto.Car;
import OrientDB.dto.Order;
import OrientDB.dto.PersonData;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.db.object.ODatabaseObject;
import com.orientechnologies.orient.object.db.OrientDBObject;
import java.util.Scanner;
import java.util.UUID;

public class OrientDB {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        UUID id;
        int from, to;
        while (true) {
            OrientDBObject orientDB = new OrientDBObject("remote:localhost", OrientDBConfig.defaultConfig());
            ODatabaseObject db = orientDB.open("serwis_samochodowy","root", "root");
            db.getEntityManager().registerEntityClasses("OrientDB.dto");
            System.out.println("\nS E R W I S - S A M O C H O D O W Y");
            System.out.println("1. Wyswietl wszystkie zlecenia");
            System.out.println("2. Wyswietl konkretne zlecenie");
            System.out.println("3. Wyswietl zlecenia dla samochodow wyprodukowanych w okreslonych latach");
            System.out.println("4. Dodaj zlecenie");
            System.out.println("5. Zaktualizuj zlecenie");
            System.out.println("6. Usun zlecenie");
            System.out.println("7. Przetwarzanie danych [zmiana imion na male litery]");
            System.out.println("8. Przetwarzanie danych [zmiana imion na duze litery]");
            System.out.println("9. Zakoncz prace klastra");
            int mode = input.nextInt();
            input.nextLine();
            Order orderTmp = new Order();
            switch (mode) {
                case 1:
                    getAllOrders(db);
                    break;
                case 2:
                    System.out.println("Wprowadz ID zlecenia do wyswietlenia");
                    id = UUID.fromString(input.nextLine());
                    getOrderById(db, id);
                    break;
                case 3:
                    System.out.println("Podaj najstarszy rocznik do wyswietlenia");
                    from = input.nextInt();
                    input.nextLine();
                    System.out.println("Podaj najnowszy rocznik do wyswietlenia");
                    to = input.nextInt();
                    input.nextLine();
                    break;
                case 4:
                    addOrder(db);
                    break;
                case 5:
                    System.out.println("Wprowadz ID zlecenia do aktualizacji");
                    id = UUID.fromString(input.nextLine());
                    for (Order order : db.browseClass(Order.class)){
                        if(order.getId().equals(id)) {
                            orderTmp = order;
                        }
                    }
                    editOrder(db, orderTmp);
                    break;
                case 6:
                    System.out.println("Wprowadz ID zlecenia do usuniecia");
                    id = UUID.fromString(input.nextLine());
                    deleteOrderById(db, id);
                    break;
                case 7:
                    changeOfCase(db, true);
                    break;
                case 8:
                    changeOfCase(db, false);
                    break;
                case 9:
                    db.close();
                    System.exit(0);
            }
        }

    }

    private static void addOrder(ODatabaseObject db) {
        String type_of_order, order_description, brand, model, name, last_name;
        int production_date, phone_number;
        System.out.println("Wprowadz rodzaj zlecenia [naprawa] [wymiana] [diagnoza]");
        type_of_order = input.nextLine();
        System.out.println("Wprowadz opis zlecenia");
        order_description = input.nextLine();
        System.out.println("Wprowadz dane na temat samochodu");
        System.out.println("- marka -");
        brand = input.nextLine();
        System.out.println("- model -");
        model = input.nextLine();
        System.out.println("- rocznik -");
        production_date = input.nextInt();
        input.nextLine();
        System.out.println("Wprowadz dane osobowe zleceniodawcy");
        System.out.println("- imie -");
        name = input.nextLine();
        System.out.println("- nazwisko -");
        last_name = input.nextLine();
        System.out.println("- numer telefonu -");
        phone_number = input.nextInt();
        input.nextLine();

        Car car = db.newInstance(Car.class);
        PersonData personData = db.newInstance(PersonData.class);
        Order order = db.newInstance(Order.class);

        car.setBrand(brand);
        car.setModel(model);
        car.setProduction_date(production_date);

        personData.setName(name);
        personData.setLast_name(last_name);
        personData.setPhone_number(phone_number);

        order.setId(UUID.randomUUID());
        order.setType_of_order(type_of_order);
        order.setOrder_description(order_description);
        order.setCar(car);
        order.setPersonData(personData);

        db.save(order);
        db.close();
    }

    private static void editOrder(ODatabaseObject db, Order order) {
        String type_of_order, order_description, brand, model,name, last_name;
        int production_date, phone_number;
        System.out.println("Wybierz pole do edycji");
        System.out.println("1. rodzaj zlecenia");
        System.out.println("2. opis_zlecenia");
        System.out.println("   dane samochodu");
        System.out.println("3. marka");
        System.out.println("4. model");
        System.out.println("5. rocznik");
        System.out.println("   dane osobowe");
        System.out.println("6. imie");
        System.out.println("7. nazwisko");
        System.out.println("8. numer telefonu");
        int mode = input.nextInt();
        input.nextLine();
        switch (mode) {
            case 1:
                System.out.println("Wprowadz rodzaj zlecenia [naprawa] [wymiana] [diagnoza]");
                type_of_order = input.nextLine();
                order.setType_of_order(type_of_order);
                db.save(order);
                db.close();
                break;
            case 2:
                System.out.println("Wprowadz opis zlecenia");
                order_description = input.nextLine();
                order.setOrder_description(order_description);
                db.save(order);
                db.close();
                break;
            case 3:
                System.out.println("- marka -");
                brand = input.nextLine();
                order.getCar().setBrand(brand);
                db.save(order);
                db.close();
                break;
            case 4:
                System.out.println("- model -");
                model = input.nextLine();
                order.getCar().setModel(model);
                db.save(order);
                db.close();
                break;
            case 5:
                System.out.println("- rocznik -");
                production_date = input.nextInt();
                input.nextLine();
                order.getCar().setProduction_date(production_date);
                db.save(order);
                db.close();
                break;
            case 6:
                System.out.println("- imie -");
                name = input.nextLine();
                order.getPersonData().setName(name);
                db.save(order);
                db.close();
                break;
            case 7:
                System.out.println("- nazwisko -");
                last_name = input.nextLine();
                order.getPersonData().setLast_name(last_name);
                db.save(order);
                db.close();
                break;
            case 8:
                System.out.println("- numer_telefonu -");
                phone_number = input.nextInt();
                input.nextLine();
                order.getPersonData().setPhone_number(phone_number);
                db.save(order);
                db.close();
                break;
        }
    }

    private static void getAllOrders(ODatabaseObject db) {
        for (Order order : db.browseClass(Order.class)){
            System.out.println(order.toString());
        }
    }

    private static void getOrderById(ODatabaseObject db, UUID id) {
        for (Order order : db.browseClass(Order.class)){
            if(order.getId().equals(id)) {
                System.out.println(order.toString());
            }
        }
    }

    private static void changeOfCase(ODatabaseObject db, boolean toLowerCase) {
        for (Order order : db.browseClass(Order.class)){
            if(toLowerCase){
                order.getPersonData().setName(order.getPersonData().getName().toLowerCase());
                db.save(order);
            }
            if(!toLowerCase) {
                order.getPersonData().setName(order.getPersonData().getName().toUpperCase());
                db.save(order);
            }
        }
        db.close();
    }

    private static void deleteOrderById(ODatabaseObject db, UUID id) {
        for (Order order : db.browseClass(Order.class)){
            if(order.getId().equals(id)) {
                db.delete(order.getCar());
                db.delete(order.getPersonData());
                db.delete(order);
            }
        }
    }

}
