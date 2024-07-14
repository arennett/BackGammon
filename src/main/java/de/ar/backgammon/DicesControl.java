package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DicesControl {
    private static final Logger logger = LoggerFactory.getLogger(DicesControl.class);
    private final Game game;
    private final BoardModelIf bModel;
    private SequenceControl psControl;

    Random random = new Random();
    private DicesPanel dicesPanel;

    private GameControl gameControl;



    ArrayList<Integer> pipStack = new ArrayList<>();

    public DicesControl(Game game, BoardModelIf bModel) {
         this.game = game;
        this.bModel = bModel;
   }

    /**
     * clear pip stack and pip sequences
     */
    public void clear() {
        pipStack.clear();
        psControl.clear();
        dicesState = DicesState.READY;
        dicesPanel.updateComponents();
    }

    public void setPsControl(SequenceControl psControl) {
        this.psControl = psControl;
    }



    public enum DicesState {READY, THROWN};
    DicesState dicesState = DicesState.READY;

    public void throwDices() {
        if (!gameControl.isRunning()) {
            game.message_error("start a new game!");
            return;
        }
        clear();
        setDice1(random.nextInt(6) + 1);
        setDice2(random.nextInt(6) + 1);
        updateStack();

    }

    /**
     * update the stack after a dices roll
     * followed by updating the pip sequences
     */
    public void updateStack() {
        clear();
        pipStack.add(getDice1());
        pipStack.add(getDice2());
        if (getDice1() == getDice2()) {
            pipStack.add(getDice1());
            pipStack.add(getDice2());
        }
        psControl.updateSequences(pipStack);
        dicesState = DicesState.THROWN;
        dicesPanel.updateComponents();
        gameControl.dicesThrown();
    }

 /**
     * check if stack contains all moved points
     * if not, point might be a sequence distance
     * @param move_range move range
     *              count nr of peces moved
     * @return true if all points on stack
     */

    public boolean checkIfMoveIsOnStack(int move_range, int count) {
        boolean allOnStack = true;
        for (int i = 0; i < count; i++) {
            if (!pipStack.contains(move_range)) {
                allOnStack = false;
            }
        }
        logger.debug("move range:{} {}x {}"
                ,move_range,count,allOnStack?"on stack":"NOT on stack, probably a sequence");
        return allOnStack;
    }

    /**
     * removes the the moved pips from stack, if stack contains all moved pips
     *
     * @param pip pip of one submove
     *              count nr of pieces moved
     * @return true if all points where removed
     */
    public boolean removePipsFromStack(int pip, int count) {
        boolean allOnStack = checkIfMoveIsOnStack(pip, count);
        if (allOnStack) {
            logger.debug("remove pips {}x [{}] from stack",count,pip);
            for (int k = 0; k < count; k++) {
                //remove pip from pointStack
                pipStack.remove(Integer.valueOf(pip));
            }
            if (pipStack.isEmpty()) {
                clear();
            }

        } else {
            assert true;
            String msg = "not all points on stack, points not removed !!!";
            logger.error(msg);
            game.message_error(msg);
            logger.error("pip:{} {}x {}"
                    , pip, count, "NOT on stack, points not removed");
        }

        psControl.updateSequences(pipStack);
        dicesPanel.updateComponents();
        return allOnStack;

    }



    /*setter and getter*******************************************************************/

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }
    public ArrayList<Integer> getPipStack() {
        return pipStack;
    }



    public void setDicesPanel(DicesPanel dicesPanel) {
        this.dicesPanel = dicesPanel;
    }



    public DicesState getDicesState() {
        return dicesState;
    }

    public int getDice1() {
        return bModel.getDice1();
    }

    Vector <Integer> getDices() {
        Vector<Integer> vec = new Vector<>();
        vec.add(getDice1());
        vec.add(getDice2());
        return vec;
    }

    public void setDice1(int dice1) {
        bModel.setDice1(dice1);
        updateStack();
    }

    public int getDice2() {
        return bModel.getDice2();
    }

    public void setDice2(int dice2) {
        bModel.setDice2(dice2);
        updateStack();
    }

}
