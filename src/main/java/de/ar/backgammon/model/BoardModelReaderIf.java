package de.ar.backgammon.model;

import de.ar.backgammon.BException;
import de.ar.backgammon.model.BoardModelIf;

import java.io.IOException;

public interface BoardModelReaderIf {
    void read(String modelName, BoardModelIf bmodel) throws IOException, BException;

    void readSetupMap(BoardModelIf bmodel) throws IOException, BException;

    void readSaveMap(BoardModelIf boardModel)throws IOException, BException;
}
