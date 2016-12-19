<%@tag body-content="empty" pageEncoding="UTF-8"%>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<table>
    <tr>
    <td>
        <h4>RU <a href="${base}/do/?action=set-locale&region=ru"><img
                src="../css/images/rus-flag.png"></a></h4>
    </td>
    <td>
        <h4>ENG <a href="${base}/do/?action=set-locale&region=en"><img
                src="../css/images/eng-flag.png"></a></h4>
    </td>
    </tr>
</table>
