<%@ page import="hello.servlet.domain.member.Member" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-07-31
  Time: 오후 3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=((Member)request.getAttribute("member")).getId()%></li>
    <li>id=${member.id}</li>
    <li>name=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
