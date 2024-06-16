package de.ar.backgammon;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static de.ar.backgammon.ConstIf.*;

public class BoardPanel extends JPanel {
    private final BoardRendererIf boardRenderer;
    JPanel innerBoard;

    public BoardPanel(BoardRendererIf boardRenderer) {
        this.boardRenderer = boardRenderer;
        initUi();
    }

    private void initUi() {
        TitledBorder tb = new TitledBorder("BoardPanel");
        setBorder(tb);
        innerBoard = new JPanel(){
            public void paint(Graphics g){
                super.paint(g);
                boardRenderer.render(g,this);

            }
        };
        setLayout(new BorderLayout());
        add(innerBoard,BorderLayout.CENTER);
        setPreferredSize(new Dimension(BOARD_WIDTH+BOARD_OFFSET,BOARD_HEIGTH+BOARD_OFFSET));
    }




}
