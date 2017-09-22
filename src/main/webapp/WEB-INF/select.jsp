<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
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
    <h1>Welcome to DojoSubscriptions <c:out value="${currentUser.firstName}"></c:out>!</h1>
    <h2>Please choose a subscription and start date</h2>
    
	<form action="/joinSub" method="POST">
	
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		
		<select name="dueOn">
			<c:forEach begin="1" end="31" varStatus="loop">
				<option value="${loop.index}">${loop.index}</option>
			</c:forEach>
		</select>
		
		<select name="choosePackage">
			<c:forEach items="${packages}" var="apackage">
				<c:if test="${apackage.isAvailable()}">
					<option value="${apackage.id}">${apackage.name} ($${apackage.cost})</option>
				</c:if>
			</c:forEach>
		</select>
		<input type="submit" value="Sign Up!"/>
		
		
	</form>
    

</body>
</html>