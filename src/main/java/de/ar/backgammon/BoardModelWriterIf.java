package de.ar.backgammon;

import java.io.IOException;

public interface BoardModelWriterIf {
    void write(String modelName,BoardModelIf bmodel) throws IOException;

    void writeSaveMap(BoardModelIf boardModel)throws IOException, BException;

}
