<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common.jsp" %>    
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<style type="text/css">
		.carousel-inner>.item>img, .carousel-inner>.item>a>img {
			width: 75%;
			margin: auto;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
	
		});
	</script>	
</head>
<body>
	<div class="container">
		<br>
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>

			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">
				<div class="item active">
					<img src="${contextPath}/images/slide1.jpg" alt="Chania" width="420"
						height="300">
					<div class="carousel-caption">
						<h3>Chania</h3>
						<p>The atmosphere in Chania has a touch of Florence and
							Venice.</p>
					</div>
				</div>

				<div class="item">
					<img src="${contextPath}/images/slide2.jpg" alt="Chania" width="420"
						height="300">
					<div class="carousel-caption">
						<h3>Chania</h3>
						<p>The atmosphere in Chania has a touch of Florence and
							Venice.</p>
					</div>
				</div>

				<div class="item">
					<img src="${contextPath}/images/slide3.jpg" alt="Flower" width="420"
						height="300">
					<div class="carousel-caption">
						<h3>Flowers</h3>
						<p>Beatiful flowers in Kolymbari, Crete.</p>
					</div>
				</div>


			</div>

			<!-- Left and right controls -->
			<a class="left carousel-control" href="#myCarousel" role="button"
				data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#myCarousel" role="button"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</div>
</body>
</html>