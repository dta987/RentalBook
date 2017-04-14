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
			<c:if test="${empty bookList}">
				<td>도서가 없습니다</td>
			</c:if>
			<c:forEach var="book" items="${bookList}">
				<div class="w3-quarter">
					<h4>
						<!-- 도서명 -->
						<a href="javascript:void(0)" id="detailbookPopupbtn"
							onclick="detailbook(${book.getBook_id()})">
							${book.getBook_name()} </a>
					</h4>
					<p>
						<label>출판사 : </label> ${book.getCompany()} | <label>저 자 :
						</label> ${book.getWriter()}
					</p>
				</div>
			</c:forEach>
		</div>

		<div class="w3-center">
			<div class="w3-bar">
				<c:if test="${beginPage != 0}">
					<c:forEach begin="${beginPage}" end="${endPage}" var="page">
						<c:if test="${page == selectPage && page != 0}">
							<a
								href="${pageContext.request.contextPath}/book/searchbook?search_name=${search_name}&selectPage=${page}"
								class="w3-button w3-green">${page}</a>
						</c:if>
						<c:if test="${page != selectPage && page != 0}">
							<a
								href="${pageContext.request.contextPath}/book/searchbook?search_name=${search_name}&selectPage=${page}"
								class="w3-button">${page}</a>
						</c:if>
					</c:forEach>
				</c:if>
			</div>
		</div>
		</footer>

		<!-- Footer -->
		<footer class="w3-row-padding w3-padding-32">
		<div class="w3-center">
			<!-- 도서등록 버튼 -->
			<a href="javascript:void(0)"
				class="w3-bar-item w3-button w3-dark-grey w3-button w3-hover-black w3-left-align"
				onclick="document.getElementById('addbookPopup').style.display='block'">
				도서 등록 </a>
			<!-- 도서반납 버튼 -->
			<a
				href="${pageContext.request.contextPath}/book/rentalbooksearch?search_name="
				class="w3-bar-item w3-button w3-dark-grey w3-button w3-hover-black w3-left-align">
				도서 반납 </a>
			<!-- 도서동계 버튼 -->
			<a href="javascript:void(0)"
				class="w3-bar-item w3-button w3-dark-grey w3-button w3-hover-black w3-left-align"
				onclick="document.getElementById('statsbookPopup').style.display='block'">
				도서 동계 </a>
		</div>
		</footer>
	</div>

	<!-- 도서등록 양식 -->
	<div id="addbookPopup" class="w3-modal" style="z-index: 4">
		<div class="w3-modal-content w3-animate-zoom">
			<!-- TOP -->
			<div class="w3-container w3-padding w3-blue">
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
						style="height: 150px; resize: none;">	</textarea>
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
					<h3 id="detali_book_name">
						<!-- 도서명 -->
					</h3>
					<label>출판사 : </label><font id="detali_company"> <!-- 출판사 -->
					</font> <label>저 자 : </label><font id="detali_writer"> <!-- 저자 -->
					</font>
					<div class="w3-left-align w3-padding-xxlarge">
						<textarea readonly="readonly" id="detali_context" name="context"
							class="w3-input w3-margin-bottom"
							style="height: 200px; resize: none; border: 0;">
								<!-- 내용 -->
							</textarea>
					</div>
				</div>

				<!-- 도서 대여 -->
				<div id="rental_btndiv">
					<!-- 도서 대여/대여중 버튼 -->
				</div>
			</div>
		</div>
	</div>

	<!-- 도서통계 -->
	<div id="statsbookPopup" class="w3-modal" style="z-index: 4">
		<div class="w3-modal-content w3-animate-zoom">
			<!-- TOP -->
			<div class="w3-container w3-padding w3-blue">
				<h2>도서통계</h2>
			</div>
			<div class="w3-panel">
				<form id="statsbookForm"
					action="${pageContext.request.contextPath}/book/statsbook"
					method="get">
					<label>파일명</label> <input id="file_name" name="file_name"
						class="w3-input w3-border w3-margin-bottom" type="text">
					<label>형태</label> 
						<input id="extension" name="extension" type="radio" value="text"> .txt
						<input id="extension" name="extension" type="radio" value="xlsx"> .xlsx
					<div class="w3-section">
						<a id="statsbookFormCancle" class="w3-button w3-red">취소  <i
							class="fa fa-remove"></i>
						</a> <a id="statsbookbtn" class="w3-button w3-light-grey w3-right">저장
							 <i class="fa fa-paper-plane"></i>
						</a>
					</div>
				</form>
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
		
		//대여하기 버튼
		function rentalbtn(select) {
			 //대여하기
			 if(select == "rentalbtn_false") {
				swal({
					  title: "대여 하시겠습니까??",
					  showCancelButton: true,
					  closeOnConfirm: false,
					  showLoaderOnConfirm: true,
					},
					function(){
						$.ajax({
							type : "get",
							url : "${pageContext.request.contextPath}/book/rentalbook",
							dataType : "text",
							data : {
								"book_id" : $("#rental_book_id").val(),
							},
							success : function(result) {
								swal(result);
								$("#detailbookPopup").css("display", "none");
							},
						});
					});
			//대여중
			} else if(select == "rentalbtn_true") {
				swal("이미 대여중인 도서입니다. \n반납예정시간\n" + $("#return_schedule_time_check").val());
			}
		}			
			
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
					$("#detali_book_name").html(result.book_name);
					$("#detali_company").html(result.company);
					$("#detali_writer").html(result.writer);
					$("#detali_context").html(result.context)
					$("#detailbookPopup").css("display", "block");
					
					//도서대여 버튼 생성
					//도서가 대여 중일 경우 true
					if(result.rental_check) {
						$("#rental_btndiv").html("<div class='w3-center'>" + "<input id='rentalbtn_true' onclick='rentalbtn(\"rentalbtn_true\")' type='button' class='button' value='대여중' style='background-color: #f44336;'>" + "<input id='return_schedule_time_check' value='" + result.return_schedule_time + "' type='hidden'>" + "</div>");
					} else {
						$("#rental_btndiv").html("<div class='w3-center'>" + "<input id='rentalbtn_false' onclick='rentalbtn(\"rentalbtn_false\")' type='button' class='button' value='대여하기'>" + "<input id='rental_book_id' name='rental_book_id' type='hidden' value='" + result.book_id + "'>" + "	</div>");
					}
				},
			});
		}
		
		//도서 통계
		$(function() {
			$("#statsbookbtn").click(function() {
				if($("#file_name").val() != "") {
					 var stringRegx = /[~!@\#$%<>^&*\()\-=+_\’.]/gi;
					 if(stringRegx.test($("#file_name").val())) {
						 alert("특수문자는 사용할수없습니다");
						 return false;
					 } else {
						 $("#statsbookForm").submit();
					 }
				} else {
					alert("파일명을 입력해주세요");
				}
			});
			
			$("#statsbookFormCancle").click(function() {
				$("#file_name").val("");
				$("#statsbookPopup").css("display", "none");
			});
		})
			
	</script>
</body>
</html>