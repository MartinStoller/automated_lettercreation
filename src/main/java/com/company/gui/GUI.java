package com.company.gui;

import com.company.businesslayer.lettercreation.LetterCreation;
import com.company.businesslayer.xml_location_input.Xml_location_input;
import com.company.database.DB_Connection;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class GUI extends JFrame {

    public static void main(String[] args) {
        JFrame myMainFrame = new GUI();
        myMainFrame.setTitle("Automated Letter Creation Tool 5000");
        myMainFrame.setSize(900, 200);
        myMainFrame.setLocation(100, 100);
        myMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton xmlButton = new JButton("Import xml-data");
        JButton showButton = new JButton("Show Database");
        JButton editButtonVeh = new JButton("Edit Vehicle Database");
        JButton editButtonCust = new JButton("Edit Customer Database");
        JButton letterButton = new JButton("Create letters");

        myMainFrame.setLayout(new FlowLayout());
        myMainFrame.add(xmlButton);
        myMainFrame.add(showButton);
        myMainFrame.add(editButtonVeh);
        myMainFrame.add(editButtonCust);
        myMainFrame.add(letterButton);

        myMainFrame.setVisible(true);

        MyButtonHandler mbh = new MyButtonHandler();
        xmlButton.addActionListener(mbh);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Kunden:");
                System.out.println(" ID,Vorname,Nachname,Geschlecht,Straße,Nr,PLZ,Ort");
                DB_Connection.printDatabase("customers");
                System.out.println();
                System.out.println();

                System.out.println("Fahrzeuge:");
                System.out.println("ID,Typ,Hersteller,Bezeichnung,Leistung,Preis");
                DB_Connection.printDatabase("vehicles");
                System.out.println();
                System.out.println();

            }
        });

        editButtonCust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Type in SQL command:");
                String query = scanner2.nextLine();
                DB_Connection.editDb(0, query);
            }
        });


        editButtonVeh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scanner scanner3 = new Scanner(System.in);
                System.out.println("Type in SQL command:");
                String query = scanner3.nextLine();
                DB_Connection.editDb(1, query);
            }
        });

        letterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LetterCreation.createAllLetters();
            }
        });

    }


    static class MyButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String[] locations = Xml_location_input.getLocations(); // get location of xml data
                File customerFolder = new File(locations[0]);
                List<String> customerXmlPaths = DB_Connection.listFilesForFolder(customerFolder);
                for (Object o : customerXmlPaths) {
                    String s = o.toString();
                    DB_Connection.parseXmlFile(locations[0]+"/"+s, 0);
                }
                DB_Connection.printData(0);

                System.out.println("updating customer database...");
                DB_Connection.updateCustomerDb();
                System.out.println("done.");
                System.out.println();

                File vehicleFolder = new File(locations[1]);
                List<String> vehicleXmlPaths = DB_Connection.listFilesForFolder(vehicleFolder);
                for (Object o : vehicleXmlPaths) {
                    String s = o.toString();
                    DB_Connection.parseXmlFile(locations[1]+"/"+s, 1);
                }
                DB_Connection.printData(1);

                System.out.println("updating vehicle database...");
                DB_Connection.updateVehicleDb();
                System.out.println("done.");
                System.out.println();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
