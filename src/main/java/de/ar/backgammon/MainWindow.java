package de.ar.backgammon;

import de.ar.backgammon.compute.ComputerIf;
import de.ar.backgammon.compute.EasyComputable;
import de.ar.backgammon.compute.MainComputer;
import de.ar.backgammon.dices.DicesControl;
import de.ar.backgammon.dices.DicesPanel;
import de.ar.backgammon.model.*;
import de.ar.backgammon.moves.MoveSetListGenerator;
import de.ar.backgammon.validation.MoveValidator;
import de.ar.backgammon.validation.MoveValidatorIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MainWindow extends JFrame implements ComponentListener {
    static Logger logger = LoggerFactory.getLogger(MainWindow.class);

    public MainWindow() {
        super("BackGammon V1.0");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        game.setBoardModel(bModel);
        MoveValidator moveValidator=new MoveValidator(bModel);
        bModel.setMoveValidator(moveValidator);
        BoardRenderer bRenderer = new BoardRenderer(bModel);
        BoardPanel boardPanel = new BoardPanel(bRenderer,game);

        BoardModelReaderIf bmReader=new BoardModelReader();
        BoardModelWriterIf bmWriter=new BoardModelWriter();


        DicesControl dicesControl = new DicesControl(game,bModel);

        EasyComputable ec= new EasyComputable(bModel,new MoveSetListGenerator(bModel));
        ComputerIf comp=new MainComputer(ec);

        GameControl gameControl=new GameControl(game,bModel,boardPanel,bmReader,bmWriter,dicesControl,comp);
        dicesControl.setGameControl(gameControl);
        boardPanel.setGameControl(gameControl);

        ButtonPanelControl bpControl= new ButtonPanelControl(
                game,
                boardPanel,
                bModel,
                bmReader,
                gameControl
        );

        DicesPanel dicesPanel = new DicesPanel(dicesControl);
        ButtonPanel buttonPanel= new ButtonPanel(bpControl,game,gameControl,dicesPanel);
        gameControl.setButtonPanel(buttonPanel);
        gameControl.setButtonPanelControl(bpControl);



        add(boardPanel,BorderLayout.CENTER);
        add(messagePanel,BorderLayout.WEST);
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
