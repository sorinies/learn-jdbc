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

  /**
   * 게시글 목록 조회 DAO
   * @param conn
   * @return boardList
   * @throws Exception
   */
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

  /**
   * 게시글 상세 조회 DAO
   * @param conn
   * @param boardNo
   * @return board
   * @throws Exception
   */
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

  /**
   * 게시글 작성 DAO
   * @param conn
   * @param board
   * @return result
   * @throws Exception
   */
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

  /**
   * 게시글 삭제 DAO
   * @param conn
   * @param boardNo
   * @return result
   * @throws Exception
   */
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

  /**
   * 개선된 게시글 상세 조회 DAO
   * @param conn
   * @param boardNo
   * @return board
   * @throws Exception
   */
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

  /**
   * 조회수 증가 처리 DAO
   * @param conn
   * @param boardNo
   * @return result
   * @throws Exception
   */
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

  /**
   * 게시글 삽입 위치 확인 DAO
   * @param conn
   * @return result
   * @throws Exception
   */
  public int nextBoardNo(Connection conn) throws Exception {
    int result = 0;
    try {
      String sql = prop.getProperty("nextBoardNo");
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        result = rs.getInt("board_no");
      }
    } finally {
      close(rs);
      close(stmt);
    }
    return result;
  }

  /**
   * 개선된 게시글 작성 DAO
   * @param conn
   * @param boardNo
   * @param boardTitle
   * @param boardContent
   * @return result
   * @throws Exception
   */
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

  /**
   * 게시글 존재(삭제) 여부 확인 DAO
   * @param conn
   * @param boardNo
   * @return result
   * @throws Exception
   */
  public int checkBoardNo(Connection conn, int boardNo) throws Exception {
    int result = 0;
    try {
      String sql = prop.getProperty("checkBoardNo");
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNo);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = rs.getInt(1);
      }
    } finally {
      close(rs);
      close(pstmt);
    }
    return result;
  }

  /**
   * 작성자 일치 확인 DAO
   * @param conn
   * @param boardNo
   * @return result
   * @throws Exception
   */
  public int checkAuthor(Connection conn, int boardNo) throws Exception {
    int result = 0;
    try {
      String sql = prop.getProperty("checkAuthor");
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, boardNo);
      pstmt.setInt(2, JDBCView.loginMember.getMemNo());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        result = rs.getInt(1);
      }
    } finally {
      close(rs);
      close(pstmt);
    }
    return result;
  }

  /**
   * 게시글 수정 DAO
   * @param conn
   * @param boardTitle
   * @param boardContent
   * @param boardNo
   * @return result
   * @throws Exception
   */
  public int modifyBoard(Connection conn, String boardTitle, String boardContent, int boardNo) throws Exception {
    int result = 0;
    try {
      String sql = prop.getProperty("modifyBoard");
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, boardTitle);
      pstmt.setString(2, boardContent);
      pstmt.setInt(3, boardNo);
      result = pstmt.executeUpdate();
    } finally {
      close(pstmt);
    }
    return result;
  }
}
