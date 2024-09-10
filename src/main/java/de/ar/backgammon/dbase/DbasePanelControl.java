package de.ar.backgammon.dbase;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BoardPanel;
import de.ar.backgammon.Game;
import de.ar.backgammon.GameControl;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReaderIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbasePanelControl {
    private static final Logger logger = LoggerFactory.getLogger(DbasePanelControl.class);
    private final Game game;
     private final BoardModelIf bmodel;
    private boolean recordingOn;

    public DbasePanelControl(Game game,
                             BoardModelIf bmodel
                            ){
        this.game = game;
        this.bmodel = bmodel;
    }


    public void setRecordingOn(boolean on) {
        if (on){
            logger.debug("db recording is on");
        }else{
            logger.debug("db recording is off");
        }
        this.recordingOn = on;
    }

    public void writeNewGame() {
        logger.debug("writeNewGame");
    }

    public void writeBoard() {
        logger.debug("writeBoard");
    }

}
