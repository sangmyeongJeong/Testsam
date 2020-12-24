<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BootStrap Sample</title>
<style type="text/css">
/*.panel-body{ margin-bottom: -30px;}*/
.form-group {
	margin-bottom: 3px;
}

.form-control {
	height: 25px;
}
</style>
</head>
<%
	int myoffset = 2; //전체 외관의 옵셋
	int mywidth = twelve - 2 * myoffset;
	int leftside = 4; //판넬의 좌측
	int rightside = twelve - leftside; //판넬의 우측
%>
<body>
	<div class="container col-sm-offset-<%=myoffset%> col-sm-<%=mywidth%>">
		<div class="panel panel-default panel-primary">
			<div class="panel-heading">
				<h1 class="panel-title" align="left">상품 상세 보기</h1>
			</div>
			<div class="panel-body">
				<div class="col-sm-<%=leftside%> col-sm-<%=leftside%>">
					<table>
						<tr>
							<td>
								<c:if test="${empty bean.image}">
									<img src="<%=uploadedFolder%>Koala.jpg" class="img-thumbnail"
										width="200" height="200" alt="no image">
								</c:if>						
								
								<c:if test="${applicationScope.debugMode == true}">
									디버그 모드가 true이면 보입니다.<br>
									${applicationScope.uploadedPath}/${bean.image}
								</c:if>
								
								<c:if test="${not empty bean.image}">
									<img src="${applicationScope.uploadedPath}/${bean.image}"
										class="img-thumbnail" width="200" height="200"
										alt="${bean.image}">
								</c:if>
							</td>
						</tr>
					</table>
				</div>
				<div class="col-sm-<%=rightside%> col-sm-<%=rightside%>">
					<table class="table table-hover table-condensed">
						<tr>
							<td width="25%" align="center">상품명(번호)</td>
							<td width="75%" align="left">${bean.name}(${bean.num})</td>
						</tr>
						<tr>
							<td width="25%" align="center">제조 회사</td>
							<td width="75%" align="left">${bean.company}</td>
						</tr>
						<tr>
							<td width="25%" align="center">재고 수량</td>
							<td width="75%" align="left">${bean.stock}</td>
						</tr>
						<tr>
							<td width="25%" align="center">가격</td>
							<td width="75%" align="left">${bean.price}</td>
						</tr>
						<tr>
							<td width="25%" align="center">설명</td>
							<td width="75%" align="left">
								${bean.contents}
							</td>
						</tr>
						
						
						<tr>
							<td width="25%" align="center">주문 수량</td>
							<td width="75%" align="left">
								<form class="form-inline" role="form" method="post" action="<%=YesForm%>">
									<div class="form-group">
										<input type="hidden" name="command" value="mallInsert">
										<input type="hidden" name="num" value="${bean.num}">
										<input type="hidden" name="stock" value="${bean.stock}">
										<input type="number" class="form-control" name="qty" id="qty"
											data-toggle="popover" title="수량 입력란"
											data-trigger="hover" data-placement="auto top"
											data-content="구매하시고자 하는 수량을 정수로 입력하세요^^">          
									</div>
									<button type="submit" class="btn btn-xs btn-default">주문</button>
								</form>
							</td>
						</tr>
						
						
						<tr>
							<td width="25%" align="center">포인트</td>
							<td width="75%" align="left">${bean.point}</td>
						</tr>
						<tr>
							<td width="25%" align="center">입고 일자</td>
							<td width="75%" align="left">${bean.inputdate}</td>
						</tr>
					</table>
				</div>
				<hr>
				<div class="col-sm-offset-5 col-sm-4">
					<button class="btn btn-primary" onclick="history.back();">
						돌아 가기</button>
				</div>
			</div>
			<!-- end panel-body -->
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$('[data-toggle="popover"]').popover();
		});
	</script>
</body>
</html>