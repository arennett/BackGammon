package de.ar.backgammon.points;

import de.ar.backgammon.BColor;
import de.ar.backgammon.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.ar.backgammon.ConstIf.MAX_PIECES_ON_POINT;

public class PointValidator implements PointValidatorIf{

    public ValidationError err;
    public  PointValidator(){
        err = new ValidationError();
    }

    private static final Logger logger = LoggerFactory.getLogger(PointValidator.class);
    @Override
    public boolean isValid(BPoint point, int spc,BColor turn) {
        logger.debug("validate point: {} ...", point);

        if (point.isOffPoint()) {
            logger.debug("validate offpoint: {} ok", point);
            return true;
        }

        if (point.getPieceCount() > 1) {
            if (point.getPieceColor() != turn) {
                err.nr=1;
                err.userMessage ="Wrong point color!";
                logger.debug("err<{}> point<{}> <{}>",err.nr, point,err.userMessage);
                return false;
            }
        }

        if (point.getPieceCount() + spc > MAX_PIECES_ON_POINT) {
            err.nr=2;
            err.userMessage ="You can only put up to 5 pieces on a field";
            logger.debug("err<{}> point<{}> <{}>",err.nr, point,err.userMessage);
            return false;
        }

        if (point.isBarPoint()) {
            err.nr=3;
            err.userMessage ="This is not a valid point!";
            logger.debug("err<{}> point<{}> <{}>",err.nr, point,err.userMessage);
            return false;
        }

        logger.debug("validate point: {} ok", point);
        return true;
    }
}
