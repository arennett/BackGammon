package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Vector;

public class DicesControl {
    private static final Logger logger = LoggerFactory.getLogger(DicesControl.class);

    Random random=new Random();
    private DicesPanel dicesPanel;

    Vector<Integer> pointStack = new Vector<>();

    int dice1=0;
    int dice2=0;

    public enum DicesState  {READY,THROWN};

    public DicesState getDicesState() {
        return dicesState;
    }

    DicesState dicesState = DicesState.READY;

    public void throwDices(){
        pointStack.clear();
        dice1=random.nextInt(6)+1;
        dice2=random.nextInt(6)+1;;
        pointStack.add(dice1);
        pointStack.add(dice2);
        if (dice1==dice2) {
            pointStack.add(dice1);
            pointStack.add(dice2);
        }
        dicesState = DicesState.THROWN;
        dicesPanel.updateComponents();
    }

    /* setter and getter */
    public void setDicesPanel(DicesPanel dicesPanel) {
        this.dicesPanel = dicesPanel;
    }

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public Vector<Integer> getPointStack() {
        return pointStack;
    }

    /**
     * removes the the moved pieces points from stack, if stack contains all moved points
     * @param pieceMoves
     * @return
     */
    public boolean removePoints(Vector<Integer> pieceMoves){
        boolean allOnStack = true;
        for (int pieceMove:pieceMoves){
            if (!pointStack.contains(pieceMove)) {
                allOnStack=false;
            }
        }
        if (allOnStack){
            for (int pieceMove:pieceMoves){
                pointStack.remove(pieceMove);
            }
            if(pointStack.isEmpty()){
                dice1=0;
                dice2=0;
                dicesState =DicesState.READY;
            }
            dicesPanel.updateComponents();
        }
        return allOnStack;
    }
}
