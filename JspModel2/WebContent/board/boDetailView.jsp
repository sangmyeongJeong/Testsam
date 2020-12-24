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


		/* 댓글들을 위한 스타일 지정 */
		* {
			padding: 0;
			margin: 0;
			color: #333;
		}
		ul { list-style: none; }
		#container { padding: 30px 20px; }
		#comment_write {
			padding: 20px 15px;
			border-bottom: 1px solid #7BAEB5;
		}

		#comment_write label {
			display: inline-block;
			width: 80px;
			font-size: 14px;
			font-weight: bold;
			margin-bottom: 10px;
		}

		#comment_write input[type='text'], #comment_write textarea {
			border: 1px solid #ccc;
			vertical-align: middle;
			padding: 3px 10px;
			font-size: 12px;
			line-height: 150%;
		}

		#comment_write textarea {
			width: 450px;
			height: 120px ;
		}

		.comment_item {
			font-size: 13px;
			color: #333;
			padding: 15px;
			border-bottom: 1px dotted #ccc;
			line-height: 150%;
		}

		.comment_item .writer {
			color: #555;
			line-height: 200%;
		}

		.comment_item .writer input {
			vertical-align: middle;
		}

		.comment_item .writer .name {
			color: #222;
			font-weight: bold;
			font-size: 14px;
		}
		
		.form-group {
			margin-bottom: 3px;
		}
		
		.form-control {
			height: 25px;
		}
	</style>
	<script type="text/javascript">
		function gotoBack(){
			location.href='<%=NoForm%>boList&${requestScope.parameters}';
			//alert('${requestScope.parameter}') ;
		}
		
		function addNewItem(cnum, writer, content, regdate) {
			/* 댓글 1개를 추가해 주는 함수 */
			var litag = $("<li>"); // 새로운 글이 추가될 li태그 객체
			litag.addClass("comment_item");
			
			var ptag = $("<p>");// 작성자 정보가 지정될 <p>태그
			ptag.addClass("writer");
			
			var spantag = $("<span>");// 작성자 정보의 이름
			spantag.addClass("name");
			spantag.html(writer + "님");
			
			var spandate = $("<span>");// 작성 일시
			spandate.html("&nbsp;&nbsp;/&nbsp;&nbsp;" + regdate + " ");
			
			var inputtag = $("<input>");// 삭제하기 버튼
			inputtag.attr({"class" : "btn btn-default btn-xs", "type" : "button", "value" : "삭제하기", "pmkey" : cnum});
			inputtag.addClass("delete_btn");
	
			var content_p = $("<p>");// 내용
			content_p.html( content );
	
			// 조립하기
			ptag.append(spantag).append(spandate).append(inputtag);
			litag.append(ptag).append(content_p);
			
			$("#comment_list").append(litag);
		}
		
		function getListComment(){ /* 댓글 목록을 읽어 온다. */
			$("#comment_list").empty() ;
			$.ajax({ /* 유효성 검사를 통과 했을 때 Ajax 함수 호출 */
	            url: '<%=MakeCommand("list.com")%>',
	            data : 'no=' + '${bean.no}', 
	    		type : "get",             
	            dataType: "json",
	            success: function (obj, textStatus) {
		           	/* var obj = JSON.parse(data); */
		           	 
					$.each(obj, function (idx) {
		           		 var cnum = obj[idx].cnum ;	 
		           		 var writer = obj[idx].writer ;
		           		 var content = obj[idx].content ;	 
		           		 var regdate = obj[idx].regdate ;
		           		 addNewItem(cnum, writer, content, regdate);
		           	});
	            }
	        });
		}
		
		/** 삭제 버튼 클릭시에 항목 삭제하도록 "미리" 지정 */
		$(document).on("click", ".delete_btn", function() {
			if (confirm("정말 선택하신 항목을 삭제하시겠습니까?")) {
				
				$.ajax({ /* 유효성 검사를 통과 했을 때 Ajax 함수 호출 */
		            url: '<%=MakeCommand("delete.com")%>',
		            data : 'cnum=' + $(this).attr('pmkey') + '&no=' + '${bean.no}',  
		    		type : "post",             
		            dataType: "text",
		            success: function (data, textStatus) { /* 댓글 삭제 */	            	
		            	getListComment() ; /* 목록 갱신 */		           
		            }
		        });			
			}
		});	
		
		$(document).ready(function() {
			getListComment() ; /* 시작하자 마자 읽어 오기 */		 
			 
			/** 덧글 내용 저장 이벤트 */
			$("#comment_form").submit(function(){
				// 작성자 이름에 대한 입력여부 검사
				if (!$("#writer").val()) {
					alert("이름을 입력하세요.");
					$("#writer").focus();
					return false;
				}
	
				// 내용에 대한 입력여부 검사
				if (!$("#content").val()) {
					alert("내용을 입력하세요.");
					$("#content").focus();
					return false;
				}		
				
				var url = '<%=MakeCommand("insert.com")%>' ;
				var parameter = $('#comment_form').serialize() ;
				$.post(url, parameter, function( data ) {
					/* alert( '댓글이 작성되었읍니다.' ) ; */
					getListComment(0) ; /* 목록 갱신 */
					$("#writer").val('') ;
					$("#content").val('') ;
					
				}).fail(function() {
					alert("덧글 작성에 실패했습니다. 잠시 후에 다시 시도해 주세요.");
				});
				return false ;
			});
			
		});	
	</script>
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
				<h1 class="panel-title" align="left">게시물 상세 보기</h1>
			</div>
			<div class="panel-body">
				<div class="col-sm-<%=leftside%> col-sm-<%=leftside%>">
					<table>
						<tr>
							<td align="center"><img align="middle" src="<%=contextPath%>/images/Koala.jpg"
								class="img-rounded" width="200" height="200"></td>
						</tr>
					</table>
				</div>
				<div class="col-sm-<%=rightside%> col-sm-<%=rightside%>">
					<table class="table table-hover table-condensed">
						<tr>
							<td width="25%" align="center">글 번호</td>
							<td width="75%" align="left">${bean.no}</td>
						</tr>						
						<tr>
							<td width="25%" align="center">작성자</td>
							<td width="75%" align="left">${bean.writer}</td>
						</tr>
						<tr>
							<td width="25%" align="center">제목</td>
							<td width="75%" align="left">${bean.subject}</td>
						</tr>
						<tr>
							<td width="25%" align="center">비밀번호</td>
							<td width="75%" align="left">${bean.password}</td>
						</tr>
						<tr>
							<td width="25%" align="center">글 내용</td>
							<td width="75%" align="left">${bean.content}
							</td>
						</tr> 
						<tr>
							<td width="25%" align="center">조회수</td>
							<td width="75%" align="left">${bean.readhit}</td>
						</tr>
						<tr>
							<td width="25%" align="center">작성 일자</td>
							<td width="75%" align="left">${bean.regdate}</td>
						</tr>
					</table>
				</div>
				<hr>
				<div class="col-sm-offset-5 col-sm-4">
					<button class="btn btn-primary" onclick="gotoBack();">
						돌아 가기</button>
				</div>
			</div>
			<!-- 댓글 영역 -->
			<div class="col-sm-12">					
				<ul id="comment_list">
					<!-- 여기에 동적 생성 요소가 들어가게 됩니다. -->
				</ul>
				<div id="comment_write">
					<form id="comment_form" action="loginProc.jsp" method="post" role="form" class="form-horizontal" >
						<div class="form-group">
							<label for="writer" class="col-xs-3 col-lg-3 control-label">작성자</label>
							<div class="col-xs-4 col-lg-4">
								<input type="hidden" name="no" value="${bean.no}" />
								<input type="text" name="writer" id="writer" class="form-control" > 
							</div>
						</div>
						<div class="form-group">
							<label for="content" class="col-xs-3 col-lg-3 control-label">덧글 내용</label>
							<div class="col-xs-9 col-lg-9">
								<textarea name="content" rows="3" cols="50" id="content" ></textarea> 
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-offset-3 col-xs-2 col-lg-2">
								<button type="submit" class="btn btn-info">
									저장하기
								</button> 
							</div>
						</div>	        		
					</form>
				</div>
			</div>

		</div>
	</div>
	<script>
		$(document).ready(function() {
			$('[data-toggle="popover"]').popover();
		});
	</script>
</body>
</html>