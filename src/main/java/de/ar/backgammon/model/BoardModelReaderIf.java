package de.ar.backgammon.model;

import de.ar.backgammon.BException;
import de.ar.backgammon.model.BoardModelIf;

import java.io.IOException;

public interface BoardModelReaderIf {

    void readFile(String fileName, BoardModelIf bModel) throws IOException, BException;

    void readModel(BoardModelIf bmodel, String modelName) throws IOException, BException;

    void readTestModel(BoardModelIf bmodel, String modelName) throws IOException, BException;

    void readSetupMap(BoardModelIf bmodel) throws IOException, BException;

    void readSaveMap(BoardModelIf bmodel) throws IOException, BException;
}
