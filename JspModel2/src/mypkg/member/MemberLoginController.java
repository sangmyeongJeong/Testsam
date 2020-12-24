package mypkg.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Member;
import mypkg.board.BoardListController;
import mypkg.common.SuperClass;
import mypkg.dao.MallDao;
import mypkg.dao.MemberDao;
import mypkg.shopping.MyCartList;
import mypkg.shopping.ShoppingInfo;

public class MemberLoginController extends SuperClass {
	private String id = null ;
	private String password = null ;
	
	@Override
	public boolean validate(HttpServletRequest request) {		
		boolean isCheck = true ; // 기본 값은 true입니다.
		
		// 만일 유효성 검사에 문제가 있으면, false로 변경합니다.
		if (this.id.length() < 4 || this.id.length() > 10) {
			request.setAttribute(super.PREFIX + "id", "아이디는 4자리 이상 10자리 이하이어야 합니다.");
			isCheck = false ;
		}
		
		if (this.password.length() < 4 || this.password.length() > 10) {			
			request.setAttribute(super.PREFIX + "password", "비밀 번호는 4자리 이상 10자리 이하이어야 합니다.");
			isCheck = false ;
		}
		
		return isCheck ;
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.id = request.getParameter("id") ;
		this.password = request.getParameter("password") ;
		
		String gotopage = "";		
		
		if (this.validate(request) == false) { 
			//유효성 검사를 통과 못함
			gotopage = "member/meLoginForm.jsp" ;
			request.setAttribute("id", this.id); //xxxxx
			request.setAttribute("password", this.password); //yyyyyy
			super.doPost(request, response);
			super.GotoPage(gotopage);
			
		} else { //유효성 검사에 문제가 없습니다.
			MemberDao dao = new MemberDao();
			Member bean = dao.SelectData(id, password) ;
			
			String message = ""; // 오류 내용을 알려 줄 메시지
			super.doPost(request, response);
			
			if(bean == null) { // 로그인 실패
				gotopage = "member/meLoginForm.jsp" ;
				message = "아이디나 비번이 잘못되었습니다.";
				super.setErrorMessage(message);
				
				super.GotoPage(gotopage);
			}else { // 로그인 성공
				// 로그인이 되었으므로 세션 영역에 로그인 정보를 바인딩합니다.
				// 이 바인딩 내용은 common.jsp 파일에서 참조하고 있습니다.
				super.session.setAttribute("loginfo", bean);
				
				// 장바구니 테이블에서 들어 있는 나의 쇼핑 정보가 있으면 읽어서 
				// session 영역에 mycart 라는 이름으로 바인딩 합니다.
				MallDao mdao = new MallDao();
				
				List<ShoppingInfo> lists = mdao.GetShoppingInfo(bean.getId());
				
				if (lists.size() > 0) { // 장바구니에 담을 내용이 있는 경우에만
					MyCartList mycart = new MyCartList() ;
					
					// 반복문을 이용하여 카트에 저장합니다.
					for(ShoppingInfo shop : lists) {
						// 해당 상품에 대한 구매 수량을 장바구니에 추가합니다.
						mycart.AddOrder(shop.getPnum(), shop.getQty());
					}				
					
					super.session.setAttribute("mycart", mycart);
				}
				
				//게시물 목록 보기 페이지
				new BoardListController().doGet(request, response); 
			}			
		}		 
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("회원 로그인 호출됨"); 
		super.doGet(request, response);
		
		String gotopage = "/member/meLoginForm.jsp";
		super.GotoPage(gotopage);		
	}
}