package golden_gym_app.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    public static Connection getConnection() {
        Connection connection = null;
        var dataBase = "golden_gym_db";
        var url = "jdbc:mysql://localhost:3306/" + dataBase;
        var user = "root";
        var password = "admin";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("An error has occurred while connecting to the DB: " + e.getMessage());
        }

        return connection;
    }

    public static void main(String[] args) {
        var connection = Connect.getConnection();
        if(connection != null) {
            System.out.println("Successful connection: " + connection);
        } else {
            System.out.println("Error while connecting...");
        }
    }
}
