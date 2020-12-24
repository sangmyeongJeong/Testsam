package mypkg.board;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Board;
import mypkg.common.SuperClass;
import mypkg.dao.BoardDao;

public class BoardReplyController extends SuperClass{
	private Board bean = null ;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		bean = new Board();
		
		// no는 시퀀스가 알아서 처리하므로 무시합니다.
		// bean.setNo(no);
		
		// 조회 수는 기본 값으로 채워질 예정 
		//bean.setReadhit(request.getParameter("readhit"));
		
		bean.setContent(request.getParameter("content"));		
		bean.setPassword(request.getParameter("password"));		
		bean.setRegdate(request.getParameter("regdate"));
		bean.setSubject(request.getParameter("subject"));
		bean.setWriter(request.getParameter("writer"));
		
		int groupno = Integer.parseInt(request.getParameter("groupno")) ;
		bean.setGroupno(groupno);
		
		int orderno = Integer.parseInt(request.getParameter("orderno")) ;
		bean.setOrderno(orderno+1);
		
		int depth = Integer.parseInt(request.getParameter("depth")) ;
		bean.setDepth(depth+1);
		
		String gotopage = "" ;
		
		if (this.validate(request) == true) {
			BoardDao dao = new BoardDao();
			int cnt = -99999;
			cnt = dao.ReplyData(bean, groupno, orderno) ;

			new BoardListController().doGet(request, response);			
		}else {
			request.setAttribute("bean", bean);
			super.doPost(request, response);
			gotopage = "board/boReplyForm.jsp" ;
			super.GotoPage(gotopage);
		}
	}
	@Override
	public boolean validate(HttpServletRequest request) {
		boolean isCheck = true ; //기본 값으로 true이고, 유효성 검사에 문제가 생기면 false으로 변경
		
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
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		String gotopage = "board/boReplyForm.jsp" ;
		super.GotoPage(gotopage);
	}	
}