package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BPoint;

public interface PointValidatorIf {
    boolean isValid(BPoint point, int spc, BColor turn);
}
