package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.dices.Dices;
import de.ar.backgammon.model.*;
import de.ar.backgammon.model.iteration.BoardAndOffPointIterator;
import de.ar.backgammon.model.iteration.OccupiedBoardPointIterator;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.validation.MoveValidatorIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * the MoveGenerator generates valid moves
 * for the thrown dices
 * if the bar has Pieces for the turn color
 * only bar moves are generated if possible
 */
public class MoveListGenerator implements MoveListGeneratorIf {
    private static final Logger logger = LoggerFactory.getLogger(MoveListGenerator.class);
    private final BoardModelIf boardModel;
    private final MoveValidatorIf moveValidator;
    ArrayList<Move> moves;
    HashSet<Move> movesHashSet;

    public MoveListGenerator(BoardModelIf boardModel, MoveValidatorIf moveValidator) {
        this.boardModel = boardModel;
        this.moveValidator = moveValidator;
        this.moves = new ArrayList<>();
        this.movesHashSet = new HashSet<>();
    }

    /**
     * generates all possible moves for the given turn and dices
     *
     * @return
     */
    @Override
    public HashSet<Move> getValidMovesHashSet(){
        doGenerateMoves();
        return movesHashSet;
    }

    @Override
    public ArrayList<Move> getValidMoves() {
        doGenerateMoves();
        return moves;
    }

    private void doGenerateMoves(){
        moves.clear();
        movesHashSet.clear();

        BPoint barPoint = boardModel.getBarPoint(boardModel.getTurn());
        Dices dices = moveValidator.getDicesStack().getDices();
        if (!barPoint.isEmpty()) {
            for (int dice : dices) {
                boolean valid = false;
                Move move;
                if (boardModel.getTurn() == BColor.WHITE) {
                    move = new Move(barPoint.getIndex(), barPoint.getIndex() + dice);
                } else {
                    move = new Move(barPoint.getIndex(), barPoint.getIndex() - dice);
                }
                valid = moveValidator.isValid(move, 1);
                if (valid) {
                    logger.debug("generate (A) move: {}", move);
                    add(move);

                }
            }
        } else {

            OccupiedBoardPointIterator pointItFrom = new OccupiedBoardPointIterator(boardModel, boardModel.getTurn());
            while (pointItFrom.hasNext()) {
                BPoint fromPoint = pointItFrom.next();
                BoardAndOffPointIterator pointItTo = new BoardAndOffPointIterator(boardModel, boardModel.getTurn());
                pointItTo.setIdx(fromPoint.getIndex());
                while (pointItTo.hasNext()) {
                    BPoint toPoint = pointItTo.next();
                    if (fromPoint != toPoint) {
                        Move move = new Move(fromPoint.getIndex(), toPoint.getIndex());
                        boolean valid = moveValidator.isValid(move, 1);
                        if (valid) {
                            logger.debug("generate (B) move: {}", move);
                            add(move);
                        }

                    }

                }
            }


        }
        assert moves.size() == movesHashSet.size();

    }

    private void add(Move move) {
        moves.add(move);
        movesHashSet.add(move);
    }
}
