<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="book.delete.result">

    <div style="padding: 20px" align="center">
        <c:if test="${not empty requestScope.impossibleToRemove}">
            <h2 style="background: red"><ftm:message key="${impossibleToRemove}"/></h2>
        </c:if>
        <c:if test="${not empty requestScope.bookDeleted}">
            <h2 style="background: green"><ftm:message key="${bookDeleted}"/></h2>
        </c:if>
    </div>

</t:page>