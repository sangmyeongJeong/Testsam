package mypkg.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.common.SuperClass;
import mypkg.dao.BoardDao;

public class BoardDeleteController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no")) ;
		
		BoardDao dao = new BoardDao();
		
		int cnt = -999999 ;
		cnt = dao.DeletaData(no) ;
		
		new BoardListController().doGet(request, response); 
	}	
}