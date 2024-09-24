package de.ar.backgammon.dbase;

import de.ar.backgammon.BException;
import de.ar.backgammon.ConstIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    private static final Logger logger = LoggerFactory.getLogger(DbConnect.class);

    private static DbConnect inst=null;

    public Connection getConnection() throws BException {
        try {
            if(conn==null || conn.isClosed()) {
                connect();
                conn.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new BException("getConnection failed",e);
        }

        return conn;
    }
    private Connection conn = null;

    public static DbConnect getInstance(){
        if (inst==null){
            inst = new DbConnect();
        }
        return inst;
    }

    public void connect() throws BException {
        if(conn!=null ){
            close();
        }
        conn = null;
        try {
            conn = DriverManager.getConnection(ConstIf.DB_URL);
            //logger.debug("dbase connected");
        } catch (SQLException e) {
            throw new BException("dbase connection failed",e);

        }
    }

    public boolean close() {
        try {
            if (conn != null) {
                conn.close();
            }
            //logger.debug("connection closed");
            return true;
        } catch (SQLException e) {
            logger.error("dbase connection closing failed",e);
        }
        return false;
    }


    public void commit() throws BException {
        try {
            conn.commit();
        } catch (SQLException e) {
            throw new BException("commit failed",e);
        }
    }
}
