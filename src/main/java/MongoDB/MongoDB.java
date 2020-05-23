package MongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;

public class MongoDB {

    private static Scanner input = new Scanner(System.in);

    private static MongoClient mongoClient;

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
        MongoDatabase db = getConnectionWithDatabase("albert", "student", "localhost", 27017, "serwis_samochodowy");
        MongoCollection<Document> collection = db.getCollection("zlecenia");

        String id;
        int from, to;
        while (true) {
            System.out.println("\nS E R W I S - S A M O C H O D O W Y");
            System.out.println("1. Wyswietl wszystkie zlecenia");
            System.out.println("2. Wyswietl konkretne zlecenie");
            System.out.println("3. Wyswietl zlecenia dla samochodow wyprodukowanych w okreslonych latach");
            System.out.println("4. Dodaj zlecenie");
            System.out.println("5. Zaktualizuj zlecenie");
            System.out.println("6. Usun zlecenie");
            System.out.println("7. Przetwarzanie danych [sortuj malejaco]");
            System.out.println("8. Przetwarzanie danych [sortuj rosnaco]");
            System.out.println("9. Zakoncz prace klastra");
            int mode = input.nextInt();
            input.nextLine();
            switch (mode) {
                case 1:
                    getAllOrders(collection);
                    break;
                case 2:
                    System.out.println("Wprowadz ID zlecenia do wyswietlenia");
                    id = input.nextLine();
                    getOrderById(collection, id);
                    break;
                case 3:
                    System.out.println("Podaj najstarszy rocznik do wyswietlenia");
                    from = input.nextInt();
                    input.nextLine();
                    System.out.println("Podaj najnowszy rocznik do wyswietlenia");
                    to = input.nextInt();
                    input.nextLine();
                    getOrdersByProductionDate(collection, from, to);
                    break;
                case 4:
                    collection.insertOne(addOrder());
                    break;
                case 5:
                    System.out.println("Wprowadz ID zlecenia do aktualizacji");
                    id = input.nextLine();
                    Document document = new Document();
                    while (document != null) {
                        document = updateOrder();
                        if (document != null)
                            collection.updateOne(eq("_id", new ObjectId(id)), document);
                    }
                    break;
                case 6:
                    System.out.println("Wprowadz ID zlecenia do usuniecia");
                    id = input.nextLine();
                    collection.deleteOne(eq("_id", new ObjectId(id)));
                    break;
                case 7:
                    sortOrders(collection, -1);
                    break;
                case 8:
                    sortOrders(collection, 1);
                    break;
                case 9:
                    mongoClient.close();
                    System.exit(0);
            }
        }
    }

    private static void getAllOrders(MongoCollection<Document> collection) {
        for (Document document : collection.find())
            System.out.println(document.toJson());
    }

    private static void getOrderById(MongoCollection<Document> collection, String id) {
        Document document = collection.find(eq("_id", new ObjectId(id))).first();
        System.out.println(document.toJson());
    }

    private static void getOrdersByProductionDate(MongoCollection<Document> collection, int from, int to) {
        BasicDBObject betweenQuery = new BasicDBObject();
        betweenQuery.put("dane_samochodu.rocznik", new BasicDBObject("$gte", from).append("$lte", to));
        for (Document document : collection.find(betweenQuery))
            System.out.println(document.toJson());
    }

    private static Document addOrder() {
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

        return new Document()
                .append("rodzaj_zlecenia", type_of_order)
                .append("opis_zlecenia", order_description)
                .append("dane_samochodu", new Document()
                        .append("marka", brand)
                        .append("model", model)
                        .append("rocznik", production_date))
                .append("dane_osobowe", new Document()
                        .append("imie", name)
                        .append("nazwisko", last_name)
                        .append("numer_telefonu", phone_number));
    }

    private static Document updateOrder() {
        String type_of_order, order_description, brand, model, name, last_name;
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
        System.out.println("9. zakoncz edycje");
        int mode = input.nextInt();
        input.nextLine();
        switch (mode) {
            case 1:
                System.out.println("Wprowadz rodzaj zlecenia [naprawa] [wymiana] [diagnoza]");
                type_of_order = input.nextLine();
                return new Document("$set", new Document("rodzaj_zlecenia", type_of_order));
            case 2:
                System.out.println("Wprowadz opis zlecenia");
                order_description = input.nextLine();
                return new Document("$set", new Document("opis_zlecenia", order_description));
            case 3:
                System.out.println("- marka -");
                brand = input.nextLine();
                return new Document("$set", new Document("dane_samochodu.marka", brand));
            case 4:
                System.out.println("- model -");
                model = input.nextLine();
                return new Document("$set", new Document("dane_samochodu.model", model));
            case 5:
                System.out.println("- rocznik -");
                production_date = input.nextInt();
                input.nextLine();
                return new Document("$set", new Document("dane_samochodu.rocznik", production_date));
            case 6:
                System.out.println("- imie -");
                name = input.nextLine();
                return new Document("$set", new Document("dane_osobowe.imie", name));
            case 7:
                System.out.println("- nazwisko -");
                last_name = input.nextLine();
                return new Document("$set", new Document("dane_osobowe.nazwisko", last_name));
            case 8:
                System.out.println("- numer_telefonu -");
                phone_number = input.nextInt();
                input.nextLine();
                return new Document("$set", new Document("dane_osobowe.numer_telefonu", phone_number));
            case 9:
                return null;
            default:
                return null;
        }
    }

    private static void sortOrders(MongoCollection<Document> collection, int sort) {
        for (Document document : collection.find().sort(new BasicDBObject("dane_samochodu.rocznik", sort)))
            System.out.println(document.toJson());
    }

    private static MongoDatabase getConnectionWithDatabase(String user, String password, String host, int port, String database) {
        String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
        MongoClientURI uri = new MongoClientURI(clientURI);
        mongoClient = new MongoClient(uri);
        return mongoClient.getDatabase(database);
    }

}