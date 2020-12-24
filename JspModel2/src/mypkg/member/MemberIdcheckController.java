package mypkg.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Member;
import mypkg.common.SuperClass;
import mypkg.dao.MemberDao;

public class MemberIdcheckController extends SuperClass{
	// 아이디 중복 체크를 수행합니다.
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id") ;
		
		MemberDao mdao = new MemberDao() ;
		Member bean = mdao.SelectDataByPk(id) ;
		
		if (bean == null ) { // 존재하지 않는 회원
			request.setAttribute("message", id + "은(는) <font color='blue'><b>사용 가능</b></font>한 아이디입니다.") ;
			request.setAttribute("isCheck", true) ;
		}else{ // 존재하는 경우
			if(bean.getId().equalsIgnoreCase("admin")) { // 관리자인  경우
				request.setAttribute("message", "admin은 <font color='red'><b>사용 불가능</b></font>한 아이디입니다.<br><font color='blue'><b>관리자</b></font>를 위한 아이디입니다.") ;
				request.setAttribute("isCheck", false) ;
			}else {
				request.setAttribute("message", id + "은(는) 이미 <font color='red'><b>사용중</b></font>인 아이디입니다.") ;
				request.setAttribute("isCheck", false) ;	
			}
		}
		super.doGet(request, response);
		
		String gotopage = "member/idCheck.jsp";
//		String gotopage = "member/meInsertForm.jsp";
		super.GotoPage( gotopage );
	}
}