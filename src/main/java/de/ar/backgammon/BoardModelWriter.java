package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static de.ar.backgammon.ConstIf.*;

public class BoardModelWriter implements BoardModelWriterIf{
    private static final Logger logger = LoggerFactory.getLogger(BoardModelWriter.class);
    @Override
/*
p0,2,w
p5,5,r
p7,3,r
p11,5,w
p12,5,r
p16,3,w
p18,5,w
p23,2,r
bar,0,r
bar,0,w
turn,x
 */
    public void write(String modelName, BoardModelIf bmodel) throws IOException {
        FileWriter filewriter;
        filewriter = new FileWriter(BOARDMAP_FILENAME+modelName+".txt");
        BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
        logger.debug("writing...");
        for(int idx=0;idx<BoardModel.MAX_POINTS;idx++){
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
        int cnt =bmodel.getBar().getCount(BColor.RED);
        bufferedWriter.write("bar,"+cnt+",r");
        bufferedWriter.newLine();
        cnt =bmodel.getBar().getCount(BColor.WHITE);
        bufferedWriter.write("bar,"+cnt+",w");
        bufferedWriter.newLine();

        bufferedWriter.close();
    }

    @Override
    public void writeSaveMap(BoardModelIf boardModel) throws IOException, BException {
        write(BOARDMAP_SAVE,boardModel);
    }

}
