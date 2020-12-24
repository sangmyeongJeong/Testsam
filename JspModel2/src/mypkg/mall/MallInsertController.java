package mypkg.mall;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.common.SuperClass;
import mypkg.product.ProductListController;
import mypkg.shopping.MyCartList;

public class MallInsertController extends SuperClass{
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		super.doPost(request, response);
		
		String gotopage = "" ;
		
		if (super.session.getAttribute("loginfo") == null) {
			// 로그인 하지 않았다면 로그인 페이지로 이동
			gotopage = "member/meLoginForm.jsp" ;
			super.GotoPage(gotopage);
		} else { // 누군가 로그인 한 상태입니다.
			int num = Integer.parseInt(request.getParameter("num")) ;
			int stock = Integer.parseInt(request.getParameter("stock")) ; // 재고
			int qty = Integer.parseInt(request.getParameter("qty")) ; // 구매 수량
			
			System.out.println(num+ "/" + stock  + "/" + qty);
			
			if (stock < qty) { // 재고 수량 초과
				String message = "재고 수량이 부족합니다." ;
				super.setErrorMessage(message);
				new ProductListController().doGet(request, response);
				
			} else { // 판매에 문제 없슴
				MyCartList mycart = (MyCartList)super.session.getAttribute("mycart") ;
				if (mycart == null) { // 카트가 없으면
					mycart = new MyCartList() ; // 매장 입구에서 카트 준비
				}
				mycart.AddOrder(num, qty); // 카트에 담기
				super.session.setAttribute("mycart", mycart);
				
				// 장바구니 목록 페이지로 이동합니다.
				new MallListController().doGet(request, response) ;
			}
		}		
	}
}