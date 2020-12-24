package mypkg.mall;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Member;
import mypkg.common.SuperClass;
import mypkg.dao.MallDao;
import mypkg.product.ProductListController;
import mypkg.shopping.MyCartList;


public class MallCalculateController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		System.out.println("장바구니 내역을 이용하여 계산을 합니다.");
		
		MyCartList mycart = (MyCartList)super.session.getAttribute("mycart");
		
		MallDao dao = new MallDao();		
		
		if (mycart != null) {
			System.out.println("maplists : 내가 구매한 상품들의 번호와 수량에 대한 컬렉션");
			Map<Integer, Integer> maplists = mycart.GetAllOrderLists();

			int totalPoint = (Integer)super.session.getAttribute( "totalPoint" ) ; 
			
			// mem : 계산을 수행하는 당사자
			Member mem = (Member)super.session.getAttribute("loginfo");
			
			System.out.println("dao.Calculate 메소드를 호출합니다.");
			// Calculate() 메소드는 계산 로직을 수행해주는 메소드입니다.
			dao.Calculate(mem, maplists, totalPoint);
					
			// 5. 세션 영역의 모든 정보를 지우도록 한다.
			super.session.removeAttribute("shoplists"); //쇼핑 정보 삭제
			super.session.removeAttribute("totalAmount");//금액 정보 삭제
			super.session.removeAttribute("totalPoint");//금액 정보 삭제
			super.session.removeAttribute("mycart"); //카트 반납하기		
			//super.session.setAttribute("message", "결재를 완료했읍니다.\n감사합니다.");	
			request.setAttribute("errmsg", "결재를 완료했읍니다.\n감사합니다.");	
		}
		
		// 이건 수정 예정임
 		//new ProductListController().doGet(request, response);
 		new MallHistoryController().doGet(request, response);
	}	
}


