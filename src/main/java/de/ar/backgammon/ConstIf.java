package de.ar.backgammon;
public interface ConstIf {

    int BOARD_WIDTH  = 800;
    int BOARD_HEIGTH = 600;
    int BOARD_OFFSET = 20;
    static int BAR_WIDTH = 40;
    static int POINT_WIDTH = (BOARD_WIDTH - BAR_WIDTH) / 12;
    static int POINT_HEIGTH = BOARD_HEIGTH * 4 / 10;
    static int PIECE_WIDTH=50;

    String BOARDMAP_FILENAME = "src/main/resources/boardmap_";
    String BOARDMAP_SETUP ="setup";




}
