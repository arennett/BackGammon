package de.ar.backgammon;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class MessagePanel extends JPanel {
    public MessagePanel(){
        initUi();
    }
    JTextArea textArea;

    private void initUi() {
        TitledBorder titledBorder = new TitledBorder("MessagePanel");
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        setBorder(titledBorder);
        textArea = new JTextArea();
        textArea.setRows(3);
        textArea.setFont(new Font("Times New Roman",Font.PLAIN,20));
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane (textArea);
        scrollPane.setAutoscrolls(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        this.add(scrollPane);
  }
  public void message(String message){
        textArea.setText(message);
  }
    public void append(String message){
        textArea.append("\n"+message);
        //test
    }
    public void error(String message,Throwable ex){
        message(message);
        append(ex.getClass().getName()+" :"+ ex.getMessage());
        if(ex.getCause()!=null){
            append(ex.getCause().getClass().getName()+" :"+ex.getCause().getMessage());
        }

    }

}
