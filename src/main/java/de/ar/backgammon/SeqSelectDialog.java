package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SeqSelectDialog extends JDialog implements ActionListener {
    static Logger logger = LoggerFactory.getLogger(SeqSelectDialog.class);

    JButton jbOption1;
    JButton jbOption2;
    private ArrayList<PipSequence> psArray;
    int option = -1;


    public SeqSelectDialog(){
        initUI();
    }

    private void initUI() {
        setTitle("BackGammon V1.0");
        setModal(true);
        setBounds(132, 132, 300, 200);
        Container dialogContainer = getContentPane();
        dialogContainer.setLayout(new BorderLayout());
        dialogContainer.add(new JLabel("          Please chose a move...")
                , BorderLayout.CENTER);
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        jbOption1 = new JButton();
        jbOption1.addActionListener(this);
        jbOption2 = new JButton();
        jbOption2.addActionListener(this);

        panel1.add(jbOption1);
        panel1.add(jbOption2);

        dialogContainer.add(panel1, BorderLayout.SOUTH);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jbOption1) {
            option=0;
            
        } else if (e.getSource()==jbOption2) {
            option=1;
        }
        setVisible(false);

    }

    public void setSequences(ArrayList<PipSequence> psArray) {

        this.psArray = psArray;
        this.jbOption1.setText(psArray.get(0).toString());
        this.jbOption2.setText(psArray.get(1).toString());


    }

    public int getOption() {
        return option;
    }
}
