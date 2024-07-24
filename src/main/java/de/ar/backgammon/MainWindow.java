package de.ar.backgammon;

import de.ar.backgammon.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements ComponentListener {
    static Logger logger = LoggerFactory.getLogger(MainWindow.class);

    public MainWindow() {
        super("BackGammon V1.0");
        //setSize(new Dimension(FRAME_WIDTH,FRAME_HEIGTH));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setMaximumSize(new Dimension(600,800));
        this.addComponentListener(this);
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
        SequenceStack pointSequenceStack =new SequenceStack(bModel);

        GameControl gameControl=new GameControl(game,bModel,boardPanel,bmReader,bmWriter,dicesControl, pointSequenceStack);
        dicesControl.setSequenceStack(pointSequenceStack);
        dicesControl.setGameControl(gameControl);
        pointSequenceStack.setGameControl(gameControl);

        boardPanel.setGameControl(gameControl);

        ButtonPanelControl bpControl= new ButtonPanelControl(
                game,
                boardPanel,
                bModel,
                bmReader,
                gameControl
        );

        DicesPanel dicesPanel = new DicesPanel(dicesControl, pointSequenceStack);
        ButtonPanel buttonPanel= new ButtonPanel(bpControl,game,gameControl,dicesPanel);
        gameControl.setButtonPanel(buttonPanel);
        gameControl.setButtonPanelControl(bpControl);



        add(boardPanel,BorderLayout.CENTER);
        add(messagePanel,BorderLayout.SOUTH);
        add(buttonPanel,BorderLayout.EAST);
        pack();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        double w = getSize().getWidth();
        double h = getSize().getHeight();
        int maxh=900;
        int maxw=maxh*7/5;
        if(w > maxw && h > maxh){
            setSize(new Dimension(maxw, maxh));
            repaint();
            revalidate();
        }

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
