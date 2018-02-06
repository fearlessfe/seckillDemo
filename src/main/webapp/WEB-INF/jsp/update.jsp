<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀商品列表</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <form method="post" action="/seckill/update">
        <input type="hidden" name="seckillId" value="${item.seckillId}"/>
        <div class="form-group">
            <label for="name">名称</label>
            <input type="text" class="form-control" id="name" name="name" value="${item.name}">
        </div>

        <div class="form-group">
            <label for="number">库存</label>
            <input type="text" class="form-control" id="number" name="number" value="${item.number}">
        </div>

        <div class="form-group">
            <label for="startTime">开始时间</label>
            <input type="text" class="form-control" id="startTime" name="startTime" value="<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />">
        </div>

        <div class="form-group">
            <label for="endTime">开始时间</label>
            <input type="text" class="form-control" id="endTime" name="endTime" value="<fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />">
        </div>





        <button type="submit" class="btn btn-primary">submit</button>
        <%--<form:form commandName="seckill" action="/admin/add" method="post">--%>
        <%--<form:input path="name"/>--%>
        <%--<form:input path="number"/>--%>
        <%--&lt;%&ndash;<form:input path="startTime"/>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<form:input path="endTime"/>&ndash;%&gt;--%>
        <%--<input type="submit" value="GO"/>--%>
        <%--</form:form>--%>

    </form>

    <%--<form method="post" action="/seckill/update">--%>
        <%--<input type="hidden" name="seckillId" value="${item.seckillId}"/>--%>

        <%--<input type="text" name="name" value="${item.name}"/>--%>
        <%--<input type="text" name="number" value="${item.number}" />--%>

        <%--<input type="text" name="startTime" value="<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>--%>
        <%--<input type="text" name="endTime" value="<fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>--%>
       <%--<button type="submit">submit</button>--%>
    <%--</form>--%>

</div>



<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</body>
</html>
