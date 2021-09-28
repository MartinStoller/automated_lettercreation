package com.company.lettercreation;

import javax.swing.text.Document;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LetterCreation {
    public static void createLetters() {
        System.out.println("Type 'y' if letters for each customer should be created. Type 'n' otherwise.");
        Scanner scanner3 = new Scanner(System.in);
        String yn = scanner3.nextLine();
        if (yn.equals("y")) {
            System.out.println("creating letters...");
        }
    }

    private void fillInTxtPlaceholders() {
        //get variables for Letter
        String firstName = "Loading data did not work";
        String lastName = "Loading data did not work";
        String gender = "Loading data did not work";
        String postalCode = "Loading data did not work";
        String street = "Loading data did not work";
        String city = "Loading data did not work";
        String houseNr = "Loading data did not work";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from customers");

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i == 2) {
                        firstName = resultSet.getString(i);
                    } else if (i == 3) {
                        lastName = resultSet.getString(i);
                    } else if (i == 4) {
                        gender = resultSet.getString(i);
                    } else if (i == 5) {
                        postalCode = resultSet.getString(i);
                    } else if (i == 6) {
                        street = resultSet.getString(i);
                    } else if (i == 7) {
                        city = resultSet.getString(i);
                    } else if (i == 8) {
                        houseNr = resultSet.getString(i);
                    }

                    System.out.print(resultSet.getString(i) + " ");
                    fillTxtWithCustomerdata(firstName, lastName, gender, postalCode, street, city, houseNr);//TODO: write this function
/*                    addCarsToLetter(); //TODO: write this function
                    saveTxtasPdf();//TODO: write this function*/
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillTxtWithCustomerdata(String firstName, String lastName, String gender, String postalCode,
                                         String street, String city, String houseNr) {
        String genderText = "geehrte Frau ";
        if (gender.equals("m")) {
            genderText = "geehrter Herr ";
        }

        String vehicles = getVehiclesString(); // TODO: write a function that provides a vehicle List

        String content = firstName + " " + lastName + " \n" + street + " " + houseNr + " \n" + postalCode + " " +
                city + " \n" + " \n" + "Sehr " + genderText + lastName + ",\n" + "\n" + "anbei finden Sie eine " +
                "Liste unserer aktuellen Angebote. Zögern Sie nicht uns bei Interesse oder Fragen jeglicher Art zu " +
                "kontaktieren." + "\n" + "\n" + "    Modell        Leistung(KW)        Preis \n" + vehicles + "\n" +
                "\n" + "Mit " + "freundlichen Grüßen \nClaudia Mustermann \nGeschäftsführering Autohaus XY";

        String fileName = "testletter.txt";
        try {
            Files.writeString(Paths.get(fileName), content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getVehiclesString() {
        String brand = "loading data failed";
        String name = "loading data failed";
        String power = "loading data failed";
        String price = "loading data failed";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from vehicles");

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i == 2) {
                        firstName = resultSet.getString(i);
                    } else if (i == 3) {
                        lastName = resultSet.getString(i);
                    } else if (i == 4) {
                        gender = resultSet.getString(i);
                    } else if (i == 5) {
                        postalCode = resultSet.getString(i);
                    } else if (i == 6) {
                        street = resultSet.getString(i);
                    } else if (i == 7) {
                        city = resultSet.getString(i);
                    } else if (i == 8) {
                        houseNr = resultSet.getString(i);
                    }

                    System.out.print(resultSet.getString(i) + " ");
                    fillTxtWithCustomerdata(firstName, lastName, gender, postalCode, street, city, houseNr);//TODO: write this function
/*                    addCarsToLetter(); //TODO: write this function
                    saveTxtasPdf();//TODO: write this function*/
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

