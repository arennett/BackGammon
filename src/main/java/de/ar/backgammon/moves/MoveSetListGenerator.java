package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BException;
import de.ar.backgammon.model.*;
import de.ar.backgammon.validation.MoveValidator;
import de.ar.backgammon.validation.MoveValidatorIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MoveSetListGenerator implements MoveSetListGeneratorIf {
    static Logger logger = LoggerFactory.getLogger(MoveSetListGenerator.class);
    private final BoardModelIf bModel;
    BoardModelWriterIf bwriter = new BoardModelWriter();
    BoardModelReaderIf breader = new BoardModelReader();
    BoardModel cModel ;
    MoveListGenerator mlGen;

    public MoveSetListGenerator(BoardModelIf bModel, MoveValidatorIf moveValidator) {

        this.bModel = bModel;
        cModel = new BoardModel();
        cModel.setMoveValidator(moveValidator);
        mlGen =new MoveListGenerator(cModel,moveValidator);

    }

    @Override
    public ArrayList<MoveSet> getValidMoveSets() {
        // create copy of boardModel
        // list mlist = list of first valid moves f(dices)
        // for all move in mlist
        //      add move to mset
        //      mlist = list of first valid moves f(dices)

        ArrayList<MoveSet> msetList = new ArrayList<>();
        ArrayList<MoveSet> msetResultList = new ArrayList<>();
        ArrayList<MoveSet> msetRemoveList = new ArrayList<>();

        msetList.add(new MoveSet());
        boolean allFinished =false;
        while (!allFinished) {
            boolean _allFinished =true;
            msetRemoveList.clear();
            msetResultList.clear();
            for (MoveSet moveSet : msetList) {
                      ArrayList<MoveSet> list = calcMoveSet(moveSet);
                    msetResultList.addAll(list);
                    msetRemoveList.add(moveSet);
                    if (!moveSet.isFinished()) {
                        _allFinished = false;
                    }

            }
            allFinished=_allFinished;
            msetList.removeAll(msetRemoveList);
            msetList.addAll(msetResultList);
        }
        return null;
    }

    public ArrayList<MoveSet> calcMoveSet(MoveSet mset){
        ArrayList<MoveSet> moveSetList = new ArrayList<>();
        if (mset.isFinished()){
            return moveSetList;
        }
        /*prepare Model*/
        resetModel(cModel);
        logger.debug("preparing model with mset: {}",mset);
        for(Move move:mset){
               boolean moved= cModel.move(move,1,false);
               assert (moved);
        }
        ArrayList<Move> moves = mlGen.getValidMoves();
        if (moves.isEmpty()){
            mset.setFinished(true);
            return moveSetList;
        }
        /*
        copy moves from mset and add new move
         */
        for (Move move : moves){
            MoveSet moveset = new MoveSet();
            for (Move mmove:mset){
                moveset.add(mmove);
            }
            moveset.add(move);
            moveSetList.add(moveset);
        }
        return moveSetList;

    }

    private void resetModel(BoardModel cModel) {
        try {
            bModel.copy(cModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (BException e) {
            throw new RuntimeException(e);
        }
    }


}
