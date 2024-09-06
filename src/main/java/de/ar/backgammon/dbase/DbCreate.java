package de.ar.backgammon.dbase;

import de.ar.backgammon.BException;
import de.ar.backgammon.ConstIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



public class DbCreate {
    private static final Logger logger = LoggerFactory.getLogger(DbCreate.class);

    public static DbCreate inst = null;

    public static DbCreate getInstance() {
        if (inst == null) {
            inst = new DbCreate();
        }
        return inst;
    }

    public void deleteDbase() throws BException {
        try {
            Files.delete(Path.of(ConstIf.DB_PATH+ConstIf.DB_FILENAME));
            logger.debug("dbase file deleted");
        } catch (IOException e) {
            throw new BException("dbase file not found",e);
        }
    }

    public void createDbase() throws  BException {
        DbConnect.getInstance().close();
        //deleteDbase();
        DbConnect.getInstance().connect();
        runMultiStatScript("dbase.db.sql");
        DbConnect.getInstance().close();

        logger.debug("dbase created");

    }

    private void runMultiStatScript(String filename) throws BException {
        try {
            MyBatisScriptUtility.runScript(ConstIf.DB_PATH + filename, DbConnect.getInstance().getConnection());
        } catch (Exception e) {
            throw new BException("runMultiStatScript failed", e);
        }
        //executeSqlFile("dbase.db.sql");
    }

    public static void main(String[] args) {
        try {
            getInstance().createDbase();
        } catch (BException e) {
            logger.error("dbase creation failed",e);
        }
    }
}