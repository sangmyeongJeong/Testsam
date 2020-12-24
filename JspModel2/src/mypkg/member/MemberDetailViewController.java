package mypkg.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Member;
import mypkg.common.SuperClass;
import mypkg.dao.MemberDao;

public class MemberDetailViewController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id") ;
		MemberDao mdao = new MemberDao(); 
		
		Member bean  = mdao.SelectDataByPk(id);
		
		request.setAttribute("bean", bean);

		super.doGet(request, response);
		String url = "member/meDetailView.jsp";		
		super.GotoPage( url );
	}
}