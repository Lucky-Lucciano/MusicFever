package net.etfbl.musicfever.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class ConnectionPool {
    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }
    private static ConnectionPool connectionPool;

    static {
        ResourceBundle bundle =
                PropertyResourceBundle.getBundle("net.etfbl.musicfever.dao.dao");
        String driver = bundle.getString("driver");
        String jdbcURL = bundle.getString("url");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        int preconnectCount = 0;
        int maxIdleConnections = 10;
        int maxConnections = 10;
        try {
            preconnectCount = Integer.parseInt(
                    bundle.getString("preconnectCount"));
            maxIdleConnections = Integer.parseInt(
                    bundle.getString("maxIdleConnections"));
            maxConnections = Integer.parseInt(
                    bundle.getString("maxConnections"));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        try {
            connectionPool = new ConnectionPool(driver,
                    jdbcURL, username, password,
                    preconnectCount, maxIdleConnections,
                    maxConnections);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    protected ConnectionPool(String aDriver,
            String aJdbcURL, String aUsername,
            String aPassword, int aPreconnectCount,
            int aMaxIdleConnections,
            int aMaxConnections)
            throws ClassNotFoundException, SQLException {

        freeConnections = new ArrayList<Connection>();
        usedConnections = new ArrayList<Connection>();
        driver = aDriver;
        jdbcURL = aJdbcURL;
        username = aUsername;
        password = aPassword;
        preconnectCount = aPreconnectCount;
        maxIdleConnections = aMaxIdleConnections;
        maxConnections = aMaxConnections;

        Class.forName(driver);
        for (int i = 0; i < preconnectCount; i++) {
            Connection conn = DriverManager.getConnection(
                    jdbcURL, username, password);
            conn.setAutoCommit(true);
            freeConnections.add(conn);
        }
        connectCount = preconnectCount;
    }

    public synchronized Connection checkOut()
            throws SQLException {

        Connection conn = null;
        if (freeConnections.size() > 0) {
            conn = (Connection) freeConnections.get(0);
            freeConnections.remove(0);
            usedConnections.add(conn);
        } else {
            if (connectCount < maxConnections) {
                conn = DriverManager.getConnection(
                        jdbcURL, username, password);
                usedConnections.add(conn);
                connectCount++;
            } else {
                try {
                    wait();
                    conn = (Connection) freeConnections.get(0);
                    freeConnections.remove(0);
                    usedConnections.add(conn);
                } catch (InterruptedException ex) {
                    ex.printStackTrace(System.err);
                }
            }
        }
        return conn;
    }

    public synchronized void checkIn(Connection aConn) {
        if (aConn == null) {
            return;
        }
        if (usedConnections.remove(aConn)) {
            freeConnections.add(aConn);
            while (freeConnections.size() > maxIdleConnections) {
                int lastOne = freeConnections.size() - 1;
                Connection conn = (Connection) freeConnections.get(lastOne);
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
                freeConnections.remove(lastOne);
            }
            notify();
        }
    }
    private String driver;
    private String jdbcURL;
    private String username;
    private String password;
    private int preconnectCount;
    private int connectCount;
    private int maxIdleConnections;
    private int maxConnections;
    private ArrayList<Connection> usedConnections;
    private ArrayList<Connection> freeConnections;
}
