package mypkg.composite;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Combo01;
import mypkg.common.SuperClass;
import mypkg.dao.CompositeDao;

public class View01Controller extends SuperClass {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		CompositeDao dao = new CompositeDao();
		
		List<Combo01> lists = dao.View01() ;
		
		request.setAttribute("lists", lists);
		
		String gotopage = "view/View01.jsp" ;
		super.GotoPage(gotopage);
	}
}





