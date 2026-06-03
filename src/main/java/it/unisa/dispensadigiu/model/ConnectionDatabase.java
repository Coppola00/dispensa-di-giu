package it.unisa.dispensadigiu.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionDatabase {
	
	private static List<Connection> freeDbConnections;

    static {
        freeDbConnections = new LinkedList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("DB driver not found: " + e.getMessage());
        }
    }

    private static synchronized Connection createDBConnection() throws SQLException {
        Connection newConnection = null;
        String url = "jdbc:mysql://localhost:3306/dispensadigiu";
        String username = "root";
        String password = "NuovaPassword123!";

        newConnection = DriverManager.getConnection(url,
                username, password);

        // ✅ Autocommit attivo: ogni operazione viene salvata subito
        newConnection.setAutoCommit(true);

        return newConnection;
    }

    public static synchronized Connection getConnection() throws SQLException {
        Connection connection;

        if (!freeDbConnections.isEmpty()) {
            connection = freeDbConnections.remove(0);

            try {
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (SQLException e) {
                connection.close();
                connection = getConnection();
            }
        } else {
            connection = createDBConnection();
        }

        return connection;
    }

    public static synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            freeDbConnections.add(connection);
        }
    }
	  
}
