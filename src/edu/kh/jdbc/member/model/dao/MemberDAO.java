package edu.kh.jdbc.member.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.member.model.vo.Member;
import edu.kh.jdbc.view.JDBCView;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class MemberDAO {
  private Statement stmt = null;
  private PreparedStatement pstmt = null;
  private ResultSet rs = null;
  private Properties prop = null;

  public MemberDAO() {
    // DAO 클래스에서 사용되는 SQL 구문은 수정될 가능성이 많으므로 별도의 XML 파일에 따로 작성하여 컴파일을 추가적으로 하지 않아도 되는 형태로 코드 작성.
    // 기본 생성자에 코드를 작성하는 이유!
    // -> 외부 XML 파일을 읽어 온다. -> IOException 발생 가능성이 있음.
    // -> 예외 처리 필요 -> 예외 처리는 메소드에만 작성 가능 -> 생성자도 메소드이기 때문에 생성자 사용
    try {
      prop = new Properties();
      prop.loadFromXML(new FileInputStream("member-query.xml"));
    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  /**
   * 로그인 DAO
   * @param conn
   * @param memId
   * @param memPw
   * @return loginMember
   * @throws Exception
   */
  public Member login(Connection conn, String memId, String memPw) throws Exception {
    Member loginMember = null;
    try {
      String sql = prop.getProperty("login");
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, memId);
      pstmt.setString(2, memPw);
      rs = pstmt.executeQuery();
      if(rs.next()) {
        int memNo = rs.getInt("mem_no");
        String rsId = rs.getString("mem_id");
        String rsNm = rs.getString("mem_nm");
        String rsPhone = rs.getString("mem_phone");
        char rsGender = rs.getString("mem_gender").charAt(0);
        Date joinDate = rs.getDate("join_date");
        loginMember = new Member(rsId, rsNm, rsPhone, rsGender, joinDate);
        loginMember.setMemNo(memNo);
      }
    } finally {
      close(pstmt);
    }
    return loginMember;
  }

  /**
   * 회원가입 DAO
   * @param conn
   * @param mem
   * @return result
   * @throws Exception
   */
  public int signUp(Connection conn, Member mem) throws Exception {
    int result = 0;
    try {
      // 1. SQL 구문 작성 (직접 작성 X, xml 에서 얻어오기 O)
      String sql = prop.getProperty("signUp");
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, mem.getMemId());
      pstmt.setString(2, mem.getMemPw());
      pstmt.setString(3, mem.getMemNm());
      pstmt.setString(4, mem.getMemPhone());
      pstmt.setString(5, mem.getMemGender() + "");
      result = pstmt.executeUpdate();
    } finally {
      close(pstmt);
    }
    return result;
  }

  /**
   * 회원 정보 수정 DAO
   * @param updateName
   * @param updatePhone
   * @return
   */
  public int updateMemberInfo(Connection conn, int memNo, String updateName, String updatePhone) throws Exception {
    int result = 0;
    try {
      String sql = prop.getProperty("updateMemberInfo");
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, updateName);
      pstmt.setString(2, updatePhone);
      pstmt.setInt(3, memNo);
      result = pstmt.executeUpdate();
    } finally {
      close(pstmt);
    }
    return result;
  }

  /**
   * 비밀번호 변경 DAO
   * @param conn
   * @param currentPw
   * @param updatePw
   * @return
   * @throws Exception
   */
  public int updatePw(Connection conn, String currentPw, String updatePw) throws Exception {
    int result = 0;
    try {
      String sql = prop.getProperty("updatePw");
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, updatePw);
      pstmt.setString(2, currentPw);
      pstmt.setInt(3, JDBCView.loginMember.getMemNo());
      result = pstmt.executeUpdate();
    } finally {
      close(pstmt);
    }
    return result;
  }

  /**
   * 회원탈퇴 DAO
   * @param conn
   * @param currentPw
   * @return
   * @throws Exception
   */
  public int secession(Connection conn, String currentPw) throws Exception {
    int result = 0;
    try {
      String sql = prop.getProperty("secession");
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, currentPw);
      pstmt.setInt(2, JDBCView.loginMember.getMemNo());
      result = pstmt.executeUpdate();
    } finally {
      close(pstmt);
    }
    return result;
  }
}
