<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="edit.book.amount.page">

    <%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
    <div align="center">
        <h3><ftm:message key="book.title"/>: ${book.title}</h3>
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <input hidden="hidden" name="action" value="edit-book-amount">
            <input hidden="hidden" name="bookID" value="${book.id}">
            <ftm:message key="book.amount"/>: <input type="number" name="bookAmount" value="${book.totalAmount}" min="0" style="width: 55px;">
            <button type="submit"
                    onclick="return confirm('<ftm:message key="confirm.change.book.amount"/>${bookAmount}')"
                    class="link-style"><ftm:message key="change.button"/></button>
        </form>
    </div>

</t:page>