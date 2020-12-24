package mypkg.member;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Member;
import mypkg.common.SuperClass;
import mypkg.dao.MemberDao;
import mypkg.utility.Paging;

public class MemberListController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDao mdao = new MemberDao();
		String pageNumber = request.getParameter("pageNumber") ;
		String pageSize = request.getParameter("pageSize") ;
		int totalCount = mdao.SelectTotalCount(); //1008 ;
		System.out.println( "토탈  카운터 : " + totalCount);
				
		String contextPath = request.getContextPath()  ;
		String myurl = contextPath + "/Shopping?command=meList" ;
		
		String mode = null ;
		String keyword = null ;
		Paging pageInfo = 
				new Paging(pageNumber, pageSize, totalCount, myurl, mode, keyword) ;
		
		List<Member> lists 
		= mdao.SelectDataList(pageInfo.getBeginRow(), pageInfo.getEndRow()) ;		
		
		request.setAttribute("lists", lists);
		request.setAttribute("pagingHtml", pageInfo.getPagingHtml());
		request.setAttribute("pagingStatus", pageInfo.getPagingStatus());
		
		super.doGet(request, response);
		String url = "/member/meList.jsp";		
		super.GotoPage( url );  	
	}
}