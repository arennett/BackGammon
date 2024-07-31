package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BException;
import de.ar.backgammon.points.BPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OccupiedPointIteratorTest {
    OccupiedPointIterator opitWhite;
    OccupiedPointIterator opitRed;
    @BeforeEach
    void init() throws IOException, BException {
        BoardModelIf boardModel = new BoardModel();
        BoardModelReaderIf  bmodelReader = new BoardModelReader();
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