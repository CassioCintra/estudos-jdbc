package application;

import dataBase.DB;
import dataBase.exceptions.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcIntegrity {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DB.getConnection();
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            int rows1 = statement.executeUpdate(
                    "UPDATE saller SET base_salary = 2090 " +
                            "WHERE departament_id = 1"
            );
            int rows2 = statement.executeUpdate(
                    "UPDATE saller SET base_salary = 3050 " +
                            "WHERE departament_id = 2"
            );

            connection.commit();

            System.out.println("rows1 " + rows1);
            System.out.println("rows2 " + rows2);
        } catch (SQLException e){
            try {
                connection.rollback();
                throw new DbException(
                        "Transaction rolled back! Caused by: " +
                                e.getMessage()
                );
            } catch (SQLException e1){
                throw new DbException("Error trying to rollback! Caused by: " +
                        e1.getMessage()
                );
            }
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
