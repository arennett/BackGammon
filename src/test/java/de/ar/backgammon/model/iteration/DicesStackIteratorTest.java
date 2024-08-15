package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BException;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelReader;
import de.ar.backgammon.model.BoardModelReaderIf;
import de.ar.backgammon.points.BPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DicesStackIteratorTest {
    static Logger logger = LoggerFactory.getLogger(DicesStackIteratorTest.class);
    BoardModel boardModel;
    BoardModelReaderIf breader;
    @BeforeEach
    public void setUp() throws IOException, BException {
        boardModel = new BoardModel();
        breader = new BoardModelReader();

    }

    @Test
    public void test1() throws IOException, BException {
        breader.readTestModel(boardModel, "boardmap_startup_dices#3#4#turn#w");
        DicesStackIterator dstackIterator
                = new DicesStackIterator(boardModel,BColor.WHITE,BoardModel.POINT_IDX_FIRST_BOARD_POINT);
        assertTrue(dstackIterator.hasNext());
        while (dstackIterator.hasNext()){
            BPoint bpoint = dstackIterator.next();
            logger.debug("Test1: {}",bpoint);
        }
    }

    @Test
    public void test2() throws IOException, BException {
        breader.readTestModel(boardModel, "boardmap_startup_dices#1#1#turn#w");
        DicesStackIterator dstackIterator
                = new DicesStackIterator(boardModel,BColor.WHITE,BoardModel.POINT_IDX_FIRST_BOARD_POINT);
        assertTrue(dstackIterator.hasNext());
        while (dstackIterator.hasNext()){
            BPoint bpoint = dstackIterator.next();
            logger.debug("Test2: {}",bpoint);
        }
    }

    @Test
    public void test3() throws IOException, BException {
        breader.readTestModel(boardModel, "boardmap_bar2w_dices#3#4#turn#w");
        DicesStackIterator dstackIterator
                = new DicesStackIterator(boardModel,BColor.WHITE,BoardModel.POINT_IDX_BAR_WHITE);
        assertTrue(dstackIterator.hasNext());
        while (dstackIterator.hasNext()){
            BPoint bpoint = dstackIterator.next();
            logger.debug("Test3: {}",bpoint);
        }
    }


    @Test
    public void test4() throws IOException, BException {
        breader.readTestModel(boardModel, "boardmap_off_dices#6#2#turn#w");
        DicesStackIterator dstackIterator
                = new DicesStackIterator(boardModel,BColor.WHITE,19);
        assertTrue(dstackIterator.hasNext());
        while (dstackIterator.hasNext()){
            BPoint bpoint = dstackIterator.next();
            logger.debug("Test4: {}",bpoint);
        }
    }


}