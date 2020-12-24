<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp"%>
<%
	int myoffset = 2;
	int mywidth = twelve - 2 * myoffset;
	int formleft = 3 ;
	int formright = twelve - formleft ; 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BootStrap Sample</title>
</head>
<body>
	<div class="container col-sm-offset-<%=myoffset%> col-sm-<%=mywidth%>">
		<div class="panel panel-default panel-warning">
			<div class="panel-heading"><h4>회원 목록</h4></div>
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>아이디</th>
						<th>이름</th>
						<th>급여</th>
						<th>입사 일자</th>
						<th>성별</th>
						<th>취미</th>
						<th>직업</th>
						<th>주소</th>
						<th>세부 주소</th>
						<th>포인트</th>					
						<th>삭제</th>
						<th>수정</th>
					</tr>
				</thead>
				<c:forEach var="bean" items="${requestScope.lists}">
					<tr>
						<td>${bean.id}</td>
						<td>
							<a href="<%=NoForm%>meDetailView&id=${bean.id}">                     
								${bean.name}
							</a>
						</td>
						<td>${bean.salary}</td>
						<td>${bean.hiredate}</td>
						<td>${bean.gender}</td>
						<td>${bean.hobby}</td>
						<td>${bean.job}</td>
						<td>${bean.address1}</td>
						<td>${bean.address2}</td>
						<td>${bean.mpoint}</td>
						<td>
							<c:if test="${bean.id == 'admin'}">
								삭제
							</c:if>
							<c:if test="${bean.id != 'admin'}">
								<a href="<%=NoForm%>meDelete&id=${bean.id}">
									삭제
								</a>
							</c:if>
						</td>
						<td>
							<a href="<%=NoForm%>meUpdateForm&id=${bean.id}">
								수정
							</a>
						</td> 
					</tr>
				</c:forEach>				
			</table>
		</div>
		<div align="center">
			<footer>${requestScope.pagingHtml}</footer>	
		</div>		
	</div>
</body>
</html>