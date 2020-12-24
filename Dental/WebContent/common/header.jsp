<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- whologin 변수는 로그인 상태를 저장하고 있는 변수입니다. -->
<c:set var="whologin" value="0" />
<c:if test="${ empty sessionScope.loginfo}">
	<!-- 로그인 하지 않은 경우 -->
	<c:set var="whologin" value="0" />
</c:if>
<c:if test="${ not empty sessionScope.loginfo}">
	<c:if test="${ sessionScope.loginfo.id == 'admin'}">
		<!-- 관리자로 로그인한 경우 -->
		<c:set var="whologin" value="2" />
	</c:if>
	<c:if test="${ sessionScope.loginfo.id != 'admin'}">
		<!-- 일반 사용자로 로그인한 경우 -->
		<c:set var="whologin" value="1" />
	</c:if>
</c:if>

<!-- 부트 스트랩 -->
<% int twelve = 12 ; %>
<c:set var="twelve" value="12" />
<%!
	String YesForm = null ;
	String NoForm = null ;
%>
<%
	String contextPath = request.getContextPath() ;
	String mappingName = "/tooth"; //서블릿에 정의 되어 있음
	//폼 태그에서 사용할 변수
	YesForm = contextPath + mappingName ;
	//폼이 아닌 곳에서 사용할 변수
	NoForm = contextPath + mappingName + "?command=" ;		
%>
<%	
	//파일 업로드 관련
	String myurl = request.getRequestURL().toString() ;
	String uri = request.getRequestURI() ;
	int idx = myurl.indexOf( uri ) ;	
	//웹서버에 올릴 이미지의 저장 경로 
	String uploadPath = "/upload" ;//개발자가 임의 지정 가능
	String uploadedFolder 
		= myurl.substring(0, idx) + contextPath + uploadPath ;	
	String realPath = application.getRealPath( uploadPath ) ;
%>
<%!
	String MakeCommand(String ... args){
		if( args.length == 0 ){
			return YesForm  ;
		}else if( args.length == 1 ){
			return YesForm + "?command=" + args[0]   ;	
		}else{
			String imsi = args[1] ;
			return YesForm + "?command=" + args[0] + "&" + imsi  ;
		}
	}
%>

<c:set var="contextPath" value="<%=contextPath%>" scope="application"/>
	
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Agency - Start Bootstrap Theme</title>
        <link rel="icon" type="image/x-icon" href="${contextPath}/assets/img/favicon.ico" />
        <!-- Font Awesome icons (free version)-->
        <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="${contextPath}/css/styles.css" rel="stylesheet" />
    </head>
    <body id="page-top">

        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
            <div class="container">
                <a class="navbar-brand js-scroll-trigger" href="#page-top"><img src="${contextPath}/assets/img/logo01.png" alt="로고" height="50" width="100"/></a>
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarClick" aria-controls="navbarClick" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars ml-1"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarClick">
                    <ul class="navbar-nav text-uppercase ml-auto">
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#">병원 소개</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#">진료 소개</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#">온라인상품</a></li>
                        <li class="nav-item"><a class="nav-link js-scroll-trigger" href="#">커뮤니티</a></li>
                    	<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">로그인</a>
							<ul class="dropdown-menu">
								<li><c:if test="${whologin == 0}">
										<a class="menuList nav-link" href="<%=NoForm%>meInsert">회원 가입</a>
									</c:if> <c:if test="${whologin != 0}">
										<a class="menuList nav-link" href="<%=NoForm%>meUpdate&id=${sessionScope.loginfo.id}">회원 정보 수정</a>
									</c:if></li>
								<li><c:if test="${whologin == 0}">
										<a class="menuList nav-link" href="<%=NoForm%>meLogin">로그인</a>
									</c:if> <c:if test="${whologin != 0}">
										<a class="menuList nav-link" href="<%=NoForm%>meLogout">로그 아웃</a>
									</c:if></li>
								<li><c:if test="${whologin == 1}">
										<a class="menuList nav-link" href="<%=NoForm%>meDelete&id=${sessionScope.loginfo.id}">회원 탈퇴</a>
									</c:if></li>
								<li><c:if test="${whologin == 2}">
										<a class="menuList nav-link" href="<%=NoForm%>meList">회원 목록 보기</a>
									</c:if></li>
								<li><c:if test="${whologin == 1}">
										<a class="menuList nav-link" href="<%=NoForm%>meDetailView&id=${sessionScope.loginfo.id}">회원 상세 보기</a>
									</c:if></li>
							</ul>
						</li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <!-- Bootstrap core JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Third party plugin JS-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
        <!-- Contact form JS-->
        <script src="${contextPath}/assets/mail/jqBootstrapValidation.js"></script>
        <script src="${contextPath}/assets/mail/contact_me.js"></script>
        <!-- Core theme JS-->
        <script src="${contextPath}/js/scripts.js"></script>
    </body>
</html>
