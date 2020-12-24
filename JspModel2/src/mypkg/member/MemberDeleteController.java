package mypkg.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.common.SuperClass;
import mypkg.dao.BoardDao;
import mypkg.dao.MemberDao;

public class MemberDeleteController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id") ;
		
		MemberDao mdao = new MemberDao();
		
		int cnt  = -99999 ; 
		
		// 해당 id가 작성한 행의 remark 컬럼을 수정하도록 합니다.
		// 해당 회원을 삭제합니다.
		cnt =  mdao.DeleteData( id );
		
		super.doGet(request, response); 
		
		super.session.invalidate(); // 세션 정보 클리어
		
		// 회원 가입 페이지로 이동합니다.
		new MemberInsertController().doGet(request, response);
	}	
}





