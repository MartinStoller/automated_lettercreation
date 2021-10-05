package com.company.businesslayer.customer;

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

    //Getter and setter:
    public int getID() {return id;}
    public void setID(int new_id) {this.id = new_id;}

    public int getPostalCode() {return postalCode;}
    public void setPostalCode(int new_postalCode) {this.postalCode = new_postalCode;}

    public String getHouseNr() {return houseNr;}
    public void setHouseNr(String new_houseNr) {this.houseNr = new_houseNr;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String new_firstName) {this.firstName = new_firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String new_lastName) {this.lastName = new_lastName;}

    public String getGender() {return gender;}
    public void setGender(String new_gender) {this.gender = new_gender;}

    public String getStreet() {return street;}
    public void setStreet(String new_street) {this.street = new_street;}

    public String getCity() {return city;}
    public void setCity(String new_city) {this.houseNr = new_city;}

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

}

