package edu.kh.jdbc.member.model.service;

// import static : 지정된 클래스에 있는 static 멤버를 호출할 때 클래스명을 생략하게 해줌.
import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.model.vo.Member;
import edu.kh.jdbc.view.JDBCView;

import java.sql.Connection;

//  Service 클래스 역할
// 1. 매개변수로 전달받은 데이터 또는 DAO 반환 데이터의 가공 처리
// 2. Connection 생성 및 트랙잭션 처리
// -> 1, 2번을 묶어 비즈니스 로직
public class MemberService {
  private MemberDAO dao = new MemberDAO();

  /**
   * 로그인 Service
   * @param memId
   * @param memPw
   * @return loginMember
   * @throws Exception
   */
  public Member login(String memId, String memPw) throws Exception {
    Connection conn = getConnection();
    // Connection 과 전달받은 매개변수를 dao.login() 메소드의 매개변수로 전달.
    Member loginMember = dao.login(conn, memId, memPw);
    close(conn);
    return loginMember;
  }

  /**
   * 회원가입 Service
   * @param mem
   * @return result
   * @throws Exception
   */
  public int signUp(Member mem) throws Exception {
    Connection conn = getConnection();
    int result = dao.signUp(conn, mem);
    if(result > 0) {
      commit(conn);
    } else {
      rollback(conn);
    }
    close(conn);
    return result;
  }

  /**
   * 회원 정보 수정 Service
   * @param memNo
   * @param updateName
   * @param updatePhone
   * @return result
   * @throws Exception
   */
  public int updateMemberInfo(int memNo, String updateName, String updatePhone) throws Exception {
    Connection conn = getConnection();
    int result = dao.updateMemberInfo(conn, memNo, updateName, updatePhone);
    if(result > 0) {
      commit(conn);
      JDBCView.loginMember.setMemNm(updateName);
      JDBCView.loginMember.setMemNm(updatePhone);
    } else {
      rollback(conn);
    }
    close(conn);
    return result;
  }

  /**
   * 비밀번호 수정 Service
   * @param currentPw
   * @param updatePw
   * @return
   * @throws Exception
   */
  public int updatePw(String currentPw, String updatePw) throws Exception {
    Connection conn = getConnection();
    int result = dao.updatePw(conn, currentPw, updatePw);
    if(result > 0) {
      commit(conn);
    } else {
      rollback(conn);
    }
    close(conn);
    return result;
  }

  /**
   * 회원 탈퇴 Service
   * @param currentPw
   * @return result
   * @throws Exception
   */
  public int secession(String currentPw) throws Exception {
    Connection conn = getConnection();
    int result = dao.secession(conn, currentPw);
    if(result > 0) {
      commit(conn);
    } else {
      rollback(conn);
    }
    close(conn);
    return result;
  }
}
