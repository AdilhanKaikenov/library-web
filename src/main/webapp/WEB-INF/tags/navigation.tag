<%@tag body-content="empty" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>
<ftm:setLocale value="ru_RU" scope="session"/>

<div>
    <a href="${pageContext.request.contextPath}/do/?action=show-page&page=welcome" class="home-icon">Home</a>
</div>

