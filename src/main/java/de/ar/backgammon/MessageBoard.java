package de.ar.backgammon;

import javax.swing.*;
import java.awt.*;

public class MessageBoard extends JFrame {
    JTextArea jTextArea;
    public MessageBoard(){
        super("MessageBoard");
        setPreferredSize(new Dimension(800,600));
        initUI();
    }

    private void initUI() {
        jTextArea = new JTextArea();
        jTextArea.append("TEST TEST");
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        add(scrollPane);
        setAutoRequestFocus(true);
        pack();
    }

    public void message(String message){
        jTextArea.append("\n"+message);
    }

}
