package com.company.customer;

import java.sql.*;

public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String street;
    private String houseNr;
    private int postalCode;
    private String city;

    public Customer(int id, String firstName, String lastName, String gender, String street, String houseNr,
                    int postalCode, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.street = street;
        this.houseNr = houseNr;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String toString() {
        return id + ": " + firstName + " " + lastName + ", Geschlecht: " + gender + ", Adresse: " + street + " " +
                houseNr + "; " + postalCode + " " + city;
    }



    public static void addCustomerToDb(Customer customer) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");
            Statement statement = connection.createStatement();
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

        } catch (SQLIntegrityConstraintViolationException es) {
            System.out.println("Customer with ID " + customer.id + " is already in database. No import.");
        } catch (Exception e) {
            e.printStackTrace();


    }
    }
}

