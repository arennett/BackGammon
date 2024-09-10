package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.entity.Board;
import de.ar.backgammon.dbase.entity.DbGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DbDaoBoard {
    private static final Logger logger = LoggerFactory.getLogger(DbDaoBoard.class);
    private static final String sql_insert
            = "insert into board (game_id,seqnr,turn,bar_r,bar_w,off_r,off_w,dice1,dice2)"
            + " VALUES (?,?,?,?,?,?,?,?,?);";
    private static final String sql_read_last
            = "SELECT id, game_id, sqltime, seqnr,turn,bar_r,bar_w,off_r,off_w,dice1,dice2 FROM board"
            + " order by id desc LIMIT 1;";
    private static final String sql_count ="SELECT count(*) as count FROM board;";
    private Connection conn;

    public DbDaoBoard(Connection conn){

        this.conn = conn;
    }

    public Board insert (Board board) throws BException {
        Board retBoard;
        DbDaoGame daoGame= new DbDaoGame(conn);
        DbGame game=daoGame.readLast();
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

    public Board readLast() throws BException {
        Board board = null;
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_read_last);
        ) {
            while (rs.next()){
                board = new Board();
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
    public int count() throws BException {
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


    public void setConnection(Connection conn) {
        this.conn=conn;
    }
}
