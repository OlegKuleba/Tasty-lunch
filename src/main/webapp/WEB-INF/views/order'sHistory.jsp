<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="description" content="">
    <meta name="author" content="">

    <title>Order's history</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico.jpg"
          type="image/png"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/myTheme.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>

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
            <li><a href="${pageContext.request.contextPath}/user/order">
                <spring:message code="label.orderPageName"/></a></li>
            <li class="active"><a href="#">
                <spring:message code="label.historyPageName"/></a></li>
            <c:if test="${adminButton != null}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/adminPage" type="submit">
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

<div class="container panel-primary">
    <c:forEach var="order" items="${orderList}">
        <h4 class="panel-heading">
            <ul class="list-inline">
                <li><spring:message code="ordersHistoryView.label.orderHeader"/></li>
                <li><fmt:formatDate value="${order.dateStartOfWeek}" pattern="dd-MM-yyy"/></li>
            </ul>
        </h4>

        <div>
            <c:forEach var="dish" items="${order.dishList}">
                <div class="row">
                    <div class="col-xs-3"><c:out value="${dish.name}"/></div>
                    <div class="col-xs-2">
                        <c:out value="${dish.cost}"/> <spring:message code="orderView.label.currency"/>
                    </div>
                    <div class="col-xs-2">${dish.dayOfWeek}</div>
                    <div class="col-xs-1">${dish.portion}</div>
                    <div class="col-xs-4">${dish.description}</div>
                </div>
            </c:forEach>
        </div>
    </c:forEach>
</div>

</body>
</html>
