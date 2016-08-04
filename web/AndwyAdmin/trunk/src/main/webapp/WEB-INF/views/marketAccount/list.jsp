<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>账户列表</title>
</head>
<body>
	<table>
		<tbody>
		<c:forEach items="${list}" var="mc">
			<tr>
				<td><a href="${ctx}/package/mclist/?mcid=${mc.id}">${mc.market.name} ${mc.developer.name}</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>	
</body>
</html>