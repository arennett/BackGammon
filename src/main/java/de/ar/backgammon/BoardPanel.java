package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static de.ar.backgammon.ConstIf.*;

public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {
    private static final Logger logger = LoggerFactory.getLogger(BoardPanel.class);

    int POINT_IDX_BAR_WHITE = 24;
    int POINT_IDX_BAR_RED   = 25;

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
        setLayout(new BorderLayout());
        add(innerBoard, BorderLayout.CENTER);
        setPreferredSize(new Dimension(BOARD_WIDTH + BOARD_OFFSET, BOARD_HEIGTH + BOARD_OFFSET));
    }

    // important! the mouse events come from the inner board panel



    private int getPointIndex(MouseEvent e) {
        int idx = -1;
        int n=-1;
        if (e.getX() > BOARD_WIDTH / 2 + BAR_WIDTH / 2) {
            n = (e.getX() - BAR_WIDTH) / POINT_WIDTH;
        } else if (e.getX() < BOARD_WIDTH / 2 - BAR_WIDTH / 2) {
            n = e.getX() / POINT_WIDTH;
        }
        if (n>-1) {
            if (e.getY() >= 0 && e.getY() <= BOARD_HEIGTH / 2) {
                idx = 11 - n;
            } else {
                idx = 12 + n;
            }
            return idx;
        }
        // we are on the bar
        int x1 = BOARD_WIDTH / 2 - BAR_WIDTH / 2 + (BAR_WIDTH-PIECE_WIDTH) / 2;
        int y1 = BOARD_HEIGTH / 2 -PIECE_WIDTH-5;
        int y2 = BOARD_HEIGTH / 2 +5;
        if (e.getX() > x1
        && e.getX() < x1 + PIECE_WIDTH) {
            logger.debug("BAR x {}",idx);






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
        boolean isMoved=gameControl.moveRequest(pointIdxPressed,pointIdxReleased);
        if (isMoved) {
           game.message("piece moved from "+pointIdxPressed+ " to "+pointIdxReleased);
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
     *  1 for lowest piece 5 for highest piece
     * @param e MouseEvent
     * @param pointSelectedIdx
     * @return
     */
    private int getPieceIdx(MouseEvent e, int pointSelectedIdx) {
        int piece_stack_height =MAX_PIECES_ON_POINT* PIECE_WIDTH;
        int piece_idx=-1;
        if (pointSelectedIdx < 12) {
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
}
