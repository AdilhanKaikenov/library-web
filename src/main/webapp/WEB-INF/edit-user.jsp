<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="edit.user.page">

    <div align="center">
            <%--@elvariable id="user" type="com.epam.adk.web.library.model.User"--%>
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <input type="hidden" name="action" value="edit-user">
            <input type="hidden" name="userID" value="${user.id}">
            <table cellpadding="10">
                <tr><h4><ftm:message key="edit.user.page"/></h4></tr>
                <tr align="center" class="th">
                    <th><ftm:message key="login.field"/></th>
                    <th><ftm:message key="role.field"/></th>
                    <th><ftm:message key="status.field"/></th>
                </tr>
                <tr align="center" bgcolor="f0ffff">
                    <td>${user.login}</td>
                    <td>
                        <select name="role">
                            <option value="USER">USER</option>
                            <option value="ADMIN">ADMIN</option>
                        </select>
                    </td>
                    <td>
                        <select name="status">
                            <option value="${user.status = true}"><ftm:message key="active.true"/></option>
                            <option value="${user.status = false}"><ftm:message key="inactive.false"/></option>
                        </select>
                    </td>
                </tr>
                <table>
                    <br>
                    <tr>
                        <td><input type="submit" class="link-style"
                                   onclick="return confirm('<ftm:message key="warning.confirm.change"/>')"
                                   value="<ftm:message key="submit.change"/>"></td>
                    </tr>
                </table>
            </table>
        </form>
    </div>

</t:page>
