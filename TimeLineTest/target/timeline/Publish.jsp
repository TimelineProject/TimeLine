<%--
  Created by IntelliJ IDEA.
  User: 56324
  Date: 2018/12/22
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>


<html>
<head>

    <title>Publish</title>


    <script language="JavaScript">
        <!--
        function MM_openBrWindow(theURL, winName, features) {
            window.open(theURL, winName, features);
        }
        -->
    </script>
</head>

<body bgcolor="#FFFFFF" text="#000000">
<%

    String flag = "-1";
    String status = "未选择图片";
    flag = (String)request.getAttribute("flag");
    %>

<p> </p>
<p align="center">
    <b><font size="5" color="#FF6633">TimeLine</font></b>
</p>
<hr size="1">
    <table width="60%" border="0" cellspacing="1" cellpadding="3"
           align="center" bgcolor="#999999">
        <form action="SendMessageServlet?method=addImage" method="post" enctype="multipart/form-data">
        <tr bgcolor="#FFFFFF">
            <td width="20%"> </td>
            <td width="80%">选择图片<input  type="file" name="mes_image"  value="" > <input type="submit" name="add_image" value="上传"></td>
        </tr>
        </form>
        <form action="SendMessageServlet?method=addInformation" method="post" >
        <tr bgcolor="#FFFFFF">
            <td width="20%">留言内容:</td>
            <td width="80%"> </td>
        </tr>
        <tr bgcolor="#FFFFFF">
            <td width="20%"> </td>
            <td width="80%"><textarea name="mes_information"  cols="45" rows="7"></textarea>
            </td>
        </tr>
        <tr bgcolor="#FFFFFF">
            <td colspan="2">
                <div align="center">
                    <input type="submit" name="cmdok" value="添加到留言薄">
                </div>
            </td>
        </tr>
        </form>
        <form action="Homepage.jsp" method="post">
        <tr bgcolor="#FFFFFF">
            <td colspan="2">
                <div align="center">
                    <input
                                type="submit" name="cmdread" value="查看所有留言" >
                </div>
            </td>
        </tr>
        </form>
    </table>
<%
    if(flag == "0"){
%>
<script>
    window.alert("请先登录!");
</script>
<%
}else if(flag == "1"){
%>
<script>
    window.alert("请输入信息!");
</script>
<%
    }else if(flag == "2"){
%>
<script>
    window.alert("发布成功!");
</script>
<%
    }else if(flag == "3"){
%>
<script>
    window.alert("上传成功!");
</script>
<%
    }
%>
<p> </p>
</body>
</html>
