package de.ar.backgammon;

import de.ar.backgammon.model.BoardModelIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class DicesStack extends ArrayList<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(DicesStack.class);
    private final BoardModelIf bModel;


    public DicesStack(BoardModelIf bModel) {

        this.bModel = bModel;

    }




    @Override
    public void clear(){
        super.clear();

    }

    public void update() {
        clear();
        logger.debug("updating the stack...");
        int dice1 = bModel.getDice1();
        int dice2 = bModel.getDice2();

        if (bModel.isAllPiecesAtHome(bModel.getTurn())) {
            logger.debug("all at home, special stack handling");
            ArrayList<Integer> max = bModel.getHomePointMaxDuo(bModel.getTurn());
            int maxPoint1 = max.get(0);
            int maxPoint2 = max.get(1);

            if (dice1 >= maxPoint1 && maxPoint1 > -1) {
                dice1 = maxPoint1;
                if (dice2 > maxPoint2 && maxPoint2 > -1) {
                    dice2 = maxPoint2;
                }
            } else if (dice2 >= maxPoint1 && maxPoint1 > -1) {
                dice2 = maxPoint1;
                if (dice1 > maxPoint2 && maxPoint2 > -1) {
                    dice1 = maxPoint2;
                }
            }
        }
        add(dice1);
        add(dice2);
        if (dice1 == dice2) {
            add(dice1);
            add(dice2);
        }
    }
}
