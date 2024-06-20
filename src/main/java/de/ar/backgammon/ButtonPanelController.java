package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonPanelController {
    private static final Logger logger = LoggerFactory.getLogger(ButtonPanelController.class);
    private final Game game;
    private final BoardPanel boardPanel;
    private final BoardModelIf bmodel;
    private final BoardModelReaderIf bmReader;
    private final GameControl gameControl;

    public ButtonPanelController(Game game,
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
}
