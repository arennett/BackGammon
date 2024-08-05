package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

public class MoveSetListGenerator implements MoveSetListGeneratorIf {
    static Logger logger = LoggerFactory.getLogger(MoveSetListGenerator.class);
    private final BoardModelIf bModel;
    BoardModelWriterIf bwriter = new BoardModelWriter();
    BoardModelReaderIf breader = new BoardModelReader();

    public MoveSetListGenerator(BoardModelIf bModel) {

        this.bModel = bModel;
    }

    @Override
    public ArrayList<MoveSet> getValidMoveSets(BColor turn) {
        // create copy of boardModel
        // list mlist = list of first valid moves f(dices)
        // for all move in mlist
        //      add move to mset
        //      mlist = list of first valid moves f(dices)

        //BoardModel cModel = bModel,cModel);
        return null;
    }

    private void copyModel(BoardModelIf fromModel, BoardModel toModel) {
        try {
            bwriter.write("tempModel", fromModel);
        } catch (IOException e) {
            logger.error("writing tempModel failed");
            throw new RuntimeException(e);
        }
        try {
            breader.readModel(toModel, "tempModel");
        } catch (Exception e) {
            logger.error("reading tempModel failed");
            throw new RuntimeException(e);
        }
    }
}
