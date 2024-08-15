package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.dices.Dices;
import de.ar.backgammon.model.*;
import de.ar.backgammon.model.iteration.BoardAndOffPointIterator;
import de.ar.backgammon.model.iteration.DicesStackIterator;
import de.ar.backgammon.model.iteration.OccupiedBoardPointIterator;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.validation.MoveValidator;
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

    public MoveListGenerator(BoardModelIf boardModel) {
        this.boardModel = boardModel;
        this.moveValidator = boardModel.getMoveValidator();
        this.moves = new ArrayList<>();
        this.movesHashSet = new HashSet<>();
    }



    @Override
    public ArrayList<Move> getValidMoves() {
        doGenerateMoves();
        return moves;
    }

    private void doGenerateMoves() {
        moves.clear();
        movesHashSet.clear();

        BPoint barPoint = boardModel.getBarPoint(boardModel.getTurn());
        Dices dices = moveValidator.getDicesStack().getDices();
        assert !dices.isEmpty();
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
            int count_movevalidations = 0;
            while (pointItFrom.hasNext()) {
                BPoint fromPoint = pointItFrom.next();
                DicesStackIterator pointItTo = new DicesStackIterator(boardModel, boardModel.getTurn(),fromPoint.getIndex());
                logger.debug("### occupied from point: {}", fromPoint);
              while (pointItTo.hasNext()) {
                    BPoint toPoint = pointItTo.next();
                    if (fromPoint != toPoint) {
                        logger.debug("### to point: {}", toPoint);
                        Move move = new Move(fromPoint.getIndex(), toPoint.getIndex());
                        count_movevalidations++;
                        boolean valid = moveValidator.isValid(move, 1);
                        if (valid) {
                            logger.debug("generate (B) move: {}", move);
                            add(move);
                        }

                    }
                }

            }
            logger.debug("count move validations: {}", count_movevalidations);


        }
        assert moves.size() == movesHashSet.size();

    }

    private void add(Move move) {
        moves.add(move);
        movesHashSet.add(move);
    }
}
