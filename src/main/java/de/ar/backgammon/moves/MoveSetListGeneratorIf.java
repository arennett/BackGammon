package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;

import java.util.ArrayList;

public interface MoveSetListGeneratorIf {
     ArrayList<MoveSet> getValidMoveSets(BColor turn);
}
