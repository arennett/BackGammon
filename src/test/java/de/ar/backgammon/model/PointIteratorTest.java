package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.points.BPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointIteratorTest {
    PointIterator pitWhite,pitRed, pitRedMin10Max20,pitWhiteMin10Max20;
    public PointIteratorTest(){
        init();
    }

    @BeforeEach
    void init() {
        BoardModelIf bmodel = new BoardModel();
        pitWhite = new PointIterator(bmodel, BColor.WHITE,
                BoardModel.POINT_IDX_FIRST_BOARD_POINT,
                BoardModel.POINT_IDX_LAST_BOARD_POINT );
        pitRed = new PointIterator(bmodel, BColor.RED,
                BoardModel.POINT_IDX_FIRST_BOARD_POINT,
                BoardModel.POINT_IDX_LAST_BOARD_POINT );

        pitRedMin10Max20 = new PointIterator(bmodel, BColor.RED,
                10,
                20 );
        pitWhiteMin10Max20 = new PointIterator(bmodel, BColor.WHITE,
                10,
                20 );

    }

    @Test
    void hasNext() {
        assertTrue(pitWhite.hasNext());
        assertTrue(pitRed.hasNext());
    }

    @Test
    @DisplayName("iterate to the firstElement")
    void firstNext() {
        BPoint point= pitWhite.next();
        assertEquals(point.getIndex(),BoardModel.POINT_IDX_FIRST_BOARD_POINT);
        point= pitRed.next();
        assertEquals(point.getIndex(),BoardModel.POINT_IDX_LAST_BOARD_POINT);
    }

    @Test
    void iterateToEnd() {
        BPoint point=null;
        while (pitWhite.hasNext()){
            point=pitWhite.next();
        }
        assertEquals(point.getIndex(),BoardModel.POINT_IDX_LAST_BOARD_POINT);
        while (pitRed.hasNext()){
            point=pitRed.next();
        }
        assertEquals(point.getIndex(),BoardModel.POINT_IDX_FIRST_BOARD_POINT);
    }

    @Test
    void  iterateMin10Max20(){
        BPoint point=null;
        while (pitRedMin10Max20.hasNext()){
            point= pitRedMin10Max20.next();
        }
        assertEquals(point.getIndex(),10);
        while (pitWhiteMin10Max20.hasNext()){
            point= pitWhiteMin10Max20.next();
        }
        assertEquals(point.getIndex(),20);


    }


}