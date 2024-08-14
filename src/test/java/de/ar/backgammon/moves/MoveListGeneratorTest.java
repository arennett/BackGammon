package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BException;
import de.ar.backgammon.dices.Dices;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReader;
import de.ar.backgammon.model.BoardModelReaderIf;
import de.ar.backgammon.model.iteration.DicesStackIterator;
import de.ar.backgammon.model.iteration.OccupiedBoardPointIterator;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.validation.MoveValidator;
import de.ar.backgammon.validation.MoveValidatorIf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    }

    @Test
    @DisplayName("boardmap_startup_dices#3#4#turn#w")
    void test_getValidMoves1() throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#3#4#turn#w");
        mlg = new MoveListGenerator(bmodel);

        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());

        assertTrue(!mlist.isEmpty());
        assertEquals(mlist.size(), mlist.size());
        assertTrue(mlist.contains(new Move(1, 4)));
        assertTrue(mlist.contains(new Move(1, 5)));
        assertTrue(mlist.contains(new Move(12, 15)));
        assertTrue(mlist.contains(new Move(12, 16)));
        assertTrue(mlist.contains(new Move(17, 20)));
        assertTrue(mlist.contains(new Move(17, 21)));
        assertTrue(mlist.contains(new Move(19, 22)));
        assertTrue(mlist.contains(new Move(19, 23)));

        for (Move m : mlist) {
            logger.debug("test_getValidMoves1 Move: {}", m);
        }

    }

    @Test
    @DisplayName("boardmap_startup_dices#1#1#turn#w")
    void test_getValidMoves2() throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#1#1#turn#w");
        mlg = new MoveListGenerator(bmodel);

        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        assertEquals(8,mlist.size());
        assertTrue(mlist.contains(new Move(1, 2)));
        assertTrue(mlist.contains(new Move(1, 3)));
        assertTrue(mlist.contains(new Move(1, 4)));
        assertTrue(mlist.contains(new Move(1, 5)));

        assertTrue(mlist.contains(new Move(17, 18)));
        assertTrue(mlist.contains(new Move(19, 20)));
        assertTrue(mlist.contains(new Move(19, 21)));
        assertTrue(mlist.contains(new Move(19, 22)));


        for (Move m : mlist) {
            logger.debug("test_getValidMoves2 Move: {}", m);
        }

    }

    @Test
    @DisplayName("boardmap_bar2w_dices#3#4#turn#w")
    void test_getValidMoves3() throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_bar2w_dices#3#4#turn#w");
        mlg = new MoveListGenerator(bmodel);

        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        assertEquals(2,mlist.size());
        assertTrue(mlist.contains(new Move(0, 3)));
        assertTrue(mlist.contains(new Move(0, 4)));

        for (Move m : mlist) {
            logger.debug("test_getValidMoves3 Move: {}", m);
        }
    }

    @Test
    @DisplayName("boardmap_off_dices#6#2#turn#w")
    void test_getValidMoves4() throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_off_dices#6#2#turn#w");
        mlg = new MoveListGenerator(bmodel);

        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        assertEquals(3,mlist.size());
        assertTrue(mlist.contains(new Move(19, 27)));
        assertTrue(mlist.contains(new Move(20, 22)));
        assertTrue(mlist.contains(new Move(21, 23)));

        for (Move m : mlist) {
            logger.debug("test_getValidMoves4 Move: {}", m);
        }
    }


}