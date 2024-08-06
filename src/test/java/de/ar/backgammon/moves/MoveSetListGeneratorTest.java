package de.ar.backgammon.moves;

import de.ar.backgammon.BException;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReader;
import de.ar.backgammon.model.BoardModelReaderIf;
import de.ar.backgammon.validation.MoveValidator;
import de.ar.backgammon.validation.MoveValidatorIf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

class MoveSetListGeneratorTest {
    static Logger logger = LoggerFactory.getLogger(MoveSetListGeneratorTest.class);
    BoardModelIf bmodel;
    MoveSetListGenerator mslg;

    @BeforeEach
    public void setUp() throws IOException, BException {

        BoardModelIf bmodel = new BoardModel();
        MoveValidatorIf moveValidator =new MoveValidator(bmodel);
        bmodel.setMoveValidator(moveValidator);
        BoardModelReaderIf breader = new BoardModelReader();
        breader.readTestModel(bmodel, "testMoveListGeneratorTest");
        mslg = new MoveSetListGenerator(bmodel,moveValidator);
    }



    @Test
    void getValidMoveSets() {
        ArrayList<MoveSet> msetList=mslg.getValidMoveSets();
        assertTrue (!msetList.isEmpty());

    }

    @Test
    void calcMoveSet() {
    }
}