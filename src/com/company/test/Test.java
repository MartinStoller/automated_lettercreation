package com.company.test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        test.fillTxtWithCustomerdata("Martin", "Stoller", "m", "84034", "Franzstr.", "Landshut", "18");
        }


    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }

    public List<String> listFilesForFolder2(final File folder) {
        List<String> xmlLocations = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                xmlLocations.add(fileEntry.getName());
            }
        }
        return xmlLocations;
    }

    private void fillTxtWithCustomerdata(String firstName, String lastName, String gender, String postalCode,
                                         String street, String city, String houseNr){
        String genderText = "geehrte Frau ";
        if (gender.equals("m")){
            genderText = "geehrter Herr ";
        }

        String vehicles = ""; // TODO: write a function that provides a vehicle List

        String content = firstName + " " + lastName + " \n" + street + " " + houseNr + " \n" + postalCode + " " +
                city +  " \n" +  " \n" + "Sehr " + genderText + lastName + ",\n" + "\n" + "anbei finden Sie eine " +
                "Liste unserer aktuellen Angebote. Zögern Sie nicht uns bei Interesse oder Fragen jeglicher Art zu " +
                "kontaktieren." + "\n" + "\n" + "    Modell        Leistung(KW)        Preis \n" + vehicles + "\n" +
                "\n" + "Mit " + "freundlichen Grüßen \nClaudia Mustermann \nGeschäftsführering Autohaus XY";

        String fileName = "testletter.txt";
        try {
            Files.writeString(Paths.get(fileName), content);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
