<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
<t:page title="profile.page">
    <div align="center">
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <input type="hidden" name="action" value="edit-profile">
            <table cellpadding="8">
                <tr><h4><ftm:message key="change.personal.data"/><br></h4></tr>
                <tr>
                    <td colspan="2">
                        <c:if test="${not empty requestScope.invalidInformation}">
                            <li style="color: red"><ftm:message key="can.not.update.user.data.message"/></li></c:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <c:if test="${not empty requestScope.passwordFormIncorrect}">
                        <li style="color: red">
                                <ftm:message key="password.form.incorrect"/>
                            </c:if>
                            <c:if test="${not empty requestScope.passwordLengthIncorrect}">
                        <li style="color: red">
                        <ftm:message key="password.length.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <th align="right"><ftm:message key="new.password"/></th>
                    <td><input type="password" name="password" value=""></td>
                </tr>
                <tr>
                    <td colspan="2"><c:if test="${not empty requestScope.emailFormIncorrect}">
                        <li style="color: red">
                        <ftm:message key="email.form.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <th align="right"><ftm:message key="new.email"/></th>
                    <td><input type="text" name="email" value="${user.email}"></td>
                </tr>
                <tr>
                    <td colspan="2"><c:if test="${not empty requestScope.mobilePhoneFormIncorrect}">
                        <li style="color: red">
                        <ftm:message key="mobileNumber.form.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <th align="right"><ftm:message key="new.phone"/></th>
                    <td><input type="text" name="mobilePhone" value="${user.mobilePhone}"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <c:if test="${not empty requestScope.addressFormIncorrect}">
                        <li style="color: red">
                                <ftm:message key="address.form.incorrect"/>
                            </c:if>
                            <c:if test="${not empty requestScope.addressLengthIncorrect}">
                        <li style="color: red">
                        <ftm:message key="address.length.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <th align="right"><ftm:message key="new.address"/></th>
                    <td><input type="text" name="address" value="${user.address}"></td>
                </tr>
                <tr align="center">
                    <td colspan="2"><input type="submit" class="b" value="<ftm:message key="submit.change"/>"
                                           onclick="return confirm('<ftm:message key="warning.change"/>')"></td>
                </tr>
            </table>
    </div>
</t:page>


