package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;

import java.util.ArrayList;

public interface MovesGeneratorIf  {
     ArrayList<Move> getValidMoves(BColor turn);
}
