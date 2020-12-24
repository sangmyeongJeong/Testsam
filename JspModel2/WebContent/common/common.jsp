<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- whologin 변수는 로그인 상태를 저장하고 있는 변수입니다. -->
<c:set var="whologin" value="0" />

<c:if test="${empty sessionScope.loginfo}">
	<!-- 로그인 하지 않은 경우 -->
	<c:set var="whologin" value="0" />
</c:if>
<c:if test="${not empty sessionScope.loginfo}">
	<c:if test="${sessionScope.loginfo.id == 'admin'}">
		<!-- 관리자로 로그인한 경우 -->
		<c:set var="whologin" value="2" />
	</c:if>	
	<c:if test="${sessionScope.loginfo.id != 'admin'}">
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
	String mappingName = "/Shopping"; //서블릿에 정의되어 있슴
	
	//폼 태그에서 사용할 변수
	YesForm = contextPath + mappingName ;
	
	//폼이 아닌 곳에서 사용할 변수
	NoForm = contextPath + mappingName + "?command=" ;
%>

<%	
	// 파일 업로드 관련
	String myurl = request.getRequestURL().toString() ;
	String uri = request.getRequestURI() ;
	int idx = myurl.indexOf( uri ) ;	
	//웹서버에 올릴 이미지의 저장 경로 
	String uploadPath = "/upload" ;//개발자가 임의 지정 가능
	String uploadedFolder 
		= myurl.substring(0, idx) + contextPath + uploadPath ;	
	String realPath = application.getRealPath( uploadPath ) ;
%>
<%
	/* out.print( "YesForm : " + YesForm + "<br>") ;
	out.print( "NoForm : " + NoForm + "<br>") ;
	out.print( "myurl : " + myurl + "<br>") ;
	out.print( "uploadedFolder : " + uploadedFolder + "<br>") ;
	out.print( "realPath : " + realPath + "<br>") ; */ 
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

<!DOCTYPE>
<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<style type="text/css">
		.err{
			font-size : 10pt;
			color:red;
			font-weight: bolder;
		}	
	</style>	
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">미니 쇼핑몰</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="<%=contextPath%>/main.jsp">Home</a></li>
					
					<li>
						<a href="#" class="dropdown-toggle"> 
							<font color='white'>
								<c:if test="${whologin == 0}">
									미로그인
								</c:if> 
								<c:if test="${whologin != 0}">
									${sessionScope.loginfo.name}(${sessionScope.loginfo.id}) 님
								</c:if>
							</font>
						</a>
					</li>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">회원<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><c:if test="${whologin == 0}">
									<a href="<%=NoForm%>meInsert">회원 가입</a>
								</c:if> <c:if test="${whologin != 0}">
									<a href="<%=NoForm%>meUpdate&id=${sessionScope.loginfo.id}">회원 정보 수정</a>
								</c:if></li>
							<li><c:if test="${whologin == 0}">
									<a href="<%=NoForm%>meLogin">로그인</a>
								</c:if> <c:if test="${whologin != 0}">
									<a href="<%=NoForm%>meLogout">로그 아웃</a>
								</c:if></li>
							<li><c:if test="${whologin == 1}">
									<a href="<%=NoForm%>meDelete&id=${sessionScope.loginfo.id}">회원 탈퇴</a>
								</c:if></li>
							<li><c:if test="${whologin == 2}">
									<a href="<%=NoForm%>meList">회원 목록 보기</a>
								</c:if></li>
							<li><c:if test="${whologin == 1}">
									<a href="<%=NoForm%>meDetailView&id=${sessionScope.loginfo.id}">회원 상세 보기</a>
								</c:if></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">게시물<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><c:if test="${whologin != 0}">
									<a href="<%=NoForm%>boInsert">게시물 등록</a>
								</c:if></li>
							<li><c:if test="${whologin != 0}">
									<a href="<%=NoForm%>boList">목록 보기</a>
								</c:if></li>
							<!-- <li><a href="#">-- 아래 항목들은 어떻게 해야 하나??</a></li>
							<li><a href="#">게시물 수정</a></li>
							<li><a href="#">게시물 삭제</a></li>
							<li><a href="#">상세 보기</a></li>
							<li><a href="#">답글 작성</a></li> -->
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">상품<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><c:if test="${whologin == 2}">
									<a href="<%=NoForm%>prInsert">상품 등록</a>
								</c:if></li>
							<li><a href="<%=NoForm%>prList">목록 보기</a></li>
							<!-- <li><a href="#">-- 아래 항목들은 어떻게 해야 하나??</a></li>
							<li><a href="#">목록 삭제</a></li>
							<li><a href="#">정보 수정</a></li>
							<li><a href="./../example/detailViewEx01.jsp">상세 보기</a></li> -->
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">쇼핑몰<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><c:if test="${whologin != 0}">
									<a href="<%=NoForm%>mallHistory">나의 쇼핑 내역</a>
								</c:if></li>
							<li><c:if test="${whologin != 0}">
									<a href="<%=NoForm%>mallList">장바구니 보기</a>
								</c:if></li>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">데이터 보기<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="<%=NoForm%>viewExam01">회원과 게시물</a></li>
							<li><a href="<%=NoForm%>viewExam02">회원별 게시물 건수</a></li>
							<li><a href="<%=NoForm%>viewExam03">주문 정보</a></li>
							<li><a href="<%=NoForm%>viewExam04">고객별 매출 총액</a></li>
							<li><a href="<%=NoForm%>viewExam05">회원별 주문 건수</a></li>
							
						</ul></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><c:if test="${empty sessionScope.loginfo}">
							<a href="<%=NoForm%>meLogin"><span
								class="glyphicon glyphicon-log-in"> 로그인 </span> </a>
						</c:if> <c:if test="${not empty sessionScope.loginfo}">
							<a href="<%=NoForm%>meLogout"><span
								class="glyphicon glyphicon-log-in"> 로그 아웃 </span> </a>
						</c:if></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<c:if test="${not empty requestScope.errmsg}">
		<div class="alert alert-danger alert-dismissable">
    		<a href="#" id="myalert" class="close" data-dismiss="alert" aria-label="close">닫기</a>
    		<strong>${requestScope.errmsg}</strong>
		</div>
	</c:if>
	
	<c:if test="${not empty sessionScope.message}">
		<script type="text/javascript">
			alert('${sessionScope.message}') ;	 
		</script>
		<% session.removeAttribute("message") ; %>
	</c:if>		
</body>
</html>