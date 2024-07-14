package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SequenceControl {
    static Logger logger = LoggerFactory.getLogger(SequenceControl.class);
    private final BoardModelIf bModel;
    private GameControl gameControl;

    public SequenceControl(BoardModelIf bModel){
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
        int range = gameControl.getRange(from,to);

        ArrayList<PipSequence> retList = new ArrayList<PipSequence>();
        nextps:
        for (PipSequence ps : pipSequences) {
            if (ps.getSum() == range) {
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
                logger.debug("add to valid sequences {}", ps);
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


    class BlotArray extends ArrayList<Integer> {
        @Override
        public boolean equals(Object o) {
            boolean ret = false;
            if (o instanceof  BlotArray) {
                BlotArray other= (BlotArray) o;
                if (this.size()==other.size()){
                    for (int i=0;i<this.size();i++){
                        if(this.get(i)!=other.get(i)) {
                            return false;
                        }
                    }
                    return true;
                }

            }
            return ret;
        }

        @Override
        public String toString() {
            StringBuffer str = new StringBuffer("");
            for (int pos : this) {
                str.append("|P" + pos + "| ");
            }
            return str.toString();

        }
    }

    /**
     * creates a list of blot positions for a sequence and a move
     * @param ps
     * @param from
     * @param to
     * @return list of blot positions
     */
    public BlotArray getBlotArray(PipSequence ps, int from, int to){
        BlotArray arr=new BlotArray();
        int direction = 0;
        if (gameControl.getTurn() == BColor.WHITE) {
            direction = 1;
        } else {
            direction = -1;
        }

        int prevpos=from;
        for (int pip : ps) {
            int pos = prevpos+ pip * direction;
            BPoint point = bModel.getPoint(pos);
            if (point.getPieceColor() != gameControl.getTurn()) {
                if (point.getPieceCount() == 1) {

                    arr.add(pos);
                }
            }
            prevpos=pos;
        }

        return arr;
    }




    public boolean psHasBlots(PipSequence ps, int from, int to){

        return !getBlotArray(ps,from,to).isEmpty();

    }


    /*setter and getter*******************************************************************/
    ArrayList<PipSequence> getPipSequences() {
        return pipSequences;
    }

}
