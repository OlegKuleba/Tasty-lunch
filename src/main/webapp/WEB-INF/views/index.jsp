<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome!</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico.jpg"
          type="image/png"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/myTheme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/styles/signin.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/styles/jumbotron.css"/>

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
    </div>
</nav>

<div class="jumbotron">
    <div class="container">
        <h1><spring:message code="indexView.label.jumbotronHeader"/></h1>

        <p><spring:message code="indexView.label.jumbotronBody"/></p>

        <p><a class="btn btn-primary btn-md" href="${pageContext.request.contextPath}/registration" role="button"
              data-toggle="modal"><spring:message code="indexView.btn.registration"/> &raquo;</a></p>
    </div>
</div>

<div>
    <c:if test="${param.error != null}">
        <h3 class="text-center text-danger">
            <spring:message code="indexView.label.loginerror"/>
        </h3>
    </c:if>
    <c:if test="${access != null}">
        <h3 class="text-center text-success">
            <spring:message code="indexView.label.registrationState"/>
        </h3>
    </c:if>
</div>

<div class="container">

    <c:url value="/login" var="loginUrl"/>
    <form class="form-signin" action="${loginUrl}" method="post">

        <h4 class="form-signin-heading"><spring:message code="indexView.label.formSignInHeader"/></h4>
        <label for="username" class="sr-only"><spring:message code="indexView.label.email"/></label>
        <input type="email" id="username" name="username" class="form-control" placeholder="Email address" required
               autofocus>
        <label for="password" class="sr-only"><spring:message code="indexView.label.password"/></label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>

        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me" name="_spring_security_remember_me"><spring:message
                    code="indexView.label.rememberMe"/>
            </label>
        </div>
        <input type="hidden"
               name="<c:out value="${_csrf.parameterName}"/>"
               value="<c:out value="${_csrf.token}"/>"/>
        <spring:message code="indexView.btn.signIn" var="btnSignInValue"/>
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="${btnSignInValue}"/>
    </form>

</div>

</body>
</html>
