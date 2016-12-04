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

    <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
    <div class="book-submit-comment-form-section">
        <c:if test="${not empty user}">
            <form action="${pageContext.request.contextPath}/do/" method="post">
                <input type="hidden" name="action" value="comment">
                <input type="hidden" name="bookId" value="${book.id}">
                <textarea type="text" name="comment" cols="125" rows="5" minlength="30" maxlength="1000" required
                          autofocus wrap="soft"></textarea>
                <br>
                <button type="submit" class="link-style"><ftm:message key="leave.comment.button"/></button>
            </form>
        </c:if>
    </div>
    <div class="send-comment-requirement-section">
        <c:if test="${empty user}">
            <ftm:message key="anon.send.comment.requirement.message"/>
        </c:if>
    </div>

    <%--@elvariable id="bookComments" type="java.util.List"--%>
    <c:if test="${not empty bookComments}">
        <div class="book-comments-section">
            <c:forEach items="${bookComments}" var="comment">
                <%--@elvariable id="comment" type="com.epam.adk.web.library.model.Comment"--%>
                <div class="comment-sender-info-section">
                    <h4>${comment.userFirstname} ${comment.userSurname} (${comment.userLogin})
                        <br>Date: ${comment.date}</h4>
                </div>
                <div class="comment-text-section">
                    <p>${comment.text}
                    <hr>
                </div>
            </c:forEach>
        </div>
    </c:if>
</t:page>
