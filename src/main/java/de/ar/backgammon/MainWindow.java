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
        BoardPanel boardPanel = new BoardPanel(bRenderer,game);

        BoardModelReaderIf bmReader=new BoardModelReader();
        BoardModelWriterIf bmWriter=new BoardModelWriter();


        DicesControl dicesControl = new DicesControl(game,bModel);
        PipSequenceControl pointSequenceControl=new PipSequenceControl(bModel);

        GameControl gameControl=new GameControl(game,bModel,boardPanel,bmReader,bmWriter,dicesControl,pointSequenceControl);
        dicesControl.setPsControl(pointSequenceControl);
        dicesControl.setGameControl(gameControl);
        pointSequenceControl.setGameControl(gameControl);

        boardPanel.setGameControl(gameControl);

        ButtonPanelControl bpControl= new ButtonPanelControl(
                game,
                boardPanel,
                bModel,
                bmReader,
                gameControl
        );

        DicesPanel dicesPanel = new DicesPanel(dicesControl, pointSequenceControl);
        ButtonPanel buttonPanel= new ButtonPanel(bpControl,game,gameControl,dicesPanel);
        gameControl.setButtonPanel(buttonPanel);
        gameControl.setButtonPanelControl(bpControl);



        add(boardPanel,BorderLayout.CENTER);
        add(messagePanel,BorderLayout.SOUTH);
        add(buttonPanel,BorderLayout.EAST);
        pack();
    }

}
