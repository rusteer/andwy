<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
<title>软件包列表</title>
</head>
<body>
	<table>
		<tbody>
		<c:forEach items="${list}" var="pkg">
			<tr>
				<td><a href="/static/package/${pkg.product.projectName}/${pkg.marketAccount.market.shortName}/${pkg.marketAccount.developer.shortName}/${pkg.packageName}.${pkg.product.versionDate}-${pkg.product.versionCount}.apk">${pkg.packageProductName}</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>	
</body>
</html>