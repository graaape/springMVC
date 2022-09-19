
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改书籍</title>
  <!-- 引入 Bootstrap -->
  <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
  <div class="row clearfix">
    <div class="col-md-12 column">
      <div class="page-header">
        <h1>
          <small>修改书籍</small>
        </h1>
      </div>
    </div>
  </div>
  <form action="${pageContext.request.contextPath}/book/updateBook" method="post">
    <%--    传递隐藏域--%>
    <input type="hidden" name="bookID" class="form-control" value="${books.bookID}">
    <div class="form-group">
      <label for="bookName">书籍名称</label>
      <input type="text" name="bookName" class="form-control" value="${books.bookName}" id="bookName">
    </div>
    <div class="form-group">
      <label for="bookCounts">书籍数量</label>
      <input type="text" name="bookCounts" class="form-control" value="${books.bookCounts}" id="bookCounts" required>
    </div>
    <div class="form-group">
      <label for="detail">书籍描述</label>
      <input type="text" name="detail" class="form-control" value="${books.detail}" id="detail" required>
    </div>
    <div class="form-group">
      <input type="submit" class="form-control" value="修改">
    </div>
  </form>
</div>
</body>
</html>
