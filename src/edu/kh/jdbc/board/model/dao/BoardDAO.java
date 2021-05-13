package edu.kh.jdbc.board.model.dao;

import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.view.JDBCView;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardDAO {
  private Statement stmt = null;
  private PreparedStatement pstmt = null;
  private ResultSet rs = null;
  private Properties prop = null;

  public BoardDAO() {
    try {
      prop = new Properties();
      prop.loadFromXML(new FileInputStream("board-query.xml"));
    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  public List<Board> selectAllBoard(Connection conn) throws Exception {
    List<Board> boardList;
    try {
      String sql = prop.getProperty("selectAllBoard");
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      boardList = new ArrayList<>();
      while(rs.next()) {
        int boardNo = rs.getInt(1);
        String boardTitle = rs.getString(2);
        Date createDate = rs.getDate(3);
        String memNm = rs.getString(4);
        int readCount = rs.getInt(5);

        boardList.add(new Board(boardNo, boardTitle, createDate, memNm, readCount));
      }
    } finally {
      close(rs);
      close(stmt);
    }
    return boardList;
  }

  public Board selectBoard(Connection conn, int boardNo) throws Exception {
    Board board = null;
    try {
      String sql = prop.getProperty("selectBoard");
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNo);
      rs = pstmt.executeQuery();
      if(rs.next()) {
        String boardTitle = rs.getString("board_title");
        String memNm = rs.getString("mem_nm");
        int memNo = rs.getInt("author_no");
        Date createDate = rs.getDate("create_date");
        int readCount = rs.getInt("read_count");
        String boardContent = rs.getString("board_content");
        board = new Board(boardNo, boardTitle, boardContent, createDate, memNm, memNo, readCount);
      }
    } finally {
      close(rs);
      close(stmt);
    }
    return board;
  }

  public int insertBoard(Connection conn, Board board) throws Exception {
    int result;
    try {
      String sql = prop.getProperty("insertBoard");
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, board.getBoardTitle());
      pstmt.setString(2, board.getBoardContent());
      pstmt.setInt(3, board.getMemNo());
      result = pstmt.executeUpdate();
    } finally {
      close(pstmt);
    }
    return result;
  }

  public int deleteBoard(Connection conn, int boardNo) throws Exception {
    int result;
    try {
      String sql = prop.getProperty("deleteBoard");
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNo);
      pstmt.setInt(2, JDBCView.loginMember.getMemNo());
      result = pstmt.executeUpdate();
    } finally {
      close(pstmt);
    }
    return result;
  }

  public Board eSelectBoard(Connection conn, int boardNo) throws Exception {
    Board board = null;
    try {
      String sql = prop.getProperty("selectBoard");
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNo);
      rs = pstmt.executeQuery();
      if(rs.next()) {
        String boardTitle = rs.getString("board_title");
        String memNm = rs.getString("mem_nm");
        int memNo = rs.getInt("author_no");
        Date createDate = rs.getDate("create_date");
        int readCount = rs.getInt("read_count");
        String boardContent = rs.getString("board_content");
        board = new Board(boardNo, boardTitle, boardContent, createDate, memNm, memNo, readCount);
      }
    } finally {
      close(rs);
      close(pstmt);
    }
    return board;
  }

  public int increaseReadCount(Connection conn, int boardNo) throws Exception {
    int result;
    try {
      String sql = prop.getProperty("increaseReadCount");
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNo);
      result = pstmt.executeUpdate();
    } finally {
      close(pstmt);
    }
    return result;
  }

  public int nextBoardNo(Connection conn) throws Exception {
    int boardNo;
    try {
      String sql = prop.getProperty("nextBoardNo");
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        boardNo = rs.getInt("board_no");
      }
    } finally {
      close(rs);
      close(stmt);
    }
    return 0;
  }

  public int eInsertBoard(Connection conn, int boardNo, String boardTitle, String boardContent) throws Exception {
    int result;
    try {
      String sql = prop.getProperty("eInsertBoard");
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNo);
      pstmt.setString(2, boardTitle);
      pstmt.setString(3, boardContent);
      pstmt.setInt(4, JDBCView.loginMember.getMemNo());

      result = pstmt.executeUpdate();
    } finally {
      close(pstmt);
    }
    return result;
  }

  public int checkBoardNo(Connection conn, int boardNo) throws Exception {
    int result;
    try {

    } finally {

    }
    return result;
  }
}
