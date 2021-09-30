package com.company.main;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.company.customer.Customer;
import com.company.lettercreation.LetterCreation;
import com.company.vehicle.Vehicle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.company.xml_location_input.Xml_location_input;
import com.company.myjdbc.MyJDBC;

public class Main {

    private List<Customer> myCustomers = new ArrayList<Customer>();
    private List<Vehicle> myVehicles = new ArrayList<Vehicle>();

    public static void main(String[] args) throws IOException, ParserConfigurationException {
        Main main = new Main();
        String[] locations = Xml_location_input.getLocations(); // get location of folders, that contain the xml data

        // parse all customer xml data in given folder:
        final File customerFolder = new File(locations[0]);
        List<String> customerXmlPaths = main.listFilesForFolder(customerFolder);
        for (Object o : customerXmlPaths) {
            String s = o.toString();
            main.parseXmlFile(locations[0]+"/"+s, 0);
        }
        main.printData(0);

        System.out.println("updating customer database...");
        main.updateCustomerDb();
        System.out.println("done.");
        System.out.println();

        System.out.println(" ID,Vorname,Nachname,Geschlecht,Straße,Nr,PLZ,Ort");
        MyJDBC.printDatabase("customers");

        MyJDBC.editDbQuery(0);
        System.out.println();

        // parse all vehicle xml data in given folder:
        final File vehicleFolder = new File(locations[1]);
        List<String> vehicleXmlPaths = main.listFilesForFolder(vehicleFolder);
        for (Object o : vehicleXmlPaths) {
            String s = o.toString();
            main.parseXmlFile(locations[1]+"/"+s, 1);
        }
        main.printData(1);

        System.out.println("updating vehicle database...");
        main.updateVehicleDb();
        System.out.println("done.");
        System.out.println();

        System.out.println("ID,Typ,Hersteller,Bezeichnung,Leistung,Preis");
        MyJDBC.printDatabase("vehicles");

        MyJDBC.editDbQuery(1);
        System.out.println();

        LetterCreation.createLetters();
    }

    private void parseXmlFile(String location, int custOrVehic) throws IOException, ParserConfigurationException {
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

    private void parseDocument(Document dom, int custOrVehic) {
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

        // for each <fahrzeug> element get text or int values of
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
        // in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    private void printData(int custOrVehic) {
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

    private void updateCustomerDb() throws IOException, ParserConfigurationException {
        // database doesn´t allow for duplicate IDs, so I can just import all Objects from xml files
        Iterator<Customer> it = myCustomers.iterator();
        while (it.hasNext()) {
            MyJDBC.addCustomerToDb(it.next());
        }
    }

    private void updateVehicleDb() throws IOException, ParserConfigurationException {
        // database doesn´t allow for duplicate IDs, so I can just import all Objects from xml files
        Iterator<Vehicle> it = myVehicles.iterator();
        while (it.hasNext()) {
            MyJDBC.addVehicleToDb(it.next());
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