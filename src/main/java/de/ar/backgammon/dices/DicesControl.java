package de.ar.backgammon.dices;

import de.ar.backgammon.Game;
import de.ar.backgammon.GameControl;
import de.ar.backgammon.model.BoardModelIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * holds the dices stack for the game and updates the components of the dices panel
 */
public class DicesControl {
    private static final Logger logger = LoggerFactory.getLogger(DicesControl.class);
    private final Game game;
    private final BoardModelIf bModel;

    private DicesStack dicesStack;

    private DicesPanel dicesPanel;
    private GameControl gameControl;

    public DicesControl(Game game, BoardModelIf bModel) {
        this.game = game;
        this.bModel = bModel;
        this.dicesStack = bModel.getDicesStack();
    }

    /**
     * clear dices stack and update components
     */
    public void clear() {
        dicesStack.clear();
        updateComponents();
    }
    public void updateComponents() {
        dicesPanel.updateComponents();
    }


    /**
     * throw dices and update components
     */
    public void throwDices() {
        if (!gameControl.isRunning()) {
            game.message_error("start a new game!");
            return;
        }
        dicesStack.throwDices();
        updateComponents();
        dicesThrown();
    }

    /*setter and getter*******************************************************************/
    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    public DicesStack getDicesStack() {
        return dicesStack;
    }

    public void setDicesPanel(DicesPanel dicesPanel) {
        this.dicesPanel = dicesPanel;
    }

    public void removePipsFromStack(int range, int spc) {
        dicesStack.removePipsFromStack(range, spc);
        updateComponents();
    }

    /**
     * load dices from the model into the dicesStack

    public void loadDices() {
         dicesStack.loadDices(bModel.getDices());
         dicesPanel.updateComponents();
         dicesThrown();

    }

    public void saveDices() {
        bModel.setDices(dicesStack.dices);
    }
     */

    /**
     * called after one or two dices were thrown,
     * it's a callback by the dices stack
     */
    public void dicesThrown() {
        gameControl.dicesThrown();
    }
}
