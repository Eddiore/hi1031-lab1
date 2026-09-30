<%@ page import="org.example.bo.Facade" %>
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
<h1>Hello from China!</h1>

<p>Cheeseburger!</p>
<h1>${mymsg}</h1>
<p>
    Current server time:
    <%= new java.util.Date() %>
</p>

<h1>JSP till Servlet Kommunikation</h1>
<p>Här är tre olika sätt som din JSP-fil kan hitta din Servlet på:</p>

<!-- Exempel 1: Anrop via ett HTML-formulär -->
<div class="box">
    <h3>1. Via ett Formulär (GET-anrop)</h3>
    <form action="TestServlet" method="GET">
        <button type="submit">Kör Servlet via Formulär</button>
    </form>
</div>

<!-- Exempel 2: Anrop via en vanlig textlänk -->
<div class="box">
    <h3>2. Via en vanlig Hyperlänk</h3>
    <a href="TestServlet">Klicka här för att köra Servleten</a>
</div>

<!-- Exempel 3: Det säkraste sättet (Rekommenderas) -->
<div class="box">
    <h3>3. Via Context Path (Bästa praxis)</h3>
    <p>Denna metod dynamiskt hämtar projektets rotmapp, vilket förhindrar 404-fel om din JSP ligger i en undermapp [1].</p>
    <a href="${pageContext.request.contextPath}/TestServlet">Kör Servlet säkert</a> [1]
</div>

<div class="box">
    <h6>GET ALL ITEMS!</h6>

    <%
        request.setAttribute("itemList", Facade.getAllItems());
    %>
    <ul>
        <c:forEach items="${itemList}" var="item">
            <li>${item.name}</li>
        </c:forEach>
    </ul>


</div>


</body>
</html>