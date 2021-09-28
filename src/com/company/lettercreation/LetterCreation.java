package com.company.lettercreation;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

import java.util.Scanner;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class LetterCreation {
    public static void createLetters() {
        System.out.println("Type 'y' if letters for each customer should be created. Type 'n' otherwise.");
        Scanner scanner3 = new Scanner(System.in);
        String yn = scanner3.nextLine();
        if (yn.equals("y")) {
            System.out.println("creating letters...");
            loopThroughCustomerDb();
        }

    }

    private static void convertTxtToPdf(String customerId){
        FileInputStream fis = null;
        DataInputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        File sourceFile = new File("testletter.txt");
        File destFile = new File("letters\\" + customerId + ".pdf");

        try{
            com.itextpdf.text.Document pdfDoc = new com.itextpdf.text.Document();
            PdfWriter writer = PdfWriter.getInstance(pdfDoc, new FileOutputStream(destFile));
            pdfDoc.open();
            pdfDoc.setMarginMirroring(true);
            pdfDoc.setMargins(36, 72, 108, 180);
            pdfDoc.topMargin();

            BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
            Font myfont = new Font(courier);
            Font bold_font = new Font();
            bold_font.setSize(10);
            bold_font.setStyle(Font.BOLD);
            myfont.setStyle(Font.NORMAL);
            myfont.setSize(10);

            pdfDoc.add(new com.itextpdf.text.Paragraph("\n"));

            if (sourceFile.exists()) {
                fis = new FileInputStream(sourceFile);
                in = new DataInputStream(fis);
                isr = new InputStreamReader(in);
                br = new BufferedReader(isr);
                String strLine;
                while ((strLine = br.readLine()) != null){
                    com.itextpdf.text.Paragraph para = new com.itextpdf.text.Paragraph(strLine + "\n", myfont);
                    para.setAlignment(Element.ALIGN_JUSTIFIED);
                    pdfDoc.add(para);
                }
                System.out.println("Letter for Customer " + customerId + "created successfully");
            } else {
                System.out.println("Source File not found!");
            }
            pdfDoc.close();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if (br != null) {
                    br.close();
                } if (fis != null) {
                    fis.close();
                } if (in != null) {
                    in.close();
                } if (isr != null) {
                    isr.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void loopThroughCustomerDb() {
        //get variables for Letter
        String firstName = "Loading data did not work";
        String lastName = "Loading data did not work";
        String gender = "Loading data did not work";
        String postalCode = "Loading data did not work";
        String street = "Loading data did not work";
        String city = "Loading data did not work";
        String houseNr = "Loading data did not work";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from customers");

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i == 2) {
                        firstName = resultSet.getString(i);
                    } else if (i == 3) {
                        lastName = resultSet.getString(i);
                    } else if (i == 4) {
                        gender = resultSet.getString(i);
                    } else if (i == 5) {
                        street = resultSet.getString(i);
                    } else if (i == 6) {
                        houseNr = resultSet.getString(i);
                    } else if (i == 7) {
                        postalCode = resultSet.getString(i);
                    } else if (i == 8) {
                        city = resultSet.getString(i);
                    }

                }
                fillTxtWithData(firstName, lastName, gender, postalCode, street, city, houseNr);
            }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    private static void fillTxtWithData(String firstName, String lastName, String gender, String postalCode,
                                 String street, String city, String houseNr) {
        String genderText = "geehrte Frau ";
        if (gender.equals("m")) {
            genderText = "geehrter Herr ";
        }

        String vehicles = getVehiclesString(); // TODO: write a function that provides a vehicle List

        String content = firstName + " " + lastName + " \n" + street + " " + houseNr + " \n" + postalCode + " " +
                city + " \n" + " \n" + " \n" + " \n" + "Sehr " + genderText + lastName + ",\n" + "\n" + "anbei finden Sie eine " +
                "Liste unserer aktuellen Angebote. Zögern Sie nicht uns bei Interesse oder Fragen jeglicher Art zu " +
                "kontaktieren." + "\n" + "\n"  + vehicles + "\n" +
                "\n" + "\n" + "Mit " + "freundlichen Grüßen \nClaudia Mustermann \nGeschäftsführering Autohaus XY";

        String fileName = "testletter.txt";
        try {
            Files.writeString(Paths.get(fileName), content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        convertTxtToPdf(lastName);
    }

    private static String getVehiclesString() {
        String brand = "loading data failed";
        String name = "loading data failed";
        String power = "loading data failed";
        String price = "loading data failed";
        String output = "  ";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/haeger-db", "root"
                    , "MartinStoller");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from vehicles");

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {
                output = output + "\n";
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i == 3) {
                        brand = resultSet.getString(i);
                        output = output + "Modell: " + brand + " ";
                    } else if (i == 4) {
                        name = resultSet.getString(i);
                        output = output + name + ",  ";
                    } else if (i ==5) {
                        power = resultSet.getString(i);
                        output = output + "Leistung: " + power + "KW,  ";
                    } else if (i == 6) {
                        price = resultSet.getString(i);
                        output = output + "Preis: " + price + "€ ";
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }



}


