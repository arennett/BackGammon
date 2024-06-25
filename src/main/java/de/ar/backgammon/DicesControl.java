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

    public void clear() {
        dice1=0;
        dice2=0;
        pointStack.clear();
        dicesState=DicesState.READY;
        dicesPanel.updateComponents();
    }

    public enum DicesState  {READY,THROWN};

    public DicesState getDicesState() {
        return dicesState;
    }

    DicesState dicesState = DicesState.READY;

    public void throwDices(){
        clear();
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
     * check if stack contains all moved points
     * @param point point of one piece move
     *        count nr of peces moved
     * @return
     */

    public boolean checkPoints(int point, int count) {
        boolean allOnStack = true;
        for (int i = 0; i < count; i++) {
            if (!pointStack.contains(point)) {
                allOnStack = false;
            }
        }
        return allOnStack;
    }

    /**
     * removes the the moved pieces points from stack, if stack contains all moved points
     * @param point point of one piece move
     *        count nr of peces moved
     * @return
     */
    public boolean removePoints(int point, int count){
        boolean allOnStack =checkPoints(point,count);
        if (allOnStack){
            for (int i=0;i<count;i++){
                pointStack.removeElement(point);
            }
            if(pointStack.isEmpty()){
                clear();
            }
            dicesPanel.updateComponents();
        }
        return allOnStack;
    }
}
