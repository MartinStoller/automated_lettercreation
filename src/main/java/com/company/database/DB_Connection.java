package com.company.database;

import java.sql.*;

public class DB_Connection {
    private final String url = "jdbc:mysql://localhost:3306/haeger-db";
    private String user = "root";
    private String password = "MartinStoller";
    private String table;
    private String command;

    //Getter and Setter:
    public String getUrl() {
        return url;
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

    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = createNewConnection();
        }
        return conn;
    }


    private static Connection createNewConnection() {
        DB_Connection db_connection = new DB_Connection();
        try {
            Connection connection = DriverManager.getConnection(db_connection.getUrl(), db_connection.getUser()
                    , db_connection.getPassword());
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

/*
    public DB_Connection(String id, String new_table) {
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
*/
