<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Dashboard</title>
</head>
<body>
    <h1>Admin Dashboard</h1>
    
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout" />
    </form>
    
    <fieldset>
    <legend>Customers</legend>
    <table border="1px">
    	<tr>
    		<th>Name</th>
    		<th>Next Due Date</th>
    		<th>Amount Due</th>
    		<th>Package Type</th>
    	</tr>
    	<c:forEach items="${users}" var="user">
    	<c:if test="${!user.isAdmin()}">
    	<tr>
    	<td>${user.firstName} ${user.lastName}</td>
    	<td><fmt:formatDate type = "date" 
         value ="${user.getDueDate()}"/></td>
    	<td>$${user.getAmountDue()}</td>
    	<td>${user.getUserpackage().getName()}</td>
    	</tr>
    	</c:if>
    	</c:forEach>
    	<tr>
    </table>
    </fieldset>
    
    <!-- TODO: Add packages attribute to model, create packages post route in controller  -->
    
    <fieldset>
    <legend>Packages</legend>
    <table border="1px">
    	<tr>
    		<th>Package Name</th>
    		<th>Package Cost</th>
    		<th>Available</th>
    		<th>Users</th>
    		<th colspan="2">Actions</th>
    	</tr>
    	
    	<c:forEach items="${packages}" var="apackage">
    		<tr>
    			<td>${apackage.name}</td>
    			<td>$${apackage.cost}</td>
    			<c:choose>
    				<c:when test="${apackage.isAvailable()}">
    					<td>available</td>
    				</c:when>
    				<c:otherwise>
    					<td>unavailable</td>
    				</c:otherwise>
    			</c:choose>
    			<td>${apackage.countUsers()}</td>
    			<td>
    			<form action="/toggleActive" method="POST">
    				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    				<input type="hidden" name="package" value="${apackage.id}"/>
	    			<c:choose>
	    				<c:when test="${apackage.isAvailable()}">
	    					<input type="submit" value="deactivate"> 
	    				</c:when>
	    				<c:otherwise>
	    					<input type="submit" value="activate"> 
	    				</c:otherwise>
	    			</c:choose>
    			</form>
    			</td>
    			<td>
    			<c:if test="${apackage.countUsers() == 0}">
    				<form action="/deletePackage" method="POST">
    					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    					<input type="hidden" name="package" value="${apackage.id}"/>
    					<input type="submit" value="delete"/>
    				</form>
    			</c:if>
    			</td>
    		</tr>
    	</c:forEach>
    </table>
    </fieldset>
    
    <fieldset>
    	<legend>Create Package</legend>
		<form:form method="POST" action="/createPackage" modelAttribute="newPackage">
			<form:label path="name">Package Name</form:label>
			<form:input path="name"></form:input>
			
			<form:label path="cost">Cost</form:label>
			<form:input path="cost"></form:input>
			<input type="submit" value="Create new package"/>
		</form:form>
    </fieldset>
</body>
</html>