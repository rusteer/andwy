<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>安装统计</title>
</head>
<body>
	<table border=1>
		<thead><tr><th colspan=2>${app.name}</th></tr></thead>
		<tbody>
		<c:forEach items="${list}" var="stat">
			<tr>
				<td>${stat.statDate}</td>
				<td>${stat.appCount}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
