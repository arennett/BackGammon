package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BException;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReader;
import de.ar.backgammon.model.BoardModelReaderIf;
import de.ar.backgammon.points.BPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OccupiedPointIteratorTest {
    static Logger logger = LoggerFactory.getLogger(OccupiedPointIteratorTest.class);
    OccupiedPointIterator opitWhite;
    OccupiedPointIterator opitRed;
    static BoardModelIf boardModel;

    @BeforeAll
    static public void init(){
        boardModel= new BoardModel();
    }


    @BeforeEach
    void setUp() throws IOException, BException {

        BoardModelReaderIf bmodelReader = new BoardModelReader();
        bmodelReader.readTestModel(boardModel,"testOccupiedPointIteratorMap");
        opitWhite = new OccupiedPointIterator(boardModel, BColor.WHITE,
                BoardModel.POINT_IDX_FIRST_BOARD_POINT,
                BoardModel.POINT_IDX_LAST_BOARD_POINT);
        opitRed = new OccupiedPointIterator(boardModel, BColor.RED,
                BoardModel.POINT_IDX_FIRST_BOARD_POINT,
                BoardModel.POINT_IDX_LAST_BOARD_POINT);

    }

    @Test
    void hasNext() {
        assertTrue(opitWhite.hasNext());
        assertTrue(opitRed.hasNext());
    }

    @Test
    void next() {
        BPoint point = opitWhite.next();
        assertEquals(1,point.getIndex());
        assertTrue(opitWhite.hasNext());
        point  = opitWhite.next();
        assertEquals(12,point.getIndex());
        assertTrue(opitWhite.hasNext());
        point  = opitWhite.next();
        assertEquals(17,point.getIndex());
        assertTrue(opitWhite.hasNext());
        point  = opitWhite.next();
        assertEquals(19,point.getIndex());
        assertFalse(opitWhite.hasNext());

        point = opitRed.next();
        assertEquals(24,point.getIndex());
        assertTrue(opitRed.hasNext());
        point  = opitRed.next();
        assertEquals(13,point.getIndex());
        assertTrue(opitRed.hasNext());
        point  = opitRed.next();
        assertEquals(8,point.getIndex());
        assertTrue(opitRed.hasNext());
        point  = opitRed.next();
        assertEquals(6,point.getIndex());
        assertFalse(opitRed.hasNext());
    }
}