package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.entity.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbDaoGame {
    private static final Logger logger = LoggerFactory.getLogger(DbDaoGame.class);
    private static final String sql_insert="INSERT INTO game DEFAULT VALUES";

    private static final String
            sql_update_inc_next_board_seqnr="UPDATE game Set next_board_seqnr=next_board_seqnr+1 where id =?";

    private static final String sql_read_last ="SELECT id,sqltime,next_board_seqnr FROM game order by id desc LIMIT 1;";
    private static final String sql_count ="SELECT count(*) as count FROM game;";

    public Game insert () throws BException {
        Game game;
        try (
                Connection conn = DbConnect.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql_insert)
        ) {
            pstmt.executeUpdate();
            game = readLast();
        } catch (Exception e){
               logger.error("insert failed",e);
               throw new BException("insert failed",e);
        }
        return game;

    }
    public void incNextBoardSeqNr() throws BException {
        Game game;
        try (

                Connection conn = DbConnect.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql_update_inc_next_board_seqnr)
        ) {
            game=readLast();
            pstmt.setInt(0,game.getId());
            pstmt.executeUpdate();
        } catch (Exception e){
            logger.error("incNextBoardSeqNr failed",e);
            throw new BException("incNextBoardSeqNr failed",e);
        }
    }


    public Game readLast() throws BException {
        Game game = null;
        try (Connection conn = DbConnect.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_read_last);
        ) {
            while (rs.next()){
                game = new Game();
                game.setId(rs.getInt("id"));
                game.setNextBoardSeqNr(rs.getInt("next_board_seqnr"));
                game.setSqltime(rs.getTimestamp("sqltime"));
            }

        } catch (Exception e){
            logger.error("read last failed");
            throw new BException("read last failed");
        }
        return game;
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
