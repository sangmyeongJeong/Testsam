package mypkg.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("메인메소드 doget");
		
		super.doProcess(request, response);
		String gotopage = "/common/main.jsp";
		super.GotoPage(gotopage);
	}
}
