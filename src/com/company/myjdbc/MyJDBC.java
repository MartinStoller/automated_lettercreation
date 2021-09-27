package com.company.myjdbc;

import com.company.customer.Customer;
import com.company.vehicle.Vehicle;
import com.mysql.cj.xdevapi.Row;

import java.sql.*;
import java.util.Scanner;

public class MyJDBC {

    public static void main(String[] args) {
        printDatabase("customers");
        printDatabase("vehicles");
    }

    public static void printDatabase(String table) {
        try{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                , "MartinStoller");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from " + table);

        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        while (resultSet.next()) {
            for(int i = 1; i <= columnsNumber; i++)
                System.out.print(resultSet.getString(i) + " ");
            System.out.println();
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editDbQuery(int custOrVehic) {
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Press 'c' to continue. Press 'e' to edit the database.");
        String continueOrEdit = scanner2.nextLine();

        if (continueOrEdit.equals('c')) {
        }
        else if (continueOrEdit.equals('e')) {
            //TODO
            System.out.println("Type in SQL command:");
            String query = scanner2.nextLine();
            editDb(custOrVehic, query);

        } else {
            System.out.println("Input not valid. Type either 'c' to continue or 'e' to edit database");
            editDbQuery(custOrVehic);
        }
    }
        // idea: 1.) wait for input c vs e: if c, then done
        //2.) if e: connect to the correct table via int custOrVehic and let person type in sql command
        //3.) show new version of database and start at 1.) again

    public static void editDb(int custOrVehic, String query) {
        //TODO: execute query
        System.out.println("Command executed: Database now looks like this:");
        if (custOrVehic == 0){
            printDatabase("customers");
        } else{
            printDatabase("vehicles");
        }
        editDbQuery(custOrVehic);
    }

}
