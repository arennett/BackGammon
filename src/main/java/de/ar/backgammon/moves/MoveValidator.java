package de.ar.backgammon.moves;

import de.ar.backgammon.*;
import de.ar.backgammon.dices.DicesStack;
import de.ar.backgammon.dices.PipSequence;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.points.PointValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MoveValidator implements MoveValidatorIf{
    private static final Logger logger = LoggerFactory.getLogger(MoveValidator.class);
    private final BoardModelIf boardModel;
    private final DicesStack dicesStack;
    public ValidationError err;
    private PointValidator pointValidator = new PointValidator();


    public MoveValidator(BoardModelIf boardModel, DicesStack dicesStack){
        this.boardModel = boardModel;
        this.dicesStack = dicesStack;
        err=new ValidationError();
    }

    @Override
    public boolean isValid(Move move, BColor turn) {
        boolean ret = false;

        BPoint bpFrom = boardModel.getPoint(move.from);
        BPoint bpTo = boardModel.getPoint(move.to);

        if (bpFrom.getPieceColor() != turn){
            err.nr=1;
            err.userMessage ="Wrong color! Turn is "+turn.getString();
            logger.debug("err<{}> move<{}> <{}>",err.nr,move,err.userMessage);
            return false;
        }

        int spc;
        if (move.isBarMove()) {
            spc = 1;

        }else{
            spc = boardModel.getStartPointSelectedPiecesCount();
        }

        if (spc < 1) {
            err.nr=2;
            err.userMessage ="No pieces selected";
            logger.debug("err<{}> move<{}> <{}>",err.nr,move,err.userMessage);
            return false;
        }
        if (spc > 4) {
            err.nr=3;
            err.userMessage ="You can only select up to 4 pieces";
            logger.debug("err<{}> move<{}> <{}>",err.nr,move,err.userMessage);
             return false;
        }

        if (move.isOffMove()){
            logger.debug ("offmove detected");
            if (!boardModel.isAllPiecesAtHome(bpFrom.getPieceColor())){
                err.nr=4;
                err.userMessage ="Not all pieces at home";
                logger.debug("err<{}> move<{}> <{}>",err.nr,move,err.userMessage);
                return false;
            }
        }

        if (turn == BColor.RED && !boardModel.getBarPoint(BColor.RED).isEmpty()){
            if (bpFrom!=boardModel.getBarPoint(BColor.RED)){
                err.nr=5;
                err.userMessage ="Red! You have pieces on the bar";
                logger.debug("err<{}> move<{}> <{}>",err.nr,move,err.userMessage);
                return false;
            }
        }
        if (turn == BColor.WHITE && !boardModel.getBarPoint(BColor.WHITE).isEmpty()){
            if (bpFrom!=boardModel.getBarPoint(BColor.WHITE)){
                err.nr=6;
                err.userMessage ="White! You have pieces on the bar";
                logger.debug("err<{}> move<{}> <{}>",err.nr,move,err.userMessage);
                return false;

            }
        }

        ret = pointValidator.isValid(bpTo, spc,turn);

        if (!ret) {
            logger.error("unvalid point");
            err= pointValidator.err;
            return false;
        }

        if(!move.isOffMove()){
            if (turn == BColor.WHITE) {
                ret = move.to > move.from;

            } else {
                ret = move.to < move.from;
            }
            if (!ret) {
                err.nr=7;
                err.userMessage ="Wrong direction !";
                logger.debug("err<{}> move<{}> <{}>",err.nr,move,err.userMessage);
                return false;

            }
        }

        //check dices

        int range = move.getRange(turn);
        logger.debug("check dices for move range: {}",range);

        ArrayList<PipSequence> psArray = dicesStack.getSequenceStack().getValidSequences(move, spc, turn);
        if (psArray.isEmpty()) {
            // maybe only one pip on stack
            logger.debug("no sequences found");
            if (range <= 6) {
                ret = dicesStack.isMoveOnStack(range, spc);
                if (!ret) {
                     err.nr=8;
                    err.userMessage ="Not allowed! Look at the dices.";
                    logger.debug("err<{}> move<{}> <{}>",err.nr,move,err.userMessage);
                    return false;

                }
            } else {
                err.nr=9;
                err.userMessage ="Not allowed! Look at the dices.";
                logger.debug("err<{}> move<{}> <{}>",err.nr,move,err.userMessage);
                return false;

            }
        }


        return true;
    }


}
