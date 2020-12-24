package mypkg.board;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Board;
import mypkg.common.SuperClass;
import mypkg.dao.BoardDao;
             
public class BoardInsertController extends SuperClass{
	private Board bean = null ;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		String gotopage = "board/boInsertForm.jsp" ;
		super.GotoPage(gotopage);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		bean  = new Board();
		//bean.setNo(no); //시퀀스는 필요 없음
		bean.setContent( request.getParameter("content") );
		bean.setPassword( request.getParameter("password") );
		bean.setRegdate( request.getParameter("regdate") );
		bean.setSubject( request.getParameter("subject") );
		bean.setWriter( request.getParameter("writer") );	
		
		if( request.getParameter("depth") != null && request.getParameter("depth") != "" ){
			bean.setDepth( Integer.parseInt( request.getParameter("depth") ));	
		}
		if( request.getParameter("groupno") != null && request.getParameter("groupno") != "" ){
			bean.setGroupno( Integer.parseInt( request.getParameter("groupno") ));	
		}
		if( request.getParameter("orderno") != null && request.getParameter("orderno") != "" ){
			bean.setOrderno( Integer.parseInt( request.getParameter("orderno") ));	
		}
		if( request.getParameter("readhit") != null && request.getParameter("readhit") != "" ){
			bean.setReadhit( Integer.parseInt( request.getParameter("readhit") ));	
		}   
		
		System.out.println( bean );
		
		if ( this.validate( request ) == false ) {			
			request.setAttribute("bean", bean);
			super.doPost(request, response);
			String url = "/board/boInsertForm.jsp";
			super.GotoPage( url );
//			super.GotoPage(this.getClass().toString(), "boInsertForm");
		}else{
			//url = "/board/boList.jsp";
			//DAO 객체를 생성한다.
			BoardDao bdao = new BoardDao();			
			int cnt = -99999 ; 			
			//Bean 객체를 이용하여 해당 게시물을 추가한다.
			cnt = bdao.InsertData(bean) ;
			new BoardListController().doGet( request, response );
		}
	}
	@Override
	public boolean validate(HttpServletRequest request) {
		final String PREFIX = "err" ;
		boolean isCheck = true ; //기본 값으로 true이고, 유효성 검사에 문제가 생기면 false으로 변경
		
		if( bean.getSubject().length() < 3 || bean.getSubject().length() > 10 ){
			request.setAttribute( PREFIX + "subject", "제목은 3글자 이상 10글자 이하이어야 합니다.");
			isCheck = false  ;
		}
		if( bean.getPassword().length() < 4 || bean.getPassword().length() > 12 ){
			request.setAttribute( PREFIX + "password", "비밀 번호는 4자리 이상 10자리 이하이어야 합니다.");
			isCheck = false  ;
		}
		if( bean.getContent().length() < 5 || bean.getContent().length() > 30 ){
			request.setAttribute( PREFIX + "content", "글 내용은 5자리 이상 30자리 이하이어야 합니다.");
			isCheck = false  ;
		}
 		String regex = "\\d{4}[-/]\\d{2}[-/]\\d{2}" ;
		if( bean.getRegdate() == null){
			bean.setRegdate( "" );
		}
		boolean result = Pattern.matches(regex, bean.getRegdate());
		if (result == false ) {
			request.setAttribute( PREFIX + "regdate", "날짜는 yyyy/MM/dd 또는 yyyy-MM-dd 형식으로 입력해 주세요.");
			isCheck = false  ;
		}
		return isCheck ;
	}
}