<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>
<ftm:setLocale value="ru_RU" scope="session"/>

<t:page title="welcome.page">
    <c:redirect url="/do/?action=show-page&page=welcome"/>
</t:page>