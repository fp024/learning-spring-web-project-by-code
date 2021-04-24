<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>파일 업로드 - exUpload</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/sample/exUploadPost" method="post" enctype="multipart/form-data">
	
	<div>
		<input type="file" name="files">
	</div>

	<div>
		<input type="file" name="files">
	</div>
	
	<div>
		<input type="file" name="files">
	</div>
	
	<div>
		<input type="file" name="files">
	</div>
	
	<div>
		<input type="file" name="files">
	</div>
	
	<div>
		<input type="submit">
	</div>	
	
	</form>
</body>
</html>