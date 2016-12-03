<%@tag body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>

<div class="header">
    <div>
        <a href="${pageContext.request.contextPath}/do/?action=welcome"
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
            <a href="${pageContext.request.contextPath}/do/?action=" class="link-style">Profile</a>
            <a href="${pageContext.request.contextPath}/do/?action=logout" class="link-style">Logout</a>
            </c:if>
    </div>
</div>
</div>