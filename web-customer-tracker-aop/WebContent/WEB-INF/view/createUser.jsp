<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css" />

<meta charset="ISO-8859-1">
<title>User creation</title>
</head>
<body>
<form:form method="POST" action="${pageContext.request.contextPath}/createUser" modelAttribute="user">
<p> Username : <form:input path="username"/>
<form:errors path="username" cssClass="error"/>
<br><br>
</p>
<p> Last name : <form:input path="lastName"/>
<form:errors path="lastName" cssClass="error"/>
<br><br>
</p>
<p> First name : <form:input path="firstName"/>
<form:errors path="firstName" cssClass="error"/>
<br><br>
</p>
<p> Email: <input type="email" name="email" value="${user.email }"/>
<form:errors path="email" cssClass="error"/>
<br><br>
</p>

<p> Password: <form:password path="password"/>
<form:errors path="password" cssClass="error"/>
<br><br>
</p>

<p>Confirm Password: <form:password path="confirmPassword"/>
<form:errors path="confirmPassword" cssClass="error"/>
<br><br>
</p>
<input type="submit" value="Register">
</form:form>
</body>
</html>