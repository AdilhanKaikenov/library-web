<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<t:page title="book.search.page">

    <h1 align="center"><ftm:message key="search"/>: ${requestScope.dataForSearch}</h1>

    <%--@elvariable id="foundBooks" type="java.util.List"--%>
    <c:if test="${foundBooks.size() == 0}">
        <div align="center">
            <h2><ftm:message key="search.query.no.result"/></h2>
        </div>
        <div style="margin: 25px">
            <h3><ftm:message key="advise.for.search.book"/></h3>
        </div>
    </c:if>
    <c:if test="${foundBooks.size() != 0}">
        <div style="margin-left: 20px">
            <h4><ftm:message key="books.header"/>:</h4>
        </div>
        <c:forEach items="${foundBooks}" var="book">
            <%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
            <div style="margin: 20px;position: relative; bottom: 25px;">
                <br><a href="${base}/do/?action=about-book&id=${book.id}">${book.title}</a>
            </div>
        </c:forEach>
    </c:if>

</t:page>