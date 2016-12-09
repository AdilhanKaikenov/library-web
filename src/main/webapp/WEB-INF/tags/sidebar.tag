<%@tag body-content="empty" pageEncoding="UTF-8"%>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<div class="sidebar" align="center">
    <div class="inner-sidebar">
        <div style="height: 35px; padding: 15px;" align="center">
            <form action="${pageContext.request.contextPath}/do/" method="post">
                <input hidden="hidden" name="action" value="book-search">
                <input type="text" name="dataForSearch" placeholder="Поиск" required>
                <button type="submit">Поиск</button>
            </form>
        </div>
        <hr>
        <h3><ftm:message key="genres.header"/></h3>
        <hr>
        <a href="${base}/do/?action=category&genre=0" class="sidebar-category-link-style"><ftm:message key="docum.litra.genre"/></a>
        <br><a href="${base}/do/?action=category&genre=1" class="sidebar-category-link-style"><ftm:message key="detective.thriller.genre"/> </a>
        <br><a href="${base}/do/?action=category&genre=2" class="sidebar-category-link-style"><ftm:message key="comp.internet.genre"/> </a>
        <br><a href="${base}/do/?action=category&genre=3" class="sidebar-category-link-style"><ftm:message key="poetry.genre"/> </a>
        <br><a href="${base}/do/?action=category&genre=4" class="sidebar-category-link-style"><ftm:message key="science.education.genre"/> </a>
    </div>
</div>