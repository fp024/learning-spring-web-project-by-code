<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Access Denied</title>
</head>
<body>
  <h1>Access Denied Page</h1>
  <h2><c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage()}"/></h2>
  <textarea readonly cols="100", rows="3"><c:out value="${SPRING_SECURITY_403_EXCEPTION}" /></textarea>
  <h2><c:out value="${msg}"/></h2>
</body>
</html>
