<%@tag %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" type="java.lang.String" required="true" %>

<ftm:setBundle basename="i18n"/>
<ftm:setLocale value="ru_RU" scope="request"/>

<html>
<head>
    <title>
        <ftm:message key="${title}"/>
    </title>
    <link rel="stylesheet" type="text/css" href="../css/page-style.css" media="all">
</head>
<body>
<div class="wrapper">
    <div class="header">
        <div>
            <a href="${pageContext.request.contextPath}/do/?action=show-page&page=welcome"
               class="library-icon">Library</a>
        </div>
        <div class="authorization-form-blog">
            <t:authorization/>
        </div>
    </div>
    <div class="horizontal-bar">
        <div class="inner-horizontal-bar">
            <t:navigation/>
        </div>
    </div>
    <div class="row">
        <div class="sidebar">
            <div class="inner-sidebar">
            </div>
        </div>
        <div class="content">
            <div class="inner-content">
                <jsp:doBody/>
            </div>
        </div>
    </div>
    <div class="footer">
    </div>
</div>
</body>
</html>