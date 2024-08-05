package de.ar.backgammon.validation;

import de.ar.backgammon.BColor;
import de.ar.backgammon.dices.DicesStack;
import de.ar.backgammon.moves.Move;

public interface MoveValidatorIf {
    DicesStack getDicesStack();

    boolean isValid(Move move, int spc);
}
