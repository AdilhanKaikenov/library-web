<%@tag body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="book" type="com.epam.adk.web.library.model.Book" required="true" %>
<%@taglib uri="custom" prefix="ct"%>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<div class="book-section">
    <div class="book-image-section">
        <img src="${base}/image/?image=${book.cover}">
    </div>
    <div class="book-title-section">
        <h1><br>${book.title}</h1>
    </div>
    <div class="book-info-section">
        <br><ftm:message key="book.author.field"/>: ${book.author.name}
        <br><ftm:message key="book.genre.field"/>: ${book.genre.value}
        <c:if test="${book.publishYear != null}">
            <br><ftm:message key="book.publish.year.field"/>: ${book.publishYear}
        </c:if>
    </div>
    <div class="book-links-section">
        <div style="float: left; margin-right: 20px;">
            <a href="${base}/do/?action=about-book&bookID=${book.id}" class="link-style"><ftm:message
                    key="about.book"/></a>
        </div>
        <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
        <ct:hasRole user="${user}" role="LIBRARIAN">
        <div style="float: left;">
                <a href="${base}/do/?action=book-amount&bookID=${book.id}"
                   class="link-style"><ftm:message key="edit.book.amount.button"/></a>
            </div>
            <div style="float: left; margin-left: 15px;">
                <form action="${base}/do/" method="post">
                    <input hidden="hidden" name="action" value="delete-book">
                    <input hidden="hidden" name="bookID" value="${book.id}">
                    <button type="submit" class="link-style"><ftm:message key="delete.button"/></button>
                </form>
            </div>
        </ct:hasRole>
    </div>
</div>