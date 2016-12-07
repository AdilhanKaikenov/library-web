<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<t:page title="rejected.orders">

    <div class="pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <a href="${base}/do/?action=rejected-orders&page=${i}"
                   class="link-style">${i}</a>
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
    <div align="center">
        <form action="${base}/do/" method="post">
            <input hidden="hidden" name="action" value="delete-old-order-requests">
            <button type="submit" class="link-style"><ftm:message key="delete.old.rejected.order.request"/></button>
        </form>
    </div>
    <table style="border: rebeccapurple; background: beige;" border="1px" align="center">
        <tr align="center" style="background: whitesmoke">
            <th width="200px">
                <ftm:message key="client.full.name"/>
            </th>
            <th width="200px">
                <ftm:message key="book.title"/>
            </th>
            <th width="100px">
                <ftm:message key="order.type"/>
            </th>
            <th width="100px">
                <ftm:message key="date.order.request"/>
            </th>
        </tr>
            <%--@elvariable id="orders" type="java.util.List"--%>
        <c:forEach items="${orders}" var="order">
            <%--@elvariable id="order" type="com.epam.adk.web.library.model.Order"--%>
            <tr align="center" class="tr">
                <td width="200px">
                        ${order.client}
                </td>
                <td width="200px">
                        ${order.bookTitle}
                </td>
                <td width="100px">
                        ${order.type.value}
                </td>
                <td width="100px">
                    <ftm:formatDate value="${order.orderDate}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    </c:if>
</t:page>