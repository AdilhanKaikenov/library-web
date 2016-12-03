<%@tag body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="book" type="com.epam.adk.web.library.model.Book" required="true"%>

<ftm:setBundle basename="i18n"/>

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
    <div class="book-links-section">
        <a href="${pageContext.request.contextPath}/do/?action=about-book&id=${book.id}" class="link-style"><ftm:message key="about.book"/></a>
    </div>
</div>