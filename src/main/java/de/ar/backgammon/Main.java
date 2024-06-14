package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("running main");
        GlobalConfig.getInstance().doConfig();
        new MainWindow();


    }
}