package de.ar.backgammon.dices;

import de.ar.backgammon.model.BoardModelIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;

/**
 * Dices stack is a list of 2..4 pips
 * if the to dices different it has this 2 pips
 * if we have a double score it has 4 pips
 * so  [3] and [4] leads to [3] [4] on stack
 * and [3] and [3] leads to [3] [3] [3] [3] on stack
 * further on it contains the SequenceStack
 * which contains all combinations of possible sequences
 * the sequence stack is updated when the dicesstack is updated
 */
public class DicesStack extends ArrayList<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(DicesStack.class);
    Dices dices;
    private SequenceStack sequenceStack;


    public enum State {EMPTY, UPDATED}
    State state = State.EMPTY;

    private final BoardModelIf bModel;

    Random random = new Random();

    public DicesStack(BoardModelIf bModel) {
        this.bModel = bModel;
        this.dices = new Dices(0,0);
        this.sequenceStack = new SequenceStack(bModel);
    }

   @Override
    public void clear(){
        super.clear();
        sequenceStack.clear();
        state = State.EMPTY;
    }

    /**
     * load dices into the stack
     * @param dices
     */
    public void loadDices(Dices dices) {
        this.dices=dices;
        state= DicesStack.State.UPDATED;
        update();
    }

    /**
     * throw dices and update the stack
     */
    public void throwDices() {
        clear();
        dices.dice1=random.nextInt(6) + 1;
        dices.dice2=random.nextInt(6) + 1;
        update();
    }

    /**
     * check if the range of a move is on the stack
     * @param move_range
     * @param count
     * @return true when the move range is a pip (1..6) and all pieces(count) are on stack
     */
    public boolean isMoveOnStack(int move_range, int count) {
        boolean allOnStack = true;
        for (int i = 0; i < count; i++) {
            if (!contains(move_range)) {
                allOnStack = false;
                break;
            }
        }
        logger.debug("move range:{} {}x {}"
                , move_range, count, allOnStack ? "on stack" : "NOT on stack, probably a sequence");
        return allOnStack;
    }
    public void update() {

        clear();
        int dice1=dices.dice1;
        int dice2=dices.dice2;

        if (bModel.isAllPiecesAtHome(bModel.getTurn())) {
            logger.debug("all at home, special stack handling");
            /*
             if the dice is greater than the highest piece
             the dice is replaced by the pip of the highest
             */
            ArrayList<Integer> max = bModel.getHomePointMaxDuo(bModel.getTurn());
            int maxPoint1 = max.get(0);
            int maxPoint2 = max.get(1);

            if (dice1 >= maxPoint1 && maxPoint1 > -1) {
                dice1 = maxPoint1;
                if (dice2 > maxPoint2 && maxPoint2 > -1) {
                    dice2 = maxPoint2;
                }
            } else if (dice2 >= maxPoint1 && maxPoint1 > -1) {
                dice2 = maxPoint1;
                if (dice1 > maxPoint2 && maxPoint2 > -1) {
                    dice1 = maxPoint2;
                }
            }
        }
        if (dice1>0) add(dice1);
        if (dice2>0) add(dice2);
        if (dice1 == dice2 && dice1> 0 && dice2 > 0) {
            // double score ,no we have 4 pips on stack
            add(dice1);
            add(dice2);
        }

        sequenceStack.updateSequences(this);
        state = State.UPDATED;
    }

    /**
     * removes the the moved pips from stack, if stack contains all moved pips
     *
     * @param pip pip of one submove
     * @param count nr of pieces moved
     * @return true if all points where removed
     */
    public boolean removePipsFromStack(int pip, int count) {
        boolean isMoveOnStack = isMoveOnStack(pip, count);
        if (isMoveOnStack) {
            for (int k = 0; k < count; k++) {
                //remove pip from pointStack
                remove(Integer.valueOf(pip));
            }
            logger.debug("removed pip {}x [{}] from stack", count, pip);

            if (isEmpty()) {
                clear();
            }

        } else {
            assert false;
            String msg = "not all points on stack, points not removed !!!";
            logger.error(msg);
            logger.error("pip:{} {}x {}"
                    , pip, count, "NOT on stack, points not removed");
        }

        sequenceStack.updateSequences(this);

        return isMoveOnStack;

    }

    public Dices getDices() {
        return dices;
    }

    public SequenceStack getSequenceStack() {
        return sequenceStack;
    }
    public State getState() {
        return state;
    }
}
