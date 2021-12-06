<%--
  Created by IntelliJ IDEA.
  User: 唉 烁
  Date: 2021/10/5
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <title>Title</title>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer-v3.1.1\layer\layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn5").click(function () {
                //alert("aaaaa");
                layer.msg("sha");
            })
        })
    </script>
</head>
<body>

<a href="${pageContext.request.contextPath}/test/ssm.html">测试整合ssm环境</a>
<h1><a href="${pageContext.request.contextPath}/admin/do/login/page.html">测试登录界面</a></h1>
<button id="btn5">点我弹窗</button>
</body>
</html>
