<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<t:page title="users.list.page">
    <div class="pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <a href="${base}/do/?action=users&page=${i}" class="link-style">${i}</a>
            </c:forEach>
        </c:if>
    </div>
    <br>
    <hr>
    <div align="center">
        <form action="${base}/do/" method="post">
            <input type="hidden" name="action" value="edit-user">
            <table>
                <tr><h3><ftm:message key="users.list.page"/></h3></tr>
                <tr bgcolor="#deb887" class="th">
                    <th width="200px"><ftm:message key="login.field"/></th>
                    <th width="200px"><ftm:message key="email.field"/></th>
                    <th width="200px"><ftm:message key="mobphone.field"/></th>
                    <th width="200px"><ftm:message key="surname.field"/></th>
                    <th width="200px"><ftm:message key="firstname.field"/></th>
                    <th width="200px"><ftm:message key="patronymic.field"/></th>
                    <th width="200px"><ftm:message key="status.field"/></th>
                    <th width="200px"><ftm:message key="role.field"/></th>
                </tr>

                    <%--@elvariable id="users" type="java.util.List"--%>
                <c:forEach items="${users}" var="user">
                    <tr bgcolor="f0ffff" class="tr" align="center">
                        <td width="200px">${user.login}</td>
                        <td width="200px">${user.email}</td>
                        <td width="200px">${user.mobilePhone}</td>
                        <td width="200px">${user.surname}</td>
                        <td width="200px">${user.firstname}</td>
                        <td width="200px">${user.patronymic}</td>
                        <td width="200px">${user.role}</td>
                        <td width="200px">
                            <c:choose>
                                <c:when test="${user.status}"><ftm:message key="active.true"/></c:when>
                                <c:when test="${not user.status}"><ftm:message key="inactive.false"/></c:when>
                            </c:choose>
                        </td>
                        <td width="200px"><a href="${base}/do/?action=edit-user&userID=${user.id}"
                               class="link-style"><ftm:message key="change.button"/></a></td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </div>

</t:page>
