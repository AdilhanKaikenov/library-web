<%@tag body-content="empty" %>
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
            <button class="button"><a href="${pageContext.request.contextPath}/do/?action=">Profile</a></button>
            <button class="button"><a href="${pageContext.request.contextPath}/do/?action=logout">Logout</a></button>
        </c:if>
    </div>
</div>
</div>