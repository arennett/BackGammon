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


    private final BoardRendererIf boardRenderer;
    JPanel innerBoard;

    int pointIdxPressed = -1;
    private GameControl gameControl;


    public BoardPanel(BoardRendererIf boardRenderer) {
        this.boardRenderer = boardRenderer;
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private int getPointIndex(MouseEvent e) {
        int idx = -1;
        int n;
        if (e.getX() > BOARD_WIDTH / 2 + BAR_WIDTH / 2) {
            n = (e.getX() - BAR_WIDTH) / POINT_WIDTH;
        } else if (e.getX() < BOARD_WIDTH / 2 - BAR_WIDTH / 2) {
            n = e.getX() / POINT_WIDTH;
        } else {
            return idx;
        }
        if (e.getY() >= 0 && e.getY() <= BOARD_HEIGTH / 2) {
            idx = 11 - n;
        } else {
            idx = 12 + n;
        }
        return idx;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pointIdxPressed = getPointIndex(e);
        if (pointIdxPressed >-1){
            gameControl.setPointSelectedIdx(pointIdxPressed);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        logger.debug("pressed on point idx: {}", pointIdxPressed);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gameControl.setPointSelectedIdx(-1);
        int pointIdxReleased = getPointIndex(e);
        this.setCursor(Cursor.getDefaultCursor());
        if (pointIdxReleased >-1 && pointIdxPressed >-1){
                moveRequest(pointIdxPressed,pointIdxReleased);
        }
        logger.debug("released on point idx: {}", pointIdxReleased);
    }

    private void moveRequest(int pointIdxPressed, int pointIdxReleased) {
        logger.debug("moveRequest from: {} to: {}", pointIdxPressed,pointIdxReleased);
        gameControl.moveRequest(pointIdxPressed,pointIdxReleased);
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

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
