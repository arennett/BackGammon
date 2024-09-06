package de.ar.backgammon;
public interface ConstIf {

    String BOARDMAP_FILENAME = "src/main/resources/maps/boardmap_";
    String BOARDMAP_TEST_FILENAME ="src/test/resources/maps/";
    public String DB_PATH = "src/main/resources/dbase/";
    public String DB_URL = "jdbc:sqlite:" + DB_PATH + ConstIf.DB_FILENAME;
    public String DB_FILENAME = "bgammon.db";
    String BOARDMAP_SETUP ="setup";
    String BOARDMAP_SAVE ="save";

    int MAX_PIECES_ON_POINT = 5;

    int PIECES_COUNT_PLAYER =15;


    int MIN_PIP = 1;
    int MAX_PIP = 6;

    int COMP_SLEEPTIME = 500;


}
