package mypkg.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Board;
import mypkg.common.SuperClass;
import mypkg.dao.BoardDao;
import mypkg.utility.FlowParameters;
import mypkg.utility.Paging;

public class BoardListController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 게시물 목록을 읽은 다음, boList.jsp 파일로 이동합니다. 
		BoardDao dao = new BoardDao() ;
		
		// 페이징과 필드 검색을 위한 파라미터를 우선 챙깁니다.
		FlowParameters parameters 
			= new FlowParameters(
					request.getParameter("pageNumber"), 
					request.getParameter("pageSize"), 
					request.getParameter("mode"), 
					request.getParameter("keyword"));
		
		// 파라미터 확인을 위한 출력
		System.out.println(this.getClass() + " : " + parameters.toString());
		
//		int totalCount = 33 ;
		int totalCount 
			= dao.SelectTotalCount(
					parameters.getMode(), 
					parameters.getKeyword());
		
		String contextPath = request.getContextPath() ;
		String myurl = contextPath + "/Shopping?command=boList" ;
		
		Paging pageInfo 
			= new Paging(
					parameters.getPageNumber(), 
					parameters.getPageSize(), 
					totalCount, 
					myurl, 
					parameters.getMode(), 
					parameters.getKeyword());
		

		List<Board> lists = dao.SelectDataList(
				pageInfo.getBeginRow(),
				pageInfo.getEndRow(),
				parameters.getMode(), 
				parameters.getKeyword() + "%") ;
				// "%" 문자열은 like 연산자 때문에 넣었습니다.
		
		// 바인딩해야 할 목록들
		request.setAttribute("lists", lists); // 게시물 목록
		
		// 페이징 관련 항목들
		request.setAttribute("pagingHtml", pageInfo.getPagingHtml());
		request.setAttribute("pagingStatus", pageInfo.getPagingStatus());
		
		// 검색 필드의 상태 값 저장을 위한 항목들  
		request.setAttribute("mode", parameters.getMode());
		request.setAttribute("keyword", parameters.getKeyword());
		
		// 상세 보기, 수정, 삭제, 답글 등의 링크에 사용될 parameter list 문자열
		request.setAttribute("parameters", parameters.toString());
		
		
		super.doGet(request, response);
		
		String gotopage = "board/boList.jsp" ;
		super.GotoPage(gotopage);
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