<%@tag body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<div class="horizontal-bar">
    <div class="inner-horizontal-bar">
        <div>
            <a href="${base}/do/?action=welcome" class="link-style"><ftm:message key="home.page"/></a>
            <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
            <c:if test="${not empty user && user.role == 'USER'}">
                <a href="${base}/do/?action=user-orders" class="link-style"><ftm:message key="my.orders.page"/></a>
            </c:if>
            <c:if test="${not empty user}">

            </c:if>
            <c:if test="${not empty user && user.role == 'ADMIN'}">
                <a href="${base}/do/?action=requests" class="link-style"><ftm:message key="requests"/></a>
                <a href="${base}/do/?action=orders" class="link-style"><ftm:message key="orders"/></a>
                <a href="${base}/do/?action=rejected-orders" class="link-style"><ftm:message key="rejected.orders"/></a>
            </c:if>
        </div>
        <div class="select-locale-navigation-section">
            <t:select-locale/>
        </div>
    </div>
</div>