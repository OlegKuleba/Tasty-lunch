<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page pageEncoding="UTF-8" %>

<c:set var="day" value="${param['dayClass']}"/>

<c:choose>
    <c:when test="${day == 'monday'}">
        <c:set var="firstDishesMenu" value="${mondayMenuFirstDish}"/>
        <c:set var="secondDishesMenu" value="${mondayMenuSecondDish}"/>
        <c:set var="saladMenu" value="${mondayMenuSalad}"/>
    </c:when>
    <c:when test="${day == 'tuesday'}">
        <c:set var="firstDishesMenu" value="${tuesdayMenuFirstDish}"/>
        <c:set var="secondDishesMenu" value="${tuesdayMenuSecondDish}"/>
        <c:set var="saladMenu" value="${tuesdayMenuSalad}"/>
    </c:when>
    <c:when test="${day == 'wednesday'}">
        <c:set var="firstDishesMenu" value="${wednesdayMenuFirstDish}"/>
        <c:set var="secondDishesMenu" value="${wednesdayMenuSecondDish}"/>
        <c:set var="saladMenu" value="${wednesdayMenuSalad}"/>
    </c:when>
    <c:when test="${day == 'thursday'}">
        <c:set var="firstDishesMenu" value="${thursdayMenuFirstDish}"/>
        <c:set var="secondDishesMenu" value="${thursdayMenuSecondDish}"/>
        <c:set var="saladMenu" value="${thursdayMenuSalad}"/>
    </c:when>
    <c:when test="${day == 'friday'}">
        <c:set var="firstDishesMenu" value="${fridayMenuFirstDish}"/>
        <c:set var="secondDishesMenu" value="${fridayMenuSecondDish}"/>
        <c:set var="saladMenu" value="${fridayMenuSalad}"/>
    </c:when>
</c:choose>

<div class="row">
    <div class="col-md-5 col-sm-6">
        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="orderView.label.choiceList"/></div>
            <div class="panel-body panel-default enabled-menu-items ${param['dayClass']}">
                <c:set var="context" value="${pageContext.request.contextPath}"/>
                <c:forEach items="${firstDishesMenu}" var="dish">
                    <div class="menu-item first-dish panel-heading row">
                        <div class="menu-item-icon col-xs-2">
                            <img src="${context}/resources/images/soup-small.png" alt="first-dish-image"/>
                        </div>
                        <div class="menu-item-name col-xs-6"><c:out value="${dish.name}"/></div>
                        <div class="menu-item-cost col-xs-2">
                            <c:out value="${dish.cost}"/><br>
                            <spring:message code="orderView.label.currency"/>
                        </div>
                        <div id="${dish.id}" class="menu-item-addButton col-xs-2 btn btn-primary">+</div>
                    </div>
                </c:forEach>
                <c:forEach items="${secondDishesMenu}" var="dish">
                    <div class="menu-item second-dish panel-heading row">
                        <div class="menu-item-icon col-xs-2">
                            <img src="${context}/resources/images/second-dish-small.png" alt="second-dish-image"/>
                        </div>
                        <div class="menu-item-name col-xs-6"><c:out value="${dish.name}"/></div>
                        <div class="menu-item-cost col-xs-2">
                            <c:out value="${dish.cost}"/><br>
                            <spring:message code="orderView.label.currency"/>
                        </div>
                        <div id="${dish.id}" class="menu-item-addButton col-xs-2 btn btn-primary">+</div>
                    </div>
                </c:forEach>
                <c:forEach items="${saladMenu}" var="dish">
                    <div class="menu-item salad-dish panel-heading row">
                        <div class="menu-item-icon col-xs-2">
                            <img src="${context}/resources/images/salad-small.png" alt="salad-image"/>
                        </div>
                        <div class="menu-item-name col-xs-6"><c:out value="${dish.name}"/></div>
                        <div class="menu-item-cost col-xs-2">
                            <c:out value="${dish.cost}"/><br>
                            <spring:message code="orderView.label.currency"/>
                        </div>
                        <div id="${dish.id}" class="menu-item-addButton col-xs-2 btn btn-primary">+</div>
                    </div>
                </c:forEach>
            </div>
            <div class="panel-footer"></div>
        </div>
    </div>
    <div class="col-md-offset-2 col-lg-offset-2 col-md-5 col-sm-6">
        <div class="panel panel-primary">
            <div class="panel-heading"><spring:message code="orderView.label.selectedItemsList"/></div>
            <div class="panel-body panel-default added-menu-items ${param['dayClass']}"></div>
            <div class="panel-footer">
                <ul class="list-inline text-right">
                    <li id="${param['dishesCostId']}">0</li>
                    <li><spring:message code="orderView.label.currency"/></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="row well well-sm text-center">
    <h3 class="text-center"><spring:message code="orderView.label.continueOrSave"/>
        <button id="${param['saveBtnId']}" class="btn btn-warning btn-sm ${param['dayClass']}">
            <spring:message code="orderView.btn.saveMenuForThisDay"/>
        </button>
    </h3>
</div>

