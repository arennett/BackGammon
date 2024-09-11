package de.ar.backgammon.dbase;

import de.ar.backgammon.dbase.entity.DbGamePanel;
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
    DbasePanelControl dbpControl;

    public DbasePanel(DbasePanelControl dbpControl,BoardModelIf boardModel) {
        this.dbpControl = dbpControl;
        this.boardModel = boardModel;
        initUi();
    }

    private void initUi() {
        TitledBorder tb = new TitledBorder("Dbase Panel");
        setBorder(tb);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        //c.fill = GridBagConstraints.BOTH;
        c.anchor =GridBagConstraints.LINE_START;
        //c.weightx = 0.5;


        JPanel panelMainControl = new JPanel();
        panelMainControl.setPreferredSize(new Dimension(200,150));
        DbGamePanel panelGames = new DbGamePanel();
        JPanel panelBoards = new JPanel();
        panelBoards.setPreferredSize(new Dimension(200,150));

        panelMainControl.setLayout(new BorderLayout());
        panelMainControl.setBorder(BorderFactory.createTitledBorder("Recording"));

        panelBoards.setLayout(new BorderLayout());
        panelBoards.setBorder(BorderFactory.createTitledBorder("Boards"));

        jtbRecordingOnOff = new JToggleButton("REC");
        jtbRecordingOnOff.addActionListener(this);
        panelMainControl.add(jtbRecordingOnOff,BorderLayout.NORTH);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx=0.0;
        add(panelMainControl, c);
        c.gridx = 1;
        c.gridy = 0;
       // c.weightx=0.5;
        add(panelGames, c);
        c.gridx = 2;
        c.gridy = 0;
        //c.weightx=0.5;
        add(panelBoards, c);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jtbRecordingOnOff) {
            dbpControl.setRecordingOn(jtbRecordingOnOff.isSelected());
        }

    }
}
