package mypkg.mall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Product;
import mypkg.common.SuperClass;
import mypkg.dao.ProductDao;
import mypkg.member.MemberLoginController;
import mypkg.product.ProductListController;
import mypkg.shopping.MyCartList;
import mypkg.shopping.ShoppingInfo;

// 장바구니 목록 보기 기능입니다.
public class MallListController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		String message = "" ;
		
		if (super.session.getAttribute("loginfo") == null) {
			message = "로그인이 필요합니다." ;
			super.setErrorMessage(message);
			new MemberLoginController().doGet(request, response);			
		} else {
			MyCartList mycart = (MyCartList)super.session.getAttribute("mycart") ;
			if (mycart==null) {
				message = "쇼핑 내역이 없어서 상품 목록으로 이동했습니다." ;
				super.setErrorMessage(message);
				 
				new ProductListController().doGet(request, response); 
			}
			
			Map<Integer, Integer> maplists = mycart.GetAllOrderLists() ;
			
			// keylist : 구매하고자 하는 상품 번호를 저장하고 있는 Set 자료 구조
			Set<Integer> keylist = maplists.keySet() ;
			
			// ShoppingInfo : 상품 1건에 대한 정보를 저장하고 있는 Bean 객체
			// shoplists : ShoppingInfo 객체들을 저장하고 있는 컬렉션 객체
			List<ShoppingInfo> shoplists = new ArrayList<ShoppingInfo>();
			
			int totalAmount = 0 ; // 총 판매 금액
			int totalPoint = 0 ; // 총 누적 포인트
			
			for(Integer pnum : keylist){ // pnum : 상품 번호
				Integer qty = maplists.get(pnum) ; // 구매 수량
				
				ProductDao pdao = new ProductDao();
				
				// 상품 번호 pnum에 대한 Bean 정보
				Product bean = pdao.SelectDataByPk(pnum) ;
				
				int point = bean.getPoint() ;
				int price = bean.getPrice() ;
				
				totalAmount += qty * price ;
				totalPoint += qty * point ;
												
				ShoppingInfo shopinfo = new ShoppingInfo() ;
				
				shopinfo.setImage(bean.getImage());
				shopinfo.setPname(bean.getName()); 
				shopinfo.setPnum(pnum);
				shopinfo.setPoint(point); 
				shopinfo.setPrice(price); 
				shopinfo.setQty(qty);  
				
				shoplists.add(shopinfo) ;
			}
			
			// 이번에 구매할 총 목록
			super.session.setAttribute("shoplists", shoplists);
			super.session.setAttribute("totalAmount", totalAmount);
			super.session.setAttribute("totalPoint", totalPoint);			
		}
		
		String gotopage = "mall/MallList.jsp" ;
		super.GotoPage(gotopage);
	}
}