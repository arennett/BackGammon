package de.ar.backgammon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static de.ar.backgammon.ConstIf.*;

public class BoardPanel extends JPanel {
    private final BoardRendererIf boardRenderer;

    public BoardPanel(BoardRendererIf boardRenderer) {
        this.boardRenderer = boardRenderer;
        initUi();
    }

    private void initUi() {
        TitledBorder tb = new TitledBorder("BoardPanel");
        setBorder(tb);
        setPreferredSize(new Dimension(BOARD_WIDTH,BOARD_HEIGTH));
    }
    public void paint(Graphics g){
        super.paint(g);
        boardRenderer.render(g);

    }



}
