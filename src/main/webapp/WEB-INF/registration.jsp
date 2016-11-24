<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Registration">
    <div align="center">
        <h1>Registration new user</h1>
        <form action="${pageContext.request.contextPath}/do/" method="post">
            <table>
                <input type="hidden" name="action" value="registration">
                <tr>
                    <td>
                        Enter your login:
                    </td>
                    <td>
                        <input type="text" name="login" value="${param.login}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Enter your password:
                    </td>
                    <td>
                        <input type="text" name="password" value="${param.password}">
                    </td>
                </tr>
                <tr>
                    <td>
                        Enter your email:
                    </td>
                    <td>
                        <input type="text" name="email" value="${param.email}">
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <button type="submit">Sign up</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</t:page>