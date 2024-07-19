package de.ar.backgammon;

import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;

import java.awt.*;

import static de.ar.backgammon.ConstIf.*;
import static de.ar.backgammon.ConstIf.PIECE_WIDTH;

public class BarRenderer {
    private final BoardModelIf bModel;

    public BarRenderer(BoardModelIf bModel){

        this.bModel = bModel;
    }

    boolean isSelected(BColor color) {
        if(color==BColor.WHITE
                && bModel.getPointSelectedIdx()== BoardModel.POINT_IDX_BAR_WHITE) {
              return true;
        }
        if(color==BColor.RED
                && bModel.getPointSelectedIdx()==BoardModel.POINT_IDX_BAR_RED) {
             return true;
        }
        return false;
    }
    public void draw(BColor color, Graphics2D g2d){
        int points=bModel.getBarPoint(color).getPieceCount();
        if (points==0){
            return;
        }
        int x1 = BOARD_WIDTH / 2 - BAR_WIDTH / 2 + (BAR_WIDTH-PIECE_WIDTH) / 2;
        int y1 = BOARD_HEIGTH / 2 -PIECE_WIDTH-5;
        int y2 = BOARD_HEIGTH / 2 +5;
        int w1 = PIECE_WIDTH;

        if (color==BColor.RED){
            y1=y2;
        }

        if (isSelected(color)) {
            g2d.setColor(Color.GREEN);
        }else {
            g2d.setColor(Color.BLACK);
        }

        g2d.setStroke(new BasicStroke(3));


        g2d.drawOval(x1,y1,w1,w1);

        g2d.setColor(color.getColor());
        w1=PIECE_WIDTH-2;
        g2d.fillOval(x1+1,y1+1,w1,w1);

        if (isSelected(color)) {
            g2d.setColor(Color.GREEN);
        }else {
            g2d.setColor(Color.BLACK);
        }

        w1=PIECE_WIDTH-10;
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval(x1+5,y1+5,w1,w1);

        g2d.drawString(""+points,x1+PIECE_WIDTH/2-3,y1+PIECE_WIDTH/2+5);

    }

    void render(Graphics2D g2d) {
        drawRect(BOARD_WIDTH / 2 - BAR_WIDTH / 2, 0, BAR_WIDTH, BOARD_HEIGTH, 2, g2d);
        draw(BColor.WHITE,g2d);
        draw(BColor.RED,g2d);

    }
    public void drawRect(int x, int y, int w, int h, int stroke_width, Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(stroke_width));
        g2d.drawRect(x , y , w, h);
        g2d.setStroke(new BasicStroke(1));
    }


}
