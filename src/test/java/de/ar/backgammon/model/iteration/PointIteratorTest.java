package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.points.BPoint;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointIteratorTest {
    PointIterator pitWhite,pitRed, pitRedMin10Max20,pitWhiteMin10Max20,pitWhite1;
    static BoardModelIf bmodel;
    @BeforeAll
    static public void init(){
        bmodel= new BoardModel();
    }
    @BeforeEach
    void setUp() {
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

        pitWhite1= new PointIterator(bmodel, BColor.WHITE,1,1);

    }


    @Test
    void  iterateRed(){
        BPoint point=null;
        assertTrue (pitRed.hasNext());
        point= pitRed.next();
        assertEquals(point.getIndex(),24);
        while (pitRed.hasNext()){
            point= pitRed.next();
        }
        assertEquals(point.getIndex(),1);
    }
    @Test
    void  iterateWhite(){
        BPoint point=null;
        assertTrue (pitWhite.hasNext());
        point= pitWhite.next();
        assertEquals(point.getIndex(),1);
        while (pitWhite.hasNext()){
            point= pitWhite.next();
        }
        assertEquals(point.getIndex(),24);
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

    @Test
    @DisplayName("iterateWhite1(): iterate over an one element Iterator")
    void  iterateWhite1(){
        BPoint point=null;
        while (pitWhite1.hasNext()){
            point= pitWhite1.next();
        }
        assertEquals(point.getIndex(),1);
        assertFalse(pitWhite1.hasNext());

    }



}