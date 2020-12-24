package mypkg.board;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Board;
import mypkg.common.SuperClass;
import mypkg.dao.BoardDao;

public class BoardUpdateController extends SuperClass{
	private Board bean = null ;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no")) ;
		
		BoardDao dao = new BoardDao();
		
		// 여기서 xxx는 현재 수정하고자 하는 이전에 기입했던 게시물 1건을 의미합니다.
		Board xxx = dao.SelectDataByPk(no);
		
		request.setAttribute("bean", xxx);
		
		super.doGet(request, response);
		
		String gotopage = "board/boUpdateForm.jsp" ;
		super.GotoPage(gotopage);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		bean = new Board();
		
		bean.setContent(request.getParameter("content")) ;
		bean.setNo(Integer.parseInt(request.getParameter("no"))) ;
		bean.setPassword(request.getParameter("password"));		
		bean.setRegdate(request.getParameter("regdate")); 
		bean.setSubject(request.getParameter("subject")); 
		bean.setWriter(request.getParameter("writer")); 
		
		// 다음 항목들은 수정에 반영할 필요가 없습니다.
		// bean.setReadhit(Integer.parseInt(request.getParameter("readhit"))) ;
		// bean.setOrderno(Integer.parseInt(request.getParameter("orderno"))) ;
		// bean.setGroupno(Integer.parseInt(request.getParameter("groupno"))) ;
		// bean.setDepth(Integer.parseInt(request.getParameter("depth"))) ;
		
		String gotopage = "" ;
		if (this.validate(request) == true) {
			// 유효성 검사 통과
			BoardDao dao = new BoardDao();
			int cnt = -999999 ;
			cnt = dao.UpdateData(bean) ;
			
			// request 객체의 내용을 보존하면서 목록 보기 페이지로 넘겨 줍니다.
			new BoardListController().doGet(request, response);
			
		}else { // 유효성 검사 실패
			request.setAttribute("bean", bean);			
			super.doPost(request, response);
			gotopage = "board/boUpdateForm.jsp" ;
			super.GotoPage(gotopage);
		}
	}
	@Override
	public boolean validate(HttpServletRequest request) {
		//기본 값으로 true이고, 유효성 검사에 문제가 생기면 false으로 변경
		boolean isCheck = true ; 
		
		if( bean.getSubject().length() < 3 || bean.getSubject().length() > 10 ){
			request.setAttribute( super.PREFIX + "subject", "제목은 3글자 이상 10글자 이하이어야 합니다.");
			isCheck = false  ;
		}
		if( bean.getPassword().length() < 4 || bean.getPassword().length() > 12 ){
			request.setAttribute( super.PREFIX + "password", "비밀 번호는 4자리 이상 10자리 이하이어야 합니다.");
			isCheck = false  ;
		}
		if( bean.getContent().length() < 5 || bean.getContent().length() > 30 ){
			request.setAttribute( super.PREFIX + "content", "글 내용은 5자리 이상 30자리 이하이어야 합니다.");
			isCheck = false  ;
		}
 		String regex = "\\d{4}[-/]\\d{2}[-/]\\d{2}" ;
		if( bean.getRegdate() == null){
			bean.setRegdate( "" );
		}
		boolean result = Pattern.matches(regex, bean.getRegdate());
		if (result == false ) {
			request.setAttribute( super.PREFIX + "regdate", "날짜는 yyyy/MM/dd 또는 yyyy-MM-dd 형식으로 입력해 주세요.");
			isCheck = false  ;
		}
		return isCheck ;
	}
}