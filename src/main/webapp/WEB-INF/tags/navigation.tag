<%@tag body-content="empty" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>

<div class="horizontal-bar">
    <div class="inner-horizontal-bar">
        <div>
            <a href="${pageContext.request.contextPath}/do/?action=welcome" class="home-icon">Home</a>
        </div>
        <div class="select-locale-navigation-section">
            <t:select-locale/>
        </div>
    </div>
</div>