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
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/navbar.css"
          type="text/css" />

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/itemDisplay.css"
          type="text/css" />
</head>

<body>
<%--    <jsp:useBean id="cart" class="org.example.ui.ShoppingCart" scope="session" />--%>

    <%
        // 1. Capture which page the user clicked
        String currentPage = request.getParameter("page");
        if (currentPage == null || currentPage.trim().isEmpty()) {
            currentPage = "home"; // Default landing page
        }



        var list = Facade.getAllItems();
        pageContext.setAttribute("itemList", list);
    %>

    <div class="navbar">
        <a class="<%= "home".equals(currentPage) ? "active" : "" %>" href="index.jsp?page=home">
            <i class="fa fa-fw fa-home"></i> Home
        </a>
        <a class="<%= "search".equals(currentPage) ? "active" : "" %>" href="index.jsp?page=search">
            <i class="fa fa-fw fa-search"></i> Search
        </a>
        <a class="<%= "contact".equals(currentPage) ? "active" : "" %>" href="index.jsp?page=contact">
            <i class="fa fa-fw fa-envelope"></i> Contact
        </a>
        <a class="<%= "login".equals(currentPage) ? "active" : "" %>" href="index.jsp?page=login">
            <i class="fa fa-fw fa-user"></i> Cart
        </a>
    </div>

    <div class="content-area" style="padding: 20px;">
        <%
            // 4. Set up the target file route safely
            String targetJsp = "home.jsp"; // Fallback default

            if ("search".equals(currentPage)) {
                targetJsp = "search.jsp";
            } else if ("contact".equals(currentPage)) {
                targetJsp = "contact.jsp";
            } else if ("login".equals(currentPage)) {
                targetJsp = "login.jsp";
            }
        %>
        <!-- Injects the contents of the chosen page right here -->
        <jsp:include page="<%= targetJsp %>" />
    </div>


    <div class="item-grid">
        <c:forEach items="${itemList}" var="item">
            <div class="item-card">
                <div class="item-image"></div>

                <div class="item-content">
                    <h3>${item.name}</h3>

                    <p class="description">
                        ${item.description}
                    </p>

                    <div class="item-footer">
                        <span class="price">
                            ${item.price}
                        </span>

                        <form action="${pageContext.request.contextPath}/cart/add" method="post">
                            <input type="hidden" name="itemId" value="${item.id}">
                            <button type="submit">Add to Cart</button>
                        </form>
                    </div>
                </div>

            </div>
        </c:forEach>
    </div>

    <a href="${pageContext.request.contextPath}/cart">
        <button type="button">View Cart</button>
    </a>
</body>
</html>