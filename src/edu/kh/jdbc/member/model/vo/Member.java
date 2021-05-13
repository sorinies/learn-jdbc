package edu.kh.jdbc.member.model.vo;

import java.sql.Date;

public class Member {
  private int memNo;
  private String memId;
  private String memPw;
  private String memNm;
  private String memPhone;
  private char memGender;
  private Date joinDate;
  private char scsnFL;

  public Member() {}

  public Member(String memId, String memNm, String memPhone, char memGender, Date joinDate) {
    this.memId = memId;
    this.memNm = memNm;
    this.memPhone = memPhone;
    this.memGender = memGender;
    this.joinDate = joinDate;
  }

  public Member(String memId, String memPw, String memNm, String memPhone, char memGender) {
    this.memId = memId;
    this.memPw = memPw;
    this.memNm = memNm;
    this.memPhone = memPhone;
    this.memGender = memGender;
  }
  public Member(int memNo, String memId, String memPw, String memNm, String memPhone, char memGender, Date joinDate, char scsnFL) {
    this.memNo = memNo;
    this.memId = memId;
    this.memPw = memPw;
    this.memNm = memNm;
    this.memPhone = memPhone;
    this.memGender = memGender;
    this.joinDate = joinDate;
    this.scsnFL = scsnFL;
  }

  public int getMemNo() {
    return memNo;
  }

  public void setMemNo(int memNo) {
    this.memNo = memNo;
  }

  public String getMemId() {
    return memId;
  }

  public void setMemId(String memId) {
    this.memId = memId;
  }

  public String getMemPw() {
    return memPw;
  }

  public void setMemPw(String memPw) {
    this.memPw = memPw;
  }

  public String getMemNm() {
    return memNm;
  }

  public void setMemNm(String memNm) {
    this.memNm = memNm;
  }

  public String getMemPhone() {
    return memPhone;
  }

  public void setMemPhone(String memPhone) {
    this.memPhone = memPhone;
  }

  public char getMemGender() {
    return memGender;
  }

  public void setMemGender(char memGender) {
    this.memGender = memGender;
  }

  public Date getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(Date joinDate) {
    this.joinDate = joinDate;
  }

  public char getScsnFL() {
    return scsnFL;
  }

  public void setScsnFL(char scsnFL) {
    this.scsnFL = scsnFL;
  }
}
