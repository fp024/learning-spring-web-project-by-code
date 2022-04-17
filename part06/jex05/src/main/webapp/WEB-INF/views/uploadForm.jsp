<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8"/>
  <title>파일 업로드 테스트 페이지</title>
</head>

<body>
<form action="uploadFormAction" method="post" enctype="multipart/form-data">
  <input type="file" name="uploadFile" multiple>
  <button>Submit</button>
</form>
</body>
</html>
