<%@tag body-content="empty" pageEncoding="UTF-8"%>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>

<div class="sidebar" align="center">
    <div class="inner-sidebar">
        <h3><ftm:message key="genres.header"/></h3>
        <hr>
        <a href="${pageContext.request.contextPath}/do/?action=category&genre=0" class="sidebar-category-link-style"><ftm:message key="docum.litra.genre"/></a>
        <br><a href="${pageContext.request.contextPath}/do/?action=category&genre=1" class="sidebar-category-link-style"><ftm:message key="detective.thriller.genre"/> </a>
        <br><a href="${pageContext.request.contextPath}/do/?action=category&genre=2" class="sidebar-category-link-style"><ftm:message key="comp.internet.genre"/> </a>
        <br><a href="${pageContext.request.contextPath}/do/?action=category&genre=3" class="sidebar-category-link-style"><ftm:message key="poetry.genre"/> </a>
        <br><a href="${pageContext.request.contextPath}/do/?action=category&genre=4" class="sidebar-category-link-style"><ftm:message key="science.education.genre"/> </a>
    </div>
</div>