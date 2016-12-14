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

    <title>Registration</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico.jpg"
          type="image/png"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/myTheme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/styles/signin.css"/>

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
            <a class="navbar-brand" href="${pageContext.request.contextPath}/"><spring:message code="label.title"/></a>
        </div>
    </div>
</nav>

<div class="container">
    <form class="form-signin" action="${pageContext.request.contextPath}/" method="post">
        <h4 class="form-signin-heading"><spring:message code="registrationView.label.registrationFormHeader"/></h4>
        <label for="inputEmail" class="sr-only"><spring:message code="registrationView.label.email"/></label>
        <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder="Email address" required
               autofocus>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>

        <br>
        <button id="registrationBtn" class="btn btn-lg btn-primary btn-block" type="submit">
            <spring:message code="registrationView.btn.registration"/>
        </button>
    </form>
</div>

</body>
</html>
