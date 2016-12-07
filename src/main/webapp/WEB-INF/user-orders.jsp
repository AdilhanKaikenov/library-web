<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<t:page title="my.orders.page">

    <div class="pagination-section" align="center">
            <%--@elvariable id="pagesNumber" type="java.lang.Integer"--%>
        <c:if test="${pagesNumber != 1}">
            <c:forEach var="i" begin="${1}" end="${pagesNumber}">
                <a href="${pageContext.request.contextPath}/do/?action=user-orders&page=${i}"
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
        <table style="border: rebeccapurple; background: beige;" border="1px" align="center">
            <tr align="center" style="background: whitesmoke">
                <th width="200px">
                    <ftm:message key="book.title"/>
                </th>
                <th width="100px">
                    <ftm:message key="order.type"/>
                </th>
                <th width="100px">
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
                            ${order.bookTitle}
                    </td>
                    <td width="100px">
                            ${order.type.value}
                    </td>
                    <td width="100px">
                        <ftm:formatDate value="${order.orderDate}"/>
                    </td>
                    <td width="200px">
                        <c:if test="${order.status == 'ALLOWED'}">
                            <c:if test="${order.type == 'SUBSCRIPTION'}">
                                <ftm:message key="subscription.duration.message"/>
                                <br><ftm:formatDate value="${order.from}"/> / <ftm:formatDate value="${order.to}"/>
                            </c:if>
                            <c:if test="${order.type == 'READING_ROOM'}">
                                <ftm:message key="day.reading,room.message"/>
                                <br><ftm:formatDate value="${order.from}"/>
                            </c:if>
                        </c:if>
                    </td>
                    <td width="200px" style="padding: 10px">
                        <c:if test="${order.status == 'CONSIDERED'}">
                            <h4 style="color: midnightblue;"><ftm:message key="request.considered.message"/></h4>
                        </c:if>
                        <c:if test="${order.status == 'REJECTED'}">
                            <h4 style="color: red"><ftm:message key="request.rejected.message"/></h4>
                        </c:if>
                        <c:if test="${order.status == 'ALLOWED'}">
                            <h4 style="color: green"><ftm:message key="request.allowed.message"/></h4>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</t:page>
