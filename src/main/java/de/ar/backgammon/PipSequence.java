package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * A pip sequence is a move consists of at least to dice pips
 * i.e. if you roll a double 6
 * the sequences are
 * [6][6][6][6]
 * [6][6][6]
 * [6][6]
 */
class PipSequence extends ArrayList<Integer> {
    static Logger logger = LoggerFactory.getLogger(BoardModel.class);

    public PipSequence(ArrayList<Integer> pointStack) {
        super();
        this.addAll(pointStack);
    }

    public PipSequence() {
        super();
    }

    /**
     * sum of all points
     *
     * @return
     */
    int getSum() {
        int sum = 0;
        for (int pip : this) {
            sum += pip;
        }
        return sum;
    }

    public Integer removeLast() {
        return super.remove(size() - 1);
    }

    /**
     * flips the first to dice pips of a sequence
     * only used for 2 pips sequences
     */
    public void flipFirst2() {
        assert size() == 2;
        int k = get(0);
        set(0, get(1));
        set(1, k);
    }

    public String toString() {
        StringBuffer str = new StringBuffer("" + getSum() + ": ");
        for (int pip : this) {
            str.append("[" + pip + "] ");
        }
        return str.toString();
    }



}
