package com.company.lettercreation;

import javax.swing.text.Document;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LetterCreation {
    public static void createLetters(){
        System.out.println("Type 'y' if letters for each customer should be created. Type 'n' otherwise.");
        Scanner scanner3 = new Scanner(System.in);
        String yn = scanner3.nextLine();
        if (yn.equals("y")){
            System.out.println("creating letters...");
        }
    }

    public static void fillInTxtPlaceholders(List customers, List vehicles){
        // gets lists of customer objects and vehicle objects and creates a custom .txt file for each customer.
        // save that txt file not as txt but as pdf
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from customers");

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
}

