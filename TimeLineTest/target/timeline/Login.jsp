<%--
  Created by IntelliJ IDEA.
  User: 56324
  Date: 2018/12/20
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>用户登录</h1>
<hr>
    <table>
        <form action="UserServlet?method=login" method="post" id="login">
        <tr>
            <td>
                用户名：
            </td>
            <td><input type="text" name="lgn_username" value=""></td>
        </tr>
        <tr>
            <td>
                密码：
            </td>
            <td><input type ="password" name="lgn_password" value=""></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" class="login" value="登录"> </td>
        </form>
        <form action="Register.jsp" method="post">
            <td colspan="2"><input type="submit" class="register" value="注册"> </td>
        </form>
        </tr>
    </table>
<%

    String flag = "-1";
    flag = (String)request.getAttribute("flag");
    if(flag == "0"){
%>
<script>
    window.alert("用户名不存在或密码错误!");
</script>
<%
    }else if(flag == "1"){
%>
<script>
    window.alert("请输入用户名和密码!");
</script>
<%
    }
%>
</body>
</html>

