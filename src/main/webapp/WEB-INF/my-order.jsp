<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<t:page title="my.order">

    <div align="center">
        <c:if test="${not empty requestScope.subscriptionBooks}">
            <table style="border: rebeccapurple; background: beige;" border="1px">
                <h3><h2><ftm:message key="subscription"/></h2></h3>
                <tr align="center" style="background: whitesmoke">
                    <th width="300px">
                        <ftm:message key="book.title"/>
                    </th>
                    <th width="300px">
                        <ftm:message key="book.author.field"/>
                    </th>
                    <th width="100px">
                        <ftm:message key="book.publish.year.field"/>
                    </th>
                    <th width="100px">
                        <ftm:message key="book.genre.field"/>
                    </th>
                    <th width="100px">

                    </th>
                </tr>
                    <%--@elvariable id="subscriptionBooks" type="java.util.List"--%>
                <c:forEach items="${subscriptionBooks}" var="book">
                    <%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
                    <tr align="center" class="tr">
                        <td>${book.title}</td>
                        <td>${book.author.name}</td>
                        <td>${book.publishYear}</td>
                        <td>${book.genre.value}</td>
                        <td>
                            <form action="${base}/do/" method="post">
                                <input hidden="hidden" name="action" value="remove-book-from-order">
                                <input hidden="hidden" name="bookID" value="${book.id}">
                                <button type="submit"
                                        onclick="return confirm('<ftm:message key="confirm.warning"/>')"
                                        class="link-style"><ftm:message key="delete.button"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="5" align="center">
                        <c:if test="${not empty requestScope.subscriptionBooks}">
                            <form action="${base}/do/" method="post">
                                <input hidden="hidden" name="action" value="order-request">
                                <input hidden="hidden" name="orderType" value="Subscription">
                                <br>
                                <button style="margin: 10px" type="submit"
                                        onclick="return confirm('<ftm:message key="confirm.warning"/>')"
                                        class="link-style"><ftm:message key="send.request.button"/></button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </table>
        </c:if>
        <c:if test="${not empty requestScope.readingRoomBooks}">
            <table style="border: rebeccapurple; background: beige;" border="1px" align="center">
                <h3 align="center"><h2><ftm:message key="reading.room"/></h2></h3>
                <tr align="center" style="background: whitesmoke">
                    <th width="300px">
                        <ftm:message key="book.title"/>
                    </th>
                    <th width="300px">
                        <ftm:message key="book.author.field"/>
                    </th>
                    <th width="100px">
                        <ftm:message key="book.publish.year.field"/>
                    </th>
                    <th width="100px">
                        <ftm:message key="book.genre.field"/>
                    </th>
                    <th width="100px">

                    </th>
                </tr>
                    <%--@elvariable id="readingRoomBooks" type="java.util.List"--%>
                <c:forEach items="${readingRoomBooks}" var="book">
                    <%--@elvariable id="book" type="com.epam.adk.web.library.model.Book"--%>
                    <tr align="center" class="tr">
                        <td>${book.title}</td>
                        <td>${book.author.name}</td>
                        <td>${book.publishYear}</td>
                        <td>${book.genre.value}</td>
                        <td>
                            <form action="${base}/do/" method="post">
                                <input hidden="hidden" name="action" value="remove-book-from-order">
                                <input hidden="hidden" name="bookID" value="${book.id}">
                                <button type="submit"
                                        onclick="return confirm('<ftm:message key="confirm.warning"/>')"
                                        class="link-style"><ftm:message key="delete.button"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="5" align="center">
                        <c:if test="${not empty requestScope.readingRoomBooks}">
                            <form action="${base}/do/" method="post">
                                <input hidden="hidden" name="action" value="order-request">
                                <input hidden="hidden" name="orderType" value="Reading room">
                                <br>
                                <button style="margin: 10px" type="submit"
                                        onclick="return confirm('<ftm:message key="confirm.warning"/>')"
                                        class="link-style"><ftm:message key="send.request.button"/></button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </table>
        </c:if>
        <c:if test="${empty requestScope.readingRoomBooks && empty requestScope.subscriptionBooks}">
            <br>
            <h2><ftm:message key="order.empty"/></h2>
        </c:if>
    </div>

</t:page>