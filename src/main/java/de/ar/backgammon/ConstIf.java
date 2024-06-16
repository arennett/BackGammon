package de.ar.backgammon;
public interface ConstIf {

    int PLAY_AREA_WIDTH = 800;
    int PLAY_AREA_HEIGHT = 600;

    int BOARD_OFFSET = 20;

    int BOARD_WIDTH  = PLAY_AREA_WIDTH + 2*BOARD_OFFSET;
    int BOARD_HEIGTH = PLAY_AREA_HEIGHT + 2*BOARD_OFFSET;

    String BOARDMAP_FILENAME = "src/main/resources/boardmap_";
    String BOARDMAP_SETUP ="setup";




}
