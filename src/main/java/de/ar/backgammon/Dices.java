package de.ar.backgammon;

import java.util.Iterator;
import java.util.Vector;

public class Dices implements Iterable<Integer>{
    public int dice1;
    public int dice2;

    public Dices(int dice1,int dice2){
        this.dice1=dice1;
        this.dice2=dice2;
    }

    @Override
    public Iterator<Integer> iterator() {
        Vector<Integer> v = new Vector<>();
        v.add(dice1);
        v.add(dice2);
        return v.iterator();
    }
}
