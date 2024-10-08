package de.ar.backgammon;

import java.awt.*;

public enum BColor {
    WHITE, RED;

    static final String[] STR_COLORS = {"white", "red"};
    static final String[] SHORTSTR_COLORS = {"w", "r"};
    static final Color[] COLORS = {Color.WHITE, Color.RED};

    public static BColor getBColor(String scol) {
        for (int i = 0; i < SHORTSTR_COLORS.length; i++) {
            if (scol.equals(SHORTSTR_COLORS[i])) {
                return BColor.values()[i];
            }
        }
        return null;
    }
    public static BColor getOtherColor(BColor col) {
        if(col==WHITE){
            return RED;
        }else if(col==RED){
            return WHITE;
        }
        return null;
    }

    public static BColor getBColorByIdx(int turn) {
        return BColor.values()[turn];
    }

    public String toString() {
        return STR_COLORS[this.ordinal()];
    }

    public String getShortString() {
        return SHORTSTR_COLORS[this.ordinal()];
    }

    public Color getColor() {
        return COLORS[this.ordinal()];
    }


}
