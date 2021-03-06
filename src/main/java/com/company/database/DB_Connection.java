package com.company.database;

import com.company.businesslayer.app_data.AppData;
import com.company.businesslayer.customer.Customer;
import com.company.businesslayer.vehicle.Vehicle;
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

public class DB_Connection {
    private final String url = "jdbc:mysql://localhost:3306/haeger-db";
    private String user = "root";
    private String password = "MartinStoller";
    private String table;
    private String command;

    //Getter and Setter:
    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String new_user) {
        this.user = new_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String new_pw) {
        this.password = new_pw;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String new_table) {
        this.table = new_table;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String new_command) {
        this.command = new_command;
    }

    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = createNewConnection();
        }
        return conn;
    }


    private static Connection createNewConnection() {
        // return a connection to the DB
        DB_Connection db_connection = new DB_Connection();
        try {
            Connection connection = DriverManager.getConnection(db_connection.getUrl(), db_connection.getUser()
                    , db_connection.getPassword());
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void printDatabase(String table) {
        // print an entire table of the DB
        try{
            Connection connection = createNewConnection();
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

    public static void editDb(int custOrVehic, String query) {
        //takes a query (SQL command) as input and executes it
        try{
            Connection connection = createNewConnection();
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
    }

    public static void addCustomerToDb(Customer customer) {
        //takes customer object and adds it to db
        try{
            Connection connection = createNewConnection();
            // get SQL command for adding customer and subsequently execute it:
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
                    customer.getID() + ",\n" +
                    "'" + customer.getFirstName() + "',\n" +
                    "'" + customer.getLastName() + "',\n" +
                    "'" + customer.getGender() + "',\n" +
                    "'" + customer.getStreet() + "',\n" +
                    customer.getHouseNr() + ",\n" +
                    customer.getPostalCode() + ",\n" +
                    "'" + customer.getCity() + "'\n" +
                    ");\n";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.execute();
            System.out.println("Customer with ID " + customer.getID() + " imported successfully.");
            connection.close();
            preparedStmt.close();
        } catch (SQLIntegrityConstraintViolationException es) {
            System.out.println("Customer with ID " + customer.getID() + " is already in database. No import.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addVehicleToDb(Vehicle vehicle) {
        //takes vehicle object and adds it to db
        try {
            Connection connection = createNewConnection();
            // create SQL command and subsequently execute it:
            String query = "INSERT INTO `haeger-db`.`vehicles`\n" +
                    "(`id`,\n" +
                    "`type`,\n" +
                    "`brand`,\n" +
                    "`name`,\n" +
                    "`power`,\n" +
                    "`price`)\n" +
                    "VALUES(\n" +
                    vehicle.getId() + ",\n" +
                    "'" + vehicle.getType() + "',\n" +
                    "'" + vehicle.getBrand() + "',\n" +
                    "'" + vehicle.getName() + "',\n" +
                    vehicle.getPower() + ",\n" +
                    vehicle.getPrice() + "\n" +
                    ")\n";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.execute();
            System.out.println("Vehicle with ID " + vehicle.getId() + " got imported successfully.");
            connection.close();
            preparedStmt.close();

        } catch (SQLIntegrityConstraintViolationException es) {
            System.out.println("Vehicle with ID " + vehicle.getId() + " is already in database. No import.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseXmlFile(String location, int custOrVehic) throws IOException, ParserConfigurationException {
        //TODO: move to business layer???

        // get the factory and document builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            // parse using builder to get DOM representation of the XML file at location
            Document dom = db.parse(location);
            // call parseDocument() on the DOM representation of the XML file, which adds each element in the file to
            // a summarizing data list
            parseDocument(dom, custOrVehic);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void parseDocument(Document dom, int custOrVehic) {
        //TODO: move to business layer???
        //Takes dom representation of an xml file and adds each element of that file to the list of existing customers
        // or vehicles

        // get the root element
        Element docEle = dom.getDocumentElement();

        // get a nodelist of elements
        if (custOrVehic == 1) {
            NodeList nl = docEle.getElementsByTagName("fahrzeug");
            if (nl != null && nl.getLength() > 0) {
                for (int i = 0; i < nl.getLength(); i++) {

                    // get each element of "fahrzeuge"
                    Element el = (Element) nl.item(i);

                    // create the vehicle object from the fahrzeug element
                    Vehicle e = getVehicle(el);

                    // add it to list
                    List<Vehicle> tmpVehicleList = AppData.getAppData().getMyVehicles();
                    tmpVehicleList.add(e);
                    AppData.getAppData().setMyVehicles(tmpVehicleList);
                }
            }
        } else {
            NodeList nl = docEle.getElementsByTagName("kunde");
            if (nl != null && nl.getLength() > 0) {
                for (int i = 0; i < nl.getLength(); i++) {

                    // get each element of "kunde"
                    Element el = (Element) nl.item(i);

                    // create the customer object from the kunde element
                    Customer e = getCustomer(el);

                    // add it to list
                    List<Customer> tmpCustList = AppData.getAppData().getMyCustomers();
                    tmpCustList.add(e);
                    AppData.getAppData().setMyCustomers(tmpCustList);
                }
            }
        }
    }

    private static Customer getCustomer(Element empEl) {
/**
 * I take a kunde element and read the values in, create a Customer object and return it
 */
//TODO: move to business layer???
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

    private static Vehicle getVehicle(Element empEl) {
        //TODO: move to business layer???
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

    private static String getTextValue(Element ele, String tagName) {
        //TODO: move to business layer???

        // get String value from element in xml file
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }

    private static int getIntValue(Element ele, String tagName) {
        //TODO: move to business layer???
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    public static void printData(int custOrVehic) {
        //TODO: move to business layer???
        if (custOrVehic == 0) {
            System.out.println("Customers found: '" + AppData.getAppData().getMyCustomers().size() + "'.");

            Iterator<Customer> it = AppData.getAppData().getMyCustomers().iterator();
            while (it.hasNext()) {
                System.out.println(it.next().toString());
            }
            System.out.println();
        } else {
            System.out.println("Vehicles found: '" + AppData.getAppData().getMyVehicles().size() + "'.");

            Iterator<Vehicle> it = AppData.getAppData().getMyVehicles().iterator();
            while (it.hasNext()) {
                System.out.println(it.next().toString());
            }
            System.out.println();
        }
    }

    public static void updateCustomerDb() throws IOException, ParserConfigurationException {
        // iterate through list of existing customers and add them to DB
        // database doesn??t allow for duplicate IDs, so I can just import all Objects from xml files
        Iterator<Customer> it = AppData.getAppData().getMyCustomers().iterator();
        while (it.hasNext()) {
            DB_Connection.addCustomerToDb(it.next());
        }
    }

    public static void updateVehicleDb() throws IOException, ParserConfigurationException {
        // iterate through list of existing vehicles and add them to DB
        // database doesn??t allow for duplicate IDs, so I can just import all Objects from xml files
        Iterator<Vehicle> it = AppData.getAppData().getMyVehicles().iterator();
        while (it.hasNext()) {
            DB_Connection.addVehicleToDb(it.next());
        }
    }

    public static List<String> listFilesForFolder(final File folder) {
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

