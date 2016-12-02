<%@tag body-content="empty" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>

<div class="header">
    <div>
        <a href="${pageContext.request.contextPath}/do/?action=welcome"
           class="library-icon">Library</a>
    </div>
    <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
    <c:if test="${empty user.role}">
        <div class="authorization-form-section">
            <t:authorization/>
        </div>
    </c:if>
</div>