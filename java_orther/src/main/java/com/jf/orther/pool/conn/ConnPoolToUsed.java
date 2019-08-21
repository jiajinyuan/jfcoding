package com.jf.orther.pool.conn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 *  ConnPoolToUsed.
 *
 * @author Junfeng
 */
public final class ConnPoolToUsed {

    private Logger log = LoggerFactory.getLogger(ConnPoolToUsed.class);

    private static ConnPool<String, Connection> pool;

    /**
     * SingletonClassInstance.
     */
    private static class SingletonClassInstance {
        private static final ConnPoolToUsed INSTANCE = new ConnPoolToUsed();
    }

    public static ConnPoolToUsed getInstance() {
        return ConnPoolToUsed.SingletonClassInstance.INSTANCE;
    }

    private ConnPoolToUsed() {
        pool = ConnPoolBuilder.newBuilder()
                .expireAfterAccess(300, TimeUnit.SECONDS)
                .removalBeforeListener((RemovalListener<String, Connection>) (key, conn) -> {
                    log.debug("Remove connection [ " + key + " / " + conn.toString() + " ] ");
                    try {
                        conn.close();
                    } catch (SQLException ignore) {

                    }
                })
                .build(key -> {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e.getCause());
                    }
                    try {
                        return DriverManager.getConnection("url", "", "");
                    } catch (SQLException e) {
                        throw new RuntimeException(e.getCause());
                    }
                });
    }


    public Connection borrowConn(String dbName, String userName, String password) {

        Connection conn = pool.get(dbName + "@" + userName + "@" + password);

        int times = 0;
        try {
            while (conn == null || !conn.isValid(2)) {
                if (times > 3) {
                    throw new RuntimeException("Connection middleware is abnormal");
                }
                if (conn != null) {
                    pool.remove(dbName + "@" + userName + "@" + password, conn);
                }
                conn = pool.get(dbName + "@" + userName + "@" + password);
                times++;
            }
            return conn;
        } catch (SQLException e) {
            log.error("Connection middleware is abnormal", e);
            throw new RuntimeException("Connection middleware is abnormal");
        }
    }

    public void returnConn(Connection conn) {
        pool.ret(conn);
    }

    public void returnConn(String dbName, String userName, String password, Connection conn) {
        if (null == dbName || "".equals(dbName)) {
            throw new IllegalArgumentException("dbName must not be null!");
        }
        if (null == userName || "".equals(userName)) {
            throw new IllegalArgumentException("userName must not be null!");
        }
        if (null == password || "".equals(password)) {
            throw new IllegalArgumentException("password must not be null!");
        }
        pool.ret(dbName + "@" + userName + "@" + password, conn);
    }

    public Connection createConn(String dbName, String userName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getCause());
        }
        try {
            return DriverManager.getConnection(MessageFormat.format("", "", "", dbName),
                    userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public void closeConn(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
