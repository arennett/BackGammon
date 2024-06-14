package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    static Logger logger = LoggerFactory.getLogger(MainWindow.class);
    static int FRAME_WIDTH  = 800;
    static int FRAME_HEIGTH = 600;

    public MainWindow(){
        super("BackGammon V1.0");
        //setSize(new Dimension(FRAME_WIDTH,FRAME_HEIGTH));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUi();
        setVisible(true);
    }

    private void initUi() {
        setLayout(new BorderLayout());
        BoardRenderer bRenderer = new BoardRenderer();
        BoardPanel bPanel = new BoardPanel(bRenderer);
        MessagePanel messagePanel= new MessagePanel();
        messagePanel.message("Welcome to JackGammon");
        add(bPanel,BorderLayout.CENTER);
        add(messagePanel,BorderLayout.SOUTH);
        pack();
    }

}
