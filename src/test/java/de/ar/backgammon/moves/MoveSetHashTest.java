package de.ar.backgammon.moves;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveSetHashTest {
    MoveSetHash msetHash;

    @BeforeEach
    public void setUp(){
        msetHash = new MoveSetHash();
    }
    @Test
    public void test_addAll(){
        MoveSet m1 = new MoveSet();
        MoveSet m2 = new MoveSet();
        MoveSet m3 = new MoveSet();
        MoveSet m4 = new MoveSet();


        m1.add(new Move(1,3));
        m1.add(new Move(2,3));
        m1.add(new Move(3,3));
        m2.add(new Move(2,3));
        m2.add(new Move(3,3));
        m2.add(new Move(1,3));
        m3.add(new Move(3,3));
        m3.add(new Move(2,3));
        m3.add(new Move(1,3));
        m4.add(new Move(4,3));
        m4.add(new Move(5,3));
        m4.add(new Move(6,3));


        ArrayList<MoveSet> arrlist = new ArrayList<>();
        arrlist.add(m1);
        arrlist.add(m2);
        arrlist.add(m3);
        arrlist.add(m4);

        MoveSetHash msetHash = new MoveSetHash(arrlist);
        assertEquals(2,msetHash.size());

    }

}