package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonPanel extends JPanel implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(ButtonPanel.class);
    private final ButtonPanelController bpController;

    JButton jButton_start;
    public ButtonPanel(ButtonPanelController bpController){
        this.bpController = bpController;

        initUi();
    }

    private void initUi() {
        TitledBorder tb = new TitledBorder("Control Panel");
        setBorder(tb);
        jButton_start=new JButton("Start");
        jButton_start.addActionListener(this);
        add(jButton_start);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jButton_start){

            bpController.start();

        }
    }
}
