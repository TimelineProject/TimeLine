<%--
  Created by IntelliJ IDEA.
  User: 56324
  Date: 2018/12/21
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>用户注册</h1>
<hr>
    <table>
        <form action="UserServlet?method=register" method="post" id="register">
        <tr>
            <td>
                用户名：
            </td>
            <td><input type="text" name="reg_username" value=""></td>
        </tr>
        <td>
            密码：
        </td>
        <td>
            <input type ="password" name="reg_password1" value="">
        </td>
        <tr>
            <td>
                确认密码：
            </td>

            <td>
                <input type ="password" name="reg_password2" value="">
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" class="Register" value="注册"> </td>
        </form>
        <form action="Login.jsp" method="post">
            <td colspan="2"><input type="submit" class="goLogin" value="前往登陆"> </td>
        </form>
        </tr>

    </table>
<%

    String flag = "-1";
    flag = (String)request.getAttribute("flag");
    if(flag == "0"){
%>
<script>
    window.alert("用户名已存在！");
</script>
<%
    }else if(flag == "1"){
%>
<script>
    window.alert("注册成功！");
</script>
<%
    }else if(flag == "2"){
%>
<script>
    window.alert("两次密码不一致！");
</script>
<%
    }else if(flag == "3"){
%><script>
    window.alert("任何一项不能为空！");
</script>
<%
    }
%>
</body>
</html>
