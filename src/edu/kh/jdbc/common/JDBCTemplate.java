package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {
  /*
   * DB 연결, JDBC 자원 반환 등의 JDBC 관련 공통 내용을 모아둔 클래스
   * getConnection() : 커넥션을 반환하는 메소드
   * close(smtm | pstmt | rs | conn) : 자원 반환 메소드
   * commit() / rollback() : 트랙잭션 처리용 메소드
   */
  private static Connection conn = null;
  public static Connection getConnection() {
    try {
      if(conn == null || conn.isClosed()) {
        // 드라이버, url, 아이디, 비밀번호는 바뀔 가능성이 많아 변경되는 경우 컴파일을 새로해 비효율 적임
        // -> 외부 파일에 내용을 작성해 읽어오는 방법으로 문제 해결.

        // 외부 파일에서 정보를 읽어와 저장할 Properties 객체 생성
        // Properties : K, V 가 String 으로 제한된 Map
        // -> 파일 입출력에 특화되어 있음.
        Properties prop = new Properties();
        // driver.xml 파일을 읽어와서 prop 에 저장
        prop.loadFromXML(new FileInputStream("driver.xml"));

        Class.forName(prop.getProperty("driver"));
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String pass = prop.getProperty("pass");
        conn = DriverManager.getConnection(url, user, pass);
        conn.setAutoCommit(false);
      } else {

      }
    } catch(Exception err) {
      err.printStackTrace();
    }
    return conn;
  }
  public static void close(Connection conn) {
    try {
      if(conn != null && !conn.isClosed()) {
        // conn 이 참조하는 Connection 객체가 있고, 그 객체가 반환되지 않았을 때
        conn.close();
      }
    } catch(SQLException err) {
      err.printStackTrace();
    }
  }
  public static void close(Statement stmt) {
    try {
      if(stmt != null && !stmt.isClosed()) {
        stmt.close();
      }
    } catch(SQLException err) {
      err.printStackTrace();
    }
  }
  public static void close(ResultSet rs) {
    try {
      if(rs != null && !rs.isClosed()) {
        rs.close();
      }
    } catch(SQLException err) {
      err.printStackTrace();
    }
  }
  public static void commit(Connection conn) {
    try {
      if(conn != null && !conn.isClosed()) {
        conn.commit();
      }
    } catch(SQLException err) {
      err.printStackTrace();
    }
  }
  public static void rollback(Connection conn) {
    try {
      if(conn != null && !conn.isClosed()) {
        conn.rollback();
      }
    } catch(SQLException err) {
      err.printStackTrace();
    }
  }
}
