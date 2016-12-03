<%@tag pageEncoding="UTF-8"%>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" type="java.lang.String" required="false" %>
<%@attribute name="optionalTitle" type="java.lang.String" required="false" %>

<ftm:setBundle basename="i18n"/>

<html>
<head>
    <title>
        <ftm:message key="${title}"/>
        ${optionalTitle}
    </title>
    <link rel="stylesheet" type="text/css" href="../css/page-style.css" media="all">
</head>
<body>
<div class="wrapper">
    <t:header/>
    <t:navigation/>
</div>
<div class="row">
    <t:sidebar/>
    <div class="content">
        <div class="inner-content">
            <jsp:doBody/>
        </div>
    </div>
</div>
<div class="footer">
    <div class="footer-message">
        <h2>Library System created by Kaikenov Adilhan</h2>
    </div>
</div>
</body>
</html>