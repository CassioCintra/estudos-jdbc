package application;

import dataBase.DB;

import java.sql.Connection;

public class JbdcConnetion {
    Connection connection;
    public static void main(String[] args) {
        Connection connection = DB.getConnection();
        DB.closeConnection();
    }
}
