<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

    <title>Admin page!</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico.jpg"
          type="image/png"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/myTheme.css">
    <style>
        .form-group.required .control-label:after {
            content: "*";
            color: red;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/resources/javascript/adminPage.js"></script>

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
            <li><a href="${pageContext.request.contextPath}/user/order'sHistory">
                <spring:message code="label.historyPageName"/></a></li>
            <li class="active"><a href="#"><spring:message code="label.adminPageName"/></a>
            </li>
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

<div class="container">

    <div class="row">
        <div class="col-sm-4">
            <div class="panel panel-default">
                <div class="panel-heading"><h4><label><spring:message code="adminView.label.deleteMenu"/></label></h4>
                </div>
                <div class="form-group panel-body">
                    <a id="deleteMenu" class="btn btn-primary form-control"
                       href="${pageContext.request.contextPath}/admin/deleteMenu">
                        <spring:message code="adminView.btn.deleteMenu"/>
                    </a>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading"><h4 class="form-signin-heading"><label>
                    <spring:message code="adminView.label.uploadFile"/></label></h4></div>
                <form class="form-group panel-body" method="post" enctype="multipart/form-data"
                      action="${pageContext.request.contextPath}/admin/uploadFile">

                    <label for="file" class="sr-only"><spring:message code="adminView.label.uploadFile"/></label>
                    <spring:message code="adminView.btn.fileChooser" var="btnFileChooserValue"/>
                    <input type="file" id="file" class="form-control btn btn-warning" name="file"
                           value="${btnFileChooserValue}"><br/>
                    <spring:message code="adminView.btn.upload" var="btnUploadFileValue"/>
                    <input class="btn btn-primary btn-block" type="submit" value="${btnUploadFileValue}"/>
                </form>
            </div>


            <a href="${pageContext.request.contextPath}/admin/createOrdersReport" type="submit">
                <spring:message code="adminView.btn.createOrdersReportFile"/>
            </a>
            <br/>
            <a href="${pageContext.request.contextPath}/admin/createDishesReport" type="submit">
                <spring:message code="adminView.btn.createDishesReportFile"/>
            </a>
            <br>

            <div>
                <c:if test="${uploadFileStatus != null}">
                    <h3 class="text-center text-warning"><c:out value="${uploadFileStatus}"/></h3>
                </c:if>
                <c:if test="${uploadErrorStatus != null}">
                    <h3 class="text-center text-danger"><c:out value="${uploadErrorStatus}"/></h3>
                </c:if>
                <c:if test="${deletedMenu != null}">
                    <h3 class="text-center text-warning"><c:out value="${deletedMenu}"/></h3>
                </c:if>
                <c:if test="${creatingReport != null}">
                    <h3 class="text-center text-warning"><c:out value="${creatingReport}"/></h3>
                </c:if>
            </div>
        </div>

        <div class="col-sm-8">
            <div class="row">
                <div class="col-sm-6">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h4><label>
                            <spring:message code="adminView.label.addUser"/></label></h4></div>
                        <form class="panel-body form-horizontal" role="form"
                              action="${pageContext.request.contextPath}/admin/addUser" method="post">
                            <div class="form-group required">
                                <label class="col-sm-4 control-label" for="email">
                                    <spring:message code="adminView.label.email"/></label>

                                <div class="col-sm-8">
                                    <input id="email" required="required" class="form-control" type="email"
                                           name="email"/>
                                </div>
                            </div>
                            <div class="form-group required">
                                <label class="col-sm-4 control-label" for="role">
                                    <spring:message code="adminView.label.role"/></label>

                                <div class="col-sm-8">
                                    <select id="role" class="form-control" size="1" name="role">
                                        <option><spring:message code="adminView.label.roleUser"/></option>
                                        <option><spring:message code="adminView.label.roleGuest"/></option>
                                        <option><spring:message code="adminView.label.roleAdmin"/></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="name">
                                    <spring:message code="adminView.label.name"/></label>

                                <div class="col-sm-8">
                                    <input id="name" class="form-control" type="text" name="name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="surname">
                                    <spring:message code="adminView.label.lastName"/></label>

                                <div class="col-sm-8">
                                    <input id="surname" class="form-control" type="text" name="surname"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="phone">
                                    <spring:message code="adminView.label.phone"/></label>

                                <div class="col-sm-8">
                                    <input id="phone" class="form-control" type="tel" name="phone"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="groupId">
                                    <spring:message code="adminView.label.groupId"/></label>

                                <div class="col-sm-8">
                                    <input id="groupId" class="form-control" type="text" name="groupId"/>
                                </div>
                            </div>
                            <button class="btn btn-primary btn-block" type="submit">
                                <spring:message code="adminView.btn.save"/></button>
                        </form>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="panel panel-default">
                        <div class="panel-heading"><h4><label>
                            <spring:message code="adminView.label.editUser"/></label></h4></div>
                        <div class="panel-body form-horizontal">
                            <input id="new-id" type="hidden">

                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="new-email">
                                    <spring:message code="adminView.label.email"/></label>

                                <div class="col-sm-8">
                                    <input id="new-email" disabled class="form-control" type="text" name="email"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="new-role">
                                    <spring:message code="adminView.label.role"/></label>

                                <div class="col-sm-8">
                                    <select id="new-role" class="form-control" size="1" name="role">
                                        <option><spring:message code="adminView.label.roleUser"/></option>
                                        <option><spring:message code="adminView.label.roleGuest"/></option>
                                        <option><spring:message code="adminView.label.roleAdmin"/></option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="new-name">
                                    <spring:message code="adminView.label.name"/></label>

                                <div class="col-sm-8">
                                    <input id="new-name" class="form-control" type="text" name="name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="new-surname">
                                    <spring:message code="adminView.label.lastName"/></label>

                                <div class="col-sm-8">
                                    <input id="new-surname" class="form-control" type="text" name="surname"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="new-phone">
                                    <spring:message code="adminView.label.phone"/></label>

                                <div class="col-sm-8">
                                    <input id="new-phone" class="form-control" type="tel" name="phone"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-4 control-label" for="new-groupId">
                                    <spring:message code="adminView.label.groupId"/></label>

                                <div class="col-sm-8">
                                    <input id="new-groupId" class="form-control" type="text" name="groupId"/>
                                </div>
                            </div>
                            <button id="editUser" class="btn btn-primary btn-block">
                                <spring:message code="adminView.btn.edit"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-heading"><h4><label><spring:message code="adminView.label.search"/></label></h4></div>
            <div class="panel-body">
                <div class="col-sm-4">
                    <form class="form-horizontal" role="form" method="post"
                          action="${pageContext.request.contextPath}/admin/searchUser">
                        <div class="form-group">
                            <div class="control-label">
                                <label class="radio-inline">
                                    <input type="radio" name="search-param-radio" value="email" checked>
                                    <spring:message code="adminView.label.email"/></label>
                                <label class="radio-inline">
                                    <input type="radio" name="search-param-radio" value="phone">
                                    <spring:message code="adminView.label.phone"/></label>
                                <label class="radio-inline">
                                    <input type="radio" name="search-param-radio" value="lastName">
                                    <spring:message code="adminView.label.lastName"/></label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-6 control-label" for="search-word-input">
                                <spring:message code="adminView.label.searchWord"/></label>

                            <div class="col-sm-6">
                                <input id="search-word-input" class="form-control" type="text"
                                       name="search-word"/>
                            </div>
                        </div>
                        <button id="search-btn" class="btn btn-primary btn-block" type="submit">
                            <spring:message code="adminView.btn.find"/></button>
                    </form>
                </div>
                <div class="col-sm-8">
                    <ul>
                        <li><h5><spring:message code="adminView.label.searchHint0"/></h5></li>
                        <li><h5><spring:message code="adminView.label.searchHint1"/></h5></li>
                        <li><h5><spring:message code="adminView.label.searchHint2"/></h5></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${users != null}">
        <div class="row focus">
            <div class="table-responsive">
                <table id="usersTable" class="table table-bordered table-striped table-hover">
                    <thead class="tab-pane">
                    <tr>
                        <th><spring:message code="adminView.label.email"/></th>
                        <th><spring:message code="adminView.label.lastName"/></th>
                        <th><spring:message code="adminView.label.name"/></th>
                        <th><spring:message code="adminView.label.phone"/></th>
                        <th><spring:message code="adminView.label.groupId"/></th>
                        <th><spring:message code="adminView.label.edit"/></th>
                        <th><spring:message code="adminView.label.delete"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td id="${user.id}email">${user.loginEmail}</td>
                            <td id="${user.id}lastName">${user.lastName}</td>
                            <td id="${user.id}name">${user.name}</td>
                            <td id="${user.id}phone">${user.phone}</td>
                            <td id="${user.id}groupId">${user.groupId}</td>
                            <td>
                                <button class="btn btn-warning btn-block" id="${user.id}edit">
                                    <spring:message code="adminView.btn.edit"/></button>
                            </td>
                            <td>
                                <button class="btn btn-danger btn-block" id="${user.id}delete">
                                    <spring:message code="adminView.btn.delete"/></button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
</div>
<br>
<a id="anchor" href="#usersTable"></a>
</body>
</html>
