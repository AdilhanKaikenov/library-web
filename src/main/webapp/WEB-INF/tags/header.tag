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
    <div class="authorization-form-section">
        <t:authorization/>
    </div>
</div>