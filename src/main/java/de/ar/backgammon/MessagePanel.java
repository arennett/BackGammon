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
        jTextArea.setRows(2);
        jTextArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        DefaultCaret caret = (DefaultCaret) jTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        scrollPane.setAutoscrolls(true);
        jTextArea.setEditable(false);
        jTextArea.setBackground(Color.WHITE);
        this.add(scrollPane);

    }

    public void message(String message) {

        this.setBackground(Color.WHITE);
        jTextArea.setText(" >>> "+message);
    }


    public void append(String message) {
        this.setBackground(Color.WHITE);
        jTextArea.append("\n >>> " + message);
        //test
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
        jTextArea.setText(" >>> "+message);

    }
}
