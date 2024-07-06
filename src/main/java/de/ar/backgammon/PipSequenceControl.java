package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class PipSequenceControl {
    static Logger logger = LoggerFactory.getLogger(PipSequenceControl.class);
    private final BoardModelIf bModel;
    private GameControl gameControl;

    public PipSequenceControl(BoardModelIf bModel){
        this.bModel = bModel;
    }

    ArrayList<PipSequence> pipSequences = new ArrayList<>();

    /**
     * checks the sequences in the sequencebuffer which ar valid for
     * the given move and nr of pieces
     * if all points of a sequence are valid, the seauence is added to return array
     * @param from start position index
     * @param to to position index
     * @param spc piece count
     * @return an array of valid sequences for the move
     */
    ArrayList<PipSequence> getValidSequences(int from, int to, int spc) {
        int distance = 0;
        if (gameControl.getTurn() == BColor.WHITE) {
            distance = to - from;
        } else {
            distance = from - to;
        }
        ArrayList<PipSequence> retList = new ArrayList<PipSequence>();
        nextps:
        for (PipSequence ps : pipSequences) {
            if (ps.getSum() == distance) {
                logger.debug("check seq {}", ps);
                int nextpos = from;
                for (int s : ps) {
                    if (from < to) {
                        nextpos += s;
                    } else {
                        nextpos -= s;
                    }
                    BPoint nextPoint = bModel.getPoint(nextpos);
                    if (!gameControl.isValidPoint(nextPoint, spc)) {
                        continue nextps;
                    }

                }
                retList.add(ps);
            }
        }
        return retList;
    }

    public void updateSequences(ArrayList<Integer> pipStack) {
        pipSequences.clear();
        PipSequence p;
        if (pipStack.size() == 1) {
            //no sequences
        } else if (pipStack.size() == 2) {
            p = new PipSequence(pipStack);
            pipSequences.add(p);
            if(pipStack.get(0)!=pipStack.get(1)) {
                p = new PipSequence(pipStack);
                p.flipFirst2();
                pipSequences.add(p);
            }
        } else if (pipStack.size() == 3) {
            assert pipStack.get(0) == pipStack.get(1);
            assert pipStack.get(1) == pipStack.get(2);

            p = new PipSequence(pipStack);
            pipSequences.add(p);
            p = new PipSequence(pipStack);
            p.removeLast();
            pipSequences.add(p);

        } else if (pipStack.size() == 4) {
            assert pipStack.get(0) == pipStack.get(1);
            assert pipStack.get(1) == pipStack.get(2);
            assert pipStack.get(2) == pipStack.get(3);

            p = new PipSequence(pipStack);
            pipSequences.add(p);
            p = new PipSequence(pipStack);
            p.removeLast();
            pipSequences.add(p);
            p = new PipSequence(p);
            p.removeLast();
            pipSequences.add(p);
            logSequences();
        }
    }
    public void logSequences(){
        logger.debug("PipSequences: --------------------");
        for (PipSequence ps : pipSequences) {
            logger.debug("{}", ps.toString());
        }
        logger.debug("-----------------------------------");
    }

    public void clear() {
        pipSequences.clear();
    }

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    public boolean psHasBlots(PipSequence ps, int from, int to){
        int direction = 0;
        if (gameControl.getTurn() == BColor.WHITE) {
            direction = 1;
        } else {
            direction = -1;
        }
        int pos = from;
        for (int p : ps) {
            BPoint point = bModel.getPoint(p);
            if (point.getPieceColor() != gameControl.getTurn()) {
                if (point.getPieceCount() == 1) {
                    logger.debug("blot detected");
                    return true;
                }
            }
            int subto = pos + p * direction;
            pos = subto;
        }
        return false;

    }


    /*setter and getter*******************************************************************/
    ArrayList<PipSequence> getPipSequences() {
        return pipSequences;
    }

}
