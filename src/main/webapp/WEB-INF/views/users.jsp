<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	isELIgnored="false" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Users</title>
</head>
<body>

<!-- récupération de l'erreur "test" qu'on a créé dans le controller -->
<spring:hasBindErrors name="userForm">
  <c:if test="${errors.hasFieldErrors('test')}">
    <h2>EXCEPTION</h2>
  </c:if>
</spring:hasBindErrors>

	<form:form method="post" modelAttribute="userForm"
		action="searchUser">
		<spring:message code="user.firstname" />
		<form:input path="firstname" />
		<b><i><form:errors path="firstname" cssclass="error" /></i></b>
		<br>
<%-- 		<spring:message code="user.lastname" /> --%>
<%-- 		<form:input path="lastname" /> --%>
<%-- 		<b><i><form:errors path="lastname" cssclass="error" /></i></b> --%>
<!-- 		<br> -->
		<input type="submit" name="searchUser"/>
	</form:form>

	<table border="1">
		<thead>
			<tr>
				<th><spring:message code="user.id" /></th>
				<th><spring:message code="user.firstname" /></th>
				<th><spring:message code="user.lastname" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.firstname}</td>
					<td>${user.lastname}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>