<%@tag body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="custom" prefix="ct"%>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<div class="horizontal-bar">
    <div class="inner-horizontal-bar">
        <div>
            <a href="${base}/do/?action=welcome" class="navigation-link-style"><ftm:message key="home.page"/></a>
            <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
            <ct:hasRole user="${user}" role="READER">
                <a href="${base}/do/?action=my-order" class="navigation-link-style"><ftm:message key="my.order"/></a>
                <a href="${base}/do/?action=my-orders" class="navigation-link-style"><ftm:message key="my.orders.page"/></a>
            </ct:hasRole>
            <ct:hasRole user="${user}" role="LIBRARIAN">
                <a href="${base}/do/?action=requests" class="navigation-link-style"><ftm:message key="requests"/></a>
                <a href="${base}/do/?action=orders" class="navigation-link-style"><ftm:message key="orders"/></a>
                <a href="${base}/do/?action=users" class="navigation-link-style"><ftm:message key="users.list.page"/></a>
                <a href="${base}/do/?action=add-new-book" class="navigation-link-style"><ftm:message key="add.new.book"/></a>
            </ct:hasRole>
        </div>

    </div>
</div>