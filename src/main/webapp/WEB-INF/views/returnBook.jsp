<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
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

<style>
.button {
	background-color: #4CAF50;
	border: none;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
}
</style>

</head>

<body>
	<!-- Top menu -->
	<div class="w3-top">
		<div class="w3-white w3-xlarge"
			style="max-width: 1200px; margin: auto">
			<div class="w3-center w3-padding-16">
				<form id="searchBookForm"
					action="${pageContext.request.contextPath}/book/rentalbooksearch"
					method="get">
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Search for..." id="search_name" name="search_name">
						<span class="input-group-btn">
							<button class="btn btn-defult" type="submit">search</button>
						</span>
					</div>
				</form>
			</div>
		</div>
	</div>	

	<!-- !PAGE CONTENT! -->
	<div class="w3-main w3-content w3-padding"
		style="max-width: 1200px; margin-top: 100px">
		<!-- 대여도서 목록-->
		<div class="col-sm-offset-${myoffset} col-sm-${mywidth}"
		style="height: 700px;">
		<table class="table">
			<thead>
				<tr>
					<th>도서명</th>
					<th>출판사/저자</th>
					<th>대여시간</th>
					<th>반납예정시간</th>
					<th>반납</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="rentalBook" items="${rentalBookList}">
					<tr>
						<td>${rentalBook.book_name}</td>
						<td>${rentalBook.company} / ${rentalBook.writer}</td>
						<td>${rentalBook.rental_time}</td>
						<td>${rentalBook.return_schedule_time}</td>
						<td><a href="javascript:void(0)" class="w3-bar-item w3-button w3-dark-grey w3-button w3-hover-black w3-left-align"
						onclick="returnbookbtn(${rentalBook.book_id}, '${rentalBook.book_name}')"> 도서 반납 </a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		
		<!-- Footer -->
 		<footer class="w3-row-padding w3-padding-32">
    	<div class="w3-center">
      		<!-- 도서등록 버튼 -->
			<a href="javascript:void(0)" class="w3-bar-item w3-button w3-dark-grey w3-button w3-hover-black w3-left-align"
				onclick="document.getElementById('addbookPopup').style.display='block'"> 도서 등록 </a>
			<!-- 도서동계 버튼 -->
			<a href="javascript:void(0)" class="w3-bar-item w3-button w3-dark-grey w3-button w3-hover-black w3-left-align"
				onclick="document.getElementById('addbookPopup').style.display='block'"> 도서 동계 </a>
    	</div>
  		</footer>
	</div>


	<script type="text/javascript">
	
	function returnbookbtn(book_id, book_name) {
		swal({
			  title: "'" + book_name + "'을(를) 반납하시겠습니까?",
			  showCancelButton: true,
			  closeOnConfirm: false,
			  showLoaderOnConfirm: true,
			},
			function(){
				$.ajax({
					type : "get",
					url : "${pageContext.request.contextPath}/book/returnbook",
					dataType : "text",
					data : {
						"book_id" : book_id,
					},
					success : function(result) {
						swal(result);
					},
				});
			});
	}
	
	
	
	</script>
</body>
</html>