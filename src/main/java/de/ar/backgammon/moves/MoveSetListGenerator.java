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
    BoardModel cModel;
    MoveListGenerator mlGen;

    MoveSet mstest;

    public MoveSetListGenerator(BoardModelIf bModel) {

        this.bModel = bModel;
        cModel = new BoardModel();
        MoveValidatorIf moveValidator = new MoveValidator(cModel);
        cModel.setMoveValidator(moveValidator);
        mlGen = new MoveListGenerator(cModel);

        mstest = new MoveSet();
        mstest.add(new Move(8,7));
        mstest.add(new Move(6,5));
        mstest.add(new Move(6,5));
        mstest.add(new Move(6,5));

    }

    /**
       return a list of possible movesets
     */
    @Override
    public MoveSetHash getValidMoveSets() {
        /**
         * in the first iteration we start with one empty moveset
         * the result is a list of 1 move moveset
         * we add this to the moveset for the next iteration
         * in the second iteration we get a list of 2 movements
         * and so on
         */

        MoveSetHash msetList = new MoveSetHash();
        MoveSetHash msetResultList = new MoveSetHash();
        MoveSetHash msetRemoveList = new MoveSetHash();

        msetList.add(new MoveSet()); //we start the iteration with an empty moveset

        // a moveset is finished if the MoveListGenerator generates any valid moves

        boolean allFinished = false;

        while (!allFinished) {

            boolean _allFinished = true;  // reverse logic, so if at least one moveset is not finsihed
                                          // _allfinsihed is false

            msetRemoveList.clear();

            for (MoveSet moveSet : msetList) {
                msetRemoveList.add(moveSet);
                ArrayList<MoveSet> list = calcMoveSet(moveSet);
                msetResultList.addAll(list);
                if (!moveSet.isFinished()) {
                    _allFinished = false;
                }
            }
            allFinished = _allFinished;
            msetList.clear();
            msetList.addAll(msetResultList);
            msetList.removeAll(msetRemoveList);
            if (!allFinished){
                msetResultList.removeAll(msetRemoveList);
            }
        }

       // TODO clean doubles
       return msetResultList;
    }

    public ArrayList<MoveSet> calcMoveSet(MoveSet mset) {
        // TEST TEST TEST
        //[(17) >> (18),(19) >> (20),(19) >> (20),
        //  8   >>  7  , 6  >> 5, 6  >> 5





        ArrayList<MoveSet> moveSetList = new ArrayList<>();
        if (mset.isFinished()) {
            return moveSetList;
        }
        /*prepare Model*/
        cModel.clear();
        resetModel(cModel);

        logger.debug("preparing model with mset: {}", mset);
        for (Move move : mset) {
            boolean moved = cModel.move(move, 1, false);
            if (!moved) {
                int test = 0;
            }
            assert (moved);

        }


        ArrayList<Move> moves = mlGen.getValidMoves();
        if (moves.isEmpty()) {
            mset.setFinished(true);
            return moveSetList;
        }
        /*
        copy moves from mset and add new move
         */
        for (Move move : moves) {
            MoveSet moveset = new MoveSet();
            for (Move mmove : mset) {
                moveset.add(mmove);
            }
            moveset.add(move);
            moveSetList.add(moveset);

            // TODO TEST
            String msteststr=mstest.toSortedString();
            String movesetstr=moveset.toSortedString();
            if (msteststr.equals(movesetstr)){
                int test = 0;
            }

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
