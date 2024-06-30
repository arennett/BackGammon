package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static de.ar.backgammon.ConstIf.MAX_PIECES_ON_POINT;

public class DicesControl {
    private static final Logger logger = LoggerFactory.getLogger(DicesControl.class);
    private final Game game;
    private final BoardModelIf bModel;

    Random random = new Random();
    private DicesPanel dicesPanel;

    private GameControl gameControl;

    /**
     * remove the point of the sequence from the dice point stack
     * @param ps
     * @param count piece count
     */
    public void removeSequencePointsFromStack(PointSequence ps, int count) {
        for (int point:ps){
            removePointsFromStack(point,count);
        }
    }


    class PointSequence extends ArrayList<Integer> {
        public PointSequence(ArrayList<Integer> pointStack) {
            super();
            this.addAll(pointStack);
        }

        public PointSequence() {
            super();
        }

        /**
         * sum of all points
         *
         * @return
         */
        int getSum() {
            int sum = 0;
            for (int point : this) {
                sum += point;
            }
            return sum;
        }

        public Integer removeLast() {
            return super.remove(size() - 1);
        }

        public void flipFirst2() {
            assert size() == 2;
            int k = get(0);
            set(0, get(1));
            set(1, k);
        }

        public String toString() {
            StringBuffer str = new StringBuffer("" + getSum() + ": ");
            for (int point : this) {
                str.append("[" + point + "] ");
            }
            return str.toString();
        }

    }

    ArrayList<Integer> pointStack = new ArrayList<>();
    ArrayList<PointSequence> pointSequences = new ArrayList<>();

    int dice1 = 0;
    int dice2 = 0;

    public DicesControl(Game game, BoardModelIf bModel) {

        this.game = game;
        this.bModel = bModel;
    }

    public void clear() {
        dice1 = 0;
        dice2 = 0;
        pointStack.clear();
        pointSequences.clear();
        dicesState = DicesState.READY;
        dicesPanel.updateComponents();
    }

    public enum DicesState {READY, THROWN}

    ;

    public DicesState getDicesState() {
        return dicesState;
    }

    DicesState dicesState = DicesState.READY;

    public void throwDices() {
        if (!gameControl.isRunning()) {
            game.message_error("start a new game!");
            return;
        }
        clear();
        dice1 = random.nextInt(6) + 1;
        dice2 = random.nextInt(6) + 1;
        ;
        pointStack.add(dice1);
        pointStack.add(dice2);
        if (dice1 == dice2) {
            pointStack.add(dice1);
            pointStack.add(dice2);
        }
        updatePointSequences(pointStack);
        dicesState = DicesState.THROWN;
        dicesPanel.updateComponents();
    }

    private void updatePointSequences(ArrayList<Integer> pointStack) {
        pointSequences.clear();
        PointSequence p;
        if (pointStack.size() == 1) {
            //no sequences
        } else if (pointStack.size() == 2) {
            p = new PointSequence(pointStack);
            pointSequences.add(p);
            p = new PointSequence(pointStack);
            p.flipFirst2();
            pointSequences.add(p);

        } else if (pointStack.size() == 3) {
            assert pointStack.get(0) == pointStack.get(1);
            assert pointStack.get(1) == pointStack.get(2);

            p = new PointSequence(pointStack);
            pointSequences.add(p);
            p = new PointSequence(pointStack);
            p.removeLast();
            pointSequences.add(p);

        } else if (pointStack.size() == 4) {
            assert pointStack.get(0) == pointStack.get(1);
            assert pointStack.get(1) == pointStack.get(2);
            assert pointStack.get(2) == pointStack.get(3);

            p = new PointSequence(pointStack);
            pointSequences.add(p);
            p = new PointSequence(pointStack);
            p.removeLast();
            pointSequences.add(p);
            p = new PointSequence(p);
            p.removeLast();
            pointSequences.add(p);

        }
        logger.debug("--------PointSequences: -------------");
        for (PointSequence ps : pointSequences) {
            logger.debug("{}", ps.toString());
        }
        logger.debug("-------------------------------------");


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

    public ArrayList<Integer> getPointStack() {
        return pointStack;
    }

    /**
     * check if stack contains all moved points
     *
     * @param point point of one piece move
     *              count nr of peces moved
     * @return
     */

    public boolean checkPointsOnStack(int point, int count) {
        boolean allOnStack = true;
        for (int i = 0; i < count; i++) {
            if (!pointStack.contains(point)) {
                allOnStack = false;
            }
        }
        logger.debug("checkPointsOnStack point:{} count:{} ok:{}",point,count,allOnStack);
        return allOnStack;
    }

    /**
     * removes the the moved pieces points from stack, if stack contains all moved points
     *
     * @param point point of one piece move
     *              count nr of peces moved
     * @return
     */
    public boolean removePointsFromStack(int point, int count) {
        boolean allOnStack = checkPointsOnStack(point, count);
        if (allOnStack) {

            for (int k = 0; k < count; k++) {
                //remove point from pointStack
                pointStack.remove(Integer.valueOf(point));
            }

            if (pointStack.isEmpty()) {
                clear();
            }
            dicesPanel.updateComponents();
        }
        updatePointSequences(pointStack);
        return allOnStack;
    }

    public PointSequence checkSequences(BPoint from, BPoint to, int point, int spc) {
        nextps:
        for (PointSequence ps : pointSequences) {
            if (ps.getSum() == point) {
                logger.debug("check seq {}", ps);
                int nextpos = from.getIndex();
                for (int s : ps) {
                    if (from.getIndex() < to.getIndex()) {
                        nextpos += s;
                    } else {
                        nextpos -= s;
                    }
                    BPoint nextPoint = bModel.getPoint(nextpos);
                    logger.debug("check seq next point: {}", nextPoint);
                    if (!isValidSeqPoint(nextPoint, spc)) {
                        continue nextps;
                    }
                }
                logger.debug("check seq {} true", ps);
                return ps;
            }
        }
        logger.debug("check seq false");
        return null;
    }

    /**
     * validition for sequence fields
     *
     * @param nextPoint
     * @return
     */
    public boolean isValidSeqPoint(BPoint nextPoint, int spc) {
        if (nextPoint.getPieceCount() > 1) {
            if (nextPoint.getPieceColor() != gameControl.getTurn()) {
                game.message_error("wrong color on sequence field");
                logger.debug("valid seq point: {} {}", nextPoint, false);
                return false;
            }
        }
        if (nextPoint.getPieceCount() > 4) {
            if (nextPoint.getPieceCount() + spc > MAX_PIECES_ON_POINT) {
                game.message_error("you can only put up to 5 pieces on a field");
                logger.debug("valid seq point: {} {}", nextPoint, false);
                return false;
            }
        }
        logger.debug("valid seq point: {} {}", nextPoint, true);
        return true;
    }

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }
}
