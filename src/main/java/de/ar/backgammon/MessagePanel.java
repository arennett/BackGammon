package de.ar.backgammon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class MessagePanel extends JPanel {
    public MessagePanel() {
        initUi();
    }

    JTextArea jTextArea;

    private void initUi() {
        TitledBorder titledBorder = new TitledBorder("MessagePanel");
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(titledBorder);
        jTextArea = new JTextArea();
        jTextArea.setRows(20);
        jTextArea.setFont(new Font("Arial", Font.BOLD, 10));
        DefaultCaret caret = (DefaultCaret) jTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        scrollPane.setAutoscrolls(true);
        jTextArea.setEditable(false);
        jTextArea.setBackground(Color.WHITE);
        this.add(scrollPane);
        this.setPreferredSize(new Dimension(350,getHeight()));

    }

    public void message(String message) {

        this.setBackground(Color.WHITE);
        jTextArea.setText(" >> "+message);
    }


    public void append(String message) {
        this.setBackground(Color.WHITE);
        jTextArea.append("\n >> " + message);
    }

    public void error(String message, Throwable ex) {
        message(message);
        append(ex.getClass().getName() + " :" + ex.getMessage());
        if (ex.getCause() != null) {
            append(ex.getCause().getClass().getName() + " :" + ex.getCause().getMessage());
        }

    }

    public void message_error(String message) {
        this.setBackground(Color.YELLOW);
        jTextArea.append("\n >>> "+message);

    }

    public void clear() {
        message("Welcome to BackGammon V1.0");
    }
}
