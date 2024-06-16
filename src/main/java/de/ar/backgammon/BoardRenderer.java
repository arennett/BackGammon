package de.ar.backgammon;

import javax.swing.*;
import java.awt.*;

import static de.ar.backgammon.ConstIf.*;

public class BoardRenderer implements BoardRendererIf {
    static int BAR_WIDTH = 40;
    static int POINT_WIDTH = (BOARD_WIDTH - BAR_WIDTH) / 12;
    static int POINT_HEIGTH = BOARD_HEIGTH * 4 / 10;
    private final BoardModelIf bModel;

    public BoardRenderer(BoardModelIf bModel){

        this.bModel = bModel;
    }

    public void render(Graphics g, JPanel panel) {
        Graphics2D g2d = (Graphics2D) g;

        drawRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH, 2, g2d);
        // drawLine(PLAY_AREA_WIDTH/2,0,PLAY_AREA_WIDTH/2,PLAY_AREA_HEIGHT,g2d);
        draw_bar(g2d);

        for (int i = 0; i < 24; i++) {
            drawPoint(i, g2d,bModel.getPoint(i));
        }
    }


    public void drawRect(int x, int y, int w, int h, int stroke_width, Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(stroke_width));
        g2d.drawRect(x , y , w, h);
        g2d.setStroke(new BasicStroke(1));
    }


    private void drawPolygon(int[] x, int[] y, int stroke_width, boolean fill, Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(stroke_width));
        if (fill) {
            g2d.fillPolygon(x, y, x.length);
        } else {
            g2d.drawPolygon(x, y, x.length);
        }


        g2d.setStroke(new BasicStroke(1));
    }


    void draw_bar(Graphics2D g2d) {
        drawRect(BOARD_WIDTH / 2 - BAR_WIDTH / 2, 0, BAR_WIDTH, BOARD_HEIGTH, 2, g2d);
    }

    void drawPoint(int idx, Graphics2D g2d, BPoint point) {
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        if (idx < 6) {
            x1 = BOARD_WIDTH - POINT_WIDTH / 2 - idx * POINT_WIDTH;
            y1 = 0;
            x2 = x1;
            y2 = POINT_HEIGTH;
            drawPointTriangle(x1, y1, x2, y2, false, idx % 2 == 0, 2, g2d);

        } else if (idx < 12) {
            int offset = BOARD_WIDTH / 2 + BAR_WIDTH / 2 + POINT_WIDTH / 2;
            x1 = BOARD_WIDTH - offset - (idx - 6) * POINT_WIDTH;
            y1 = 0;
            x2 = x1;
            y2 = POINT_HEIGTH;
            drawPointTriangle(x1, y1, x2, y2, false, idx % 2 == 0, 2, g2d);

        } else if (idx < 18) {
            x1 = POINT_WIDTH / 2 + (idx - 12) * POINT_WIDTH;
            y1 = BOARD_HEIGTH - POINT_HEIGTH;
            x2 = x1;
            y2 = BOARD_HEIGTH;
            drawPointTriangle(x1, y1, x2, y2, true, idx % 2 == 0, 2, g2d);

        } else if (idx < 24) {
            int offset = BOARD_WIDTH / 2 + BAR_WIDTH / 2 + POINT_WIDTH / 2;
            x1 = offset + (idx - 18) * POINT_WIDTH;
            y1 = BOARD_HEIGTH - POINT_HEIGTH;
            x2 = x1;
            y2 = BOARD_HEIGTH;
            drawPointTriangle(x1, y1, x2, y2, true, idx % 2 == 0, 2, g2d);

        }
        g2d.drawLine(x1, y1, x2, y2);
        g2d.drawString(""+point.getPieceCount(),x1+10,y1+10);
        g2d.drawString(""+point.getPieceCount(),x2+10,y2);
    }

    private void drawPointTriangle(int x1, int y1, int x2, int y2, boolean up, boolean fill, int stroke_width, Graphics2D g2d) {
        int x_down[] = {x1 - POINT_WIDTH / 2, x1 + POINT_WIDTH / 2, x2};
        int y_down[] = {y1, y1, y2};
        int x_up[] = {x2 - POINT_WIDTH / 2, x2 + POINT_WIDTH / 2, x1};
        int y_up[] = {y2, y2, y1};
        int x[];
        int y[];
        if (up) {
            x = x_up;
            y = y_up;
        } else {
            x = x_down;
            y = y_down;
        }

        if (fill) {
            g2d.setColor(new Color(196, 164, 132));
        }

        drawPolygon(x, y, stroke_width, fill, g2d);

        g2d.setColor(Color.BLACK);
        drawPolygon(x, y, stroke_width, false, g2d);
    }



}
