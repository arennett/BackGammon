package de.ar.backgammon.dbase;

import de.ar.backgammon.model.BoardModelIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DbasePanel extends JPanel implements ActionListener {
    private final BoardModelIf boardModel;
    private static final Logger logger = LoggerFactory.getLogger(DbasePanel.class);

    JToggleButton jtbRecordingOnOff;

    public DbasePanel(BoardModelIf boardModel) {

        this.boardModel = boardModel;
        initUi();
    }

    private void initUi() {
        TitledBorder tb = new TitledBorder("Dbase Panel");
        setBorder(tb);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor =GridBagConstraints.LINE_START;
        //c.weightx = 0.5;


        JPanel panelMainControl = new JPanel();
        JPanel panelGames = new JPanel();
        JPanel panelBoards = new JPanel();

        panelMainControl.setLayout(new BorderLayout());
        panelMainControl.setBorder(BorderFactory.createTitledBorder("Recording"));
        panelGames.setLayout(new BorderLayout());
        panelGames.setBorder(BorderFactory.createTitledBorder("Games"));


        panelBoards.setLayout(new BorderLayout());
        panelBoards.setBorder(BorderFactory.createTitledBorder("Boards"));


        jtbRecordingOnOff = new JToggleButton("TEST");
        jtbRecordingOnOff.addActionListener(this);
        panelMainControl.add(jtbRecordingOnOff);

        panelGames.add(new JButton("TEST"));
        panelGames.add(new JButton("TEST"),BorderLayout.SOUTH);

        panelBoards.add(new JButton("TEST"));


        c.gridx = 0;
        c.gridy = 0;
        c.weightx=0.0;
        add(panelMainControl, c);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx=0.5;
        add(panelGames, c);
        c.gridx = 3;
        c.gridy = 0;
        c.weightx=0.5;
        add(panelBoards, c);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
