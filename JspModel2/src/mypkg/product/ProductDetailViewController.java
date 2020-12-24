package mypkg.product;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Product;
import mypkg.common.SuperClass;
import mypkg.dao.ProductDao;

public class ProductDetailViewController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int num = Integer.parseInt( request.getParameter("num") ) ;
		ProductDao pdao = new ProductDao(); 
		
		Product bean  = pdao.SelectDataByPk(num);
		
		String gotopage = "" ; 
		if( bean != null){ //상세 보기로 이동			 
			request.setAttribute("bean", bean);
			gotopage = "/product/prDetailView.jsp";		 
		}else{
			gotopage = "/product/prList.jsp";		 
		}		
		
		super.doGet(request, response);		 
		super.GotoPage( gotopage ); 	
	}
}