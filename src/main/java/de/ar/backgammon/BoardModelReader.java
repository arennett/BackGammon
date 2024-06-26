package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static de.ar.backgammon.ConstIf.BOARDMAP_FILENAME;
import static de.ar.backgammon.ConstIf.BOARDMAP_SETUP;

public class BoardModelReader implements BoardModelReaderIf{
    private static final Logger logger = LoggerFactory.getLogger(BoardModelReader.class);

    @Override
    public void read(String modelName, BoardModelIf bModel) throws IOException, BException {
        FileReader filereader;
        filereader = new FileReader(BOARDMAP_FILENAME+modelName+".txt");
        BufferedReader bufferedReader = new BufferedReader(filereader);
        logger.debug("reading...");
        while (bufferedReader.ready()){
            String line = bufferedReader.readLine();
            String[] strs= line.split(",");
            int pidx;
            int psize;
            if (strs[0].startsWith("p")){
                try{
                    pidx = Integer.parseInt(strs[0].substring(1));
                    psize =Integer.parseInt(strs[1]);
                }catch (NumberFormatException nfe){
                    throw new BException("read failed",nfe);
                }
                String scol=strs[2];
                BColor bColor =BColor.getBColor(scol);
                bModel.setPoint(pidx,psize,bColor);
            }
            if (strs[0].startsWith("bar")){
                try{
                   psize =Integer.parseInt(strs[1]);
                }catch (NumberFormatException nfe){
                    throw new BException("read failed",nfe);
                }
                String scol=strs[2];
                BColor bColor =BColor.getBColor(scol);
                bModel.setBar(psize,bColor);
            }
       }


    }

    @Override
    public void readSetupMap(BoardModelIf bmodel) throws IOException, BException {
        read(BOARDMAP_SETUP,bmodel);
    }
}
