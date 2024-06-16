package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonPanelController {
    private static final Logger logger = LoggerFactory.getLogger(ButtonPanelController.class);
    private final Game game;
    private final BoardPanel boardPanel;
    private final BoardModelIf bmodel;
    private final BoardModelReaderIf bmReader;

    public ButtonPanelController(Game game,
                                 BoardPanel boardPanel,
                                 BoardModelIf bmodel,
                                 BoardModelReaderIf bmReader){
        this.game = game;
        this.boardPanel = boardPanel;

        this.bmodel = bmodel;
        this.bmReader = bmReader;
    }

    public void start()  {

         bmodel.clear();
        try {
            bmReader.readSetupMap(bmodel);
            game.message("Game Started");
        } catch (Exception ex) {
            logger.error("start failed",ex);
            game.error("start failed!",ex);
        }finally {
            boardPanel.repaint();
        }

    }



}
