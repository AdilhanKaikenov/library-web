<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<html>
<head>
    <title><ftm:message key="error"/> ${requestScope.statusCode}</title>
    <link rel="stylesheet" type="text/css" href="../css/error-style.css" media="all">
</head>
<body>
<div style="float: left">
    <img src="../css/images/error-page-image.jpeg">
</div>
<div class="text-error">
    <h1><ftm:message key="error"/> ${requestScope.statusCode}</h1>
    <a href="${pageContext.request.contextPath}/do/?action=welcome" class="link-style"><ftm:message key="home.page"/></a>
</div>
</body>
</html>
