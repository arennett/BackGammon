package de.ar.backgammon;

import de.ar.backgammon.dices.DicesPanel;
import de.ar.backgammon.model.BoardModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class ButtonPanel extends JPanel implements ActionListener, ChangeListener {
    private static final Logger logger = LoggerFactory.getLogger(ButtonPanel.class);
    private final ButtonPanelControl bpController;
    private final GameControl gControl;
    private final DicesPanel dicesPanel;

    JButton jbStart, jbSave, jbLoad;
    JRadioButton jrbCompWhite,jrbCompRed;
    JToggleButton jtbSetMode;
    JTextArea jtaStatistics;
    JSlider jsCompSpeed;



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

        jbStart =new JButton("Start");
        jbStart.addActionListener(this);
        c.gridx = 0;
        c.gridy = 2;
        add(jbStart,c);

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


        JPanel jpCompute = new JPanel();
        jpCompute.setBorder(new TitledBorder("Computing"));
        jpCompute.setLayout(new BorderLayout());

        jrbCompWhite= new JRadioButton("White");
        jrbCompWhite.addActionListener(this);
        jpCompute.add(jrbCompWhite,BorderLayout.WEST);

        jrbCompRed= new JRadioButton("Red");
        jrbCompRed.addActionListener(this);
        jpCompute.add(jrbCompRed,BorderLayout.EAST);

        jsCompSpeed= new JSlider(0,100);
        jsCompSpeed.setValue(50);
        bpController.setCompSpeed(jsCompSpeed.getValue());
        Hashtable<Integer, JLabel> labelTable =
                new Hashtable<Integer, JLabel>();
        labelTable.put(0,
                new JLabel("0") );

        labelTable.put(100 ,
                new JLabel("100") );
        jsCompSpeed.setLabelTable(labelTable);
        jsCompSpeed.setPaintLabels(true);
        jsCompSpeed.addChangeListener(this);
        jpCompute.add(jsCompSpeed,BorderLayout.SOUTH);


        c.gridx = 0;
        c.gridy = 8;
        add(jpCompute,c);



        updateComponents();
    }
public void updateComponents(){
   jbTurnColor.setBackground(gControl.getTurn().getColor());

    int wout=bpController.getBoardModel().getPoint(BoardModel.POINT_IDX_OFF_WHITE).getPieceCount();
    int rout=bpController.getBoardModel().getPoint(BoardModel.POINT_IDX_OFF_RED).getPieceCount();
    int win  = bpController.getBoardModel().getPiecesCount(BColor.WHITE);
    int rin = bpController.getBoardModel().getPiecesCount(BColor.RED);



    String wstr=String.format("WHITE \tIN/OUT: %d/%d",win,wout);
    String rstr=String.format("\nRED \tIN/OUT: %d/%d",rin,rout);
    jtaStatistics.setText(wstr);
    jtaStatistics.append(rstr);

    dicesPanel.updateComponents();



}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbStart) {

            bpController.start();

        } else if (e.getSource() == jtbSetMode) {
            bpController.setSetMode(jtbSetMode.isSelected());

        } else if (e.getSource() == jbTurnColor) {
            bpController.switchTurn();

        } else if (e.getSource() == jbSave) {
            bpController.saveModel();

        } else if (e.getSource() == jbLoad) {
            bpController.loadModel();

        } else if (e.getSource() == jrbCompWhite) {
            bpController.setCompWhiteOn(jrbCompWhite.isSelected());

        } else if (e.getSource() == jrbCompRed) {
            bpController.setCompRedOn(jrbCompRed.isSelected());

        }


    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource()==jsCompSpeed){
            bpController.setCompSpeed(jsCompSpeed.getValue());

        }
    }
}
