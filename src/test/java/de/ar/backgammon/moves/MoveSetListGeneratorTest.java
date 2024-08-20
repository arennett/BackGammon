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
        Move.CmpToString=true;
        bmodel = new BoardModel();
        MoveValidatorIf moveValidator = new MoveValidator(bmodel);
        bmodel.setMoveValidator(moveValidator);

    }

    public void testCompare(String model1, String model2) throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, model1);
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel);

        MoveSetHash moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        //assertEquals(20,moveSetHash.size());
        String cmpString1 = moveSetHash.toSortedString();


        breader.readTestModel(bmodel, model2);
        moveSetHash = mslg.getValidMoveSets();
        assertTrue(!moveSetHash.isEmpty());
        //assertEquals(20,moveSetHash.size());
        String cmpString2 = moveSetHash.toSortedString();

        assertEquals(cmpString1,cmpString2);
        logger.debug("msetList1 : size: {} {}", moveSetHash.size(), cmpString1);
        logger.debug("msetList2 : size: {} {}", moveSetHash.size(), cmpString2);
    }
    @Test
    void getValidMoveSets1() throws IOException, BException {

        testCompare("boardmap_startup_dices#3#4#turn#w","boardmap_startup_dices#3#4#turn#r");

   }

    @Test
    void getValidMoveSets2() throws IOException, BException {
        testCompare("boardmap_startup_dices#1#1#turn#w","boardmap_startup_dices#1#1#turn#r");

    }

    @Test
    void getValidMoveSets2b() throws IOException, BException {
        testCompare("boardmap_startup_dices#2#2#turn#w","boardmap_startup_dices#2#2#turn#r");
    }
    @Test
    void getValidMoveSets2c() throws IOException, BException {
        testCompare("boardmap_only2_dices#1#1#turn#w","boardmap_only2_dices#1#1#turn#r");
    }

    @Test
    void getValidMoveSets2d() throws IOException, BException {
        testCompare("boardmap_testerr1_dices#1#1w","boardmap_testerr1_dices#1#1r");
      }
    @Test
    void getValidMoveSets3() throws IOException, BException {
        testCompare("boardmap_bar2w_dices#3#4#turn#w","boardmap_bar2r_dices#3#4#turn#r");
    }

    @Test
    void getValidMoveSets4() throws IOException, BException {
        testCompare("boardmap_off_dices#6#2#turn#w","boardmap_off_dices#6#2#turn#r");
    }



}
