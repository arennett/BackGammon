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
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
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
        breader.readTestModel(bmodel, "boardmap_startup_dices#3#4#turn#w");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(16,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
   }

    @Test
    void getValidMoveSets2() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#1#1#turn#w");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(36,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
    void getValidMoveSets2b() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#2#2#turn#w");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(52,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }
    @Test
    void getValidMoveSets2c() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_only2_dices#1#1#turn#w");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
    //boardmap_testerr1_dices#1#1w.txt
    void getValidMoveSets2d() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_testerr1_dices#1#1w");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
        //boardmap_testerr1_dices#1#1w.txt
    void getValidMoveSets2e() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_testerr1_dices#1#1w");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);
        MoveListGenerator mg = new MoveListGenerator(bmodel);

//        bmodel.move(new Move(17,18),1,false);
//        bmodel.move(new Move(19,20),1,false);
//        bmodel.move(new Move(19,20),1,false);

        ArrayList<Move> moves=mg.getValidMoves();
        logger.debug("movelist : {}",moves);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
    void getValidMoveSets3() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_bar2w_dices#3#4#turn#w");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(1,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
    void getValidMoveSets4() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_off_dices#6#2#turn#w");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(2,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }



    @Test
    void getValidMoveSets5() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#3#4#turn#r");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(16,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
    void getValidMoveSets6() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#1#1#turn#r");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(35,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
    void getValidMoveSets6b() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#2#2#turn#r");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(52,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
    void getValidMoveSets6c() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_only2_dices#1#1#turn#r");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
        //boardmap_testerr1_dices#1#1r.txt
    void getValidMoveSets6d() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_testerr1_dices#1#1r");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
        //boardmap_testerr1_dices#1#1r.txt
    void getValidMoveSets6e() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_testerr1_dices#1#1r");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);
        MoveListGenerator mg = new MoveListGenerator(bmodel);

//        bmodel.move(new Move(17,18,true),1,false);
//        bmodel.move(new Move(19,20,true),1,false);
//        bmodel.move(new Move(19,20,true),1,false);

        ArrayList<Move> moves=mg.getValidMoves();
        logger.debug("movelist : {}",moves);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
    void getValidMoveSets7() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_bar2r_dices#3#4#turn#r");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(1,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

    @Test
    void getValidMoveSets8() throws IOException, BException {

        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_off_dices#6#2#turn#r");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        assertEquals(3,moveSetHash.size());

        logger.debug("msetList : size: {} {}", moveSetHash.size(), moveSetHash.getFormatedList());
    }

}
