<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page pageEncoding="UTF-8" %>
<input type="hidden" id="error" name="error">

<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <h3 id="myModalLabel"><spring:message code="orderView.modal.label.header"/></h3>
        </div>
        <div class="modal-body">
            <div id="dayError" class="hidden">
                <div><spring:message code="orderView.modal.label.dayError"/></div>
            </div>
            <div id="weekError" class="hidden">
                <div><spring:message code="orderView.modal.label.weekError"/></div>
                <div id="weekDays">
                    <ul class="list-inline">
                        <li id="mondayModal" class="hidden"><spring:message code="orderView.label.tab.monday"/>,</li>
                        <li id="tuesdayModal" class="hidden"><spring:message code="orderView.label.tab.tuesday"/>,</li>
                        <li id="wednesdayModal" class="hidden"><spring:message code="orderView.label.tab.wednesday"/>,
                        </li>
                        <li id="thursdayModal" class="hidden"><spring:message code="orderView.label.tab.thursday"/>,
                        </li>
                        <li id="fridayModal" class="hidden"><spring:message code="orderView.label.tab.friday"/></li>
                    </ul>
                </div>
            </div>
            <div id="success" class="hidden">
                <p><spring:message code="orderView.modal.label.success"/></p>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">
                <spring:message code="orderView.modal.btn.ok"/></button>
        </div>
    </div>
</div>