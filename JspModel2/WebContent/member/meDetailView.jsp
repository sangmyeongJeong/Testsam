<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="./../common/common.jsp"%>
<%
	int offset = 2 ; //오프 셋 
	int content = twelve - 2 * offset ; //12 - 2 * 오프셋	
%>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">	
	<title>BootStrap</title>
	<style type="text/css">
		/*.panel-body{ margin-bottom: -30px;}*/
	</style>
</head>
<body>
	<div class="container col-md-offset-<%=offset%> col-md-<%=content%>" align="right">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h1 class="panel-title" align="left">회원 정보 상세 보기</h1>
			</div>
			<div class="panel-body" align="left">
				<div class="col-md-12">
					<table class="table table-condendes table-striped" >
						<tr>
							<td width="25%">아이디</td>
							<td width="75%">${bean.id}</td>
						</tr>
						<tr>
							<td width="25%">이름</td>
							<td width="75%">${bean.name}</td>
						</tr>		
						<tr>
							<td width="25%">비밀 번호</td>
							<td width="75%">${bean.password}</td>
						</tr>
						<tr>
							<td width="25%">급여</td>
							<td width="75%">${bean.salary}</td>
						</tr>
						<tr>
							<td width="25%">입사 일자</td>
							<td width="75%">${bean.hiredate}</td>
						</tr>
						<tr>
							<td width="25%">성별</td>
							<td width="75%">${bean.gender}</td>
						</tr>
						<tr>
							<td width="25%">취미</td>
							<td width="75%">${bean.hobby}</td>
						</tr>
						<tr>
							<td width="25%">직업</td>
							<td width="75%">${bean.job}</td>
						</tr>	
						<tr>
							<td width="25%">우편 번호</td>
							<td width="75%">${bean.zipcode}</td>
						</tr>
						<tr>
							<td width="25%">주소</td>
							<td width="75%">${bean.address1}</td>
						</tr>
						<tr>
							<td width="25%">세부 주소</td>
							<td width="75%">${bean.address2}</td>
						</tr>
						<tr>
							<td width="25%">포인트</td>
							<td width="75%">${bean.mpoint}</td>
						</tr>	
					</table>				
				</div>
				<hr>
				<div class="col-md-offset-5 col-md-4">
					<button class="btn btn-primary" onclick="history.back();">돌아 가기</button>
				</div>
			</div><!-- end panel-body -->
		</div>		
	</div>		
</body>
</html>