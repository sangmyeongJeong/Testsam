package mypkg.member;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypkg.bean.Member;
import mypkg.common.SuperClass;
import mypkg.dao.MemberDao;

public class MemberInsertController extends SuperClass{
	private Member bean = null ;	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);
		String gotopage = "member/meInsertForm.jsp";		
		super.GotoPage( gotopage );
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		bean  = new Member();
		bean.setAddress1( request.getParameter("address1") );	
		bean.setAddress2( request.getParameter("address2") );
		bean.setGender( request.getParameter("gender") );
		bean.setHiredate( request.getParameter("hiredate") );		
		bean.setId( request.getParameter("id") );
		bean.setJob( request.getParameter("job") );
		if( request.getParameter("mpoint") != null || request.getParameter("mpoint").equals("") == false ){
			bean.setMpoint( Integer.parseInt( request.getParameter("mpoint") ));	
		}
		bean.setName( request.getParameter("name") );
		bean.setPassword( request.getParameter("password") );
		if( request.getParameter("salary") != null || request.getParameter("salary").equals("") == false ){
			bean.setSalary( Integer.parseInt( request.getParameter("salary") ));	
		}
		bean.setZipcode( request.getParameter("zipcode") );
		
		// 선택된 갯수 만큼이 배열로 만들어 지는 데 이것을 멤버 변수에 대입합니다.
		String[] hobby = request.getParameterValues("hobby") ;
		bean.setHobby( hobby );
		
		System.out.println( bean );
		
		String gotopage = "";
		if ( this.validate( request ) == true ) {
			gotopage = "member/meLoginForm.jsp";		
			//DAO 객체를 생성한다.
			MemberDao mdao = new MemberDao();			
			int cnt = -99999 ; 			
			//Bean 객체를 이용하여 해당 게시물을 추가한다.
			cnt = mdao.InsertData(bean) ;
			
			super.session.setAttribute( "message" , "축하합니다. 회원 가입이 되었습니다. 로그인 후 게시물 목록 페이지로 이동하겠습니다." );
			
			// 회원 가입을 성공하면, 바로 로그인되도록 처리해줍니다.
			// doGet() 메소드를 사용하면 안되욧....
			// doPost() 메소드를 호출하여 바로 로그인을 수행합니다.
			new MemberLoginController().doPost(request, response);
		}else{
			gotopage = "member/meInsertForm.jsp";  
			request.setAttribute("bean", bean);
			super.doPost(request, response);
			super.GotoPage( gotopage );
		}		
	}
	@Override
	public boolean validate(HttpServletRequest request) {
		boolean isCheck = true ; //기본 값으로 true이고, 유효성 검사에 문제가 생기면 false으로 변경
		
		if( bean.getId().length() < 4 || bean.getId().length() > 10 ){
			request.setAttribute( super.PREFIX + "id", "아이디는 4자리 이상 10자리 이하이어야 합니다.");
			isCheck = false  ;
		}
		if( bean.getName().length() < 2 || bean.getName().length() > 10 ){
			request.setAttribute( super.PREFIX + "name", "이름은 2자리 이상 10자리 이하이어야 합니다.");
			isCheck = false  ;
		}
		if( bean.getPassword().length() < 4 || bean.getPassword().length() > 10 ){
			request.setAttribute( super.PREFIX + "password", "비밀 번호는 4자리 이상 10자리 이하이어야 합니다.");
			isCheck = false  ;
		}
		if( bean.getGender() == null){
			request.setAttribute( super.PREFIX + "gender", "성별은 반드시 체크가 되어야 합니다.	");
			isCheck = false  ;
		}
		
		String[] arr = bean.getHobby().split(",") ;
		if( arr.length <= 1 ){
			request.setAttribute( super.PREFIX + "hobby", "취미는 최소 2개이상이어야 합니다.");
			isCheck = false  ;
		}		
		int salary = 100 ;
		if( bean.getSalary() < salary ){
			request.setAttribute( super.PREFIX + "salary", "최소 급여는 " + salary + "원 이상입니다.");
			isCheck = false  ;
		}
		if( bean.getJob().equals("-")){ //선택하지 않았을 때의 기본 값이 하이폰(-)이다.
			request.setAttribute( super.PREFIX + "job", "직업을 선택해 주세요.");
			isCheck = false  ;
		}		
		String regex = "\\d{4}[-/]\\d{2}[-/]\\d{2}" ;
		if( bean.getHiredate() == null){
			bean.setHiredate( "" );
		}
		boolean result = Pattern.matches(regex, bean.getHiredate());
		if (result == false ) {
			request.setAttribute( super.PREFIX + "hiredate", "날짜는 yyyy/MM/dd 또는 yyyy-MM-dd 형식으로 입력해 주세요.");
			isCheck = false  ;
		}

		if( bean.getZipcode() == null || bean.getZipcode() == ""){
			request.setAttribute( super.PREFIX + "zipcode", "우편 번호는 필수 입력 사항입니다.");
			isCheck = false  ;
		}
		if( bean.getAddress1() == null || bean.getAddress1() == "" ){
			request.setAttribute( super.PREFIX + "address1", "주소는 필수 입력 사항입니다.");
			isCheck = false  ;
		}
		return isCheck ;
	}
}