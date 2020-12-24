package mypkg.member;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Member;
import mypkg.common.SuperClass;
import mypkg.dao.MallDao;
import mypkg.shopping.MyCartList;

public class MemberLogoutController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		// 만약 세션 공간에 장바구니 정보가 있다면
		// 이 정보를 장바구니 임시 테이블에 저장합니다.
		MyCartList mycart= (MyCartList)super.session.getAttribute("mycart") ;
		
		if (mycart != null) {			
			Map<Integer, Integer> maplists = mycart.GetAllOrderLists() ;
			
			// 카트에 내용물이 없더라도, 기존 잔여 데이터가 있을 수 있으므로 
			// 다오는 무조건 호출합니다.
			Member mem = (Member)super.session.getAttribute("loginfo") ;
			MallDao dao = new MallDao() ;
			dao.InsertCartData(mem, maplists) ;
		}
		
		// 로그인 정보를 세션 영역에서 지우기
		super.session.invalidate(); 
		
		String gotopage = "/member/meLoginForm.jsp" ; 
		super.GotoPage(gotopage); 
	}
}
