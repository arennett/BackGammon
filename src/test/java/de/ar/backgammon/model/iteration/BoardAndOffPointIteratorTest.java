package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.points.BPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardAndOffPointIteratorTest {

    BoardAndOffPointIterator pitWhite, pitRed;
    static BoardModelIf bmodel;
    @BeforeAll
    static public void init(){
        bmodel= new BoardModel();
    }

    @BeforeEach
    void setUp() {
        pitWhite = new BoardAndOffPointIterator(bmodel, BColor.WHITE);
        pitRed = new BoardAndOffPointIterator(bmodel, BColor.RED);
    }

    @Test
    void hasNext() {
        assertTrue(pitWhite.hasNext());
        assertTrue(pitRed.hasNext());
    }

    @Test
    @DisplayName("iterate to the firstElement")
    void firstNext() {
        BPoint point;
        assertTrue(pitWhite.hasNext());
        point = pitWhite.next();
        assertEquals(point.getIndex(), BoardModel.POINT_IDX_FIRST_BOARD_POINT);
        assertEquals(pitWhite.getIdx(),BoardModel.POINT_IDX_FIRST_BOARD_POINT);
        assertTrue(pitRed.hasNext());
        point = pitRed.next();
        assertEquals(point.getIndex(), BoardModel.POINT_IDX_LAST_BOARD_POINT);
        assertEquals(pitRed.getIdx(),BoardModel.POINT_IDX_LAST_BOARD_POINT);

    }

    @Test
    void iterateToEnd() {
        BPoint point = null;
        while (pitWhite.hasNext()) {
            point = pitWhite.next();
        }
        assertEquals(point.getIndex(),BoardModel.POINT_IDX_OFF_WHITE);
        assertEquals(pitWhite.getIdx(),BoardModel.POINT_IDX_OFF_WHITE);


        while (pitRed.hasNext()) {
            point = pitRed.next();
        }
        assertEquals(point.getIndex(), BoardModel.POINT_IDX_OFF_RED);
        assertEquals(pitRed.getIdx(),BoardModel.POINT_IDX_OFF_RED);

    }
}
