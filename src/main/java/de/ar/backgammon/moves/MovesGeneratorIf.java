package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.dices.Dices;

import java.util.ArrayList;

public interface MovesGeneratorIf  {
     ArrayList<Move> getValidMoves(BColor turn);
}
