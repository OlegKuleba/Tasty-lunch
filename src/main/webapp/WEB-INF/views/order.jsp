<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="description" content="">
    <meta name="author" content="">

    <title>Order!</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico.jpg"
          type="image/png"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/myTheme.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/resources/javascript/order.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"><spring:message code="label.title"/></a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="#"><spring:message code="label.orderPageName"/></a></li>
            <li><a href="${pageContext.request.contextPath}/user/order'sHistory">
                <spring:message code="label.historyPageName"/></a></li>
            <c:if test="${adminButton != null}">
                <li><a href="${pageContext.request.contextPath}/admin/adminPage" type="submit">
                    <spring:message code="label.adminPageName"/></a>
                </li>
            </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:url value="/logout" var="logoutUrl"/>
            <form class="hidden" id="logout" action="${logoutUrl}" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a href="#">${pageContext.request.userPrincipal.name}</a></li>
                <li>
                    <a href="javascript:document.getElementById('logout').submit()">
                        <spring:message code="label.logout"/>
                    </a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>

<%--Modal window--%>
<div class="modal fade" id="modalForOrder" role="dialog">
    <c:import url="modalForOrder.jsp"/>
</div>

<div class="container">
    <ul id="tab-days" class="nav nav-tabs row">
        <li class="active"><a href="#monday" data-toggle="tab"><spring:message code="orderView.label.tab.monday"/></a>
        </li>
        <li><a href="#tuesday" data-toggle="tab"><spring:message code="orderView.label.tab.tuesday"/></a></li>
        <li><a href="#wednesday" data-toggle="tab"><spring:message code="orderView.label.tab.wednesday"/></a></li>
        <li><a href="#thursday" data-toggle="tab"><spring:message code="orderView.label.tab.thursday"/></a></li>
        <li><a href="#friday" data-toggle="tab"><spring:message code="orderView.label.tab.friday"/></a></li>
    </ul>
</div>
<div class="container">
    <div class="tab-content">

        <%--Monday tab--%>
        <div id="monday" class="tab-pane active">
            <h3 class="text-center"><spring:message code="orderView.label.headerForMondayTab"/></h3>

            <c:import url="dayTabForOrder.jsp">
                <c:param name="dayClass" value="monday"/>
                <c:param name="dishesCostId" value="mondayAmount"/>
                <c:param name="saveBtnId" value="mondaySave"/>
            </c:import>
        </div>

        <%--Tuesday tab--%>
        <div id="tuesday" class="tab-pane">
            <h3 class="text-center"><spring:message code="orderView.label.headerForTuesdayTab"/></h3>

            <c:import url="dayTabForOrder.jsp">
                <c:param name="dayClass" value="tuesday"/>
                <c:param name="dishesCostId" value="tuesdayAmount"/>
                <c:param name="saveBtnId" value="tuesdaySave"/>
            </c:import>
        </div>

        <%--Wednesday tab--%>
        <div id="wednesday" class="tab-pane">
            <h3 class="text-center"><spring:message code="orderView.label.headerForWednesdayTab"/></h3>

            <c:import url="dayTabForOrder.jsp">
                <c:param name="dayClass" value="wednesday"/>
                <c:param name="dishesCostId" value="wednesdayAmount"/>
                <c:param name="saveBtnId" value="wednesdaySave"/>
            </c:import>
        </div>

        <%--Thursday tab--%>
        <div id="thursday" class="tab-pane">
            <h3 class="text-center"><spring:message code="orderView.label.headerForThursdayTab"/></h3>

            <c:import url="dayTabForOrder.jsp">
                <c:param name="dayClass" value="thursday"/>
                <c:param name="dishesCostId" value="thursdayAmount"/>
                <c:param name="saveBtnId" value="thursdaySave"/>
            </c:import>
        </div>

        <%--Friday tab--%>
        <div id="friday" class="tab-pane">
            <h3 class="text-center"><spring:message code="orderView.label.headerForFridayTab"/></h3>

            <c:import url="dayTabForOrder.jsp">
                <c:param name="dayClass" value="friday"/>
                <c:param name="dishesCostId" value="fridayAmount"/>
                <c:param name="saveBtnId" value="fridaySave"/>
            </c:import>
        </div>
    </div>
    <button id="weekSave" class="btn btn-primary"><spring:message code="orderView.btn.saveMenuForWeek"/></button>
</div>

</body>
</html>
