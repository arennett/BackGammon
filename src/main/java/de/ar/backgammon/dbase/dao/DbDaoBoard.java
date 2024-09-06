package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.entity.Board;
import de.ar.backgammon.dbase.entity.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbDaoBoard {
    private static final Logger logger = LoggerFactory.getLogger(DbDaoBoard.class);
    private static final String sql_insert="insert into board (game_id,seqnr,turn) VALUES (?,?,?);";
    private static final String sql_read_last ="SELECT id,sqltime,seqnr,turn FROM board order by id desc LIMIT 1;";
    private static final String sql_count ="SELECT count(*) as count FROM board;";

    public Board insert (Board board) throws BException {
        Board retBoard;
        DbDaoGame daoGame= new DbDaoGame();
        try (
                Connection conn = DbConnect.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql_insert)
        ) {
            Game game=daoGame.readLast();
            pstmt.setInt(1,game.getId());

            pstmt.setInt(2,game.getNextBoardSeqNr();
            daoGame.incNextBoardSeqNr();
            pstmt.setInt(3,board.getTurn());
            pstmt.executeUpdate();
            retBoard = readLast();
        } catch (Exception e){
               logger.error("insert failed",e);
               throw new BException("insert failed",e);
        }
        return retBoard;

    }

    public Board readLast() throws BException {
        Board board = null;
        try (Connection conn = DbConnect.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_read_last);
        ) {
            while (rs.next()){
                board = new Board();
                board.setId(rs.getInt("id"));
                board.setGameId(rs.getInt("game_id"));
                board.setSqltime(rs.getTimestamp("sqltime"));
                board.setSeqNr(rs.getInt("seq_nr"));
                board.setTurn(rs.getInt("turn"));
            }

        } catch (Exception e){
            logger.error("read last failed");
            throw new BException("read last failed");
        }
        return board;
    }
    public int count() throws BException {
        int count = -1;
        try (Connection conn = DbConnect.getInstance().getConnection();
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
