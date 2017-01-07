<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<t:page title="list.of.books">

    <br>
    <div align="center">
        <h2><ftm:message key="list.of.books"/></h2>
    </div>
    <%--@elvariable id="ordersBooks" type="java.util.List"--%>
    <table cellpadding="10" style="border: rebeccapurple; background: beige;" border="1px" align="center">
        <tr align="center" style="background: whitesmoke">
            <th width="500px">
                <ftm:message key="book.title"/>
            </th>
            <th width="200px">
                <ftm:message key="book.publish.year.field"/>
            </th>
            <th width="300px">
                <ftm:message key="book.author.field"/>
            </th>
        </tr>
        <c:forEach items="${ordersBooks}" var="orderBook">
            <%--@elvariable id="orderBook" type="com.epam.adk.web.library.model.OrderBook"--%>
            <tr align="center" class="tr">
                <td width="200px">
                        ${orderBook.book.title}
                </td>
                <td width="200px">
                        ${orderBook.book.publishYear}
                </td>
                <td width="200px">
                        ${orderBook.book.author.name}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" align="center">
                <a href="${base}/do/?action=my-orders" class="link-style"><ftm:message key="back.button"/></a>
            </td>
        </tr>
    </table>

</t:page>