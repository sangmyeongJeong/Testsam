package mypkg.product;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import mypkg.bean.Product;
import mypkg.common.SuperClass;
import mypkg.dao.ProductDao;

public class ProductUpdateController extends SuperClass{
	private Product bean = null ;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int num = Integer.parseInt( request.getParameter("num") ) ;
				
		ProductDao pdao = new ProductDao();
		Product bean = pdao.SelectDataByPk(num) ;
		
		request.setAttribute( "bean", bean );
		String gotopage = null ;
		gotopage = "product/prUpdateForm.jsp" ; 
		super.doGet(request, response);
		super.GotoPage( gotopage );		
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request 영역에 있는 업로드을 위한 객체 multi 정보를 읽어 들인다.
		MultipartRequest multi = (MultipartRequest)request.getAttribute("multi") ;
		
		bean  = new Product();
		bean.setCategory( multi.getParameter("category") );  
		bean.setCompany( multi.getParameter("company") );
		bean.setContents( multi.getParameter("contents") );		
		bean.setInputdate( multi.getParameter("inputdate") );
		bean.setName( multi.getParameter("name") );
		
		bean.setImage( multi.getFilesystemName("image") );
		
		if( multi.getParameter("num") != null && multi.getParameter("num").equals("") == false){
			bean.setNum( Integer.parseInt( multi.getParameter("num") ));	
		}
		if( multi.getParameter("point") != null && multi.getParameter("point").equals("") == false){
			bean.setPoint( Integer.parseInt( multi.getParameter("point") ));	
		}
		if( multi.getParameter("price") != null && multi.getParameter("price").equals("") == false){
			bean.setPrice( Integer.parseInt( multi.getParameter("price") ));	
		}
		if( multi.getParameter("stock") != null && multi.getParameter("stock").equals("") == false){
			bean.setStock( Integer.parseInt( multi.getParameter("stock") ));	
		}				
				
		System.out.println( bean );		
	 
		String gotopage = "";
		if ( this.validate( request ) == false ) {
			System.out.println("수정 문제 있음");			  
			request.setAttribute("bean", bean);
			gotopage = "product/prUpdateForm.jsp" ;
			super.doPost(request, response);
			super.GotoPage( gotopage );		
		}else{
			ProductDao pdao = new ProductDao();			
			int cnt = -999999 ; 			
			cnt = pdao.UpdateData(bean) ;
			new ProductListController().doGet(request, response);
		}
	}
	@Override
	public boolean validate(HttpServletRequest request) {	
		boolean isCheck = true ; //기본 값으로 true이고, 유효성 검사에 문제가 생기면 false으로 변경
		
		if( bean.getName().length() < 3 || bean.getName().length() > 15 ){
			request.setAttribute( super.PREFIX + "name", "상품 이름은 3자리 이상 15자리 이하이어야 합니다.");
			isCheck = false  ;
		}
		
		if( bean.getCompany().length() < 3 || bean.getCompany().length() > 30 ){
			request.setAttribute( super.PREFIX + "company", "제조 회사 이름은 3자리 이상 30자리 이하이어야 합니다.");
			isCheck = false  ;
		}
		String regex = "\\d{4}[-/]\\d{2}[-/]\\d{2}" ;
		if( bean.getInputdate() == null){
			bean.setInputdate( "" );
		}
		boolean result = Pattern.matches(regex, bean.getInputdate());
		if (result == false ) {
			request.setAttribute( super.PREFIX + "inputdate", "입고 일자는 yyyy/MM/dd 또는 yyyy-MM-dd 형식으로 입력해 주세요.");
			isCheck = false  ;
		}	
		if( bean.getImage() == null || bean.getImage() == "" ){
			request.setAttribute( super.PREFIX + "image", "이미지는 필수 입력 사항입니다.");
			isCheck = false  ;
		}		
		int stock = 10 ;
		if( bean.getStock() < stock ){
			request.setAttribute( super.PREFIX + "stock", "재고 수량은 최소 " + stock + "개 이상입니다.");
			isCheck = false  ;
		}		
		if( bean.getPoint() < 5 || bean.getPoint() > 10 ){
			request.setAttribute( super.PREFIX + "point", "포인트는 최소 5 이상 10 이하로 입력 하셔야 합니다.");
			isCheck = false  ;
		}
		if( bean.getContents().length() < 5 || bean.getContents().length() > 255 ){
			request.setAttribute( super.PREFIX + "contents", "상품 설명은 5자리 이상 255자리 이하이어야 합니다.");
			isCheck = false  ;
		} 
		if( bean.getCategory().equals("-") == true ){
			request.setAttribute( super.PREFIX + "category", "상품 카테고리를 선택해 주셔야 합니다.");
			isCheck = false  ;
		} 
		return isCheck ;
	}
}
