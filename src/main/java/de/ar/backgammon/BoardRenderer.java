package de.ar.backgammon;

import java.awt.*;

import static de.ar.backgammon.ConstIf.*;

public class BoardRenderer implements BoardRendererIf {
    static int BAR_WIDTH = 40;
    static int POINT_WIDTH = (PLAY_AREA_WIDTH - BAR_WIDTH) / 12;
    static int POINT_HEIGTH = PLAY_AREA_HEIGHT * 4 / 10;



    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        drawRect(0, 0, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT, 2,g2d);
        // drawLine(PLAY_AREA_WIDTH/2,0,PLAY_AREA_WIDTH/2,PLAY_AREA_HEIGHT,g2d);
        draw_bar(g2d);
        for (int i = 0; i < 24; i++) {
            drawPoint(i, g2d);
        }
    }


    public void drawRect(int x, int y, int w, int h, int stroke_width, Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(stroke_width));
        g2d.drawRect(x + BOARD_OFFSET, y + BOARD_OFFSET, w, h);
        g2d.setStroke(new BasicStroke(1));
    }

    public void drawLine(int x1, int y1, int x2, int y2, Graphics2D g2d) {
        g2d.drawLine(x1 + BOARD_OFFSET, y1 + BOARD_OFFSET, x2 + BOARD_OFFSET, y2 + BOARD_OFFSET);
    }

    private void drawPolygon(int[] x,int[] y,int stroke_width,boolean fill,Graphics2D g2d){
        g2d.setStroke(new BasicStroke(stroke_width));
        int[] xs=addArr(x,BOARD_OFFSET);
        int[] ys=addArr(y,BOARD_OFFSET);
        if (fill) {
            g2d.fillPolygon(xs,ys,x.length);
        }else{
            g2d.drawPolygon(xs,ys,x.length);
        }


        g2d.setStroke(new BasicStroke(1));
    }


    void draw_bar(Graphics2D g2d) {
        drawRect(PLAY_AREA_WIDTH / 2 - BAR_WIDTH / 2, 0, BAR_WIDTH, PLAY_AREA_HEIGHT,2, g2d);
    }

    void drawPoint(int idx, Graphics2D g2d) {
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        if (idx < 6) {
            x1 = PLAY_AREA_WIDTH - POINT_WIDTH / 2 - idx * POINT_WIDTH;
            y1 = 0;
            x2 = x1;
            y2 = POINT_HEIGTH;
            drawPointTriangle(x1,y1,x2,y2,false,idx%2==0,2,g2d);

        } else if (idx < 12) {
            int offset =PLAY_AREA_WIDTH/2+BAR_WIDTH/2 + POINT_WIDTH / 2;
            x1 = PLAY_AREA_WIDTH - offset  - (idx-6) * POINT_WIDTH;
            y1 = 0;
            x2 = x1;
            y2 = POINT_HEIGTH;
            drawPointTriangle(x1,y1,x2,y2,false,idx%2==0,2,g2d);

        } else if (idx < 18) {
            x1 = POINT_WIDTH/2  + (idx-12) * POINT_WIDTH;
            y1 = PLAY_AREA_HEIGHT - POINT_HEIGTH;
            x2 = x1;
            y2 = PLAY_AREA_HEIGHT;
            drawPointTriangle(x1,y1,x2,y2,true,idx%2==0,2,g2d);

        } else if (idx < 24) {
            int offset =PLAY_AREA_WIDTH/2+BAR_WIDTH/2 + POINT_WIDTH / 2;
            x1 = offset  + (idx-18) * POINT_WIDTH;
            y1 = PLAY_AREA_HEIGHT - POINT_HEIGTH;
            x2 = x1;
            y2 = PLAY_AREA_HEIGHT;
            drawPointTriangle(x1,y1,x2,y2,true,idx%2==0,2,g2d);

        }
        drawLine(x1, y1, x2, y2, g2d);
    }

    private void drawPointTriangle(int x1, int y1, int x2, int y2, boolean up,boolean fill, int stroke_width, Graphics2D g2d) {
        int x_down[] ={x1-POINT_WIDTH/2,x1+POINT_WIDTH/2,x2};
        int y_down[] ={y1,y1,y2};
        int x_up[] ={x2-POINT_WIDTH/2,x2+POINT_WIDTH/2,x1};
        int y_up[] ={y2,y2,y1};
        int x[];
        int y[];
        if (up){
            x=x_up;
            y=y_up;
        }else{
            x=x_down;
            y=y_down;
        }

        if (fill) {
            g2d.setColor(new Color(196, 164, 132));
        }

        drawPolygon(x,y,stroke_width,fill,g2d);

        g2d.setColor(Color.BLACK);
        drawPolygon(x,y,stroke_width,false,g2d);
    }


    int[] addArr(int[]x,int val) {
        int[] xs = new int[x.length];
        for (int i=0;i<x.length;i++) {
            xs[i]=x[i]+val;
        }
        return xs;
    }


}
