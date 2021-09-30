package com.company.customer;

import java.sql.*;

public class Customer {

    public int id;
    public String firstName;
    public String lastName;
    public String gender;
    public String street;
    public String houseNr;
    public int postalCode;
    public String city;

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

