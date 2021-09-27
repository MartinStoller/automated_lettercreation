package com.company.vehicle;

import java.sql.*;

public class Vehicle {
    private int id;
    private String type;
    private String brand;
    private String name;
    private int power;
    private int price;

    public Vehicle(int id, String type, String brand, String name, int power, int price) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.name = name;
        this.power = power;
        this.price = price;
    }

    public String toString() {
        return id + ": " + "Typ: " + type + ", Hersteller: " + brand + ", Bezeichnung: " + name + ", Leistung: " +
                power + ", Preis: " + price + "; ";
    }

    public static void addVehicleToDb(Vehicle vehicle) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");
            Statement statement = connection.createStatement();
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

        } catch (SQLIntegrityConstraintViolationException es) {
            System.out.println("Vehicle with ID " + vehicle.id + " is already in database. No import.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
