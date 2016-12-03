<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
<t:page title="about.book" optionalTitle="${book.title}">

    <t:book book="${book}"/>

    <div class="book-description-section">
        <p align="justify">${book.description}</p>
        <hr>
    </div>
    <div class="book-comment-section">
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <input type="hidden" name="action" value="comment">
            <input type="hidden" name="bookId" value="${book.id}">
            <textarea type="text" name="comment" cols="125" rows="5" minlength="30" maxlength="1000" required
                      autofocus></textarea>
            <br>
            <button type="submit" class="link-style"><ftm:message key="leave.comment.button"/></button>
        </form>
    </div>
</t:page>
