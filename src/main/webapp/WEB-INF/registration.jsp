<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="registration.page">
    <h1 align="center"><ftm:message key="registration.page"/></h1>
    <div class="registration-form-section" align="center">
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <table>
                <input type="hidden" name="action" value="registration">
                <tr>
                    <td colspan="2">
                        <c:if test="${not empty requestScope.userExist}">
                            <li style="color: red"><ftm:message key="user.exist.message"/></li></c:if>
                    </td>
                </tr>
                <tr>
                <td colspan="2"><c:if test="${not empty requestScope.loginFormIncorrect}">
                    <li style="color: red"><ftm:message key="login.form.incorrect"/></c:if>
                <c:if test="${not empty requestScope.loginLengthIncorrect}">
                    <li style="color: red"><ftm:message key="login.length.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <td align="right"><ftm:message key="register.enter.login.field"/></td>
                    <td><input type="text" name="login" value="${param.login}" placeholder="Login123"></td>
                </tr>
                <tr>
                    <td colspan="2"><c:if test="${not empty requestScope.passwordFormIncorrect}">
                        <li style="color: red"><ftm:message key="password.form.incorrect"/></c:if>
                    <c:if test="${not empty requestScope.passwordLengthIncorrect}">
                        <li style="color: red"><ftm:message key="password.length.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <td  align="right"><ftm:message key="register.enter.password.field"/></td>
                    <td><input type="password" name="password" value="${param.password}"></td>
                </tr>
                <tr>
                    <td colspan="2"><c:if test="${not empty requestScope.emailFormIncorrect}">
                        <li style="color: red"><ftm:message key="email.form.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <td align="right"><ftm:message key="register.enter.email.field"/></td>
                    <td><input type="text" name="email" value="${param.email}" placeholder="email@epam.com"></td>

                </tr>
                <tr>
                <td colspan="2"><c:if test="${not empty requestScope.fullNameFormIncorrect}">
                    <li style="color: red"><ftm:message key="fullName.form.incorrect"/></c:if>
                <c:if test="${not empty requestScope.fullNameLengthIncorrect}">
                    <li style="color: red"><ftm:message key="fullName.length.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <td align="right"><ftm:message key="register.enter.firstname.field"/></td>
                    <td><input type="text" name="firstname" value="${param.firstname}" placeholder="Adilhan"></td>
                </tr>
                <tr>
                    <td align="right"><ftm:message key="register.enter.surname.field"/></td>
                    <td><input type="text" name="surname" value="${param.surname}" placeholder="Kaikenov"></td>
                </tr>
                <tr>
                    <td align="right"><ftm:message key="register.enter.patronymic.field"/></td>
                    <td><input type="text" name="patronymic" value="${param.patronymic}" placeholder="Dayletkhanovich"></td>
                </tr>
                <tr>
                    <td align="right"><ftm:message key="register.enter.gender.field"/></td>
                    <td>
                        <i><input type="radio" name="gender" value="Male" checked="checked"><ftm:message
                                key="gender.male"/></i>
                        <i><input type="radio" name="gender" value="Female"><ftm:message key="gender.female"/></i>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><c:if test="${not empty requestScope.addressFormIncorrect}">
                        <li style="color: red"><ftm:message key="address.form.incorrect"/></c:if>
                    <c:if test="${not empty requestScope.addressLengthIncorrect}">
                        <li style="color: red"><ftm:message key="address.length.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <td align="right"><ftm:message key="register.enter.address.field"/></td>
                    <td><input type="text" name="address" value="${param.address}" placeholder="Abaya street 21\2"></td>
                </tr>
                <tr>
                    <td colspan="2"><c:if test="${not empty requestScope.mobilePhoneFormIncorrect}">
                        <li style="color: red"><ftm:message key="mobileNumber.form.incorrect"/></c:if></td>
                </tr>
                <tr>
                    <td align="right"><ftm:message key="register.enter.mobphone.field"/></td>
                    <td><input type="text" name="mobilePhone" value="${param.mobilePhone}" placeholder="87771112233"></td>
                </tr>
                    <tr align="center">
                        <td colspan="2">
                            <br>
                            <button type="submit"
                                    onclick="return confirm('<ftm:message key="confirm.message"/>')" class="link-style"><ftm:message
                                    key="button.sign.up"/></button>
                        </td>
                    </tr>
            </table>
        </form>
    </div>
</t:page>