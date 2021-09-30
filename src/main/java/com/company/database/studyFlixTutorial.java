package com.company.database;

import java.sql.*;

public class studyFlixTutorial {
    private final String url = "jdbc:mysql://localhost:3306/haeger-db";
    private  String user = "root";
    private String password = "MartinStoller";
    private String table;
    private String command;

    public String getUrl() {
        return this.url;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String new_user) {
        this.user = new_user;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String new_pw) {
        this.password = new_pw;
    }

    public String getTable() {
        return table;
    }
    public void setTable(String new_table) {
        this.table = new_table;
    }

    public String getCommand() {
        return command;
    }
    public void setCommand(String new_command) {
        this.command = new_command;
    }

    public studyFlixTutorial (String id, String new_table) {
        this.setTable(new_table);
        this.setCommand("SELECT * FROM " + this.getTable() + " WHERE id LIKE '%"+id+"%'");
        try{
            Connection connection = DriverManager.getConnection(this.getUrl(), this.getUser()
                    , this.getPassword());
            Statement statement = connection.createStatement();
            statement.execute("USE"+" "+this.getTable());
            ResultSet resultSet = statement.executeQuery(this.getCommand());

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {
                for(int i = 1; i <= columnsNumber; i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
