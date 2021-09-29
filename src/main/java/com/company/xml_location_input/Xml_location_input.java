package com.company.xml_location_input;

import java.util.*;

public class Xml_location_input {
    public static String[] getLocations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the location (directory)" +
                " of the customer datafiles (must be XML format):");
        String xmlCustomerLoc = scanner.nextLine();
        System.out.println("Please enter the location (directory) of the vehicle datafiles (must be XML format):");
        String xmlVehicleLoc = scanner.nextLine();
        System.out.println("Searching for customer data in /" + xmlCustomerLoc + " ... ");
        System.out.println("Searching for customer data in /" + xmlVehicleLoc + " ... ");
        System.out.println();
        String[] locations = {xmlCustomerLoc, xmlVehicleLoc};
        return locations;
    }

}

