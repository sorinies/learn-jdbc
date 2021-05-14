package edu.kh.jdbc.board.model.vo;

import java.sql.Date;

// v_board 뷰 조회 결과를 담을 객체
// + board 테이블 DML을 위한 데이터 저장 객체

public class Board {
  private int boardNo;
  private String boardTitle;
  private String boardContent;
  private Date createDate;
  private String memNm;
  private int readCount;
  private int memNo;

  public Board() {}

  public Board(String boardTitle, String boardContent, int memNo) {
    super();
    this.boardTitle = boardTitle;
    this.boardContent = boardContent;
    this.memNo = memNo;
  }

  public Board(int boardNo, String boardTitle, Date createDate, int readCount) {
    super();
    this.boardNo = boardNo;
    this.boardTitle = boardTitle;
    this.createDate = createDate;
    this.readCount = readCount;
  }

  public Board(int boardNo, String boardTitle, Date createDate, String memNm, int readCount) {
    super();
    this.boardNo = boardNo;
    this.boardTitle = boardTitle;
    this.createDate = createDate;
    this.memNm = memNm;
    this.readCount = readCount;
  }

  public Board(int boardNo, String boardTitle, String boardContent, Date createDate, String memNm, int memNo, int readCount) {
    super();
    this.boardNo = boardNo;
    this.boardTitle = boardTitle;
    this.boardContent = boardContent;
    this.createDate = createDate;
    this.memNm = memNm;
    this.memNo = memNo;
    this.readCount = readCount;
  }

  public int getBoardNo() {
    return boardNo;
  }
  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  public String getBoardTitle() {
    return boardTitle;
  }
  public void setBoardTitle(String boardTitle) {
    this.boardTitle = boardTitle;
  }
  public String getBoardContent() {
    return boardContent;
  }
  public void setBoardContent(String boardContent) {
    this.boardContent = boardContent;
  }
  public Date getCreateDate() {
    return createDate;
  }
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  public String getMemNm() {
    return memNm;
  }
  public void setMemNm(String memNm) {
    this.memNm = memNm;
  }
  public int getReadCount() {
    return readCount;
  }
  public void setReadCount(int readCount) {
    this.readCount = readCount;
  }
  public int getMemNo() {
    return memNo;
  }
  public void setMemNo(int memNo) {
    this.memNo = memNo;
  }

  @Override
  public String toString() {
    return "Board{" +
        "boardNo=" + boardNo +
        ", boardTitle='" + boardTitle + '\'' +
        ", boardContent='" + boardContent + '\'' +
        ", createDate=" + createDate +
        ", memNm='" + memNm + '\'' +
        ", readCount=" + readCount +
        ", memNo=" + memNo +
        '}';
  }
}
