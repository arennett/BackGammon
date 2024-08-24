package de.ar.backgammon.moves;

import de.ar.backgammon.BException;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReader;
import de.ar.backgammon.model.BoardModelReaderIf;
import de.ar.backgammon.validation.MoveValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class MoveListGeneratorTurnRedTest {
    static Logger logger = LoggerFactory.getLogger(MoveListGeneratorTurnRedTest.class);
    BoardModelIf bmodel;
    MoveListGenerator mlg;

    @BeforeEach
    public void setUp() throws IOException, BException {
        bmodel = new BoardModel();
        MoveValidator moveValidator = new MoveValidator(bmodel);

    }

    @Test
    @DisplayName("boardmap_startup_dices#3#4#turn#r")
    void test_getValidMoves1() throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#3#4#turn#r");
        mlg = new MoveListGenerator(bmodel);
        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        assertTrue(mlist.contains(new Move(24, 21)));
        assertTrue(mlist.contains(new Move(24, 20)));
        assertTrue(mlist.contains(new Move(13, 10)));
        assertTrue(mlist.contains(new Move(13,  9)));
        assertTrue(mlist.contains(new Move( 8,  5)));
        assertTrue(mlist.contains(new Move( 8,  4)));
        assertTrue(mlist.contains(new Move( 6,  3)));
        assertTrue(mlist.contains(new Move( 6,  2)));


         assertEquals(mlist.size(), 8);

        for (Move m : mlist) {
            logger.debug("test_getValidMoves1 Move: {}", m);
        }

    }

    @Test
    @DisplayName("boardmap_startup_dices#1#1#turn#r")
    void test_getValidMoves2() throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#1#1#turn#r");
        mlg = new MoveListGenerator(bmodel);

        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        for (Move m : mlist) {
            logger.debug("test_getValidMoves2 Move: {}", m);
        }
        assertTrue(mlist.contains(new Move(24, 23)));
        assertTrue(mlist.contains(new Move(24, 22)));
        assertTrue(mlist.contains(new Move(24, 21)));
        assertTrue(mlist.contains(new Move(24, 20)));
        assertTrue(mlist.contains(new Move( 8,  7)));
        assertTrue(mlist.contains(new Move( 6,  5)));
        assertTrue(mlist.contains(new Move( 6,  4)));
        assertTrue(mlist.contains(new Move( 6,  3)));
        assertTrue(mlist.contains(new Move( 6,  2)));

        assertEquals(9,mlist.size());
    }
    @Test
    void test_getValidMoves2b() throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_startup_dices#1#1#turn#r");

        // MOVE !!!!
        bmodel.move(new Move(24, 23),1,false);


        mlg = new MoveListGenerator(bmodel);
        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        for (Move m : mlist) {
            logger.debug("test_getValidMoves2b Move: {}", m);
        }
        assertTrue(mlist.contains(new Move(24, 23)));
        assertTrue(mlist.contains(new Move(24, 22)));
        assertTrue(mlist.contains(new Move(24, 21)));
        assertTrue(mlist.contains(new Move( 8,  7)));
        assertTrue(mlist.contains(new Move( 6,  5)));
        assertTrue(mlist.contains(new Move( 6,  4)));
        assertTrue(mlist.contains(new Move( 6,  3)));


        assertEquals(10,mlist.size());
    }

    @Test
    @DisplayName("boardmap_bar2r_dices#3#4#turn#r")
    void test_getValidMoves3() throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_bar2r_dices#3#4#turn#r");
        mlg = new MoveListGenerator(bmodel);

        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        assertTrue(mlist.contains(new Move(25,22)));
        assertTrue(mlist.contains(new Move(25,21)));
        assertEquals(2,mlist.size());

        for (Move m : mlist) {
            logger.debug("test_getValidMoves3 Move: {}", m);
        }
    }

    @Test
    @DisplayName("boardmap_off_dices#6#2#turn#r")
    void test_getValidMoves4() throws IOException, BException {
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "boardmap_off_dices#6#2#turn#r");
        mlg = new MoveListGenerator(bmodel);

        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());
        assertTrue(mlist.contains(new Move(6,4)));
        assertTrue(mlist.contains(new Move(6,26)));
        assertTrue(mlist.contains(new Move(5,3)));
        assertTrue(mlist.contains(new Move(4,2)));

        assertEquals(4,mlist.size());

        for (Move m : mlist) {
            logger.debug("test_getValidMoves4 Move: {}", m);
        }
    }


}