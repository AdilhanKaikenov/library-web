<%@tag %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" type="java.lang.String" required="true" %>

<html>
<head>
    <title>
        ${title}
    </title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/page-style.css"/>" media="all">
</head>
<body>
<div class="wrapper">
    <%--HEADER--%>
    <div class="header">
        <%--Content body--%>
    </div>
    <%--HORIZONTAL BAR--%>
    <div class="horizontal-bar">
        <div class="inner-horizontal-bar">
            <%--Content body--%>
        </div>
    </div>
    <div class="row">
        <%--SIDEBAR--%>
        <div class="sidebar">
            <div class="inner-sidebar">
                <%--Content body--%>
            </div>
        </div>
        <%--CONTENT--%>
        <div class="content">
            <div class="inner-content">
                <jsp:doBody/>
            </div>
        </div>
    </div>
    <%--FOOTER--%>
    <div class="footer">
        <%--Content body--%>
    </div>
</div>
</body>
</html>