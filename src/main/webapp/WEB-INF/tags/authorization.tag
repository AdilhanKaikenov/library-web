<%@tag body-content="empty" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ftm:setBundle basename="i18n"/>
<ftm:setLocale value="ru_RU" scope="session"/>

<form action="${pageContext.request.contextPath}/do/" method="post">
    <table>
        <input hidden="hidden" name="action" value="authorization">
        <tr>
            <td><ftm:message key="auth.form.login.field"/></td>
            <td>
                <input type="text" name="login" value="${param.login}">
            </td>
        </tr>
        <tr>
            <td><ftm:message key="auth.form.password.field"/></td>
            <td>
                <input type="password" name="password" value="${param.password}">
            </td>
        </tr>
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/do/?action=show-page&page=registration"><ftm:message key="registration.page"/></a>
            </td>
        </tr>
    </table>
    <table>
        <tr>
            <td>
                <br>
                <button type="submit"><ftm:message key="button.sign.in"/></button>
            </td>
        </tr>
    </table>
</form>