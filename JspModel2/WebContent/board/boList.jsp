<%@page import="mypkg.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp"%>
<%
	int myoffset = 2;
	int mywidth = twelve - 2 * myoffset;
	int formleft = 3;
	int formright = twelve - formleft;
	int mysearch = 2;
	//int label = 3 ; //양식의 왼쪽에 보여지는 라벨의 너비 
	//int content = twelve - label ; //우측의 내용 입력(input, select, textarea)의 너비
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BootStrap Sample</title>
	<style type="text/css">
		.xxx {
			margin-left: 0px;
		}
		.re{font-size: 11px;}
	</style>
	<script type="text/javascript">
		function writeForm(){
				location.href='<%=NoForm%>boInsert';
		}
		function search(){
			if( $('#mode').val() == 'all' ){
				alert('검색 목록을 선택해주세요') ;
				//$('#mode').focus();
			}else{
				//alert('하하') ;
			}
			//alert( $('#mode').val() );
		}
		function searchAll(){
			//$('#mode').val('-');
			//$('#keyword').val('');
			location.href='<%=NoForm%>boList';
		}
	</script>	
</head>
<body>
	<div class="container col-sm-offset-<%=myoffset%> col-sm-<%=mywidth%>">
		<div class="panel panel-default panel-primary">
			<div class="panel-heading">
				<form class="form-inline" role="form">
					<h2>게시물 목록</h2>
				</form>
			</div>
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<!-- <th>글 번호</th> -->
						<th>제목</th>
						<th>작성자</th>						
						<th>비밀 번호</th>
						<th>글 내용</th>
						<th>조회수</th>
						<th>작성 일자</th>
						<th>수정</th>
						<th>삭제</th>
						<th>답글</th>
						<th>비고</th>
					</tr>
				</thead>
				<tr>
					<td colspan="10" align="center">
						<form class="form-inline" role="form" name="myform" action="<%=YesForm%>" method="get">
							<input type="hidden" name="command" value="boList">
							<div class="form-group">
								<select class="form-control" name="mode" id="mode">
									<option value="all" selected="selected">-- 선택하세요---------
									<option value="writer" >작성자
									<option value="subject" >제목									
									<option value="content" >글 내용									
								</select>
							</div>
							<div class="form-group">
								<input type="text" class="form-control btn-xs" name="keyword"
									id="keyword" placeholder="검색 키워드">
							</div>
							<button class="btn btn-default btn-warning" type="submit" onclick="search();">검색</button>
							<button class="btn btn-default btn-warning" type="button" onclick="searchAll();">전체 검색</button>
							<button class="btn btn-default btn-info" type="button"
								onclick="writeForm();">글 쓰기</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<p class="form-control-static">${requestScope.pagingStatus}</p>
						</form>
					</td>
				</tr>
				<c:forEach var="bean" items="${requestScope.lists}">
					<tr>
						<%-- <td>${bean.no}</td> --%>						
						<td>
							<c:forEach var="cnt" begin="1" end="${bean.depth}">
								<span class="badge re">re</span>
							</c:forEach>
							<a href="<%=NoForm%>boDetailView&no=${bean.no}&${requestScope.parameters}">
								${bean.subject}
							</a>
						</td>
						<td>${bean.writer}</td>
						<td>${bean.password}</td>
						<td>${bean.content}</td>
						<td>${bean.readhit}</td>
						<td>${bean.regdate}</td>
						<td>
							<c:if test="${sessionScope.loginfo.id == bean.writer}">
								<a href="<%=NoForm%>boUpdate&no=${bean.no}&${requestScope.parameters}">
									수정
								</a>
							</c:if>
							<c:if test="${sessionScope.loginfo.id != bean.writer}">
								수정
							</c:if>
						</td>
						<td>
							<c:if test="${sessionScope.loginfo.id == bean.writer}">
								<a href="<%=NoForm%>boDelete&no=${bean.no}&${requestScope.parameters}">
									삭제
								</a>
							</c:if>
							<c:if test="${sessionScope.loginfo.id != bean.writer}">
								삭제
							</c:if>
						</td>
						<td>
							<c:if test="${bean.depth <3 }">
								<a href="<%=NoForm%>boReply&no=${bean.no}&${requestScope.parameters}&groupno=${bean.groupno}&orderno=${bean.orderno}&depth=${bean.depth}">
									답글 
								</a>
							</c:if>
							<c:if test="${bean.depth >= 3 }">
								답글
							</c:if>
						</td>
						<td>
							${bean.remark}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div align="center">
			<footer>${requestScope.pagingHtml}</footer>			
		</div>		
	</div>
	<br><br><br><br>
	<script type="text/javascript">
	   /* 방금 전 선택한 콤보 박스를 그대로 보여 주기 */ 
		$('#mode option').each(function (index){
			if( $(this).val() == '${requestScope.mode}' ){
				$(this).attr('selected', 'selected') ;
			}
		});	
		/* 이전에 넣었던 값 그대로 보존 */
		$('#keyword').val( '${requestScope.keyword}' ) ;		
	</script>	
</body>
</html>