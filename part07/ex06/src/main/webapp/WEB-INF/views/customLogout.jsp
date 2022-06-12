<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Custom Logout Page</title>
</head>
<body>
<h1>Custom Logout Page</h1>

<form method="post" action="/customLogout">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
  <button>로그아웃</button>
</form>

</body>
</html>
