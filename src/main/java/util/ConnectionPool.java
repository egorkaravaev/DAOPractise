package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
    private int size;
    private int minPoolSize;
    private int maxPoolSize;
    private long idleTimeLimit;
    private double loadFactor;
    private int incrementPortion;

    final String DB_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost:3306/Books";
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "root";

    private LinkedList<Connection> availableConnections = new LinkedList<Connection>();

    public ConnectionPool() {
    }

    public ConnectionPool(Configuration configuration) { //peredavat' object config
        try {
            Class.forName(DB_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.minPoolSize = configuration.getMinPoolSize();
        this.size = minPoolSize;
        this.maxPoolSize = configuration.getMaxPoolSize();
        this.idleTimeLimit = configuration.getIdleTimeLimit();
        this.loadFactor = configuration.getLoadFactor();
        this.incrementPortion = configuration.getIncrementPortion();
        for (int i = 0; i < minPoolSize; i++) {
            availableConnections.add(getConnection());
        }
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public synchronized Connection retrieve() throws SQLException {
        Connection newConn = null;
        if(size <= maxPoolSize) {
            if (availableConnections.size() <= loadFactor / 100 * size) {
                for (int i = 0; i < incrementPortion; i++) {
                    availableConnections.add(getConnection());
                }
                size = size + incrementPortion;
            } else {
                newConn = availableConnections.getLast();
                availableConnections.removeLast();
            }

            return newConn;
        } else {
            System.out.println("The pool size is exceeded!");
            return null;
        }
    }

    public synchronized void putBack(Connection c){
        availableConnections.add(c);
    }

}
