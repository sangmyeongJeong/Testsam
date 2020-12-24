package mypkg.composite;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Combo01;
import mypkg.bean.Combo03;
import mypkg.common.SuperClass;
import mypkg.dao.CompositeDao;

public class View03Controller extends SuperClass {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		CompositeDao dao = new CompositeDao();
		
		List<Combo03> lists = dao.View03() ;
		
		request.setAttribute("lists", lists);
		
		String gotopage = "view/View03.jsp" ;
		super.GotoPage(gotopage);
	}
}





