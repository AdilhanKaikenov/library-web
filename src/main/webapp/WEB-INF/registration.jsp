<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>
<ftm:setLocale value="ru_RU" scope="request"/>

<t:page title="registration.page">
    <h1 align="center"><ftm:message key="registration.page"/></h1>
    <div class="registration-form-blog" align="right">
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <table>
                <input type="hidden" name="action" value="registration">
                <tr>
                    <td><ftm:message key="register.enter.login.field"/></td>
                    <td><input type="text" name="login" value="${param.login}" placeholder="Login123"></td>
                </tr>
                <tr>
                    <td><ftm:message key="register.enter.password.field"/></td>
                    <td><input type="password" name="password" value="${param.password}"></td>
                </tr>
                <tr>
                    <td><ftm:message key="register.enter.email.field"/></td>
                    <td><input type="text" name="email" value="${param.email}" placeholder="email@epam.com"></td>
                </tr>
                <tr>
                    <td><ftm:message key="register.enter.firstname.field"/></td>
                    <td><input type="text" name="firstname" value="${param.firstname}" placeholder="Adilhan"></td>
                </tr>
                <tr>
                    <td><ftm:message key="register.enter.surname.field"/></td>
                    <td><input type="text" name="surname" value="${param.surname}" placeholder="Kaikenov"></td>
                </tr>
                <tr>
                    <td><ftm:message key="register.enter.patronymic.field"/></td>
                    <td><input type="text" name="patronymic" value="${param.patronymic}" placeholder="Dayletkhanovich">
                    </td>
                </tr>
                <tr>
                    <td><ftm:message key="register.enter.gender.field"/></td>
                    <td>
                        <i><input type="radio" name="gender" value="Male" checked="checked"><ftm:message
                                key="gender.male"/></i>
                        <i><input type="radio" name="gender" value="Female"><ftm:message key="gender.female"/></i>
                    </td>
                </tr>
                <tr>
                    <td><ftm:message key="register.enter.address.field"/></td>
                    <td><input type="text" name="address" value="${param.address}" placeholder="Abaya street 21\2"></td>
                </tr>
                <tr>
                    <td><ftm:message key="register.enter.mobphone.field"/></td>
                    <td><input type="text" name="mobilePhone" value="${param.mobilePhone}" placeholder="87771112233">
                    </td>
                </tr>
                <table>
                    <tr>
                        <td>
                            <br>
                            <button type="submit"
                                    onclick="return confirm('<ftm:message key="confirm.message"/>')"><ftm:message
                                    key="button.sign.up"/></button>
                        </td>
                    </tr>
                </table>
            </table>
        </form>
    </div>
    <div class="error-message-blog">
        <td><c:if test="${not empty loginFormError}">
            <li>
            <ftm:message key="login.form.incorrect"/></c:if></td>
        <td><c:if test="${not empty loginLengthError}">
            <li>
            <ftm:message key="login.length.incorrect"/></c:if></td>
        <td><c:if test="${not empty passwordFormError}">
            <li>
            <ftm:message key="password.form.incorrect"/></c:if></td>
        <td><c:if test="${not empty passwordLengthError}">
            <li>
            <ftm:message key="password.length.incorrect"/></c:if></td>
        <td><c:if test="${not empty emailFormError}">
            <li>
            <ftm:message key="email.form.incorrect"/></c:if></td>
        <td><c:if test="${not empty fullNameFormError}">
            <li>
            <ftm:message key="fullName.form.incorrect"/></c:if></td>
        <td><c:if test="${not empty fullNameLengthError}">
            <li>
            <ftm:message key="fullName.length.incorrect"/></c:if></td>
        <td><c:if test="${not empty addressFormError}">
            <li>
            <ftm:message key="address.form.incorrect"/></c:if></td>
        <td><c:if test="${not empty addressLengthError}">
            <li>
            <ftm:message key="address.length.incorrect"/></c:if></td>
        <td><c:if test="${not empty mobilePhoneFormError}">
            <li>
            <ftm:message key="mobileNumber.form.incorrect"/></c:if></td>
        <td><c:if test="${not empty userExist}">
            <li>
            <ftm:message key="user.exist.message"/></c:if></td>
    </div>
</t:page>