package de.ar.backgammon.dbase.point;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.board.DbDaoBoard;
import de.ar.backgammon.dbase.board.DbBoard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class DbDaoPoint {
    private static final Logger logger = LoggerFactory.getLogger(DbDaoPoint.class);
    private static final String sql_insert="insert into point (board_id,idx,color,cnt) VALUES (?,?,?,?);";
    private static final String sql_read_last ="SELECT id, board_id, sqltime, idx,color,cnt FROM point order by id desc LIMIT 1;";
    private static final String sql_count ="SELECT count(*) as count FROM point;";
    private static final String sql_read_points ="SELECT id, board_id, sqltime, idx,color,cnt FROM point "
                                                  +" WHERE id = ?  order by idx;";



    public DbDaoPoint(){

    }

    public DbPoint insert (DbPoint point) throws BException {
        DbPoint retPoint;
        Connection conn = DbConnect.getInstance().getConnection();
        DbDaoBoard daoBoard = new DbDaoBoard();
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
        Connection conn = DbConnect.getInstance().getConnection();
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql_read_last);
        ) {
            while (rs.next()){
                point = new DbPoint();
                point.setId(rs.getInt("id"));
                point.setBoardId(rs.getInt("board_id"));
                point.setSqltime(rs.getTimestamp("sqltime"));
                point.setIdx(rs.getInt("idx"));
                point.setColor(rs.getInt("color"));
                point.setCount(rs.getInt("cnt"));
            }

        } catch (Exception e){
            logger.error("read last failed",e);
            throw new BException("read last failed",e);
        }
        return point;
    }
    public int count() throws BException {
        int count = -1;
        Connection conn = DbConnect.getInstance().getConnection();
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
    public ArrayList<DbPoint> readPoints(int board_id) throws BException{
        ArrayList<DbPoint> pointsList = new ArrayList<>();
        DbPoint point = null;
        Connection conn = DbConnect.getInstance().getConnection();
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql_read_points)
        ) {
            pstmt.setInt(1,board_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                point = new DbPoint();
                pointsList.add(point);
                point.setId(rs.getInt("id"));
                point.setBoardId(rs.getInt("board_id"));
                point.setSqltime(rs.getTimestamp("sqltime"));
                point.setIdx(rs.getInt("idx"));
                point.setColor(rs.getInt("color"));
                point.setCount(rs.getInt("cnt"));

            }
            rs.close();
        } catch (Exception e){
            logger.error("read points",e);
            throw new BException("read points",e);
        }
        return pointsList;
    }


}
