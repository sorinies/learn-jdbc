package edu.kh.jdbc.board.model.service;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.vo.Board;

import java.sql.Connection;
import java.util.List;

import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardService {
  private BoardDAO dao = new BoardDAO();

  public List<Board> selectAllBoard() throws Exception {
    Connection conn = getConnection();
    List<Board> boardList = dao.selectAllBoard(conn);
    close(conn);

    return boardList;
  }

  public Board selectBoard(int boardNo) throws Exception {
    Connection conn = getConnection();
    Board board = dao.selectBoard(conn, boardNo);
    close(conn);

    return board;
  }

  public int insertBoard(Board board) throws Exception {
    Connection conn = getConnection();
    int result = dao.insertBoard(conn, board);
    if(result > 0) {
      commit(conn);
    } else {
      rollback(conn);
    }
    close(conn);
    return result;
  }

  public int deleteBoard(int boardNo) throws Exception {
    Connection conn = getConnection();
    int result = dao.deleteBoard(conn, boardNo);
    if(result > 0) {
      commit(conn);
    } else {
      rollback(conn);
    }
    close(conn);
    return result;
  }

  public Board eSelectBoard(int boardNo) throws Exception {
    Connection conn = getConnection();
    Board board = dao.eSelectBoard(conn, boardNo);
    // 조회수 증가 코드
    if(board != null) {
      int result = dao.increaseReadCount(conn, boardNo);
      if(result > 0) {
        commit(conn);
        // 이미 조회 수 증가 전에 조회해둔 board 의 readCount 를 UPDATE 된 DB read_count 컬럼 값과 똑같이 1 증가 시킴.
        board.setReadCount(board.getReadCount() + 1);
      } else {
        rollback(conn);
      }
    }
    close(conn);
    return board;
  }
}
