package edu.kh.jdbc.board.model.service;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.vo.Board;

import java.sql.Connection;
import java.util.List;

import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardService {
  private BoardDAO dao = new BoardDAO();

  /**
   * 게시글 목록 조회 Service
   * @return boardList
   * @throws Exception
   */
  public List<Board> selectAllBoard() throws Exception {
    Connection conn = getConnection();
    List<Board> boardList = dao.selectAllBoard(conn);
    close(conn);

    return boardList;
  }

  /**
   * 게시글 상세 조회 Service
   * @param boardNo
   * @return board
   * @throws Exception
   */
  public Board selectBoard(int boardNo) throws Exception {
    Connection conn = getConnection();
    Board board = dao.selectBoard(conn, boardNo);
    close(conn);

    return board;
  }

  /**
   * 게시글 작성 Service
   * @param board
   * @return result
   * @throws Exception
   */
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

  /**
   * 게시글 삭제 Service
   * @param boardNo
   * @return result
   * @throws Exception
   */
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

  /**
   * 개선된 게시글 상세 조회 Service
   * @param boardNo
   * @return board
   * @throws Exception
   */
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

  /**
   * 개선된 게시글 작성 Service
   * @param boardTitle
   * @param boardContent
   * @return board
   * @throws Exception
   */
  public Board eInsertBoard(String boardTitle, String boardContent) throws Exception {
    Connection conn = getConnection();
    // 1. 삽입될 다음 게시글 번호를 얻어옴.
    int boardNo = dao.nextBoardNo(conn);
    // 2. 얻어온 번호를 이용해서 게시글 삽입 진행
    int result = dao.eInsertBoard(conn, boardNo, boardTitle, boardContent);
    // 3. 삽입을 성공한 경우 COMMIT -> boardNo를 이용해 새로 작성한 글 상세조회
    Board board = null;
    if (result > 0) {
      commit(conn);
      board = eSelectBoard(boardNo);
    } else {
      rollback(conn);
    }
    return board;
  }

  /**
   * 작성자 일치 & 게시글 존재(삭제) 여부 확인 Service
   * @param boardNo
   * @return result
   * @throws Exception
   */
  public int checkBoardNo(int boardNo) throws Exception {
    Connection conn = getConnection();
    int result = dao.checkBoardNo(conn, boardNo);
    if (result == 0) { // 글 번호 존재하지 않음.
      return result;
    } else {
      result = dao.checkAuthor(conn, boardNo);
      if (result == 0) { // 작성자가 일치하지 않음.
        result = -1;
      }
    }
    close(conn);
    return result;
  }

  /**
   * 게시글 수정 Service
   * @param boardTitle
   * @param boardContent
   * @param boardNo
   * @return board
   * @throws Exception
   */
  public Board modifyBoard(String boardTitle, String boardContent, int boardNo) throws Exception {
    Connection conn = getConnection();
    int result = dao.modifyBoard(conn, boardTitle, boardContent, boardNo);
    Board board = null;
    if (result > 0) {
      commit(conn);
      board = dao.eSelectBoard(conn, boardNo);
    } else {
      rollback(conn);
    }
    return board;
  }
}
