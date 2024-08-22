package de.ar.backgammon.compute;

import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.moves.MoveSet;

public class MainComputer implements ComputerIf{

    private final ComputableIf computable;

    public MainComputer(ComputableIf computable){

        this.computable = computable;
    }
    @Override
    public MoveSet compute() {
        return computable.compute();
    }
}
