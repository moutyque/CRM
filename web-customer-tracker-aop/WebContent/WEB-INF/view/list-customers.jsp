<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List customers</title>
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">


<!-- <link type="text/css" rel="stylesheet" href="/resources/css/style.css"> -->
<!-- ../resources/css/style.css -->
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>

		</div>
	</div>

	<div id="container">

		<div id="content">
			<input type="button" value="Add Customer"
				onclick="window.location.href='showFormForAdd'; return false;"
				class="add-button" /><br>
				
						         <!--  add a search box -->
            <form:form action="searchCustomer" method="GET">
                Search customer: <input type="text" name="searchCriteria" />
                
                <input type="submit" value="Search" class="add-button" />
            </form:form>
				
			<table>
				<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email</th>
						<th>Action</th>
					</tr>
				</thead>



				<c:forEach items="${customers}" var="customer">

					<c:url var="updateLink" value="/customer/showFormForUpdate">
						<c:param name="customerId" value="${customer.id}" />
					</c:url>
					
					<c:url var="deleteLink" value="/customer/delete">
						<c:param name="customerId" value="${customer.id}" />
					</c:url>
					<tr>
						<td><c:out value="${customer.firstName }"></c:out></td>
						<td><c:out value="${customer.lastName }"></c:out></td>
						<td><c:out value="${customer.email }"></c:out></td>
						<td><a href="${ updateLink}">Update</a>|<a href="${ deleteLink}"  onclick="if(!(confirm('Are you sure you want to delete this customer ?'))) return false; " >Delete</a></td>
					</tr>

				</c:forEach>
			</table>
		</div>
	</div>




</body>
</html>