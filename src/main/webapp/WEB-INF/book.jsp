<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="book.page">

    <div>

    <%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
    <t:book book="${book}"/>
    </div>

    <div class="book-description-section">

        <p align="justify">${book.description}</p>

    </div>

</t:page>
