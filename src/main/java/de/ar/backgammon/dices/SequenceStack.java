package de.ar.backgammon.dices;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.moves.Move;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.validation.PointValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SequenceStack {
    static Logger logger = LoggerFactory.getLogger(SequenceStack.class);
    private final BoardModelIf bModel;


    public SequenceStack(BoardModelIf bModel){
        this.bModel = bModel;
    }

    ArrayList<PipSequence> pipSequences = new ArrayList<>();

    PointValidator pointValidator = new PointValidator();

    /**
     * checks the sequences in the sequencebuffer which ar valid for
     * the given move and nr of pieces
     * if all points of a sequence are valid, the seauence is added to return array
     * @param move
     * @param spc piece count
     * @return an array of valid sequences for the move
     */
    public ArrayList<PipSequence> getValidSequences(Move move, int spc, BColor turn) {
        int range = move.getRange(turn);

        ArrayList<PipSequence> retList = new ArrayList<PipSequence>();
        nextps:
        for (PipSequence ps : pipSequences) {
            if (ps.getSum() == range*spc) {
                logger.debug("check seq {} for range:{} spc:{}", ps,range,spc);
                int nextpos = move.from;
                int pips=0;
                for (int pip : ps) {
                    pips += pip*spc;
                    if (pips <= ps.getSum()) {
                        if (move.isOffMove()) {
                            // off moves
                            if (move.to == BoardModel.POINT_IDX_OFF_RED) {
                                nextpos -= pip;
                            } else {
                                nextpos += pip;
                            }
                        } else { // normal moves
                            if (move.from < move.to) {
                                nextpos += pip;
                            } else {
                                nextpos -= pip;
                            }
                        }

                        // delegate next point 0/25 to offpoints 26/27
                        if (move.isOffMove()) {
                            if (nextpos == BoardModel.POINT_IDX_BAR_WHITE) {
                                nextpos = BoardModel.POINT_IDX_OFF_RED;
                            } else if (nextpos == BoardModel.POINT_IDX_BAR_RED) {
                                nextpos = BoardModel.POINT_IDX_OFF_WHITE;
                            }
                        }
                        BPoint nextPoint = bModel.getPoint(nextpos);


                        if (!pointValidator.isValid(nextPoint, spc,turn)) {
                            continue nextps;
                        }
                    }else { //if (pip*spc <= ps.getSum())
                        break ;
                    }
                }//for (int pip : ps)
                logger.debug("add to valid sequences {}", ps);
                retList.add(ps);
            }
        }
        return retList;
    }

    public void updateSequences(ArrayList<Integer> dicesStack) {
        logger.debug("updating sequences...");
        pipSequences.clear();

        PipSequence p;
        if (dicesStack.size() == 1) {
            //no sequences
        } else if (dicesStack.size() == 2) {
            p = new PipSequence(dicesStack);
            pipSequences.add(p);
            if(dicesStack.get(0)!=dicesStack.get(1)) {
                p = new PipSequence(dicesStack);
                p.flipFirst2();
                pipSequences.add(p);
            }
        } else if (dicesStack.size() == 3) {
            assert dicesStack.get(0) == dicesStack.get(1);
            assert dicesStack.get(1) == dicesStack.get(2);

            p = new PipSequence(dicesStack);
            pipSequences.add(p);
            p = new PipSequence(dicesStack);
            p.removeLast();
            pipSequences.add(p);

        } else if (dicesStack.size() == 4) {
            assert dicesStack.get(0) == dicesStack.get(1);
            assert dicesStack.get(1) == dicesStack.get(2);
            assert dicesStack.get(2) == dicesStack.get(3);

            p = new PipSequence(dicesStack);
            pipSequences.add(p);
            p = new PipSequence(dicesStack);
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

    public class BlotArray extends ArrayList<Integer> {
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
     * @param move
     * @return list of blot positions
     */
    public BlotArray getBlotArray(PipSequence ps,Move move,int spc,BColor turn){
        BlotArray arr=new BlotArray();
        int direction = 0;
        if (turn == BColor.WHITE) {
            direction = 1;
        } else {
            direction = -1;
        }

        int prevpos=move.from;
        int pips = 0;
        for (int pip : ps) {
            pips+=pip*spc;
            if (pips <= ps.getSum()) {
                int pos = prevpos+ pip * direction;
                BPoint point = bModel.getPoint(pos);
                if (point.getPieceColor() != turn) {
                    if (point.getPieceCount() == 1) {

                        arr.add(pos);
                    }
                }
                prevpos=pos;
            }else{
                break;
            }

        }

        return arr;
    }




    public boolean psHasBlots(PipSequence ps, Move move,int spc,BColor turn){

        return !getBlotArray(ps,move,spc,turn).isEmpty();

    }


    /*setter and getter*******************************************************************/
    public ArrayList<PipSequence> getPipSequences() {
        return pipSequences;
    }

}
