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

    JButton jButton_start;
    JToggleButton jtbSetMode;

    JLabel lbTurnText;
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

        jButton_start=new JButton("Start");
        jButton_start.addActionListener(this);
        c.gridx = 0;
        c.gridy = 2;
        add(jButton_start,c);

        jtbSetMode=new JToggleButton("SetMode");
        jtbSetMode.addActionListener(this);
        c.gridx = 0;
        c.gridy = 3;
        add(jtbSetMode,c);

        c.gridx = 0;
        c.gridy = 4;
        add(dicesPanel,c);
        updateComponents();
    }
public void updateComponents(){
   jbTurnColor.setBackground(gControl.getTurn().getColor());
}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jButton_start){

            bpController.start();

        }else if (e.getSource()==jtbSetMode){
            bpController.setSetMode(jtbSetMode.isSelected());

        }else if (e.getSource()==jbTurnColor){
            bpController.switchTurn();

        }
    }



}
