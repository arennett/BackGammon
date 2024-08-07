package de.ar.backgammon.moves;

import de.ar.backgammon.BException;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReader;
import de.ar.backgammon.model.BoardModelReaderIf;
import de.ar.backgammon.validation.MoveValidator;
import de.ar.backgammon.validation.MoveValidatorIf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

class MoveSetListGeneratorTest {
    static Logger logger = LoggerFactory.getLogger(MoveSetListGeneratorTest.class);
    BoardModelIf bmodel;
    MoveSetListGenerator mslg;
    MoveListGenerator mlg;

    @BeforeEach
    public void setUp() throws IOException, BException {
        bmodel = new BoardModel();
        MoveValidatorIf moveValidator = new MoveValidator(bmodel);
        bmodel.setMoveValidator(moveValidator);

    }

    @Test
    void getValidMoveSets1() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "testMoveListGeneratorTest");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());

    }

    @Test
    void getValidMoveSets2() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "testMoveListGeneratorTest2");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());

    }

    @Test
    void getValidMoveSets3() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "testMoveListGeneratorTest3");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());

    }
    @Test
    void calcMoveSet() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "testMoveListGeneratorTest");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());

    }

}
