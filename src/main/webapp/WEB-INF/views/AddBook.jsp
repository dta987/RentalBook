<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>! 도서를 등록해보자 !</h1>

	<form id="addbookForm" action="${pageContext.request.contextPath}/book/addbook" method="post">
		도서명 : <input type="text" id="book_name" name="book_name"> </br>
		출판사 : <input type="text" id="company" name="company"> </br>
		저   자 : <input type="text" id="writer" name="writer"> </br>
		가   격 : <input type="text" id="price" name="price"> </br>
		
		
		<input type="submit" value="저장">
	</form>

</body>
</html>