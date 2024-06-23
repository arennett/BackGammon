package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonPanel extends JPanel implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(ButtonPanel.class);
    private final ButtonPanelController bpController;
    private final GameControl gControl;

    JButton jButton_start;

    JLabel lbTurnText, lbTurnColor;

    public ButtonPanel(ButtonPanelController bpController,Game game,GameControl gControl){
        this.bpController = bpController;
        this.gControl = gControl;

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
        lbTurnColor = new JLabel(" ");
        lbTurnColor.setOpaque(true);
        panelTurn.add(lbTurnColor);
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

        updateComponents();
    }
public void updateComponents(){
   lbTurnColor.setBackground(gControl.getTurn().getColor());
}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jButton_start){

            bpController.start();

        }
    }



}
