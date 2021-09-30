package com.company.main;

import java.io.File;
import java.io.IOException;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.company.lettercreation.LetterCreation;
import com.company.xml_location_input.Xml_location_input;
import com.company.database.DBConnection;

public class Main {


    public static void main(String[] args) throws IOException, ParserConfigurationException {
        DBConnection DBConnection = new DBConnection();
        String[] locations = Xml_location_input.getLocations(); // get location of folders, that contain the xml data

        // parse all customer xml data in given folder:
        final File customerFolder = new File(locations[0]);
        List<String> customerXmlPaths = DBConnection.listFilesForFolder(customerFolder);
        for (Object o : customerXmlPaths) {
            String s = o.toString();
            DBConnection.parseXmlFile(locations[0]+"/"+s, 0);
        }
        DBConnection.printData(0);

        System.out.println("updating customer database...");
        DBConnection.updateCustomerDb();
        System.out.println("done.");
        System.out.println();

        System.out.println(" ID,Vorname,Nachname,Geschlecht,Straße,Nr,PLZ,Ort");
        DBConnection.printDatabase("customers");

        DBConnection.editDbQuery(0);
        System.out.println();

        // parse all vehicle xml data in given folder:
        final File vehicleFolder = new File(locations[1]);
        List<String> vehicleXmlPaths = DBConnection.listFilesForFolder(vehicleFolder);
        for (Object o : vehicleXmlPaths) {
            String s = o.toString();
            DBConnection.parseXmlFile(locations[1]+"/"+s, 1);
        }
        DBConnection.printData(1);

        System.out.println("updating vehicle database...");
        DBConnection.updateVehicleDb();
        System.out.println("done.");
        System.out.println();

        System.out.println("ID,Typ,Hersteller,Bezeichnung,Leistung,Preis");
        DBConnection.printDatabase("vehicles");

        DBConnection.editDbQuery(1);
        System.out.println();

        LetterCreation.createLetters();
    }


}