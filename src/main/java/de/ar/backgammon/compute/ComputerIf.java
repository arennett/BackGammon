package de.ar.backgammon.compute;

import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.moves.MoveSet;

public interface ComputerIf {
    public MoveSet compute();
}
