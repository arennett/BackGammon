package de.ar.backgammon;

import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.moves.Move;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

import static de.ar.backgammon.ConstIf.*;

public class BoardPanel extends JPanel
        implements  MouseListener,
                    MouseMotionListener,
                    ComponentListener {

    public static  int BOARD_HEIGTH = 600;

    public static  int BOARD_WIDTH  = BOARD_HEIGTH/3*4;
    public static  int POINT_HEIGTH = BOARD_HEIGTH * 4 / 10;
    public static  int BOARD_OFFSET = BOARD_HEIGTH/30;
    public static  int BAR_WIDTH = BOARD_WIDTH/20;
    public static  int POINT_WIDTH = (BOARD_WIDTH - BAR_WIDTH) / 12;
    public static  int PIECE_WIDTH=BOARD_HEIGTH/12;
    private static final Logger logger = LoggerFactory.getLogger(BoardPanel.class);

    private final BoardRendererIf boardRenderer;
    private final Game game;
    JPanel innerBoard;

    int pointIdxPressed = -1;
    int pointSelectedIdx = -1;
    private GameControl gameControl;
    private int pieceSelectedIdx =-1;


    public BoardPanel(BoardRendererIf boardRenderer, Game game) {
        this.boardRenderer = boardRenderer;
        this.game = game;
        initUi();
    }

    private void initUi() {
        TitledBorder tb = new TitledBorder("BoardPanel");
        setBorder(tb);
        innerBoard = new JPanel() {
            public void paint(Graphics g) {
                super.paint(g);
                boardRenderer.render(g, this);

            }
        };
        innerBoard.addMouseListener(this);
        innerBoard.addMouseMotionListener(this);
        innerBoard.addComponentListener(this);
        setLayout(new BorderLayout());
        add(innerBoard, BorderLayout.CENTER);
        setPreferredSize(new Dimension(BOARD_WIDTH + BOARD_OFFSET, BOARD_HEIGTH + BOARD_OFFSET));
        logger.debug("initialized");
    }

    // important! the mouse events come from the inner board panel



    private int getPointIndex(MouseEvent e) {
        int idx = -1;

        if ( e.getX() > BOARD_WIDTH
            || e.getX() < 0
            ||e.getY() > BOARD_HEIGTH
            || e.getY() < 0){
            return BoardModel.POINT_IDX_OFF;
//            if (e.getY() < BOARD_HEIGTH /2) {
//                return BoardModel.POINT_IDX_OFF_RED;
//            }else{
//                return BoardModel.POINT_IDX_OFF_WHITE;
//            }
        }



        int n=-1;

        if (e.getX() > BOARD_WIDTH / 2 + BAR_WIDTH / 2) {
            n = (e.getX() - BAR_WIDTH) / POINT_WIDTH;
        } else if (e.getX() < BOARD_WIDTH / 2 - BAR_WIDTH / 2) {
            n = e.getX() / POINT_WIDTH;
        }
        if (n>-1) {
            if (e.getY() >= 0 && e.getY() <= BOARD_HEIGTH / 2) {
                idx = 12 - n;
            } else {
                idx = 13 + n;
            }
            return idx;
        }
        // we are on the bar
        int x1 = BOARD_WIDTH / 2 - BAR_WIDTH / 2 + (BAR_WIDTH-PIECE_WIDTH) / 2;
        int y1 = BOARD_HEIGTH / 2 -PIECE_WIDTH-5;
        int y2 = BOARD_HEIGTH / 2 +5;
        if (e.getX() > x1 && e.getX() < x1 + PIECE_WIDTH) {
            if (e.getY() > y1 && e.getY() < y1 + PIECE_WIDTH) {
                idx = BoardModel.POINT_IDX_BAR_WHITE;
            }
            if (e.getY() > y2 && e.getY() < y2 + PIECE_WIDTH) {
                idx = BoardModel.POINT_IDX_BAR_RED;
            }
        }
        return idx;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pointIdxPressed = getPointIndex(e);
        if (pointIdxPressed >-1){
            gameControl.setStartPointSelectedIdx(pointIdxPressed);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        //logger.debug("pressed on point idx: {}", pointIdxPressed);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int pointIdxReleased = getPointIndex(e);
        this.setCursor(Cursor.getDefaultCursor());
        if (pointIdxReleased >-1 && pointIdxPressed >-1){
                moveRequest(pointIdxPressed,pointIdxReleased);
        }
        //logger.debug("released on point idx: {}", pointIdxReleased);
        gameControl.setStartPointSelectedIdx(-1);
    }

    private void moveRequest(int pointIdxPressed, int pointIdxReleased) {
       //logger.debug("moveRequest from: {} to: {} ", pointIdxPressed,pointIdxReleased);
        boolean isMoved=gameControl.moveRequest(new Move(pointIdxPressed,pointIdxReleased));
        if (isMoved) {
          // game.message("piece moved from "+pointIdxPressed+ " to "+pointIdxReleased);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setGameControl(GameControl gameControl) {

        this.gameControl = gameControl;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int newPointSelected = getPointIndex(e);
        if (newPointSelected != pointSelectedIdx){
            pointSelectedIdx =newPointSelected;
            //logger.debug("select point idx: {}", pointSelectedIdx);
            gameControl.setPointSelectedIdx(pointSelectedIdx);

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int newPointSelectedIdx = getPointIndex(e);
        if (newPointSelectedIdx != pointSelectedIdx){
            pointSelectedIdx =newPointSelectedIdx;
            //logger.debug("selected point idx: {}", pointSelectedIdx);
            gameControl.setPointSelectedIdx(pointSelectedIdx);
        }
        int newPieceSelectedIdx = getPieceIdx(e, pointSelectedIdx);
        if (newPieceSelectedIdx != pieceSelectedIdx){
            pieceSelectedIdx =newPieceSelectedIdx;
            //logger.debug("selected piece idx: {}", pieceSelectedIdx);
            gameControl.setPieceSelectedIdx(pieceSelectedIdx);
        }
    }

    /**
     *
     * @param e MouseEvent
     * @param  pointSelectedIdx the selected point(triangle)
     * @return the piece idx 1 for lowest piece 5 for highest piece
     */
    private int getPieceIdx(MouseEvent e, int pointSelectedIdx) {
        int piece_stack_height =MAX_PIECES_ON_POINT* PIECE_WIDTH;
        int piece_idx=-1;
        if (pointSelectedIdx < 13) {
            if (e.getY() <= piece_stack_height) {
                piece_idx=MAX_PIECES_ON_POINT-(piece_stack_height-e.getY())/PIECE_WIDTH;
            }
        }else{
            if (e.getY() >= BOARD_HEIGTH-piece_stack_height) {
                piece_idx=MAX_PIECES_ON_POINT-(e.getY() -(BOARD_HEIGTH-piece_stack_height))/PIECE_WIDTH;
            }
        }
        if (piece_idx > -1){
            piece_idx--;
        }
        return piece_idx;
    }

    @Override
    public void componentResized(ComponentEvent e) {
       Component comp= e.getComponent();
       BOARD_HEIGTH=(int)(getSize().getHeight()-20);
       recalcDimensions();
       repaint();

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    private static void recalcDimensions(){
        BOARD_WIDTH  = BOARD_HEIGTH/3*4;
        POINT_HEIGTH = BOARD_HEIGTH * 4 / 10;
        BOARD_OFFSET = BOARD_HEIGTH/30;
        BAR_WIDTH = BOARD_WIDTH/20;
        POINT_WIDTH = (BOARD_WIDTH - BAR_WIDTH) / 12;
        PIECE_WIDTH=BOARD_HEIGTH/12;
    }

}
