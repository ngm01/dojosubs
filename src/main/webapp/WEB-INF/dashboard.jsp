<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    <h1>Welcome <c:out value="${currentUser.firstName}"></c:out>!</h1>
    
    <div id="box">
    
    	<table border="1px">
    	<tr>
    		<td>Current Package:</td><td>${currentUser.getUserpackage().getName()}</td>
    	</tr>
    	<tr>
    		<td>Next Due Date:</td><td><fmt:formatDate type = "date" 
         value ="${currentUser.getDueDate()}"/></td>
    	</tr>
    	<tr>
    		<td>Amount Due:</td><td>$${currentUser.getAmountDue()}</td>
    	</tr>
    	<tr>
    		<td>Member Since:</td><td><fmt:formatDate type = "date" 
         value ="${currentUser.createdAt}"/></td>
    	</tr>
    	</table>
    
    </div>
    

</body>
</html>