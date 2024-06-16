package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    static Logger logger = LoggerFactory.getLogger(MainWindow.class);

    public MainWindow(){
        super("BackGammon V1.0");
        //setSize(new Dimension(FRAME_WIDTH,FRAME_HEIGTH));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUi();
        setVisible(true);
    }

    private void initUi() {
        setLayout(new BorderLayout());

        MessagePanel messagePanel= new MessagePanel();
        messagePanel.message("Welcome to BackGammon V1.0");
        Game game = new Game(messagePanel);

        BoardModelIf bModel= new BoardModel();
        BoardRenderer bRenderer = new BoardRenderer(bModel);
        BoardPanel boardPanel = new BoardPanel(bRenderer);

        BoardModelReaderIf bmReader=new BoardModelReader();
        ButtonPanelController bpController= new ButtonPanelController(
                game,
                boardPanel,
                bModel,
                bmReader
        );
        ButtonPanel buttonPanel= new ButtonPanel(bpController);

        add(boardPanel,BorderLayout.CENTER);
        add(messagePanel,BorderLayout.SOUTH);
        add(buttonPanel,BorderLayout.EAST);
        pack();
    }

}
