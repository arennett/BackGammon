package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(ButtonPanel.class);
    private final ButtonPanelControl bpController;
    private final GameControl gControl;
    private final DicesPanel dicesPanel;

    JButton jButtonStart, jbSave, jbLoad;
    JToggleButton jtbSetMode;
    JTextArea jtaStatistics;


    JButton jbTurnColor;


    public ButtonPanel(ButtonPanelControl bpController, Game game, GameControl gControl, DicesPanel dicesPanel){
        this.bpController = bpController;
        this.gControl = gControl;
        this.dicesPanel = dicesPanel;

        initUi();
    }

    private void initUi() {
        TitledBorder tb = new TitledBorder("Control Panel");
        setBorder(tb);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JPanel panelTurn = new JPanel();
        panelTurn.setLayout(new BorderLayout());
        panelTurn.setBorder(BorderFactory.createTitledBorder("Turn"));
        jbTurnColor = new JButton();
        jbTurnColor.setOpaque(true);
        jbTurnColor.addActionListener(this);
        panelTurn.add(jbTurnColor);
        c.gridx = 0;
        c.gridy = 0;
        add(panelTurn,c);

        c.gridx = 0;
        c.gridy = 1;
        add(new JSeparator(),c);

        jButtonStart =new JButton("Start");
        jButtonStart.addActionListener(this);
        c.gridx = 0;
        c.gridy = 2;
        add(jButtonStart,c);

        jtbSetMode=new JToggleButton("SetMode");
        jtbSetMode.addActionListener(this);
        c.gridx = 0;
        c.gridy = 3;
        add(jtbSetMode,c);

        jbSave =new JButton("Save");
        jbSave.addActionListener(this);
        c.gridx = 0;
        c.gridy = 4;
        add(jbSave,c);

        jbLoad =new JButton("Load");
        jbLoad.addActionListener(this);
        c.gridx = 0;
        c.gridy = 5;
        add(jbLoad,c);

        c.gridx = 0;
        c.gridy = 6;
        add(dicesPanel,c);

        jtaStatistics=new JTextArea();
        JPanel jpStat = new JPanel();
        jpStat.setBorder(new TitledBorder("Statistics"));
        jpStat.add(jtaStatistics);

        c.gridx = 0;
        c.gridy = 7;
        add(jpStat,c);

        updateComponents();
    }
public void updateComponents(){
   jbTurnColor.setBackground(gControl.getTurn().getColor());

    int wout=bpController.getBoardModel().getPoint(BoardModel.POINT_IDX_OFF_WHITE).getPieceCount();
    int rout=bpController.getBoardModel().getPoint(BoardModel.POINT_IDX_OFF_RED).getPieceCount();
    int win  = bpController.getBoardModel().getBoardPiecesCount(BColor.WHITE);
    int rin = bpController.getBoardModel().getBoardPiecesCount(BColor.RED);



    String wstr=String.format("WHITE \tIN/OUT: %d/%d",win,wout);
    String rstr=String.format("\nRED \tIN/OUT: %d/%d",rin,rout);
    jtaStatistics.setText(wstr);
    jtaStatistics.append(rstr);



}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== jButtonStart){

            bpController.start();

        }else if (e.getSource()==jtbSetMode){
            bpController.setSetMode(jtbSetMode.isSelected());

        }else if (e.getSource()==jbTurnColor){
            bpController.switchTurn();

        }else if (e.getSource()== jbSave){
            bpController.saveModel();

        }else if (e.getSource()== jbLoad){
            bpController.loadModel();

        }
    }



}
