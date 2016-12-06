<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="welcome.page">
    <div class="pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <a href="${pageContext.request.contextPath}/do/?action=welcome&page=${i}" class="link-style">${i}</a>
            </c:forEach>
        </c:if>
    </div>
    <br>
    <hr>
    <%--@elvariable id="books" type="java.util.List"--%>
    <c:forEach items="${books}" var="book">
        <%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
        <t:book book="${book}"/>
    </c:forEach>
</t:page>
