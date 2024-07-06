package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DicesControl {
    private static final Logger logger = LoggerFactory.getLogger(DicesControl.class);
    private final Game game;
    private final BoardModelIf bModel;
    private PipSequenceControl psControl;

    Random random = new Random();
    private DicesPanel dicesPanel;

    private GameControl gameControl;

    int dice1 = 0;
    int dice2 = 0;

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

    public void setPsControl(PipSequenceControl psControl) {
        this.psControl = psControl;
    }



    public enum DicesState {READY, THROWN};
    DicesState dicesState = DicesState.READY;

    public void rollDices() {
        if (!gameControl.isRunning()) {
            game.message_error("start a new game!");
            return;
        }
        clear();
        dice1 = random.nextInt(6) + 1;
        dice2 = random.nextInt(6) + 1;
        updateStack();
    }

    /**
     * update the stack after a dices roll
     * followed by updating the pip sequences
     */
    private void updateStack() {
        clear();
        pipStack.add(dice1);
        pipStack.add(dice2);
        if (dice1 == dice2) {
            pipStack.add(dice1);
            pipStack.add(dice2);
        }
        psControl.updateSequences(pipStack);
        dicesState = DicesState.THROWN;
        dicesPanel.updateComponents();
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
        logger.debug("move_range:{} {}x {}"
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

    public void setDice1(int point) {
        dice1=point;
        updateStack();
    }
    public void setDice2(int point) {
        dice2=point;
        updateStack();
    }

    public void setDicesPanel(DicesPanel dicesPanel) {
        this.dicesPanel = dicesPanel;
    }

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public DicesState getDicesState() {
        return dicesState;
    }

}
