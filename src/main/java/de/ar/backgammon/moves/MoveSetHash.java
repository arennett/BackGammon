package de.ar.backgammon.moves;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
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
        Collections.sort(list);
        return list;
    }

    public String getFormatedList(){
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<MoveSet> list=getSortedList ();
        stringBuffer.append("\n");
        for (MoveSet moveSet:list){
            stringBuffer.append(moveSet.toString());
            stringBuffer.append("\n");
        }
        return  stringBuffer.toString();



    }

}


