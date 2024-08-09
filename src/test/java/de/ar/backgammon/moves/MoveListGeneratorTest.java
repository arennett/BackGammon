package de.ar.backgammon.moves;

import de.ar.backgammon.BException;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReader;
import de.ar.backgammon.model.BoardModelReaderIf;
import de.ar.backgammon.validation.MoveValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

class MoveListGeneratorTest {
    static Logger logger = LoggerFactory.getLogger(MoveListGeneratorTest.class);
    BoardModelIf bmodel;
    MoveListGenerator mlg;

    @BeforeEach
    public void setUp() throws IOException, BException {
        bmodel = new BoardModel();
        MoveValidator moveValidator = new MoveValidator(bmodel);
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "testMoveListGeneratorTest");
        mlg = new MoveListGenerator(bmodel);
    }

    @Test
    void test_getValidMoves() {
        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        for (Move m : mlist) {
            logger.debug("Move: {}", m);
        }
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
        for (Move m : mlist) {
            logger.debug("Move: {}", m);
        }

        mhset = mlg.getValidMovesHashSet();
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
    void test_getValidMoves3() {
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

        boolean moved =bmodel.move(new Move(1,6),1,false);
        assert(!moved);


    }
}