package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardMoveIterator;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.dices.Dices;
import de.ar.backgammon.GameControl;
import de.ar.backgammon.model.BoardModelIf;
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
    @Override
    public ArrayList<Move> getValidMoves(Dices dices, BColor turn) {
        ArrayList<Move> moves = new ArrayList<>();
        BPoint barPoint= boardModel.getBarPoint(turn);

        if (!barPoint.isEmpty()) {
            for (int dice:dices) {
                boolean valid=false;
                Move move;
                if (turn== BColor.WHITE) {
                    move =new Move(barPoint.getIndex(), barPoint.getIndex()+dice);
                }else{
                    move=new Move(barPoint.getIndex(), barPoint.getIndex()-dice);
                }
                valid = moveValidator.isValid(move,turn);
                if (valid) {
                    logger.debug("generate move: {}",move);
                    moves.add(move);
                }
            }
        }else {
            BoardMoveIterator  pointItFrom= new BoardMoveIterator(boardModel,turn);
            while (pointItFrom.hasNext()){
                BPoint fromPoint = pointItFrom.next();
                BoardMoveIterator  pointItTo= new BoardMoveIterator(boardModel,turn);
                while (pointItTo.hasNext()){
                    BPoint toPoint = pointItTo.next();
                    if (fromPoint!=toPoint) {
                        Move move= new Move(fromPoint.getIndex(),toPoint.getIndex());

                        boolean valid = moveValidator.isValid(move, turn);
                        if (valid) {
                            logger.debug("generate move: {}",move);
                            moves.add(move);
                        }

                    }

                }
            }


        }
        return moves;
    }
}
