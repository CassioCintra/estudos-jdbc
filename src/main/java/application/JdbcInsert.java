package application;

import dataBase.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JdbcInsert {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            connection = DB.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO saller" +
                            "(name, email, birth_date, base_salary, departament_id)" +
                            "VALUES(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, "Carlos");
            preparedStatement.setString(2,"carlos@gmail.com");
            preparedStatement.setDate(3,
                    new java.sql.Date(dateFormat.parse("22/04/1985").getTime()));
            preparedStatement.setDouble(4,3000.0);
            preparedStatement.setInt(5,1);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()){
                    int id = resultSet.getInt(1);
                    System.out.println("Done! Id = " + id);
                }
            }
            else {
                System.out.println("No rowns affected!");
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }
}
