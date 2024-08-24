package de.ar.backgammon;

import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReaderIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonPanelControl {
    private static final Logger logger = LoggerFactory.getLogger(ButtonPanelControl.class);
    private final Game game;
    private final BoardPanel boardPanel;
    private final BoardModelIf bmodel;
    private final BoardModelReaderIf bmReader;
    private final GameControl gameControl;
    private boolean setMode;
    private boolean compWhite;
    private boolean compRed;

    public ButtonPanelControl(Game game,
                              BoardPanel boardPanel,
                              BoardModelIf bmodel,
                              BoardModelReaderIf bmReader,
                              GameControl gameControl){
        this.game = game;
        this.boardPanel = boardPanel;

        this.bmodel = bmodel;
        this.bmReader = bmReader;
        this.gameControl = gameControl;
    }

    public void start()  {
            gameControl.start();
    }

    public boolean isSetMode() {
        return setMode;
    }

    public void setSetMode(boolean setMode) {

        this.setMode = setMode;
    }

    public void switchTurn() {
        gameControl.switch_turn(BColor.WHITE);
    }

    public void saveModel() {
        gameControl.saveModel();
    }

    public void loadModel() {

        gameControl.loadModel();
    }
    public BoardModelIf getBoardModel() {

        return gameControl.getBoardModel();
    }

    public void setCompWhite(boolean compWhite) {
        this.compWhite = compWhite;
        gameControl.setCompWhite( compWhite);
    }

    public boolean isCompWhite() {
        return compWhite;
    }

    public boolean isCompRed() {
        return compRed;

    }

    public boolean isComp(BColor col) {
        if (col==BColor.RED){
            return compRed;
        }else{
            return compWhite;
        }
    }

    public void setCompRed(boolean compRed) {
        this.compRed = compRed;
        gameControl.setCompRed( compRed);
    }
}
