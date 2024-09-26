package de.ar.backgammon.dbase;

import de.ar.backgammon.dbase.board.DbBoardsPanel;
import de.ar.backgammon.dbase.game.DbGamesPanel;
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
    DbGamesPanel panelGames;
    DbBoardsPanel panelBoards;

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
        panelGames = new DbGamesPanel();

        panelGames.getTable().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelBoards = new DbBoardsPanel();
        panelGames.getTable().getSelectionModel().addListSelectionListener(e -> {
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (e.getValueIsAdjusting()){
                    return;
                }
            if (lsm.isSelectionEmpty()) {
                return;
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int game_id = (int) panelGames.getModel().getValueAt(minIndex, 0);
                panelBoards.getModel().update(game_id);
                logger.debug("game idx:<{}>  game id:<{}> selected", minIndex, game_id);
                panelBoards.getTable().updateUI();
            }
        });

        panelMainControl.setLayout(new BorderLayout());
        panelMainControl.setBorder(BorderFactory.createTitledBorder("Recording"));


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

        if (panelGames.getTable().getRowCount()>0) {
            panelGames.getTable().getSelectionModel().setSelectionInterval(0, 0);
        }

    }

    public void updateTables() {
        panelGames.updateTables();
        int rcount = panelGames.getTable().getRowCount();
        if (rcount > 0) {
            int gameid = panelGames.getModel().getGameId(rcount - 1);
            panelBoards.updateTables(gameid);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jtbRecordingOnOff) {
            dbpControl.setRecordingOn(jtbRecordingOnOff.isSelected());
        }

    }


}
