<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="book.page">

    <%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
    <t:book book="${book}"/>

    <div class="book-description-section">

        <p align="justify">${book.description}</p>

    </div>

    <div class="book-comment-section">
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <input type="hidden" name="action" value="comment">
            <input type="hidden" name="id" value="${book.id}">
            <textarea type="text" name="comment" cols="125" rows="5" maxlength="1000"></textarea>
            <br><button type="submit">Comment</button>
        </form>
    </div>
</t:page>
