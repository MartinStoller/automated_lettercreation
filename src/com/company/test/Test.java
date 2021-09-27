package com.company.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        final File folder = new File("vehicles");
        test.listFilesForFolder(folder);
        System.out.println();
        List listi = test.listFilesForFolder2(folder);
        for (Object s : listi) {
            String s2 = s.toString();
            System.out.println(s2);
        }

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

}
