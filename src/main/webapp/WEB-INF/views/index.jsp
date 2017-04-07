<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="http://ajax.aspnetcdn.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js"></script>
<script type="text/x-jquery-tmpl" id="detailTemplate">

</script>

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
					action="${pageContext.request.contextPath}/book/searchbook"
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
		<!-- 도서목록-->
		<div class="w3-row-padding w3-padding-16 w3-center" id="food">
			<c:forEach var="book" items="${bookList}">
				<div class="w3-quarter">
					<h4>
						<a href="javascript:void(0)" id="detailbookPopupbtn"
							onclick="detailbook(${book.getBook_id()})">
							${book.getBook_name()} </a>
					</h4>
					<p>출판사 : ${book.getCompany()}</p>
					<p>저 자 : ${book.getWriter()}</p>
					<p>가 격 : ${book.getPrice()}</p>
				</div>
			</c:forEach>
		</div>
	</div>

	<!-- 도서등록 버튼 -->
	<a href="javascript:void(0)"
		class="w3-bar-item w3-button w3-dark-grey w3-button w3-hover-black w3-left-align"
		onclick="document.getElementById('addbookPopup').style.display='block'">
		도서 등록 </a>


	<!-- 도서등록 양식 -->
	<div id="addbookPopup" class="w3-modal" style="z-index: 4">
		<div class="w3-modal-content w3-animate-zoom">
			<!-- TOP -->
			<div class="w3-container w3-padding w3-red">
				<h2>도서등록</h2>
			</div>
			<div class="w3-panel">
				<form id="addbookForm"
					action="${pageContext.request.contextPath}/book/addbook"
					method="post">
					<label>도서명</label> <input id="book_name" name="book_name"
						class="w3-input w3-border w3-margin-bottom" type="text"> <label>출판사</label>
					<input id="company" name="company"
						class="w3-input w3-border w3-margin-bottom" type="text"> <label>저
						자</label> <input id="writer" name="writer"
						class="w3-input w3-border w3-margin-bottom" type="text"> <label>가
						격</label> <input id="price" name="price"
						class="w3-input w3-border w3-margin-bottom" type="number">
					<label>내 용</label>
					<textarea rows="" cols="" id="context" name="context"
						class="w3-input w3-border w3-margin-bottom"
						style="height: 150px; resize: none;"></textarea>
					<div class="w3-section">
						<a id="addbookFormCancle" class="w3-button w3-red">취소  <i
							class="fa fa-remove"></i>
						</a> <a id="addbook" class="w3-button w3-light-grey w3-right">저장  <i
							class="fa fa-paper-plane"></i>
						</a>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 도서 상세보기 -->
	<div id="detailbookPopup" class="w3-modal"
		style="z-index: 4; overflow: auto;">
		<div class="w3-modal-content w3-animate-zoom">
			<div class="w3-row">
				<span
					onclick="document.getElementById('detailbookPopup').style.display='none'"
					class="w3-button w3-right w3-xxlarge"><i
					class="fa fa-remove"></i> </span>
				<div class="w3-padding-64 w3-center">
					<h1></h1>
					출판사 : \${company} 저 자 : \${writer}
					<div class="w3-left-align w3-padding-xxlarge">
						<textarea readonly="readonly" id="context" name="context"
						class="w3-input w3-margin-bottom"
						style="height: 200px; resize: none; border: 0;">
						\${context}
						</textarea>

					</div>
				</div>
				<div id="btndiv">
					<div class="w3-center">
						<input id="rentalbtn_false" type="button" class="button"
							value="대여하기">
					</div>
				<</div>
					<div class="w3-center">
						<input id="rentalbtn_true" type="button" class="button"
							value="대여중" style="background-color: #f44336;">
					</div>
			</div>
		</div>
	</div>



	<script type="text/javascript">
		//도서등록
		$(function() {

			//창을 닫고 입력내용 초기화
			$("#addbookFormCancle").click(function() {
				$("#addbookPopup").css("display", "none");
				$("#book_name").val("");
				$("#company").val("");
				$("#writer").val("");
				$("#price").val("");
				$("#context").val("");
			});

			//도서정보 입력확인
			$("#addbook").click(function() {
				if ($("#book_name").val() == "") {
					alert("도서명을 입력해주세요.");
				} else if ($("#company").val() == "") {
					alert("출판사을 입력해주세요.");
				} else if ($("#writer").val() == "") {
					alert("저자을 입력해주세요.");
				} else if ($("#price").val() == "") {
					alert("가격을 입력해주세요.");
				} else {
					$("#addbookForm").submit();
				}
			});
		});
		
		$(function() {
			$("#rentalbtn").click(function() {
				
			})
		})
		
		
		//도서 상세보기
		function detailbook(book_id) {
			
			$.ajax({
				type : "get",
				url :  "${pageContext.request.contextPath}/book/detailbook",
				dataType : "JSON",
				data : {
					"book_id" : book_id,
				},
				success : function(result) {
					var date = {
							"book_name" : result.book_name,
							"company" : result.company,
							"writer" : result.writer,
							"price" : result.price,
							"rental_check" : result.rental_check,
							"context" : result.context,
					};
					
					var div = $("#detailTemplate").tmpl(date);
					/* $("#detailbookPopup").html(div); */
					$("#detailbookPopup").css("display", "block");
				},
			});
			
		}
			
	</script>
	
	<form id="rentalBookForm" action="${pageContext.request.contextPath}/book/rentalbook" method="get">
		도서번호 : <input type="text" id="book_id" name="book_id"> </br>
		<input type="submit" value="대여">
	</form> --%>
</body>
</html>