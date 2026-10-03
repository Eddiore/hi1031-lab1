<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Anropa Servlet Test</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; line-height: 1.6; }
        .box { border: 1px solid #ccc; padding: 15px; margin-bottom: 20px; border-radius: 5px; }
        button { padding: 8px 15px; font-size: 14px; cursor: pointer; }
    </style>
</head>

<body>

    <%
        System.out.println("contextPath:" + request.getContextPath());
        response.sendRedirect(request.getContextPath() + "/controller/home");
    %>

</body>
</html>