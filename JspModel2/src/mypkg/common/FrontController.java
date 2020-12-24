package mypkg.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import mypkg.utility.Myutility;


// debugMode 파마리터는 디버그를 위한 변수입니다.
// 실제 배포시에는 false 값으로 배포해야 합니다.
// 임시 테스트용 코드를 사용하고자 하는 경우 true으로 지정하고 사용하도록 합니다.
@WebServlet(urlPatterns = {"/Shopping"},
		initParams = {
				@WebInitParam(name="configFile", value="/WEB-INF/todolist.txt"),
				@WebInitParam(name="debugMode", value="false")
		})
public class FrontController extends HttpServlet {	
	// todolist : 우리가 명시한 모든 작업 목록을 저장하고 있는 맵 구조
	private Map<String, SuperController> todolist 
		= new HashMap<String, SuperController>();
	
	private ServletContext application = null;

	//업로드가 되는 실제 경로
	private String uploadedPath = null ; 
	
	@Override
	public void init(ServletConfig config) throws ServletException {		
		String configFile = config.getInitParameter("configFile") ;
		System.out.println("설정 파일 이름 : " + configFile);
		
		String debugMode = config.getInitParameter("debugMode") ;
		
		this.application = config.getServletContext() ;
		
		// 실제 웹 서버 상에 존재하는 설정 파일 이름
		String configFilePath = this.application.getRealPath(configFile);
		this.todolist = Myutility.getActionMapList(configFilePath) ;
		
		this.application.setAttribute("debugMode", debugMode);
		
		// 업로드 폴더가 'upload'라고 가정했습니다.
		this.uploadedPath = this.application.getRealPath("/upload") ;
		
		this.application.setAttribute("uploadedPath", uploadedPath);
		System.out.println("파일 업로드 경로 : \n" + uploadedPath);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command") ;
		System.out.println("커맨드 파라미터 : " + command);
		
		if (command==null) {
			System.out.println("파일 업로드를 수행합니다.");
			MultipartRequest multi = Myutility.getMultiPartRequest(request, uploadedPath);
			
			if (multi != null) {
				command = multi.getParameter("command") ;
				
				// delete_image : 이전에 업로드 했던 이미지입니다.
				// 이 파라미터는 상품 수정 페이지에서 넘어 옵니다.
				String delete_image = multi.getParameter("image") ;
				System.out.println("삭제할 이미지 이름 : " + delete_image );
				
				// 삭제될 이미지 전체 경로를 구합니다.
				String delete_file = this.uploadedPath + "/" + delete_image ;
				System.out.println("삭제될 파일 : " + delete_file );
				
				// File 객체를 구합니다.
				File delfile = new File(delete_file) ;
				
				// File 객체의 delete() 메소드를 사용하여 이전에 업로드했던 이미지 파일을 삭제합니다.
				delfile.delete() ; 
				
				// 파일 업로드 객체를 바인딩합니다.
				request.setAttribute("multi", multi); 
			} else {
				System.out.println("MultipartRequest 객체를 구하지 못했습니다.");
			}
		} 
		
		SuperController controller = this.todolist.get(command) ;
		if(controller != null) {
			String method = request.getMethod().toLowerCase();
			if(method.equals("get")) {
				System.out.println(controller.toString() + " get 호출됨");
				controller.doGet(request, response);
			}else {
				System.out.println(controller.toString() + " post 호출됨");
				controller.doPost(request, response);
			}	
		}else {
			System.out.println("command가 널입니다."); 
		}		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doProcess(request, response);
	}	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doProcess(request, response);
	}	
}