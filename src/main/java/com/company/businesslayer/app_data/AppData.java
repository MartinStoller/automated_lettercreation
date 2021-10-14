package com.company.businesslayer.app_data;

import com.company.businesslayer.customer.Customer;
import com.company.businesslayer.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class AppData {
    // Lists which store all objects retrieved from all xml files
    private List<Customer> myCustomers = new ArrayList<Customer>();
    private List<Vehicle> myVehicles = new ArrayList<Vehicle>();

    private  static  AppData appData = null;

    // private Constructor for Singelton
    private AppData(){
    }

    public static AppData getAppData(){
        if(appData == null){
            appData = new AppData();
        }
        return appData;
    }


    // getter and setter for the two lists:
    public List<Customer> getMyCustomers() {
        return myCustomers;
    }

    public List<Vehicle> getMyVehicles() {
        return myVehicles;
    }

    public void setMyCustomers(List<Customer> newList){
        myCustomers = newList;
    }

    public void setMyVehicles(List<Vehicle> newList){
        myVehicles = newList;

    }
}
