<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="profile.page">

    <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
    <div align="center">
        <table cellpadding="10">
            <tr><h4><ftm:message key="profile"/></h4></tr>
            <tr>
                <th><ftm:message key="user.fullname"/>:</th>
                <td>
                        ${user.surname} ${user.firstname} ${user.patronymic}
                </td>
            </tr>
            <tr>
                <th><ftm:message key="login.field"/></th>
                <td>
                        ${user.login}
                </td>
            </tr>
            <tr>
                <th><ftm:message key="email.field"/></th>
                <td>
                        ${user.email}
                </td>
            </tr>
            <tr>
                <th><ftm:message key="address.field"/></th>
                <td>
                        ${user.address}
                </td>
            </tr>
            <tr>
                <th><ftm:message key="mobphone.field"/></th>
                <td>
                        ${user.mobilePhone}
                </td>
            </tr>
        </table>
        <br>
        <a href="${pageContext.request.contextPath}/do/?action=edit-profile" class="link-style"><ftm:message key="edit.profile"/></a>
    </div>


</t:page>