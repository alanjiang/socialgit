<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path=request.getContextPath();
  String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
  request.setAttribute("ctxPath",basePath);
%>

<html>
<head>
  <title>AWS short url service </title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css">
  <script src="${ctxPath}/js/jquery.min.js"></script>
  <script src="https://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
  <script src="${ctxPath}/js/bootstrap.min.js"></script>

    
    <script src="${ctxPath}/js/json2.js"></script>
  
</head>
<body>

<div class="container">
  <h2 style="color:red">AWS cloud  short URL service</h2>
  <p class="alert alert-warning"> 请使用chome流览器浏览 </p>
  
  <c:if test="${not empty error }">
  
     <p class="alert alert-warning"> ${error} </p>
  </c:if>
  
  <c:if test="${not empty msg}">
  
     <p class="alert alert-success"> ${msg} </p>
  </c:if>
  
 
  <h3 style="color:red;">Welcome to GitAdapter page!</h3>
  
   <div class="row">
      <a href="${ctxPath}/swagger-ui.html" target="_blank">文档API(Swagger)</a>
    </div>
</div>


<footer class="footer navbar-fixed-bottom">
    <div class="container">
    
      <p> 技术支持： 姜鹏,Email: <b>jiangpenghnlg@126.com</b>, Tel: 13926003676</p>
    </div>
</footer>



</body>

</html>
 
