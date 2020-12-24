package mypkg.common;

import java.io.IOException;
import java.rmi.ServerException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
		//System.out.println("휴 여기까지 왔군요.");
		String gotopage = "/common/main.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(gotopage);
		dispatcher.forward(request, response);
	}
}