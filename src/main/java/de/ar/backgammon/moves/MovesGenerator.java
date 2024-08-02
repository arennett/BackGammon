package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.dices.Dices;
import de.ar.backgammon.model.*;
import de.ar.backgammon.model.iteration.BoardAndOffPointIterator;
import de.ar.backgammon.model.iteration.BoardPointIterator;
import de.ar.backgammon.model.iteration.OccupiedBoardPointIterator;
import de.ar.backgammon.model.iteration.PointIterator;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.validation.MoveValidatorIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/** the MoveGenerator generates valid moves
 *  for the thrown dices
 *  if the bar has Pieces for the turn color
 *  only bar moves are generated if possible
 *
 */
public class MovesGenerator implements MovesGeneratorIf{
    private final BoardModelIf boardModel;
    private final MoveValidatorIf moveValidator;

    private static final Logger logger = LoggerFactory.getLogger(MovesGenerator.class);


    public MovesGenerator(BoardModelIf boardModel,MoveValidatorIf moveValidator){

        this.boardModel = boardModel;

        this.moveValidator = moveValidator;
    }

    /**
     * generates all possible moves for the given turn and dices
     * the dicesstack is part of the validator
     * @param turn
     * @return
     */
    @Override
    public ArrayList<Move> getValidMoves(BColor turn) {
        ArrayList<Move> moves = new ArrayList<>();
        BPoint barPoint= boardModel.getBarPoint(turn);
        Dices dices=moveValidator.getDicesStack().getDices();
        if (!barPoint.isEmpty()) {
            for (int dice:dices) {
                boolean valid=false;
                Move move;
                if (turn== BColor.WHITE) {
                    move =new Move(barPoint.getIndex(), barPoint.getIndex()+dice);
                }else{
                    move=new Move(barPoint.getIndex(), barPoint.getIndex()-dice);
                }
                valid = moveValidator.isValid(move,turn,1);
                if (valid) {
                    logger.debug("generate (A) move: {}",move);
                    moves.add(move);
                }
            }
        }else {

            OccupiedBoardPointIterator pointItFrom= new OccupiedBoardPointIterator(boardModel,turn);
            while (pointItFrom.hasNext()){
                BPoint fromPoint = pointItFrom.next();
                BoardAndOffPointIterator pointItTo= new BoardAndOffPointIterator(boardModel,turn);
                pointItTo.setIdx(fromPoint.getIndex());
                while (pointItTo.hasNext()){
                    BPoint toPoint = pointItTo.next();
                    if (fromPoint!=toPoint) {
                        Move move= new Move(fromPoint.getIndex(),toPoint.getIndex());
                        boolean valid = moveValidator.isValid(move, turn,1);
                        if (valid) {
                            logger.debug("generate (B) move: {}",move);
                            moves.add(move);
                        }

                    }

                }
            }


        }
        return moves;
    }
}
