package de.ar.backgammon.dices;

import de.ar.backgammon.model.BoardModelIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public DicesStack(BoardModelIf bModel,DicesStack dstack) {
        this.bModel = bModel;
        this.dices = new Dices(0,0);
        this.dices.dice1 = dstack.dices.dice1;
        this.dices.dice2 = dstack.dices.dice2;
        this.sequenceStack = new SequenceStack(bModel);
        this.update();
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
        logger.debug("loading dices...");
        this.dices=dices;
        state= DicesStack.State.UPDATED;
        update();
    }

    /**
     * throw dices and update the stack
     */
    protected void throwDices() {
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
        logger.debug("updating dices...");
        clear();
        int dice1=dices.dice1;
        int dice2=dices.dice2;

        if (dice1>0) add(dice1);
        if (dice2>0) add(dice2);
        if (dice1 == dice2 && dice1> 0 && dice2 > 0) {
            // double score ,no we have 4 pips on stack
            add(dice1);
            add(dice2);
        }

        /*if stack piece > board pieces, replace stack pieces*/
        cutStackPipsToBoardPips();

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


            cutStackPipsToBoardPips();


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

    private void cutStackPipsToBoardPips() {
        // sort stack from high to low
        // if all piece at home
        // and if  pips from stack > max pips on board
        // replace pips with max pips on board
        // as long as pip from stack is > board pip

        Collections.sort(this, Collections.reverseOrder());

        if (bModel.isAllPiecesAtHome(bModel.getTurn())) {
            logger.debug("all at home, special stack handling");
            ArrayList<Integer> boardPips = bModel.getMaxHomePipArray(bModel.getTurn());
            for (int i = 0; i < this.size() && i < boardPips.size() ; i++) {
                if (this.get(i) > boardPips.get(i)) {
                    logger.debug("replace pip from stack {} with board {}",this.get(i) ,boardPips.get(i));
                    this.set(i, boardPips.get(i));
                }else{
                    break;
                }
            }
        }
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
