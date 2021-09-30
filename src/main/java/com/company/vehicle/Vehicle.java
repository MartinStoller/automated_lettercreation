package com.company.vehicle;

import java.sql.*;

public class Vehicle {
    public int id;
    public String type;
    public String brand;
    public String name;
    public int power;
    public int price;

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

}
