<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="users.list.page">
    <div class="pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <a href="${pageContext.request.contextPath}/do/?action=users&page=${i}" class="link-style">${i}</a>
            </c:forEach>
        </c:if>
    </div>
    <br>
    <hr>
    <div align="center">
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <input type="hidden" name="action" value="edit-user">
            <table cellpadding="10">
                <tr><h4><ftm:message key="users.list.page"/></h4></tr>
                <tr bgcolor="#deb887" class="th">
                    <th><ftm:message key="login.field"/></th>
                    <th><ftm:message key="email.field"/></th>
                    <th><ftm:message key="mobphone.field"/></th>
                    <th><ftm:message key="surname.field"/></th>
                    <th><ftm:message key="firstname.field"/></th>
                    <th><ftm:message key="patronymic.field"/></th>
                    <th><ftm:message key="status.field"/></th>
                    <th><ftm:message key="role.field"/></th>
                </tr>

                    <%--@elvariable id="users" type="java.util.List"--%>
                <c:forEach items="${users}" var="user">
                    <tr bgcolor="f0ffff" class="tr">
                        <td>${user.login}</td>
                        <td>${user.email}</td>
                        <td>${user.mobilePhone}</td>
                        <td>${user.surname}</td>
                        <td>${user.firstname}</td>
                        <td>${user.patronymic}</td>
                        <td>${user.role}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.status}"><ftm:message key="active.true"/></c:when>
                                <c:when test="${not user.status}"><ftm:message key="inactive.false"/></c:when>
                            </c:choose>
                        </td>
                        <td><a href="${pageContext.request.contextPath}/do/?action=edit-user&id=${user.id}"
                               class="link-style"><ftm:message key="change.button"/></a></td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </div>

</t:page>
