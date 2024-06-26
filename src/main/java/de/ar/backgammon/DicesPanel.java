package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

public class DicesPanel extends JPanel implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(DicesPanel.class);
    private final DicesControl dc;

    public DicesPanel(DicesControl dc) {
        this.dc = dc;
        dc.setDicesPanel(this);

        initUi();
    }


    JLabel jlDice1,jlDice2,jlStack;
    JButton jbThrow;

    private void initUi() {
        setBorder(new TitledBorder(("Dices")));
        setLayout(new GridLayout(3,1));
        JPanel dpanel= new JPanel();
        dpanel.setBorder(new TitledBorder(("")));
        dpanel.setLayout(new GridLayout(1,2));
        jlDice1=new JLabel();
        jlDice2=new JLabel();
        dpanel.add(jlDice1);
        dpanel.add(jlDice2);
        add(dpanel);
        jbThrow = new JButton("throw");
        jbThrow.addActionListener(this);
        add(jbThrow);
        JPanel spanel= new JPanel();
        spanel.setBorder(new TitledBorder(("Stack")));
        spanel.setLayout(new BorderLayout());
        jlStack=new JLabel("[ ][ ][ ][ ]");
        spanel.add(jlStack);

        add(spanel);
        updateComponents();
    }
    public void updateComponents(){
        jlDice1.setText("["+dc.getDice1()+"]");
        jlDice2.setText("["+dc.getDice2()+"]");
        StringBuffer sb = new StringBuffer("");
        for (int i=0; i< 4 ; i++){
            if (i< dc.getPointStack().size()){
                sb.append("["+dc.getPointStack().get(i)+"]");
            }else{
                sb.append("[  ]");
            }
        }
        jlStack.setText(sb.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jbThrow){
            dc.throwDices();
        }

    }
}
