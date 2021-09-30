package com.company.database;

import com.company.customer.Customer;
import com.company.vehicle.Vehicle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class DBConnection {

    private List<Customer> myCustomers = new ArrayList<Customer>();
    private List<Vehicle> myVehicles = new ArrayList<Vehicle>();

    public static void printDatabase(String table) {
        try{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                , "MartinStoller");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + table);

        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        while (resultSet.next()) {
            for(int i = 1; i <= columnsNumber; i++) {
                System.out.print(resultSet.getString(i) + " ");
            }
            System.out.println();
        }

        resultSet.close();
        statement.close();
        connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editDbQuery(int custOrVehic) {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Above is the current Database. Press 'c' to continue. Press 'e' to edit the database.");
        String continueOrEdit = scanner2.nextLine();
        System.out.println(continueOrEdit);

        if (continueOrEdit.equals("c")) {
        }
        else if (continueOrEdit.equals("e")) {
            System.out.println("Type in SQL command:");
            String query = scanner2.nextLine();
            editDb(custOrVehic, query);

        } else {

            System.out.println("Input not valid. Type either 'c' to continue or 'e' to edit database");
            editDbQuery(custOrVehic);
        }
    }

    public static void editDb(int custOrVehic, String query) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.execute();

            connection.close();
            preparedStmt.close();

        System.out.println("Command executed: Database now looks like this:");
    } catch (Exception e) {
        e.printStackTrace();
    }
        if (custOrVehic == 0){
            printDatabase("customers");
        } else{
            printDatabase("vehicles");
        }
        editDbQuery(custOrVehic);
    }


    public static void addCustomerToDb(Customer customer) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");

            String query = "INSERT INTO `haeger-db`.`customers`\n" +
                    "(`id`,\n" +
                    "`firstname`,\n" +
                    "`lastname`,\n" +
                    "`gender`,\n" +
                    "`street`,\n" +
                    "`housenumber`,\n" +
                    "`postalcode`,\n" +
                    "`city`)\n" +
                    "VALUES(\n" +
                    customer.id + ",\n" +
                    "'" + customer.firstName + "',\n" +
                    "'" + customer.lastName + "',\n" +
                    "'" + customer.gender + "',\n" +
                    "'" + customer.street + "',\n" +
                    customer.houseNr + ",\n" +
                    customer.postalCode + ",\n" +
                    "'" + customer.city + "'\n" +
                    ");\n";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.execute();
            System.out.println("Customer with ID " + customer.id + " imported successfully.");
            connection.close();
            preparedStmt.close();
        } catch (SQLIntegrityConstraintViolationException es) {
            System.out.println("Customer with ID " + customer.id + " is already in database. No import.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addVehicleToDb(Vehicle vehicle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");

            String query = "INSERT INTO `haeger-db`.`vehicles`\n" +
                    "(`id`,\n" +
                    "`type`,\n" +
                    "`brand`,\n" +
                    "`name`,\n" +
                    "`power`,\n" +
                    "`price`)\n" +
                    "VALUES(\n" +
                    vehicle.id + ",\n" +
                    "'" + vehicle.type + "',\n" +
                    "'" + vehicle.brand + "',\n" +
                    "'" + vehicle.name + "',\n" +
                    vehicle.power + ",\n" +
                    vehicle.price + "\n" +
                    ")\n";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.execute();
            System.out.println("Vehicle with ID " + vehicle.id + " got imported successfully.");
            connection.close();
            preparedStmt.close();

        } catch (SQLIntegrityConstraintViolationException es) {
            System.out.println("Vehicle with ID " + vehicle.id + " is already in database. No import.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseXmlFile(String location, int custOrVehic) throws IOException, ParserConfigurationException {
        // get the factory and document builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            // parse using builder to get DOM representation of the XML file
            Document dom = db.parse(location);
            parseDocument(dom, custOrVehic);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void parseDocument(Document dom, int custOrVehic) {
        // get the root element
        Element docEle = dom.getDocumentElement();

        // get a nodelist of elements
        if (custOrVehic == 1) {
            NodeList nl = docEle.getElementsByTagName("fahrzeug");
            if (nl != null && nl.getLength() > 0) {
                for (int i = 0; i < nl.getLength(); i++) {

                    // get each element of "fahrzeuge"
                    Element el = (Element) nl.item(i);

                    // get the vehicle/customer object
                    Vehicle e = getVehicle(el);

                    // add it to list
                    myVehicles.add(e);
                }
            }
        } else {
            NodeList nl = docEle.getElementsByTagName("kunde");
            if (nl != null && nl.getLength() > 0) {
                for (int i = 0; i < nl.getLength(); i++) {

                    // get each element of "kunde"
                    Element el = (Element) nl.item(i);

                    // get the vehicle/customer object
                    Customer e = getCustomer(el);

                    // add it to list
                    myCustomers.add(e);
                }
            }
        }
    }

    /**
     * I take a kunde element and read the values in, create a Customer object and return it
     */
    private Customer getCustomer(Element empEl) {

        // for each <kunde> element get text or int values of
        int id = getIntValue(empEl, "id");
        String firstName = getTextValue(empEl, "vorname");
        String lastName = getTextValue(empEl, "nachname");
        String gender = getTextValue(empEl, "geschlecht");
        String street = getTextValue(empEl, "strasse");
        String houseNr = getTextValue(empEl, "hausnr");
        int postalCode = getIntValue(empEl, "plz");
        String city = getTextValue(empEl, "ort");

        // Create a new Customer object with the value read from the xml nodes
        Customer e = new Customer(id, firstName, lastName, gender, street, houseNr, postalCode, city);

        return e;
    }

    private Vehicle getVehicle(Element empEl) {

        // for each <fahrzeug> element get text or int values
        int id = getIntValue(empEl, "id");
        String type = getTextValue(empEl, "fahrzeugtyp");
        String brand = getTextValue(empEl, "hersteller");
        String name = getTextValue(empEl, "fahrzeugbezeichnung");
        int power = getIntValue(empEl, "leistung");
        int price = getIntValue(empEl, "verkaufspreis");

        // Create a new Vehicle Object with the value read from the xml nodes
        Vehicle e = new Vehicle(id, type, brand, name, power, price);

        return e;
    }

    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    private int getIntValue(Element ele, String tagName) {
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    public void printData(int custOrVehic) {
        if (custOrVehic == 0) {
            System.out.println("Customers found: '" + myCustomers.size() + "'.");

            Iterator<Customer> it = myCustomers.iterator();
            while (it.hasNext()) {
                System.out.println(it.next().toString());
            }
            System.out.println();
        } else {
            System.out.println("Vehicles found: '" + myVehicles.size() + "'.");

            Iterator<Vehicle> it = myVehicles.iterator();
            while (it.hasNext()) {
                System.out.println(it.next().toString());
            }
            System.out.println();
        }
    }

    public void updateCustomerDb() throws IOException, ParserConfigurationException {
        // database doesn´t allow for duplicate IDs, so I can just import all Objects from xml files
        Iterator<Customer> it = myCustomers.iterator();
        while (it.hasNext()) {
            DBConnection.addCustomerToDb(it.next());
        }
    }

    public void updateVehicleDb() throws IOException, ParserConfigurationException {
        // database doesn´t allow for duplicate IDs, so I can just import all Objects from xml files
        Iterator<Vehicle> it = myVehicles.iterator();
        while (it.hasNext()) {
            DBConnection.addVehicleToDb(it.next());
        }
    }

    public List<String> listFilesForFolder(final File folder) {
        List<String> xmlLocations = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                xmlLocations.add(fileEntry.getName());
            }
        }
        return xmlLocations;
    }
}
