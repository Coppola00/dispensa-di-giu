package it.unisa.dispensadigiu.model;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionDatabase {
    
    private static DataSource ds;

    static {
        try {
            // Recupero del contesto iniziale di Tomcat
            InitialContext ctx = new InitialContext();
            // Ricerca della risorsa configurata nel context.xml 
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/dispensadigiu");
        } catch (NamingException e) {
            System.out.println("Errore durante l'inizializzazione del DataSource JNDI: " + e.getMessage());
        }
    }

    // Restituisce una connessione prelevata direttamente dal Connection Pool di Tomcat.  
    public static synchronized Connection getConnection() throws SQLException {
        if (ds == null) {
            throw new SQLException("DataSource non configurato o non raggiungibile.");
        }
        return ds.getConnection();
    }

    // Chiudendo la connessione del DataSource, questa viene restituita automaticamente al pool.
    public static synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}