<%@tag body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<div class="header">
    <div>
        <a href="${base}/do/?action=welcome"
           class="library-icon">Library</a>
        <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
    </div>
    <div class="authorization-form-section">
        <c:if test="${empty user.role}">
            <t:authorization/>
        </c:if>
    </div>
    <div class="user-header-section">
        <c:if test="${not empty user}">
            <i><ftm:message key="log.in.info.field"/> ${user.login} (${user.role.value}) </i>
            <br>
            <br>
            <a href="${base}/do/?action=personal-area" class="link-style"><ftm:message key="profile"/></a>
            <a href="${base}/do/?action=logout" class="link-style"><ftm:message key="logout.button"/></a>
            </c:if>
    </div>
</div>
</div>