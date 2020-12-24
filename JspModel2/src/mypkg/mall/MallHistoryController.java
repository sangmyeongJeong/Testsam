package mypkg.mall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.common.SuperClass;
import mypkg.dao.MallDao;
import mypkg.member.MemberLoginController;
import mypkg.bean.Member;
import mypkg.bean.Order;

public class MallHistoryController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		Member loginfo = (Member)super.session.getAttribute("loginfo") ;
		
		if( loginfo == null ){  //미로그인 시
			//session.setAttribute("destination", "redirect:/Order.mall");
			new MemberLoginController().doGet(request, response);  
		}else{ 
			//orderlists : 로그인 된 사람의 이전 쇼핑 내역을 저장하고 있는 컬렉션
			List<Order> orderlists = new ArrayList<Order>() ;
			
			MallDao mdao = new MallDao() ;
			
			// lists : 현재 로그인 한 사람의 쇼핑 주문 내역들을 담고 있는 컬렉션(최근 주문 내역이 먼저 나옴)
			List<Order> lists = mdao.OrderMall(loginfo.getId()) ;
				
			request.setAttribute("lists", lists);
			
			if(lists.size() == 0) {
				request.setAttribute("errmsg", "이전 쇼핑 내역이 존재하지 않습니다.");	
			}
			
			String gotopage = "mall/ShopList.jsp";  
			super.GotoPage(gotopage);	
		}			
	}
}