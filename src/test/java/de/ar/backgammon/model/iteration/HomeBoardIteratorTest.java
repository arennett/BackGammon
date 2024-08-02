package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.points.BPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeBoardIteratorTest {
    HomeBoardIterator pitWhite, pitRed, pitRedMin10Max20, pitWhiteMin10Max20;
    static BoardModelIf bmodel;
    @BeforeAll
    static public void init(){
        bmodel= new BoardModel();
    }

    @BeforeEach
    void setUp() {

        pitWhite = new HomeBoardIterator(bmodel, BColor.WHITE);
        pitRed = new HomeBoardIterator(bmodel, BColor.RED);
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
        assertEquals(point.getIndex(), BoardModel.POINT_IDX_LAST_BOARD_POINT);

        assertTrue(pitRed.hasNext());
        point = pitRed.next();
        assertEquals(point.getIndex(), BoardModel.POINT_IDX_FIRST_BOARD_POINT);

    }

    @Test
    void iterateToEnd() {
        BPoint point = null;
        int count=0;
        while (pitWhite.hasNext()) {
            point = pitWhite.next();
            count++;
        }
        assertEquals(6,count);
        assertEquals(point.getIndex(),19);
        count=0;
        while (pitRed.hasNext()) {
            point = pitRed.next();
            count++;
        }
        assertEquals(6,count);
        assertEquals(point.getIndex(), 6);

    }
}