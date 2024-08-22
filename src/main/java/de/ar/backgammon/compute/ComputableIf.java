package de.ar.backgammon.compute;

import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.moves.MoveSet;

public interface ComputableIf {
    MoveSet compute();
}
