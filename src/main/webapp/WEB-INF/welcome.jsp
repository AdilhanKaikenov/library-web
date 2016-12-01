<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="welcome.page">

    <%--@elvariable id="books" type="java.util.List"--%>
    <c:forEach items="${books}" var="book">
        <%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
        <div class="book-section">
            <div class="book-image-section">
                <img src="${pageContext.request.contextPath}/image/?image=${book.cover}">
            </div>
            <div class="book-title-section">
                <h1><br>${book.title}</h1>
            </div>
            <div class="book-info-section">
                <br><ftm:message key="book.section.authors.field"/> ${book.authors}
                <br><ftm:message key="book.section.genre.field"/> ${book.genre.value}
                <c:if test="${book.publishYear != null}">
                    <br><ftm:message key="book.section.publish.year.field"/> ${book.publishYear}
                </c:if>
            </div>
        </div>
    </c:forEach>

</t:page>
