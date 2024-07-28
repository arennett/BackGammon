package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;

public interface MoveValidatorIf {
    boolean isValid(Move move, BColor turn, int spc);
}
