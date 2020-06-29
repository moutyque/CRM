<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
   <%@taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
   <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login page</title>
</head>
<body>
<c:if test="${param.error != null}">
<c:out value="Invalid username or password." ></c:out> 
<br><br>
</c:if>
<form:form method="POST" action="${pageContext.request.contextPath}/authentificateTheUser"> <p>
			User name :
			<input type="text" name="username">
		</p>
		<p>
			Password :
			<input type="password" name="password">
		</p>
		<input type="submit" value="login">
		</form:form>
		
		<form:form method="GET" action="${pageContext.request.contextPath}/createUser">
		<input type="submit" value="Registration">
		</form:form>
</body>
</html>