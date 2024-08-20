package de.ar.backgammon.moves;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class MoveSetHash extends HashSet<MoveSet>{
    private static final Logger logger = LoggerFactory.getLogger(MoveSetHash.class);

    public MoveSetHash(ArrayList<MoveSet> arrlist) {
        super(arrlist);
    }
    public MoveSetHash() {
        super();
    }

    private ArrayList<MoveSet> getList(){
        ArrayList<MoveSet> arrList=new ArrayList<>();
        arrList.addAll(this);
        return arrList;
    }


    public ArrayList<MoveSet> getSortedList (){
        ArrayList<MoveSet> list = getList();
        Collections.sort(list, new Comparator<MoveSet>() {
            @Override
            public int compare(MoveSet o1, MoveSet o2) {
                return o1.toSortedString().compareTo(o2.toSortedString());
            }
        });
        return list;
    }

    public String toSortedString(){
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<MoveSet> list=getSortedList ();
        stringBuffer.append("\n");
        for (MoveSet moveSet:list){
            stringBuffer.append(moveSet.toSortedString());
            stringBuffer.append("\n");
        }
        return  stringBuffer.toString();



    }

}


