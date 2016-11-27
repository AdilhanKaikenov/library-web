<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="messages"/>
<ftm:setLocale value="en_US" scope="session"/>

<t:page title="Registration">
    <h1 align="center">Registration new user</h1>
    <div class="registration-form-blog" align="right">
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <table>
                <input type="hidden" name="action" value="registration">
                <tr>
                    <td>
                        Enter your login:
                    </td>
                    <td>
                        <input type="text" name="login" value="${param.login}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Enter your password:
                    </td>
                    <td>
                        <input type="text" name="password" value="${param.password}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Enter your email:
                    </td>
                    <td>
                        <input type="text" name="email" value="${param.email}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Enter your firstname:
                    </td>
                    <td>
                        <input type="text" name="firstname" value="${param.firstname}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Enter your surname:
                    </td>
                    <td>
                        <input type="text" name="surname" value="${param.surname}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Enter your patronymic:
                    </td>
                    <td>
                        <input type="text" name="patronymic" value="${param.patronymic}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Choose the gender:
                    </td>
                    <td>
                        <i><input type="radio" name="gender" value="Male" checked="checked">Male</i>
                        <i><input type="radio" name="gender" value="Female">Female</i>
                    </td>
                </tr>
                <tr>
                    <td>
                        Enter your address:
                    </td>
                    <td>
                        <input type="text" name="address" value="${param.address}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Enter your mobile phone number:
                    </td>
                    <td>
                        <input type="text" name="mobilePhone" value="${param.mobilePhone}">
                    </td>
                </tr>
                <table>
                    <tr>
                        <td>
                            <br>
                            <button type="submit">Sign up</button>
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
    </div>
</t:page>