package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BPoint;
import de.ar.backgammon.dices.Dices;
import de.ar.backgammon.GameControl;
import de.ar.backgammon.model.BoardModelIf;

import java.util.ArrayList;

public class MovesGenerator implements MovesGeneratorIf{
    private final BoardModelIf boardModel;
    private final GameControl gameControl;


    public MovesGenerator(BoardModelIf boardModel, GameControl gameControl){

        this.boardModel = boardModel;
        this.gameControl = gameControl;
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
                valid = gameControl.validateMove(move);
                if (valid) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }
}
