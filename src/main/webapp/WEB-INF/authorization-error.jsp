<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>
<ftm:setLocale value="ru_RU" scope="request"/>

<html>
<head>
    <title>
        <ftm:message key="authorization.error.page"/>
    </title>
    <link rel="stylesheet" type="text/css" href="../css/page-style.css" media="all">
</head>
<body>
<div class="auth-error-content">
    <div class="inner-auth-error-message">
        <c:if test="${not empty authorizationFormIncorrect}">
        <ftm:message key="auth.error.message.one"/>
        <br><ftm:message key="auth.error.message.two"/>
        <li><ftm:message key="auth.error.message.three"/>
        <li><ftm:message key="auth.error.message.four"/>
        <li>
                <ftm:message key="login.form.incorrect"/>
                <ftm:message key="login.length.incorrect"/>
        <li>
                <ftm:message key="password.form.incorrect"/>
                <ftm:message key="password.length.incorrect"/>
            </c:if>
            <c:if test="${not empty authorizationError}">
               <ftm:message key="auth.error.message.one"/>
            <br><ftm:message key="auth.error"/>
            <br><ftm:message key="auth.error.message.two"/>
            </c:if>
    </div>
    <div class="inner-auth-error-content">
        <t:authorization/>
    </div>
</div>
</body>
</html>