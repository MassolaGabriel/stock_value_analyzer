package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {

    private static final String HOST = "jdbc:mysql://localhost:3306/stock";
    private static final String USER = "root";
    private static final String PASSWORD = "!180720Ga";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(HOST, USER, PASSWORD);
            System.out.println("Conectado com sucesso!");
            return connection;
        } catch (SQLException e) {
             System.out.println("Erro ao se conectar ao banco. " + e.getMessage());
             return null;
        }
    }
}
