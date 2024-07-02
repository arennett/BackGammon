package de.ar.backgammon;

import javax.swing.*;
import java.awt.*;

import static de.ar.backgammon.ConstIf.*;

public class BoardRenderer implements BoardRendererIf {


    private final BoardModelIf bModel;
    private BarRenderer barRenderer ;

    public BoardRenderer(BoardModelIf bModel){

        this.bModel = bModel;
        barRenderer = new BarRenderer(bModel);
    }

    public void render(Graphics g, JPanel panel) {
        Graphics2D g2d = (Graphics2D) g;

        drawRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH, 2, g2d);
        // drawLine(PLAY_AREA_WIDTH/2,0,PLAY_AREA_WIDTH/2,PLAY_AREA_HEIGHT,g2d);
        barRenderer.render(g2d);

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
            drawPointTriangle(idx,x1, y1, x2, y2, false, idx % 2 == 0, 2, g2d);

        } else if (idx < 12) {
            int offset = BOARD_WIDTH / 2 + BAR_WIDTH / 2 + POINT_WIDTH / 2;
            x1 = BOARD_WIDTH - offset - (idx - 6) * POINT_WIDTH;
            y1 = 0;
            x2 = x1;
            y2 = POINT_HEIGTH;
            drawPointTriangle(idx,x1, y1, x2, y2, false, idx % 2 == 0, 2, g2d);

        } else if (idx < 18) {
            x1 = POINT_WIDTH / 2 + (idx - 12) * POINT_WIDTH;
            y1 = BOARD_HEIGTH - POINT_HEIGTH;
            x2 = x1;
            y2 = BOARD_HEIGTH;
            drawPointTriangle(idx,x1, y1, x2, y2, true, idx % 2 == 0, 2, g2d);

        } else if (idx < 24) {
            int offset = BOARD_WIDTH / 2 + BAR_WIDTH / 2 + POINT_WIDTH / 2;
            x1 = offset + (idx - 18) * POINT_WIDTH;
            y1 = BOARD_HEIGTH - POINT_HEIGTH;
            x2 = x1;
            y2 = BOARD_HEIGTH;
            drawPointTriangle(idx,x1, y1, x2, y2, true, idx % 2 == 0, 2, g2d);

        }
        g2d.drawLine(x1, y1, x2, y2);
        if(!point.isEmpty()){
            for (int i=0;i <point.getPieceCount();i++){

                if (idx < 12){
                    drawPiece(idx,i,x1,y1+25+PIECE_WIDTH*i,point.getPieceColor(),g2d);

                }else {
                    drawPiece(idx,i,x2,y2-25-PIECE_WIDTH*i,point.getPieceColor(),g2d);
                }
            }

        }


        String str=""+point.getIndex();
        //if(point.getPieceCount()> 0) {
           // str = str+"/"+point.getPieceCount() + point.getPieceColor().getShortString();
        //}

        if (idx < 12){
            g2d.drawString(str,x1-10,y1+15);
        }else {
            g2d.drawString(str, x2-10, y2-5);
        }
    }

    private void drawPointTriangle(int idx,int x1, int y1, int x2, int y2, boolean up, boolean fill, int stroke_width, Graphics2D g2d) {
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
        int psidx=bModel.getPointSelectedIdx();
        if ( psidx > -1 && psidx==idx) {
            g2d.setColor(Color.GREEN);
            drawPolygon(x, y, stroke_width+1, false, g2d);
        }else{
            g2d.setColor(Color.BLACK);
            drawPolygon(x, y, stroke_width, false, g2d);
        }


        g2d.setColor(Color.BLACK);
    }

    private void drawPiece(int pointidx, int pieceidx,int x1, int y1, BColor color, Graphics2D g2d){

        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(x1-PIECE_WIDTH/2,y1-PIECE_WIDTH/2,PIECE_WIDTH,PIECE_WIDTH);
        g2d.setColor(color.getColor());
        BPoint bpoint = bModel.getPoint(pointidx);
        int spc=bpoint.getSelectedPiecesCount();
        /*highlight hovered pieces*/
        if (spc > 0) {
            if (bpoint.getPieceCount()- (pieceidx+1) < spc ){
                   g2d.setColor(Color.GREEN);
            }
        }

        /* highlight the selected (dragged) pieces of a move*/
        if (pointidx == bModel.getStartPointSelectedIdx()) {
            int spsc=bModel.getStartPointSelectedPiecesCount();
            if (spsc > 0) {
                if (bpoint.getPieceCount()- (pieceidx+1) < spsc ){
                    g2d.setColor(Color.GREEN);
                }
            }
        }

        int w1=PIECE_WIDTH-2;
        g2d.fillOval(x1-w1/2,y1-w1/2,w1,w1);
        g2d.setColor(Color.BLACK);
        w1=PIECE_WIDTH-10;
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval(x1-w1/2,y1-w1/2,w1,w1);
    }
}
