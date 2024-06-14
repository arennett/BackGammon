package de.ar.backgammon;

import javax.swing.*;
import java.awt.*;

public class GlobalConfig {
    public static GlobalConfig inst = null;
    public static GlobalConfig getInstance(){
        if (inst==null){
            inst = new GlobalConfig();
        }
        return inst;
    }
    private GlobalConfig(){}

    public void doConfig(){

//        UIManager.put("Button.font", /* font of your liking */);
//        UIManager.put("ToggleButton.font", /* font of your liking */);
//        UIManager.put("RadioButton.font", /* font of your liking */);
//        UIManager.put("CheckBox.font", /* font of your liking */);
//        UIManager.put("ColorChooser.font", /* font of your liking */);
//        UIManager.put("ComboBox.font", /* font of your liking */);
//        UIManager.put("Label.font", /* font of your liking */);
//        UIManager.put("List.font", /* font of your liking */);
//        UIManager.put("MenuBar.font", /* font of your liking */);
//        UIManager.put("MenuItem.font", /* font of your liking */);
//        UIManager.put("RadioButtonMenuItem.font", /* font of your liking */);
//        UIManager.put("CheckBoxMenuItem.font", /* font of your liking */);
//        UIManager.put("Menu.font", /* font of your liking */);
//        UIManager.put("PopupMenu.font", /* font of your liking */);
//        UIManager.put("OptionPane.font", /* font of your liking */);
//        UIManager.put("Panel.font", /* font of your liking */);
//        UIManager.put("ProgressBar.font", /* font of your liking */);
//        UIManager.put("ScrollPane.font", /* font of your liking */);
//        UIManager.put("Viewport.font", /* font of your liking */);
//        UIManager.put("TabbedPane.font", /* font of your liking */);
//        UIManager.put("Table.font", /* font of your liking */);
//        UIManager.put("TableHeader.font", /* font of your liking */);
//        UIManager.put("TextField.font", /* font of your liking */);
//        UIManager.put("PasswordField.font", /* font of your liking */);
//        UIManager.put("TextArea.font", /* font of your liking */);
//        UIManager.put("TextPane.font", /* font of your liking */);
//        UIManager.put("EditorPane.font", /* font of your liking */);
//        UIManager.put("TitledBorder.font", /* font of your liking */);
//        UIManager.put("ToolBar.font", /* font of your liking */);
//        UIManager.put("ToolTip.font", /* font of your liking */);
//        UIManager.put("Tree.font", /* font of your liking */);
          UIManager.put("TitledBorder.font", new Font("Arial",  Font.ITALIC, 10));
          UIManager.put("Menu.font",  new Font("Arial", Font.BOLD,14));
          UIManager.put("MenuBar.font",  new Font("Arial", Font.BOLD,16));
          UIManager.put("MenuItem.font", new Font("Arial",Font.BOLD, 12));

    }
}
