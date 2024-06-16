package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BException extends Exception{
    private static final Logger logger = LoggerFactory.getLogger(BException.class);
    public BException(String message, Throwable rootCause){
        super(message,rootCause);
        logger.error(message,rootCause);
    }
    public BException(String message){
        this(message,null);
    }

}
