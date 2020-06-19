<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Save customer</title>
<link type="text/css" rel="stylesheet" href="../resources/css/style.css">
<link type="text/css" rel="stylesheet"
	href="../resources/css/add-customer-style.css">

</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	<div id="container">
		<h3>Save customer</h3>
		<div id="content"></div>
		<form:form action="saveCustomer" modelAttribute="customer"
			methode="POST">
			<form:hidden path="id"/>
			<table>
				<tbody>
					<tr>
						<td><label>First Name</label></td>
						<td><form:input path="firstName" /></td>
					</tr>
					<tr>
						<td><label>Last Name</label></td>
						<td><form:input path="lastName" /></td>
					</tr>
					<tr>
						<td><label>Email</label></td>
						<td><input type="email" name="email" value="${customer.email }" /></td>
					</tr>

					<tr>
						<td><input type="submit" value="Save" class="Save" /></td>
					</tr>
				</tbody>
			</table>
		</form:form>
		<div style=""></div>

		<p>
			<a href="${pageContext.request.contextPath }/customer/list">Back
				to list</a>
		</p>
	</div>

</body>
</html>