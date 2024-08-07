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
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

class MoveSetListGeneratorTest {
    static Logger logger = LoggerFactory.getLogger(MoveSetListGeneratorTest.class);
    BoardModelIf bmodel;
    MoveSetListGenerator mslg;
    MoveListGenerator mlg;

    @BeforeEach
    public void setUp() throws IOException, BException {

        bmodel = new BoardModel();
        MoveValidatorIf moveValidator =new MoveValidator(bmodel);
        bmodel.setMoveValidator(moveValidator);
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "testMoveListGeneratorTest");
        mslg = new MoveSetListGenerator(bmodel);
        mlg = new MoveListGenerator(bmodel, moveValidator);
    }
    @Test
    void test_getValidMoves2() {
        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());

        HashSet<Move> mhset = mlg.getValidMovesHashSet();
        assertTrue(!mhset.isEmpty());
        assertEquals(mhset.size(),mlist.size());
        assertTrue(mhset.contains(new Move(1, 4)));
        assertTrue(mhset.contains(new Move(1, 5)));
        assertTrue(mhset.contains(new Move(12, 15)));
        assertTrue(mhset.contains(new Move(12, 16)));
        assertTrue(mhset.contains(new Move(17, 20)));
        assertTrue(mhset.contains(new Move(17, 21)));
        assertTrue(mhset.contains(new Move(19, 22)));
        assertTrue(mhset.contains(new Move(19, 23)));

        bmodel.move(new Move(1,4),1,false);

        mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        assertTrue(mlist.size()==4);

        for (Move m : mlist) {
            logger.debug("Move: {}", m);
        }

        mhset = mlg.getValidMovesHashSet();
        assertTrue(mhset.contains(new Move(4, 8)));
        assertTrue(mhset.contains(new Move(12, 16)));
        assertTrue(mhset.contains(new Move(17, 21)));
        assertTrue(mhset.contains(new Move(19, 23)));

        assertFalse(mhset.contains(new Move(1, 5)));
        assertFalse(mhset.contains(new Move(1, 4)));
        assertFalse(mhset.contains(new Move(12, 15)));
        assertFalse(mhset.contains(new Move(17, 20)));
        assertFalse(mhset.contains(new Move(19, 22)));

    }

    @Test
    void getValidMoveSets() {
        ArrayList<MoveSet> msetList=mslg.getValidMoveSets();
        assertTrue (!msetList.isEmpty());
        logger.debug("msetList : size: {} {}",msetList.size(),msetList);

    }

    @Test
    void calcMoveSet() {
    }
}