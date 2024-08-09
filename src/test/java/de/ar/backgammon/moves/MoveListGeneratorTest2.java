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

class MoveListGeneratorTest2 {
    static Logger logger = LoggerFactory.getLogger(MoveListGeneratorTest2.class);
    BoardModelIf bmodel;
    MoveListGenerator mlg;

    @BeforeEach
    public void setUp() throws IOException, BException {
        bmodel = new BoardModel();
        MoveValidator moveValidator = new MoveValidator(bmodel);
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "MoveSetListGeneratorTest2");
        mlg = new MoveListGenerator(bmodel);
    }


    @Test
    void test_getValidMoves3() {
        ArrayList<Move> mlist = mlg.getValidMoves();
        assertTrue(!mlist.isEmpty());

        HashSet<Move> mhset = mlg.getValidMovesHashSet();
        assertTrue(!mhset.isEmpty());
        assertEquals(mhset.size(),mlist.size());
        assertTrue(mhset.contains(new Move(24, 23)));
        assertTrue(mhset.contains(new Move(24, 22)));
        assertTrue(mhset.contains(new Move(24, 21)));
        assertFalse(mhset.contains(new Move(4, 3)));


        boolean moved =bmodel.move(new Move(6,4),1,false);
        assert(!moved);


    }
}