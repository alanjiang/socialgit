<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path=request.getContextPath();
  String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
  request.setAttribute("ctxPath",basePath);
%>

<!--  bootstrap start -->

<html>
<head>
  <title>400</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="${ctxPath}/js/jquery.min.js"></script>
  <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
  <script src="${ctxPath}/js/bootstrap.min.js"></script>

    
    
  
</head>
<body>

<div class="container">
  
  <p class="alert alert-warning">404，抱歉，阁下访问的页面不存在!</p>
  
      <a href="/">返回首页</a>
</div>

</body>

</html>
 

<!--  bootstrap end -->
