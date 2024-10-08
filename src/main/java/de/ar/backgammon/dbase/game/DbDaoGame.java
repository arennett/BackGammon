package de.ar.backgammon.dbase.game;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class DbDaoGame {
    private static final Logger logger = LoggerFactory.getLogger(DbDaoGame.class);
    private static final String sql_insert="INSERT INTO game DEFAULT VALUES;";

    private static final String
            sql_update_inc_next_board_seqnr="UPDATE game Set next_board_seqnr=next_board_seqnr+1 where id =?;";

    private static final String sql_read_all ="SELECT id,sqltime,next_board_seqnr FROM game order by id;";
    private static final String sql_read_last ="SELECT id,sqltime,next_board_seqnr FROM game order by id desc LIMIT 1;";
    private static final String sql_count ="SELECT count(*) as count FROM game;";


    public DbDaoGame(){

    }

    public DbGame insert () throws BException {
        DbGame game;
        Connection conn = DbConnect.getInstance().getConnection();
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql_insert)
        ) {
            pstmt.executeUpdate();
            game = readLast();
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
        return game;

    }
    public void incNextBoardSeqNr() throws BException {
        DbGame game=readLast();
        Connection conn = DbConnect.getInstance().getConnection();
        try (
             PreparedStatement pstmt = conn.prepareStatement(sql_update_inc_next_board_seqnr)
        ) {
            pstmt.setInt(1,game.getId());
            pstmt.executeUpdate();
        } catch (Exception e){
            logger.error("incNextBoardSeqNr failed",e);
            try {
                conn.rollback();
                logger.debug("incNextBoardSeqNr rollback");
            } catch (SQLException ex) {
                throw new BException("incNextBoardSeqNr rollback failed",e);
            }
            throw new BException("incNextBoardSeqNr failed",e);
        }
    }


    public DbGame readLast() throws BException {
        DbGame game = null;
        Connection conn = DbConnect.getInstance().getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_read_last);
        ) {
            while (rs.next()){
                game = new DbGame();
                game.setId(rs.getInt("id"));
                game.setNextBoardSeqNr(rs.getInt("next_board_seqnr"));
                game.setSqltime(rs.getTimestamp("sqltime"));
            }

        } catch (Exception e){
            logger.error("read last failed",e);
            throw new BException("read last failed",e);
        }
        return game;
    }

    public ArrayList<DbGame> readAll() throws BException {
        ArrayList<DbGame> gameList = new ArrayList<>();
        Connection conn = DbConnect.getInstance().getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_read_all);
        ) {
            while (rs.next()){
                DbGame game = new DbGame();
                game.setId(rs.getInt("id"));
                game.setNextBoardSeqNr(rs.getInt("next_board_seqnr"));
                game.setSqltime(rs.getTimestamp("sqltime"));
                gameList.add(game);
            }

        } catch (Exception e){
            logger.error("read all failed",e);
            throw new BException("read all failed",e);
        }
        return gameList;
    }
    public int count() throws BException {
        Connection conn = DbConnect.getInstance().getConnection();
        int count = -1;
        try (Statement stmt = conn.createStatement();
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
