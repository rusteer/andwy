<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="class1" value="class='warning'" />
<c:set var="class2" value="class='info'" />
<c:set var="class3" value="class='danger'" />
<%
	int fieldCount=0;
	boolean showPushCount="true".equals(request.getParameter("showPushCount"));
	boolean showInstallCount="true".equals(request.getParameter("showInstallCount"));
	boolean showInstallEarning="true".equals(request.getParameter("showInstallEarning"));
	if(showPushCount) fieldCount+=1;
	if(showInstallCount) fieldCount+=2;
	if(showInstallEarning) fieldCount+=2;
	request.setAttribute("fieldCount", fieldCount);
	request.setAttribute("showPushCount", showPushCount);
	request.setAttribute("showInstallCount", showInstallCount);
	request.setAttribute("showInstallEarning", showInstallEarning);
%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/assets/css/bootstrap-responsive.css" rel="stylesheet">
    <link REL="SHORTCUT ICON" href="${ctx}/assets/icon/favicon.ico">
	<style>
	body {
		margin: 0;
		font-family: Verdana, "Helvetica Neue", Helvetica, Arial, sans-serif;
		font-size:12px;
		line-height: 10px;
		color: #333333;
		text-align: center;
		background-color: #ffffff;
	}	
	td { text-align: center; align:center; font-size: 8px; }
	th { text-align: center; align:center; font-size: 9px; }
	</style>
  </head>
  <body>
  <div class="navbar">
    <div class="navbar-inner">
      <div class="container">
        <a class="brand" href="#">${title}</a>
        <div class="nav-collapse">
          <ul class="nav">
            <li><a href="${ctx}/stat/batch?hideEmpty=${param.hideEmpty}&showPushCount=${param.showPushCount}&showInstallCount=${param.showInstallCount}&showInstallEarning=${param.showInstallEarning}">批次统计</a></li>
            <li><a href="${ctx}/stat/market?hideEmpty=${param.hideEmpty}&showPushCount=${param.showPushCount}&showInstallCount=${param.showInstallCount}&showInstallEarning=${param.showInstallEarning}">市场统计</a></li>
            <li><a href="${ctx}/stat/app?hideEmpty=${param.hideEmpty}&showPushCount=${param.showPushCount}&showInstallCount=${param.showInstallCount}&showInstallEarning=${param.showInstallEarning}">广告统计</a></li>
            <li><a href="${ctx}/stat/developer?hideEmpty=${param.hideEmpty}&showPushCount=${param.showPushCount}&showInstallCount=${param.showInstallCount}&showInstallEarning=${param.showInstallEarning}">开发者统计</a></li>
          </ul>
         <form action="#" method="get" class="navbar-search pull-left">
             <input type="checkbox" name="showPushCount" value="true" onclick="this.form.submit()" ${showPushCount?"checked":""}>显示推送 &nbsp;
             <input type="checkbox" name="showInstallCount" value="true" onclick="this.form.submit()" ${showInstallCount?"checked":""}>显示安装 &nbsp;
             <input type="checkbox" name="showInstallEarning" value="true" onclick="this.form.submit()" ${showInstallEarning?"checked":""}>显示收益 &nbsp;
             <input type="hidden" name="hideEmpty" value="${param.hideEmpty}">
             <input type="hidden" name="productId" value="${param.productId}">
             <input type="hidden" name="batchId" value="${param.batchId}">
        </form> 	
        </div><!-- /.nav-collapse -->
      </div>
    </div><!-- /navbar-inner -->
  </div><!-- /navbar -->

	<table class="table table-hover table-bordered table-condensed table-striped"
		style="font-size: 11px;">
		<thead>
			<tr>
				<th>日期</th>
				<c:forEach items="${beans}" var="bean" varStatus="status">
					<c:if test="${status.index==0}">
						<c:set var="thClass" value="${class3}" />
					</c:if>
					<c:if test="${status.index>0 && status.index%2==0}">
						<c:set var="thClass" value="${class1}" />
					</c:if>
					<c:if test="${status.index>0 && status.index%2==1}">
						<c:set var="thClass" value="${class2}" />
					</c:if>
					<c:if test="${!bean.hasData}">
						<th ${thClass}>${bean.name}</th>
					</c:if>
					<c:if test="${bean.hasData}">
						<th ${thClass} colspan=${fieldCount}>${bean.name}</th>
					</c:if>
				</c:forEach>
			</tr>
			<tr>
				<th />
				<c:forEach items="${beans}" var="bean" varStatus="status">
					<c:if test="${status.index==0}">
						<c:set var="thClass" value="${class3}" />
					</c:if>
					<c:if test="${status.index>0 && status.index%2==0}">
						<c:set var="thClass" value="${class1}" />
					</c:if>
					<c:if test="${status.index>0 && status.index%2==1}">
						<c:set var="thClass" value="${class2}'" />
					</c:if>
					<c:if test="${!bean.hasData}">
						<th ${thClass}>&nbsp;</th>
					</c:if>
					<c:if test="${bean.hasData}">
						<c:if test="${param.showPushCount}">
							<th ${thClass}>推送</th>
						</c:if>
						<c:if test="${param.showInstallCount}">
							<th ${thClass} colspan=2>安装</th>
							 
						</c:if>
						<c:if test="${param.showInstallEarning}">
							<th ${thClass} colspan=2>收益</th>
						</c:if>
					</c:if>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${stats}" var="item" >
				<tr>
					<th>${item.key}</th>
					<c:forEach items="${item.value}" var="stat" varStatus="status">
						<c:if test="${status.index==0}">
							<c:set var="thClass" value="${class3}" />
						</c:if>
						<c:if test="${status.index>0 && status.index%2==0}">
							<c:set var="thClass" value="${class1}" />
						</c:if>
						<c:if test="${status.index>0 && status.index%2==1}">
							<c:set var="thClass" value="${class2}" />
						</c:if>
						<c:if test="${!stat.bean.hasData}">
							<c:if test="${!param.hideEmpty}">
								<th ${thClass}>&nbsp;</th>
							</c:if>
						</c:if>
						<c:if test="${stat.bean.hasData}">
							<c:if test="${param.showPushCount}">
								<td ${thClass}><c:if test="${stat.pushCount>0}">${stat.pushCount}</c:if></td>
							</c:if>
							<c:if test="${param.showInstallCount}">
								<td ${thClass}><c:if test="${stat.installCount>0}">${stat.installCount}</c:if></td>
								<td ${thClass}><c:if test="${stat.pushCount>0 && stat.installCount>0}"> <fmt:formatNumber value="${stat.installCount*1000.0/stat.pushCount}" pattern="#0.0"/>‰</c:if></td>
							</c:if>
							<c:if test="${param.showInstallEarning}">
								<td ${thClass}><c:if test="${stat.installEarning>0}"><fmt:formatNumber value="${stat.installEarning}" pattern="#0" /></c:if></td>
								<td ${thClass}> <c:if test="${stat.pushCount>0 && stat.installEarning>0}"> <fmt:formatNumber value="${stat.installEarning*1000.0/stat.pushCount}" pattern="#0.0"/>‰</c:if></td>
							</c:if>
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster
    <script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script> -->
    <script src="${ctx}/assets/js/jquery.js"></script>
    <script src="${ctx}/assets/js/google-code-prettify/prettify.js"></script>
    <script src="${ctx}/assets/js/bootstrap-transition.js"></script>
    <script src="${ctx}/assets/js/bootstrap-alert.js"></script>
    <script src="${ctx}/assets/js/bootstrap-modal.js"></script>
    <script src="${ctx}/assets/js/bootstrap-dropdown.js"></script>
    <script src="${ctx}/assets/js/bootstrap-scrollspy.js"></script>
    <script src="${ctx}/assets/js/bootstrap-tab.js"></script>
    <script src="${ctx}/assets/js/bootstrap-tooltip.js"></script>
    <script src="${ctx}/assets/js/bootstrap-popover.js"></script>
    <script src="${ctx}/assets/js/bootstrap-button.js"></script>
    <script src="${ctx}/assets/js/bootstrap-collapse.js"></script>
    <script src="${ctx}/assets/js/bootstrap-carousel.js"></script>
    <script src="${ctx}/assets/js/bootstrap-typeahead.js"></script>
    <script src="${ctx}/assets/js/application.js"></script>
  </body>
  </html>