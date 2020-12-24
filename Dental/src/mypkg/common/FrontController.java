package mypkg.common;

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

import mypkg.utility.Myutility;

@WebServlet(urlPatterns = {"/tooth"},
initParams = {
		@WebInitParam(name="configFile", value="/WEB-INF/todolist.txt"),
		@WebInitParam(name="debugMode", value="false")
})

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, SuperController> todolist
	= new HashMap<String, SuperController>();
	
	private ServletContext application = null;
  
    public FrontController() {
       
    }

	public void init(ServletConfig config) throws ServletException {
		//todolist : 맵 형식으로 불러오기
		String configFile = config.getInitParameter("configFile");
		System.out.println("init파라미터 값 : " + configFile);
		
		String debugMode = config.getInitParameter("debugMode") ;
		
		this.application = config.getServletContext() ;
		
		// 실제 웹 서버 상에 존재하는 설정 파일 이름
		String configFilePath = this.application.getRealPath(configFile);
		this.todolist = Myutility.getActionMapList(configFilePath) ;
		
		this.application.setAttribute("debugMode", debugMode);
		
	}

	protected void doprocess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		System.out.println("커맨드 파라미터 : " + command);
		
		SuperController controller = this.todolist.get(command);
		
		if (controller != null) {
			String method = request.getMethod().toLowerCase();
			if (method.equals("get")) {
				System.out.println(controller.toString() + " get 호출됨");
				controller.doGet(request, response);
			}else {
				System.out.println(controller.toString() + " post 호출됨");
				controller.doPost(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doprocess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doprocess(request, response);
	}

	

}
