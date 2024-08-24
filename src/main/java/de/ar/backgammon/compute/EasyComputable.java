package de.ar.backgammon.compute;

import de.ar.backgammon.GameControl;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.moves.MoveSet;
import de.ar.backgammon.moves.MoveSetHash;
import de.ar.backgammon.moves.MoveSetListGeneratorIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;

public class EasyComputable implements ComputableIf{
    private final BoardModelIf bModel;
    private Random random;
    private final MoveSetListGeneratorIf moveSetListGenerator;
    private static final Logger logger = LoggerFactory.getLogger(EasyComputable.class);
    public EasyComputable(BoardModelIf bModel,MoveSetListGeneratorIf moveSetListGenerator){

        this.bModel = bModel;
        this.moveSetListGenerator = moveSetListGenerator;
        this.random = new Random();
    }
    @Override
    public MoveSet compute() {
        logger.debug("huhu, i am computing");
        MoveSetHash moveSets=moveSetListGenerator.getValidMoveSets();
        if (moveSets.isEmpty()){
            logger.debug("no valid moves found");
                return null;
        }
        int size =moveSets.size();

        int idx =random.nextInt(size);
        MoveSet mset=(MoveSet) moveSets.toArray()[idx];
        logger.debug("random mset: {} computed",mset);
        return mset;
    }
}
