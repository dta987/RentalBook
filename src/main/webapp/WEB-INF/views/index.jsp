<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
body, h1, h2, h3, h4, h5, h6 {
	font-family: "Karma", sans-serif
}

.w3-bar-block .w3-bar-item {
	padding: 20px
}
</style>

</head>

<body>
	<!-- Top menu -->
	<div class="w3-top">
		<div class="w3-white w3-xlarge"
			style="max-width: 1200px; margin: auto">
			<div class="w3-center w3-padding-16">
				<form id="searchBookForm" action="${pageContext.request.contextPath}/book/searchbook" method="get">
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Search for..."> <span
							class="input-group-btn">
							<button class="btn btn-defult" type="button">search</button>
						</span>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- !PAGE CONTENT! -->
	<div class="w3-main w3-content w3-padding"
		style="max-width: 1200px; margin-top: 100px">

		<!-- First Photo Grid-->
		<div class="w3-row-padding w3-padding-16 w3-center" id="food">
			<div class="w3-quarter">
				<img src="/w3images/sandwich.jpg" alt="Sandwich" style="width: 100%">
				<h3>The Perfect Sandwich, A Real NYC Classic</h3>
				<p>Just some random text, lorem ipsum text praesent tincidunt
					ipsum lipsum.</p>
			</div>
			<div class="w3-quarter">
				<img src="/w3images/steak.jpg" alt="Steak" style="width: 100%">
				<h3>Let Me Tell You About This Steak</h3>
				<p>Once again, some random text to lorem lorem lorem lorem ipsum
					text praesent tincidunt ipsum lipsum.</p>
			</div>
			<div class="w3-quarter">
				<img src="/w3images/cherries.jpg" alt="Cherries" style="width: 100%">
				<h3>Cherries, interrupted</h3>
				<p>Lorem ipsum text praesent tincidunt ipsum lipsum.</p>
				<p>What else?</p>
			</div>
			<div class="w3-quarter">
				<img src="/w3images/wine.jpg" alt="Pasta and Wine"
					style="width: 100%">
				<h3>Once Again, Robust Wine and Vegetable Pasta</h3>
				<p>Lorem ipsum text praesent tincidunt ipsum lipsum.</p>
			</div>
		</div>

		<script>
			// Script to open and close sidebar
			function w3_open() {
				document.getElementById("mySidebar").style.display = "block";
			}

			function w3_close() {
				document.getElementById("mySidebar").style.display = "none";
			}
		</script>

		<a href="${pageContext.request.contextPath}/book/addbook">도서등록</a> </br> </br>

		<%-- <form id="searchBookForm" action="${pageContext.request.contextPath}/book/searchbook" method="get">
		도서명 : <input type="text" id="search_name" name="search_name"> </br>
		<input type="submit" value="검색">
	</form>
	
	<form id="rentalBookForm" action="${pageContext.request.contextPath}/book/rentalbook" method="get">
		도서번호 : <input type="text" id="book_id" name="book_id"> </br>
		<input type="submit" value="대여">
	</form> --%>
</body>
</html>