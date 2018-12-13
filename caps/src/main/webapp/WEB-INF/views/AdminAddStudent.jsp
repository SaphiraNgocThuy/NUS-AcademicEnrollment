<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
   
   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form method="POST" modelAttribute="student" action="${pageContext.request.contextPath}/admin/student/add.html">

	Add New Student
	<table align="center">
		<tr>
			<td><spring:message	code="Student Id"/></td>
			<td><form:input path="studentId" value="${sid}" readonly="true"/></td>
		</tr>
		<tr>		
			<td><spring:message code="Student Name"/></td>
			<td><form:input path="firstMidName"/></td>
		</tr>
		<tr>	
			<td><spring:message code="Last Name"/></td>
			<td><form:input path="lastName"/></td>
		</tr>
		<tr>
			<td><spring:message code="Enrollment Date"/></td>
			<td><form:input path="enrollmentDate" type="date" format="yyyy-MM-dd"/></td>			
			
		</tr>
		<tr>
			<td><input type="submit" value="Add"/></td>
			<td></td>
			<td></td>
		</tr>
	</table>
</form:form>
</body>
</html>