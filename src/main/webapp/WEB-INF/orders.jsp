<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<t:page title="orders">

    <div class="pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <a href="${base}/do/?action=requests&page=${i}" class="link-style">${i}</a>
            </c:forEach>
        </c:if>
    </div>
    <br>
    <hr>
    <c:if test="${empty requestScope.orders}">
        <div align="center">
            <ftm:message key="empty.message"/>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.orders}">
        <table cellpadding="10" style="border: rebeccapurple; background: beige;" border="1px" align="center">
            <tr align="center" style="background: whitesmoke">
                <th width="200px">
                    <ftm:message key="order.number"/>
                </th>
                <th width="200px">
                    <ftm:message key="client.full.name"/>
                </th>
                <th width="100px">
                    <ftm:message key="order.type"/>
                </th>
                <th width="100px">
                    <ftm:message key="date.order.request"/>
                </th>
                <th width="300px">
                    <ftm:message key="period.of.books.use"/>
                </th>
                <th width="100px">

                </th>
            </tr>
                <%--@elvariable id="orders" type="java.util.List"--%>
            <c:forEach items="${orders}" var="order">
                <%--@elvariable id="order" type="com.epam.adk.web.library.model.Order"--%>
                <tr align="center" class="tr">
                    <td width="200px">
                            ${order.id}
                    </td>
                    <td width="200px">
                            ${order.user.surname} ${order.user.firstname} ${order.user.patronymic}
                    </td>
                    <td width="200px">
                            ${order.orderType.value}
                    </td>
                    <td width="200px">
                            <ftm:formatDate value="${order.orderDate}"/>
                    </td>
                    <td width="300px">
                        <c:if test="${order.orderType == 'SUBSCRIPTION'}">
                            <ftm:formatDate value="${order.from}"/> / <ftm:formatDate value="${order.to}"/>
                        </c:if>
                        <c:if test="${order.orderType == 'READING_ROOM'}">
                            <ftm:formatDate value="${order.from}"/>
                        </c:if>
                    </td>
                    <td width="200px">
                        <a href="${pageContext.request.contextPath}/do/?action=handle-return-books&orderID=${order.id}" class="link-style"><ftm:message key="handle.return.books"/></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</t:page>