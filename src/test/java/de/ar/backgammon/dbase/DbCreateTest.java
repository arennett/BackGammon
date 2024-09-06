package de.ar.backgammon.dbase;

import de.ar.backgammon.BException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DbCreateTest {

    @Test
    void deleteDbase() throws BException {
        DbCreate.getInstance().deleteDbase();
    }

    @Test
    void createDbase() throws BException {
        DbCreate.getInstance().createDbase();
    }
}