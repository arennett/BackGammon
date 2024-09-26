package de.ar.backgammon.dbase.board;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.game.DbDaoGame;
import de.ar.backgammon.dbase.game.DbGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class DbDaoBoard {
    private static final Logger logger = LoggerFactory.getLogger(DbDaoBoard.class);
    private static final String sql_insert
            = "insert into board (game_id,seqnr,turn,bar_r,bar_w,off_r,off_w,dice1,dice2)"
            + " VALUES (?,?,?,?,?,?,?,?,?);";
    private static final String sql_read_last
            = "SELECT id, game_id, sqltime, seqnr,turn,bar_r,bar_w,off_r,off_w,dice1,dice2 FROM board"
            + " order by id desc LIMIT 1;";
    private static final String sql_read_boards
            = "SELECT id, game_id, sqltime, seqnr,turn,bar_r,bar_w,off_r,off_w,dice1,dice2 FROM board"
            + " WHERE game_id = ?"
            + " order by id;";
    private static final String sql_count ="SELECT count(*) as count FROM board;";

    public DbDaoBoard(){


    }

    public DbBoard insert (DbBoard board) throws BException {
        DbBoard retBoard;
        DbDaoGame daoGame= new DbDaoGame();
        DbGame game=daoGame.readLast();
        Connection conn = DbConnect.getInstance().getConnection();
        try (

                 PreparedStatement pstmt = conn.prepareStatement(sql_insert)
        ) {

            pstmt.setInt(1,game.getId());
            pstmt.setInt(2,game.getNextBoardSeqNr());
            pstmt.setInt(3,board.getTurn());
            pstmt.setInt(4,board.getBarRed());
            pstmt.setInt(5,board.getBarWhite());
            pstmt.setInt(6,board.getOffRed());
            pstmt.setInt(7,board.getOffWhite());
            pstmt.setInt(8,board.getDice1());
            pstmt.setInt(9,board.getDice2());

            pstmt.executeUpdate();
            retBoard = readLast();
        } catch (Exception e){
               logger.error("insert failed",e);
            try {
                conn.rollback();
                logger.debug("insert rollback");
            } catch (SQLException ex) {
                throw new BException("insert rollback failed",e);
            }
            throw new BException("insert failed",e);
        }
        daoGame.incNextBoardSeqNr();
        return retBoard;

    }

    public DbBoard readLast() throws BException {
        DbBoard board = null;
        Connection conn = DbConnect.getInstance().getConnection();
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_read_last);
        ) {
            while (rs.next()){
                board = new DbBoard();
                board.setId(rs.getInt("id"));
                board.setGameId(rs.getInt("game_id"));
                board.setSqltime(rs.getTimestamp("sqltime"));
                board.setSeqNr(rs.getInt("seqnr"));
                board.setTurn(rs.getInt("turn"));
                board.setBarRed(rs.getInt("bar_r"));
                board.setBarWhite(rs.getInt("bar_w"));
                board.setOffRed(rs.getInt("off_r"));
                board.setOffWhite(rs.getInt("off_w"));
                board.setDice1(rs.getInt("dice1"));
                board.setDice2(rs.getInt("dice2"));

            }

        } catch (Exception e){
            logger.error("read last failed",e);
            throw new BException("read last failed",e);
        }
        return board;
    }

    public ArrayList<DbBoard> readBoards(int game_id) throws BException{
        ArrayList<DbBoard> boardsList = new ArrayList<>();
        DbBoard board = null;
        Connection conn = DbConnect.getInstance().getConnection();
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql_read_boards)
         ) {
            pstmt.setInt(1,game_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                board = new DbBoard();
                boardsList.add(board);
                board.setId(rs.getInt("id"));
                board.setGameId(rs.getInt("game_id"));
                board.setSqltime(rs.getTimestamp("sqltime"));
                board.setSeqNr(rs.getInt("seqnr"));
                board.setTurn(rs.getInt("turn"));
                board.setBarRed(rs.getInt("bar_r"));
                board.setBarWhite(rs.getInt("bar_w"));
                board.setOffRed(rs.getInt("off_r"));
                board.setOffWhite(rs.getInt("off_w"));
                board.setDice1(rs.getInt("dice1"));
                board.setDice2(rs.getInt("dice2"));
            }
            rs.close();
        } catch (Exception e){
            logger.error("read last failed",e);
            throw new BException("read last failed",e);
        }
        return boardsList;
    }

    public int count() throws BException {
        Connection conn = DbConnect.getInstance().getConnection();
        int count = -1;
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_count);
        ) {
            while (rs.next()){
                count =rs.getInt("count");
             }

        } catch (Exception e){
            logger.error("count failed");
            throw new BException("count failed");
        }
        return count;
    }

}
