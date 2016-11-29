<%@tag body-content="empty" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>
<ftm:setLocale value="ru_RU" scope="request"/>

<div class="header">
    <div>
        <a href="${pageContext.request.contextPath}/do/?action=show-page&page=welcome"
           class="library-icon">Library</a>
    </div>
    <div class="authorization-form-blog">
        <t:authorization/>
    </div>
</div>