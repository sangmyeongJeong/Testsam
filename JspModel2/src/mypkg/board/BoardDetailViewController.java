package mypkg.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Board;
import mypkg.bean.Member;
import mypkg.common.SuperClass;
import mypkg.dao.BoardDao;
import mypkg.utility.FlowParameters;

public class BoardDetailViewController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no")) ;
		
		BoardDao dao = new BoardDao() ;
		
		Board bean = dao.SelectDataByPk(no) ;
		
		FlowParameters parameters 
			= new FlowParameters(
					request.getParameter("pageNumber"), 
					request.getParameter("pageSize"), 
					request.getParameter("mode"), 
					request.getParameter("keyword")) ;
		
		System.out.println(this.getClass() + " : " + parameters.toString());
		
		super.doGet(request, response);
		
		if (bean != null) { 
			// 작성자의 게시물이 아니면 조회수를 +1 증가시킵니다.
			// bean.getWriter()와 세션의 loginfo의 id를 비교합니다.
			// 동일하지 않으면 조회수를 +1
			
			// login : 현재 접속한 사람의 정보를 저장하고 있는 객체입니다.
			Member login =  (Member)super.session.getAttribute("loginfo") ;
			
			if(!bean.getWriter().equals(login.getId())) {
				dao.UpdateReadhit(no) ;
			}
			request.setAttribute("bean", bean);
			request.setAttribute("parameters", parameters.toString());
			
			//상세 보기 페이지로 이동
			String gotopage = "board/boDetailView.jsp" ;
			super.GotoPage(gotopage);
		} else {
			// 포워딩을 이용하여 목록 페이지로 다시 돌아갑니다.
			// 다음과 같이 코딩하면 request와 response 객체가 그대로 다시 넘어 갑니다.
			new BoardListController().doGet(request, response); 
		}		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		
		String gotopage = "" ;
		if (this.validate(request) == true) {
			gotopage = "" ;
			super.GotoPage(gotopage);
			
		}else {
			gotopage = "" ;
			super.GotoPage(gotopage);
		}
	}
	@Override
	public boolean validate(HttpServletRequest request) {
		boolean isCheck = true ;
		
		return isCheck ;
	}
}