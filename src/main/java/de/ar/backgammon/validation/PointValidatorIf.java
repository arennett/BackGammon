package de.ar.backgammon.validation;

import de.ar.backgammon.BColor;
import de.ar.backgammon.points.BPoint;

public interface PointValidatorIf {
    boolean isValid(BPoint point, int spc, BColor turn);
}
