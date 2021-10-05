package com.company.businesslayer.vehicle;

import java.sql.*;

public class Vehicle {
    private int id;
    private String type;
    private String brand;
    private String name;
    private int power;
    private int price;

    //getter and setter:
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getPower() {return power;}
    public void setPower(int power) {this.power = power;}

    public int getPrice() {return price;}
    public void setPrice(int price) {this.price = price;}


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
