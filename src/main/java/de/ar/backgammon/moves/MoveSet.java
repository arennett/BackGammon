package de.ar.backgammon.moves;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/** a move set has one to 4 single moves
 * a move set is played out by the computer player while a game turn
 * the order of the MoveSet is important, as a move can depend on the previous move in the set
 * */

public class MoveSet extends ArrayList<Move> implements Comparable{
    private static final Logger logger = LoggerFactory.getLogger(MoveSet.class);
    static public int MAX_SIZE = 4;
    boolean finished = false;
    public MoveSet(){

    }
    public MoveSet(MoveSet moves) {
        this.addAll(moves);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
        if (finished) {
            logger.debug("moveset {} was finished",this);
        }
    }


    @Override
    public boolean add(Move move) {
        assert(this.size() < MAX_SIZE);
        return super.add(move);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MoveSet){
            MoveSet mset = (MoveSet) o;
            if (mset.size() == this.size()){
                for (Move m:this){
                    if (!mset.contains(m)){ ///TODO  Fehler
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Move m:this){
            hashCode+=m.hashCode();
        }
        return hashCode;
    }

    @Override
    public int compareTo(Object o) {
        MoveSet other =  (MoveSet) o;
        return this.toString().compareTo(other.toString());
 }
    @Override
    public String toString() {
        StringBuffer buff =new StringBuffer();
        buff.append("[");
        for (Iterator<Move> mit=iterator();mit.hasNext();) {
            Move m=mit.next();
            buff.append(m.toString());
            if (mit.hasNext()){
                buff.append(",");
            }

        }
        buff.append("]");
        return buff.toString();
    }
    public String toSortedString() {
        MoveSet ms = new MoveSet(this);
        Collections.sort(ms,new MoveComparator());
        return ms.toString();
    }

}
