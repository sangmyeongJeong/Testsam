package mypkg.mall;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.common.SuperClass;
import mypkg.shopping.MyCartList;

public class MallDeleteController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		String gotopage = "" ;
		
		if (super.session.getAttribute("loginfo") == null) {			
			gotopage = "member/meLoginForm.jsp";
			super.GotoPage(gotopage);
			
		} else {
			MyCartList mycart = (MyCartList)session.getAttribute("mycart") ;
			if ( mycart == null ) {
				mycart = new MyCartList() ;
			}
			
			int pnum = Integer.parseInt(request.getParameter("pnum")) ;			
			
			mycart.DeleteOrder(pnum); 
			session.setAttribute("mycart", mycart); 
			new MallListController().doGet(request, response); 
		}
	}

}