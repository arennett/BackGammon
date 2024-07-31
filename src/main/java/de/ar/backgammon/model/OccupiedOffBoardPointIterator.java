package de.ar.backgammon.model;

import de.ar.backgammon.BColor;

public class OccupiedOffBoardPointIterator extends OccupiedPointIterator{
    public OccupiedOffBoardPointIterator(BoardModelIf boardModel, BColor bColor) {
        super(boardModel,bColor,0,0);


    }
}
