package de.ar.backgammon;

import de.ar.backgammon.model.BoardModelIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;

public class DicesStack extends ArrayList<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(DicesStack.class);
    Dices dices;
    private SequenceStack sequenceStack;


    public enum State {READY, THROWN}
    State state = State.READY;

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
        state = State.READY;

    }

    public void throwDices() {
        clear();
        dices.dice1=random.nextInt(6) + 1;
        dices.dice2=random.nextInt(6) + 1;
        update();
    }

    public boolean isMoveOnStack(int move_range, int count) {
        boolean allOnStack = true;
        for (int i = 0; i < count; i++) {
            if (!contains(move_range)) {
                allOnStack = false;
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
        add(dice1);
        add(dice2);
        if (dice1 == dice2) {
            add(dice1);
            add(dice2);
        }

        sequenceStack.updateSequences(this);
        state = State.THROWN;
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
