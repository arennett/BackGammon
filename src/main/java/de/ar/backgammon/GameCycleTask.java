package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class GameCycleTask extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger(GameCycleTask.class);
    private static GameControl gc;
    private static Timer timer;
    private final int period;


    private GameCycleTask(int period)  {
        this.period = period;
    }

    static public void init(GameControl gc){
        GameCycleTask.gc=gc;
    }


    static void startTimer(int period) throws BException {
        if (gc == null) {
            throw new BException("TimerTask not inited");
        }
        cancelTimer();
        TimerTask timerTask = new GameCycleTask(period);
        //running timer task as daemon thread
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, period);
        logger.debug("TimerTask started: period {} ms", period);
    }

    static void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            logger.debug("Timer canceled");
        }

    }

    @Override
    public void run() {
        if (!gc.isRunning()) {
            logger.debug("run Task / cancel Timer");
            timer.cancel();
        } else {
//            logger.debug("run Task");
            try {
                if (gc.isRequestTurnSwitch()) {
                    gc.switch_turn(null);
                }  else {
//                    logger.debug("nothing to do");
                }
            } catch (Exception e) {
                logger.error("switch failed", e);
                timer.cancel();
            }
        }

    }


}
