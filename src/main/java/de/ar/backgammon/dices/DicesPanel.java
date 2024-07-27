package de.ar.backgammon.dices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DicesPanel extends JPanel implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(DicesPanel.class);
    private final DicesControl dc;


    public DicesPanel(DicesControl dc) {
        this.dc = dc;

        dc.setDicesPanel(this);

        initUi();
    }


    JButton jbDice1, jbDice2;
    JTextArea jtaStack;
    JButton jbThrow;

    private void initUi() {
        setBorder(new TitledBorder(("Dices")));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        JPanel dpanel= new JPanel();
        dpanel.setBorder(new TitledBorder(("")));
        dpanel.setLayout(new GridLayout(1,2));
        jbDice1 =new JButton("[ ]");
        jbDice1.addActionListener(this);
        jbDice2 =new JButton("[ ]");
        jbDice2.addActionListener(this);
        dpanel.add(jbDice1);
        dpanel.add(jbDice2);
        c.gridx = 0;
        c.gridy = 0;
        add(dpanel,c);
        jbThrow = new JButton("throw");
        jbThrow.addActionListener(this);
        c.gridx = 0;
        c.gridy = 1;
        add(jbThrow,c);
        JPanel spanel= new JPanel();
        spanel.setBorder(new TitledBorder(("Stack")));
        spanel.setLayout(new BorderLayout());
        jtaStack=new JTextArea("[ ][ ][ ][ ]");
        spanel.add(jtaStack);
        c.gridx = 0;
        c.gridy = 2;
        add(spanel,c);
        updateComponents();
    }
    public void updateComponents(){
        int dice1=dc.getDicesStack().getDices().dice1;
        int dice2=dc.getDicesStack().getDices().dice2;

        if (dc.getDicesStack().getState()== DicesStack.State.UPDATED) {
            jbDice1.setText("["+(dice1>0?dice1:" ") +"]");
            jbDice2.setText("["+(dice2>0?dice2:" ") +"]");
        }else{
            jbDice1.setText("[ ]");
            jbDice2.setText("[ ]");
        }

        StringBuffer sb = new StringBuffer("    ");
        for (int i=0; i< 4 ; i++){
            if (i< dc.getDicesStack().size()){
                sb.append("["+dc.getDicesStack().get(i)+"]");
            }else{
                sb.append("[  ]");
            }
        }
        for (PipSequence ps: dc.getDicesStack().getSequenceStack().getPipSequences()){
            sb.append("\n"+ps);
        }


            jtaStack.setText(sb.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jbThrow){
            dc.throwDices();
        }
        if(e.getSource()==jbDice1){
            String s = JOptionPane.showInputDialog("Pip (1-6): ");
            try{
                int pip =Integer.parseInt(s);
                dc.getDicesStack().getDices().dice1=pip;
                dc.getDicesStack().update();
                updateComponents();
                dc.dicesThrown();
            }catch (NumberFormatException ne){
                //
            }
        }
        if(e.getSource()==jbDice2){
            String s = JOptionPane.showInputDialog("Pip (1-6): ");
            try{
                int pip =Integer.parseInt(s);
                dc.getDicesStack().getDices().dice2=pip;
                dc.getDicesStack().update();
                updateComponents();
                dc.dicesThrown();
            }catch (NumberFormatException ne){
                //do noting
            }
        }

    }
}
