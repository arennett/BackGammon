package de.ar.backgammon;

import java.awt.*;

public enum BColor {
    RED,BLACK;

    static final String[] STR_COLORS = {"Red","Green"};
    static final Color[] COLORS={Color.RED,Color.BLACK};

    public String getString(){
        return STR_COLORS[this.ordinal()];
    }
    public  Color getColor(){
        return COLORS[this.ordinal()];
    }


}
