package de.ar.backgammon.moves;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveSetTest {

    @Test
    void testEquals1() {
        MoveSet aset = new MoveSet();
        MoveSet bset = new MoveSet();
        assertTrue(aset.equals(bset));

    }
    @Test
    void testEquals2() {
        MoveSet aset = new MoveSet();
        MoveSet bset = new MoveSet();
        aset.add(new Move(1,3));
        bset.add(new Move(1,3));

        assertTrue(aset.equals(bset));

    }
    @Test
    void testEquals3() {
        MoveSet aset = new MoveSet();
        MoveSet bset = new MoveSet();
        aset.add(new Move(1,3));
        bset.add(new Move(1,4));

        assertFalse(aset.equals(bset));

    }
    @Test
    void testEquals4() {
        MoveSet aset = new MoveSet();
        MoveSet bset = new MoveSet();
        aset.add(new Move(1,3));
        aset.add(new Move(1,4));
        bset.add(new Move(1,3));
        bset.add(new Move(1,4));

        assertTrue(aset.equals(bset));

    }
    @Test
    void testEquals5() {
        MoveSet aset = new MoveSet();
        MoveSet bset = new MoveSet();
        aset.add(new Move(1,3));
        aset.add(new Move(1,4));
        bset.add(new Move(1,4));
        bset.add(new Move(1,3));

        assertTrue(aset.equals(bset));

    }
    @Test
    void testEquals6() {
        MoveSet aset = new MoveSet();
        MoveSet bset = new MoveSet();
        aset.add(new Move(1,3));
        aset.add(new Move(1,4));
        bset.add(new Move(1,4));
        bset.add(new Move(1,8));

        assertFalse(aset.equals(bset));

    }
    @Test
    void testEquals7() {
        MoveSet aset = new MoveSet();
        MoveSet bset = new MoveSet();
        aset.add(new Move(1,3));
        aset.add(new Move(1,4));
        bset.add(new Move(1,4));
        bset.add(new Move(1,8));
        bset.add(new Move(1,9));

        assertFalse(aset.equals(bset));

    }

}