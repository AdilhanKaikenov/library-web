<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="rejected.orders">

    <div class="pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <a href="${pageContext.request.contextPath}/do/?action=rejected-orders&page=${i}" class="link-style">${i}</a>
            </c:forEach>
        </c:if>
    </div>
    <br>
    <hr>

    <table style="border: rebeccapurple; background: beige;" border="1px" align="center" >
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
            <th width="100px">

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
                <td>
                    <form action="${pageContext.request.contextPath}/do/" method="post">
                        <button type="submit" class="link-style">Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</t:page>