<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="category.page">
    <div class="pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <%--@elvariable id="genreID" type="java.lang.Integer"--%>
                <a href="${pageContext.request.contextPath}/do/?action=category&genre=${genreID}&page=${i}"
                   class="link-style">${i}</a>
            </c:forEach>
        </c:if>
    </div>
    <c:if test="${empty requestScope.genreBooks}">
        <div align="center">
            <ftm:message key="empty.message"/>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.genreBooks}">
        <%--@elvariable id="genreBooks" type="java.util.List"--%>
        <c:forEach items="${genreBooks}" var="book">
            <%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
            <t:book book="${book}"/>
        </c:forEach>
    </c:if>

</t:page>
