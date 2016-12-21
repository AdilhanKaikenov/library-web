<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ftm:setBundle basename="i18n"/>

<c:set value="${pageContext.request.contextPath}" var="base"/>

<t:page title="add.new.book">

    <div align="center">
        <h3><ftm:message key="add.new.book"/></h3>
        <li style="background: goldenrod"><ftm:message key="add.book.requirements.message.one"/></li>
        <li style="background: goldenrod"><ftm:message key="add.book.requirements.message.two"/></li>
        <form action="${base}/do/" method="post" enctype="multipart/form-data">
            <input hidden="hidden" name="action" value="add-new-book"><br>
            <table>
                <tr>
                    <td><ftm:message key="book.title"/></td>
                    <td><input name="title" type="text" size="50" required></td>
                </tr>
                <tr>
                    <td><ftm:message key="book.cover.field"/></td>
                    <td><input name="cover" type="file" accept="image/jpeg,image/png"></td>
                </tr>
                <tr>
                    <td><ftm:message key="book.authors.field"/></td>
                    <td><input name="authors" type="text" size="50" required></td>
                </tr>
                <tr>
                    <td><ftm:message key="book.publish.year.field"/></td>
                    <td><input style="width: 50px" name="publishYear" type="number" min="1900" max="2016" required></td>
                </tr>
                <tr>
                    <td><ftm:message key="book.genre.field"/></td>
                    <td><select name="genre">
                        <option value="Documental literature">Documental literature</option>
                        <option value="Detectives and thrillers">Detectives and thrillers</option>
                        <option value="Computers and Internet">Computers and Internet</option>
                        <option value="Poetry">Poetry</option>
                        <option value="Science and education">Science and education</option>
                    </select></td>
                </tr>
                <tr>
                    <td><ftm:message key="short.book.info.header"/>:</td>
                    <td><textarea style="resize: none;overflow: hidden;text-overflow: ellipsis;" type="text"
                                  name="description" cols="100" rows="4" minlength="30" maxlength="1500"
                                  required></textarea></td>
                </tr>
                <tr>
                    <td><ftm:message key="book.amount"/></td>
                    <td><input style="width: 50px" name="totalAmount" type="number" min="0" required></td>
                </tr>
                <tr align="center">
                    <td colspan="2">
                        <button type="submit"
                                onclick="return confirm('<ftm:message key="confirm.warning"/>')"
                                class="link-style"><ftm:message key="add.button"/></button>
                    </td>
                </tr>
            </table>
        </form>
    </div>

</t:page>
