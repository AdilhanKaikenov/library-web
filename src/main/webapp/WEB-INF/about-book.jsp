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

    <ftm:message key="min.max.review.length.message" var="maxCommentLength"/>
    <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
    <div class="book-submit-comment-form-section">
        <c:if test="${not empty user}">
            <form action="${pageContext.request.contextPath}/do/" method="post">
                <input type="hidden" name="action" value="comment">
                <input type="hidden" name="bookId" value="${book.id}">
                <textarea style="resize: none;overflow: hidden;text-overflow: ellipsis;" onresize="" type="text" name="comment" cols="125" rows="5" minlength="30" maxlength="250" required
                          autofocus placeholder="${maxCommentLength}"></textarea>
                <br>
                <button type="submit"
                        onclick="return confirm('<ftm:message key="confirm.send.review.message"/>')" class="link-style"><ftm:message key="leave.comment.button"/></button>
            </form>
        </c:if>
    </div>
    <div class="send-comment-requirement-section">
        <c:if test="${empty user}">
            <ftm:message key="anon.send.comment.requirement.message"/>
        </c:if>
    </div>
    <div class="comment-pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <%--@elvariable id="genreID" type="java.lang.Integer"--%>
                <a href="${pageContext.request.contextPath}/do/?action=about-book&id=${book.id}&page=${i}" class="link-style">${i}</a>
            </c:forEach>
        </c:if>
    </div>
    <%--@elvariable id="bookComments" type="java.util.List"--%>
    <c:if test="${not empty bookComments}">
        <div class="book-comments-section">
            <c:forEach items="${bookComments}" var="comment">
                <%--@elvariable id="comment" type="com.epam.adk.web.library.model.Comment"--%>
                <div class="comment-sender-info-section">
                    <h4>${comment.userFirstname} ${comment.userSurname} (${comment.userLogin})
                        <br><ftm:message key="time.field.message"/> <ftm:formatDate value="${comment.time}" pattern="yyyy-MM-dd HH:mm:ss"/></h4>
                </div>
                <div class="comment-text-section">
                    <p>${comment.text}
                    <hr>
                </div>
            </c:forEach>
        </div>
    </c:if>
</t:page>
