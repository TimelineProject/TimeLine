<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="database.DatabaseConnect" %>
<%@ page import="java.sql.Statement" %><%--
  Created by IntelliJ IDEA.
  User: 56324
  Date: 2018/12/22
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    request.setCharacterEncoding("UTF-8");
%>
<html>
<head>

    <title>TimeLine</title>

</head>
<script>
    function more()
    {
        document.getElementById("demo").innerHTML=Date();
    }
</script>
<body bgcolor="#FFFFFF" text="#000000">
<p> </p>
<p align="center">
    <b><font size="5" color="#FF6633">TimeLine </font></b>
<form action="Publish.jsp" name="PublishForm" method="post">
    <input type="submit" class="publish" value="发布信息">
</form>
</p>
<hr size="1">
<div align="center">
    <input type="button" class="refresh" onclick="javascript:window.location.reload()" value="刷新"/>
</div>
<%
    ResultSet rs;
    Connection conn = DatabaseConnect.conn();
    String sql = "select * from infos natural join users order by time desc";
    Statement state = conn.createStatement();
    rs = state.executeQuery(sql);
    if (!rs.next()) {
%>
<p> </p>
<p align="center">目前还没有文章！</p>
<%
} else {
%>
<table width="60%" border="1" cellspacing="0" cellpadding="5" class="message"
       align="center" bordercolor="#999999">
    <%
        String time;
        String image;
        String userName;
        String information;
        do{
            userName = rs.getString("account");
            time = rs.getString("time");
            information = rs.getString("information");
            image = rs.getString("image");

    %>
    <tr>
        <td bgcolor="#CCFFCC" height="27" colspan="2"><b>作者:<%=userName%>;&emsp;&emsp;
            &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;发布时间：<%=time %>
        </b></td>
    </tr>
    <tr>
        <td><%=information%>
        </td>
        <td>
             <% if(!image.equals("")){

             %>
            <img src="/image/<%=image%>" align="center" width="150" height="120" border="1"/>
            <%}%>
        </td>
    </tr>



    <%

            }while(rs.next());
        }
    %>
</table>
</div>
</body>
</html>
