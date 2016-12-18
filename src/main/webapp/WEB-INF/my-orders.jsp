<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<t:page title="my.orders.page">

    <div class="pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <a href="${base}/do/?action=my-orders&page=${i}"
                   class="link-style">${i}</a>
            </c:forEach>
        </c:if>
    </div>
    <br>
    <hr>
    <br>
    <c:if test="${empty requestScope.userOrders}">
        <div align="center">
            <ftm:message key="empty.message"/>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.userOrders}">
        <table cellpadding="10" style="border: rebeccapurple; background: beige;" border="1px" align="center">
            <tr align="center" style="background: whitesmoke">
                <th width="200px">
                    <ftm:message key="order.number"/>
                </th>
                <th width="200px">
                    <ftm:message key="date.order.request"/>
                </th>
                <th width="200px">
                    <ftm:message key="notice.message"/>
                </th>
                <th width="200px">
                    <ftm:message key="request.status"/>
                </th>
            </tr>
                <%--@elvariable id="userOrders" type="java.util.List"--%>
            <c:forEach items="${userOrders}" var="order">
                <%--@elvariable id="order" type="com.epam.adk.web.library.model.Order"--%>
                <tr align="center" class="tr">
                    <td width="200px">
                            ${order.id}
                    </td>
                    <td width="200px">
                        <ftm:formatDate value="${order.orderDate}"/>
                    </td>
                    <td width="200px">
                        <c:if test="${order.status == true}">
                            <c:if test="${order.orderType == 'SUBSCRIPTION'}">
                                <ftm:message key="subscription.duration.message"/>
                                <br><ftm:formatDate value="${order.from}"/> / <ftm:formatDate value="${order.to}"/>
                            </c:if>
                            <c:if test="${order.orderType == 'READING_ROOM'}">
                                <ftm:message key="day.reading,room.message"/>
                                <br><ftm:formatDate value="${order.from}"/>
                            </c:if>
                            <a href="${base}/do/?action=order-book-list&orderID=${order.id}" class="link-style"><ftm:message key="list.of.books"/></a>
                        </c:if>
                    </td>
                    <td width="200px">
                        <c:if test="${order.status == false}">
                            <h4 style="color: midnightblue;"><ftm:message key="request.considered.message"/></h4>
                        </c:if>
                        <c:if test="${order.status == true}">
                            <h4 style="color: green"><ftm:message key="request.allowed.message"/></h4>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</t:page>
