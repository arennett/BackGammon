package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.entity.DbBoard;
import de.ar.backgammon.dbase.entity.DbPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DbDaoPoint {
    private static final Logger logger = LoggerFactory.getLogger(DbDaoPoint.class);
    private static final String sql_insert="insert into point (board_id,idx,color,cnt) VALUES (?,?,?,?);";
    private static final String sql_read_last ="SELECT id, board_id, sqltime, idx,color,cnt FROM point order by id desc LIMIT 1;";
    private static final String sql_count ="SELECT count(*) as count FROM point;";
    private final Connection conn;

    public DbDaoPoint(Connection conn){

        this.conn = conn;
    }

    public DbPoint insert (DbPoint point) throws BException {
        DbPoint retPoint;
        DbDaoBoard daoBoard = new DbDaoBoard(conn);
        DbBoard board = daoBoard.readLast();
        try (
                 PreparedStatement pstmt = conn.prepareStatement(sql_insert)
        ) {

            pstmt.setInt(1,board.getId());
            pstmt.setInt(2,point.getIdx());
            pstmt.setInt(3,point.getColor());
            pstmt.setInt(4,point.getCount());

            pstmt.executeUpdate();
            retPoint = readLast();
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

        return retPoint;

    }

    public DbPoint readLast() throws BException {
        DbPoint point = null;
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_read_last);
        ) {
            while (rs.next()){
                point = new DbPoint();
                point.setId(rs.getInt("id"));
                point.setBoardId(rs.getInt("board_id"));
                point.setSqltime(rs.getTimestamp("sqltime"));
                point.setCount(rs.getInt("cnt"));
                point.setIdx(rs.getInt("idx"));
                point.setColor(rs.getInt("color"));
            }

        } catch (Exception e){
            logger.error("read last failed",e);
            throw new BException("read last failed",e);
        }
        return point;
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





}
