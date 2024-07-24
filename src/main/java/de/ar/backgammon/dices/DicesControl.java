package de.ar.backgammon.dices;

import de.ar.backgammon.Game;
import de.ar.backgammon.GameControl;
import de.ar.backgammon.model.BoardModelIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DicesControl {
    private static final Logger logger = LoggerFactory.getLogger(DicesControl.class);
    private final Game game;
    private final BoardModelIf bModel;

    DicesStack dicesStack;

    private DicesPanel dicesPanel;
    private GameControl gameControl;

    public DicesControl(Game game, BoardModelIf bModel) {
        this.game = game;
        this.bModel = bModel;
        this.dicesStack = new DicesStack(bModel);
    }

    /**
     * clear pip stack and pip sequences
     */
    public void clear() {
        dicesStack.clear();

        dicesPanel.updateComponents();
    }


    public void throwDices() {
        if (!gameControl.isRunning()) {
            game.message_error("start a new game!");
            return;
        }
        dicesStack.throwDices();
        dicesPanel.updateComponents();

    }

    /**
     * update the stack after a dices roll
     * followed by updating the pip sequences
     */
    public void updateStack() {
        dicesStack.update();
        dicesPanel.updateComponents();
        gameControl.dicesThrown();
    }

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }


    /*setter and getter*******************************************************************/

    public DicesStack getDicesStack() {
        return dicesStack;
    }

    public void setDicesPanel(DicesPanel dicesPanel) {
        this.dicesPanel = dicesPanel;
    }

    public void removePipsFromStack(int range, int spc) {
        dicesStack.removePipsFromStack(range, spc);
        dicesPanel.updateComponents();
    }


    public void loadDices() {
        dicesStack.dices.dice1= bModel.getDice1();
        dicesStack.dices.dice2= bModel.getDice1();
        dicesPanel.updateComponents();

    }

    public void saveDices() {
        bModel.setDice1(dicesStack.dices.dice1);
        bModel.setDice2(dicesStack.dices.dice2);
    }
}
