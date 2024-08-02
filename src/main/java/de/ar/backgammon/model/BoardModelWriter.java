package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BException;
import de.ar.backgammon.points.BPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static de.ar.backgammon.ConstIf.BOARDMAP_FILENAME;
import static de.ar.backgammon.ConstIf.BOARDMAP_SAVE;

public class BoardModelWriter implements BoardModelWriterIf{
    private static final Logger logger = LoggerFactory.getLogger(BoardModelWriter.class);
    @Override

    public void write(String modelName, BoardModelIf bmodel) throws IOException {
        FileWriter filewriter;
        filewriter = new FileWriter(BOARDMAP_FILENAME+modelName+".txt");
        BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
        logger.debug("writing...");
        for(int idx = 0; idx <= BoardModel.MAX_POINTS; idx++){
            BPoint point = bmodel.getPoint(idx);
            if(!point.isEmpty()) {
                bufferedWriter.write("p");
                bufferedWriter.write(""+idx);
                bufferedWriter.write(",");
                bufferedWriter.write(""+point.getPieceCount());
                bufferedWriter.write(",");
                bufferedWriter.write(point.getPieceColor().getShortString());
                bufferedWriter.newLine();
            }
        }
        int  cnt =bmodel.getBarPoint(BColor.RED).getPieceCount();
        bufferedWriter.write("bar,"+cnt+",r");
        bufferedWriter.newLine();
        cnt =bmodel.getBarPoint(BColor.WHITE).getPieceCount();
        bufferedWriter.write("bar,"+cnt+",w");
        bufferedWriter.newLine();

        cnt =bmodel.getOffPoint(BColor.RED).getPieceCount();
        bufferedWriter.write("off,"+cnt+",r");
        bufferedWriter.newLine();
        cnt =bmodel.getOffPoint(BColor.WHITE).getPieceCount();
        bufferedWriter.write("off,"+cnt+",w");
        bufferedWriter.newLine();

        bufferedWriter.write("turn,"+bmodel.getTurn().getShortString());
        bufferedWriter.newLine();
        bufferedWriter.write("dice1,"+bmodel.getDices().dice1);
        bufferedWriter.newLine();
        bufferedWriter.write("dice2,"+bmodel.getDices().dice2);
        bufferedWriter.newLine();

        bufferedWriter.close();
    }

    @Override
    public void writeSaveMap(BoardModelIf boardModel) throws IOException, BException {
        write(BOARDMAP_SAVE,boardModel);
    }

}
