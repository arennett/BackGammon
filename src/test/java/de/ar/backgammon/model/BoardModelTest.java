package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardModelTest {
    static Logger logger = LoggerFactory.getLogger(BoardModelTest.class);

    @Test
    void getMinHomePipArray() throws IOException, BException {
        BoardModelIf boardModel=new BoardModel();
        BoardModelReaderIf bmodelReader = new BoardModelReader();
        bmodelReader.readTestModel(boardModel,"testBoardModel_getMaxHomePipArray");
        ArrayList<Integer> arr=boardModel.getMinHomePipArray(BColor.WHITE);
        assertTrue(!arr.isEmpty());
        assertEquals(9,arr.size());
        for (Integer pip:arr){
            logger.debug("Test MinHomePip: {}",pip);
        }
    }
    @Test
    void getMaxHomePipArray() throws IOException, BException {
        BoardModelIf boardModel=new BoardModel();
        BoardModelReaderIf bmodelReader = new BoardModelReader();
        bmodelReader.readTestModel(boardModel,"testBoardModel_getMaxHomePipArray");
        ArrayList<Integer> arr=boardModel.getMaxHomePipArray(BColor.WHITE);
        assertTrue(!arr.isEmpty());
        assertEquals(9,arr.size());
        for (Integer pip:arr){
            logger.debug("Test MaxHomePip: {}",pip);
        }
    }
}