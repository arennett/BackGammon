package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

public class BoardModel implements BoardModelIf {
    static Logger logger = LoggerFactory.getLogger(BoardModel.class);
    static int MAX_POINTS=24;
    Vector<BPoint> pVector =new Vector<>();

    public BoardModel(){

        initModel();
    }

    private void initModel() {
        for (int i=0;i<MAX_POINTS;i++){
            BPoint point =new BPoint();
            pVector.add(point);
        }
    }

    @Override
    public void setPoint(int pidx, int psize, BColor bcolor) {
        BPoint bpoint=pVector.elementAt(pidx);
        bpoint.setColor(bcolor);

    }

    @Override
    public void clear() {

    }


}
