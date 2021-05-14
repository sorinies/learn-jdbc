package edu.kh.jdbc.view;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.member.model.service.MemberService;
import edu.kh.jdbc.member.model.vo.Member;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * JDBC 실습용 View 클래스
 * @author 백승훈
 */
public class JDBCView {
  public static Member loginMember = null;
  private Scanner sc = new Scanner(System.in);
  private MemberService memberService = new MemberService();
  private BoardService boardService = new BoardService();

  /**
  JDBC 프로젝트 메뉴 출력 View
  */
  public void displayMenu() {
    int sel = 0;
    do {
      try {
        if(loginMember == null) {
          System.out.println("==================================");
          System.out.println("[로그인 화면]");
          System.out.println("1. 로그인");
          System.out.println("2. 회원가입");
          System.out.println("0. 프로그램 종료");
          System.out.println("==================================");
          System.out.print("메뉴 선택 >> ");
          sel = sc.nextInt();
          sc.nextLine();

          switch (sel) {
            case 1: login(); break;
            case 2: signUp(); break;
            case 0: System.out.println("프로그램 종료"); break;
            default: System.out.println("잘못 입력하셨습니다.");
          }
        } else {
          System.out.println("==================================");
          System.out.println("[회원 전용 메뉴]");
          System.out.println("1. 내 정보 조회");
          System.out.println("2. 내 정보 수정");
          System.out.println("3. 비밀번호 변경");
          System.out.println("4. 회원 탈퇴");
          System.out.println("5. 게시글 목록 조회");
          System.out.println("6. 게시글 상세 조회");
          System.out.println("7. 게시글 작성");
          System.out.println("8. 게시글 삭제");
          System.out.println("9. (개선된) 게시글 상세 조회");
          System.out.println("10. (개선된) 게시글 작성");
          System.out.println("11. (개선된) 게시글 삭제");
          System.out.println("12. 게시글 수정");
          System.out.println("13. 게시물 복구 신청");
          System.out.println("0. 로그아웃");
          System.out.println("==================================");
          System.out.print("메뉴 선택 >> ");
          sel = sc.nextInt();
          sc.nextLine();

          switch (sel) {
            case 1: selectMyInfo(); break;
            case 2: updateMemberInfo(); break;
            case 3: updatePw(); break;
            case 4: secession(); break;
            case 5: selectAllBoard(); break;
            case 6: selectBoard(); break;
            case 7: insertBoard(); break;
            case 8: deleteBoard(); break;
            case 9: eSelectBoard(); break;
            case 10: eInsertBoard(); break;
            case 11: eDeleteBoard(); break;
            case 12: modifyBoard(); break;
            case 13: reqRecoverBoard(); break;
            case 0:
              loginMember = null;
              System.out.println("로그아웃 되었습니다.");
              sel = -1;
              break;
            default: System.out.println("잘못 입력하셨습니다.");
          }
        }
      } catch(InputMismatchException err) {
        System.out.println("정수만 입력해주세요.");
        sel = -1;
        sc.nextLine();
      }

    } while(sel != 0);
  }

  /**
   * 로그인 View
   */
  private void login() {
    System.out.println("[로그인]");
    System.out.print("아이디: ");
    String memId = sc.next();
    System.out.print("비밀번호: ");
    String memPw = sc.next();

    try {
      loginMember = memberService.login(memId, memPw);
      if(loginMember == null) {
        System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
      } else {
        System.out.println(loginMember.getMemNm() + "님 환영합니다.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 회원 가입 View
   */
  private void signUp() {
    System.out.println("[회원 가입]");
    // 아이디 중복 검사는 추후에 진행 예정
    System.out.print("아이디: ");
    String memId = sc.next();
    System.out.print("비밀번호: ");
    String memPw = sc.next();
    System.out.print("이름: ");
    String memName = sc.next();
    System.out.print("전화번호: ");
    String memPhone = sc.next();
    System.out.print("성별(M/F): ");
    char memGender = sc.next().toUpperCase().charAt(0);

    // 입력받은 값을 Member VO 객체에 저장
    Member mem = new Member(memId, memPw, memName, memPhone, memGender);
    try {
      int result = memberService.signUp(mem);
      if (result > 0) {
        System.out.println("회원가입 성공");
      } else {
        System.out.println("회원가입 실패...");
      }
    } catch (Exception e) {
      System.out.println("회원 가입 중 오류 발생");
      e.printStackTrace();
    }
  }

  /**
   * 내 정보 조회 View
   */
  private void selectMyInfo() {
    System.out.println("[내 정보 조회]");
    System.out.println("아이디: " + loginMember.getMemId());
    System.out.println("이름: " + loginMember.getMemNm());
    System.out.println("전화번호: " + loginMember.getMemPhone());
    System.out.print("성별: ");
    System.out.println((loginMember.getMemGender() + "").equals("M") ? "남자" : "여자");
    System.out.println("가입일: " + loginMember.getJoinDate());
  }

  /**
   * 회원 정보 수정 View
   */
  private void updateMemberInfo() {
    System.out.println("[회원 정보 수정]");
    System.out.print("변경할 이름: ");
    String updateName = sc.next();
    System.out.print("변경할 전화번호: ");
    String updatePhone = sc.next();

    try {
      int result = memberService.updateMemberInfo(loginMember.getMemNo(), updateName, updatePhone);
      if(result > 0) {
        System.out.println("회원 정보 수정 성공");
      } else {
        System.out.println("회원 정보 수정 실패...");
      }
    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  /**
   * 비밀번호 수정 View
   */
  private void updatePw() {
    System.out.println("[비밀번호 수정]");
    System.out.print("현재 비밀번호: ");
    String currentPw = sc.next();
    System.out.print("변경할 비밀번호: ");
    String updatePw = sc.next();
    System.out.print("변경할 비밀번호 확인: ");
    String confirmPw = sc.next();
    if(updatePw.equals(confirmPw)) {
      try {
        int result = memberService.updatePw(currentPw, updatePw);
        if(result > 0) {
          System.out.println("비밀번호 변경 성공");
          loginMember = null;
          System.out.println("다시 로그인 해주세요.");
        } else {
          System.out.println("비밀번호 변경 실패");
        }
      } catch (Exception err) {
        err.printStackTrace();
      }
    } else {
      System.out.println("변경할 비밀번호가 일치하지 않습니다.");
    }
  }

  /**
   * 회원 탈퇴 View
   */
  private void secession() {
    System.out.println("[회원 탈퇴]");
    System.out.print("비밀번호 입력: ");
    String currentPw = sc.next();
    System.out.print("탈퇴혀?(Y/N): ");
    char ch = sc.next().toUpperCase().charAt(0);
    if(ch == 'Y') {
      try {
        int result = memberService.secession(currentPw);
        if(result > 0) {
          System.out.println("탈퇴 성공");
          loginMember = null;
        } else {
          System.out.println("비밀번호가 일치하지 않습니다.");
        }
      } catch (Exception err) {
        System.out.println("회원 탈퇴 중 오류가 발생했습니다.");
        err.printStackTrace();
      }
    } else if(ch == 'N') {
      System.out.println("회원 탈퇴를 취소합니다.");
    } else {
      System.out.println("잘못 입력하셨습니다.");
    }
  }

  /**
   * 게시물 전체 목록 조회 View
   */
  private void selectAllBoard() {
    try {
      System.out.println("[게시글 목록 조회]");
      // DB 에서 게시글 목록을 조회해서 반환하는 Service 호출
      List<Board> boardList = boardService.selectAllBoard();
      if (boardList.isEmpty()) {
        System.out.println("게시글이 존재하지 않습니다.");
      } else {
        for (Board b : boardList) {
          System.out.printf("%d | %s | %s | %s | %d\n",
              b.getBoardNo(), b.getBoardTitle(), b.getMemNm(), b.getCreateDate().toString(), b.getReadCount());
        }
      }
    } catch (Exception err) {
      System.out.println("조회 중 오류 발생");
      err.printStackTrace();
    }
  }

  /**
   * 게시글 상세 조회 View
   */
  private void selectBoard() {
    try {
      System.out.println("[게시글 상세 조회]");
      System.out.print("조회할 게시글 번호: ");
      int boardNo = sc.nextInt();
      sc.nextLine();
      Board board = boardService.selectBoard(boardNo);
      if (board == null) {
        System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
      } else {
        System.out.println("--------------------------------------------------------");
        System.out.printf("글번호: %d | 제목: %s\n", board.getBoardNo(), board.getBoardTitle());
        System.out.printf("작성자명: %s | 작성일: %s | 조회수: %d\n", board.getMemNm(), board.getCreateDate(), board.getReadCount());
        System.out.println("--------------------------------------------------------");
        System.out.println(board.getBoardContent());
        System.out.println("--------------------------------------------------------");
      }
    } catch (InputMismatchException err) {
      System.out.println("정수만 입력해주세요.");
    } catch (Exception err) {
      System.out.println("조회 중 오류 발생");
      err.printStackTrace();
    }
  }


  /**
   * 게시글 삽입 View
   */
  private void insertBoard() {
    System.out.println("[게시글 작성]");
    System.out.println("제목을 입력하세요:");
    String boardTitle = sc.nextLine();
    System.out.println("내용을 입력하세요:");
    String boardContent = sc.nextLine();

    Board board = new Board(boardTitle, boardContent, loginMember.getMemNo());
    try {
      int result = boardService.insertBoard(board);
      if (result > 0) {
        System.out.println("게시글 작성 성공");
      } else {
        System.out.println("게시글 작성 실패...");
      }
    } catch (Exception e) {
      System.out.println("게시글 작성 중 오류 발생");
      e.printStackTrace();
    }
  }

  /**
   * 게시글 삭제 View
   */
  private void deleteBoard() {
    System.out.println("[게시글 삭제]");
    System.out.print("삭제할 게시글 번호 입력: ");
    int boardNo = sc.nextInt();
    sc.nextLine();
    try {
      Board board = boardService.selectBoard(boardNo);
      if (board == null) {
        System.out.println("번호가 일치하는 게시글이 없습니다.");
      } else if(board.getMemNo() != loginMember.getMemNo()) {
        System.out.println("글 작성자가 아닙니다." + board.getMemNo() + "|" + loginMember.getMemNo());
      } else {
        System.out.println("-------------------------------------------");
        System.out.printf("글 제목: %s\n", board.getBoardTitle());
        System.out.println("글 내용:");
        System.out.println(board.getBoardContent());
        System.out.println("-------------------------------------------");
        System.out.print("삭제하시겠습니까? (Y/N): ");
        char ch = sc.next().toUpperCase().charAt(0);
        if(ch == 'Y') {
          try {
            int result = boardService.deleteBoard(boardNo);
            if(result > 0) {
              System.out.println("게시글이 삭제되었습니다.");
            }
          } catch (Exception err) {
            System.out.println("삭제 중 오류가 발생했습니다.");
            err.printStackTrace();
          }
        } else if(ch == 'N') {
          System.out.println("게시글 삭제를 취소합니다.");
        } else {
          System.out.println("잘못 입력하셨습니다.");
        }
      }
    } catch (Exception err) {
      System.out.println("잘못 입력하셨습니다.");
      err.printStackTrace();
    }
  }

  /**
   * 개선된 게시글 상세 조회 View
   */
  private void eSelectBoard() {
    try {
      System.out.println("[개선된 게시글 상세 조회]");
      System.out.print("조회할 게시글 번호: ");
      int boardNo = sc.nextInt();
      sc.nextLine();
      Board board = boardService.eSelectBoard(boardNo);
      if (board == null) {
        System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
      } else {
        printBoard(board);
      }
    } catch (InputMismatchException err) {
      System.out.println("정수만 입력해주세요.");
    } catch (Exception err) {
      System.out.println("조회 중 오류 발생");
      err.printStackTrace();
    }
  }

  /**
   * 개선된 게시글 작성 View
   */
  private void eInsertBoard() {
    System.out.println("[개선된 게시글 작성]");
    System.out.println("제목을 입력하세요:");
    String boardTitle = sc.nextLine();
    System.out.println("--- 내용 입력(:q 입력시 입력 종료) ---");
    StringBuffer sb = new StringBuffer();
    // String 에 내용 누적시 메모리 낭비가 심함 --> StringBuffer 로 해결.

    String str = null; // 입력된 문자열을 임시 저장할 변수
    while(true) {
      str = sc.nextLine();
      if (str.equals(":q")) {
        break;
      }
      sb.append(str);
      sb.append("\n");
    }

    try {
      Board board = boardService.eInsertBoard(boardTitle, sb.toString());
      if (board == null) {
        System.out.println("게시글 삽입 실패...");
      } else {
        printBoard(board);
      }
    } catch (Exception err) {
      System.out.println("게시글 작성 중 오류 발생");
      err.printStackTrace();
    }
  }

  /**
   * 개선된 게시글 삭제 View
   */
  private void eDeleteBoard() {
    System.out.println("[개선된 게시글 삭제]");
    System.out.print("삭제할 게시글 번호 입력: ");
    int boardNo = sc.nextInt();
    sc.nextLine();
    try {
      int result = boardService.checkBoardNo(boardNo);
      if (result == 0) {
        System.out.println("존재하지 않는 게시글입니다.");
      } else if (result == -1) {
        System.out.println("작성하신 글만 삭제할 수 있습니다.");
      } else {
        System.out.print("정말로 삭제하시겠습니까? (Y/N): ");
        char ch = sc.next().toUpperCase().charAt(0);
        if(ch == 'Y') {
          result = boardService.deleteBoard(boardNo);
          if (result > 0) {
            System.out.println("삭제되었습니다.");
          } else {
            System.out.println("삭제 실패");
          }
        } else if(ch == 'N') {
          System.out.println("게시글 삭제를 취소합니다.");
        } else {
          System.out.println("잘못 입력하셨습니다.");
        }
      }
    } catch (Exception err) {
      System.out.println("게시글 삭제 과정에서 오류 발생.");
      err.printStackTrace();
    }
  }

  /**
   * 게시글 수정 View
   */
  private void modifyBoard() {
    System.out.println("[게시글 수정]");
    System.out.print("수정할 게시글 번호: ");
    int boardNo = sc.nextInt();
    sc.nextLine();
    try {
      int result = boardService.checkBoardNo(boardNo);
      if (result == 0) {
        System.out.println("존재하지 않는 게시글 번호입니다.");
      } else if (result == -1) {
        System.out.println("작성하신 글이 아닙니다.");
      } else {
        System.out.print("수정할 제목: ");
        String boardTitle = sc.nextLine();
        System.out.println("--- 내용 입력(:q 입력시 입력 종료) ---");
        StringBuffer sb = new StringBuffer();
        String str = null;
        while (true) {
          str = sc.nextLine();
          if (str.equals(":q")) {
            break;
          }
          sb.append(str);
          sb.append("\n");
        }
        Board board = boardService.modifyBoard(boardTitle, sb.toString(), boardNo);
        if (board != null) {
          printBoard(board);
        } else {
          System.out.println("게시글 수정 실패..");
        }
      }
    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  /**
   * 게시물 복구 신청 View
   */
  private void reqRecoverBoard() {
    System.out.println("[게시물 복구 신청]");
    try {
      List<Board> deletedList = boardService.selectDeletedBoard(loginMember.getMemNo());
      if (deletedList.isEmpty()) {
        System.out.println("삭제된 게시글이 없습니다.");
      } else {
        for (Board b : deletedList) {
          System.out.printf("%d | %s | %s | %s | %d\n",
              b.getBoardNo(), b.getBoardTitle(), b.getMemNm(), b.getCreateDate().toString(), b.getReadCount());
        }
        System.out.println("------------------------------------------------");
        System.out.print("복구 신청할 게시글 번호 입력: ");
        int boardNo = sc.nextInt();
        sc.nextLine();
        int result = boardService.reqRecoverBoard(boardNo, loginMember.getMemNo());
        if (result > 0) {
          System.out.println("복구 신청을 완료 했습니다.");
        } else {
          System.out.println("복구 신청 실패..");
        }
      }
    } catch (SQLIntegrityConstraintViolationException err) {
      // UNIQUE 제약 조건을 위배했다 == 이미 복구가 신청된 게시글
      System.out.println("이미 복구 신청된 글입니다.");
    } catch (Exception err) {
      System.out.println("조회 중 오류 발생");
      err.printStackTrace();
    }
  }

  /**
   * 게시물 상세 출력 View
   * @param board
   */
  private void printBoard(Board board) {
    System.out.println("--------------------------------------------------------");
    System.out.printf("글번호: %d | 제목: %s\n", board.getBoardNo(), board.getBoardTitle());
    System.out.printf("작성자명: %s | 작성일: %s | 조회수: %d\n", board.getMemNm(), board.getCreateDate(), board.getReadCount());
    System.out.println("--------------------------------------------------------");
    System.out.println(board.getBoardContent());
    System.out.println("--------------------------------------------------------");
  }
}
