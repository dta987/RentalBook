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

	<form id="searchbookForm" action="${pageContext.request.contextPath}/book/searchbook" method="get">
		도서명 : <input type="text" id="search_name" name="search_name"> </br>
		<input type="submit" value="저장">
	</form>

</body>
</html>