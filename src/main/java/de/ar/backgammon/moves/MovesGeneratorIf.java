package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.Dices;

import java.util.ArrayList;

public interface MovesGeneratorIf  {
     ArrayList<Move> getValidMoves(Dices dices, BColor turn);
}
