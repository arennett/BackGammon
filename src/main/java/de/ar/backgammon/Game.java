package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {
    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private final MessagePanel mpanel;

    public Game(MessagePanel mpanel){

        this.mpanel = mpanel;
    }

    public void message(String message){
        mpanel.message(message);
    }
    public void message_append(String message){
        mpanel.append(message);
    }

    public void error(String message,Throwable ex){
        mpanel.error(message,ex);
        logger.error(message,ex);
    }


    public void message_error(String message) {
        mpanel.message_error(message);
    }


}
