<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar">

    <div class="navbar-left">
        <a href="${pageContext.request.contextPath}/controller/home">
            Home
        </a>

        <a href="${pageContext.request.contextPath}/controller/products">
            Products
        </a>
    </div>

    <div class="navbar-right">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <span class="current-user">
                    Current user: ${sessionScope.user.username}
                </span>
            </c:when>

            <c:otherwise>
                <span class="current-user">
                    Not logged in
                </span>
            </c:otherwise>
        </c:choose>

        <a href="${pageContext.request.contextPath}/controller/profile">
            Profile
        </a>

        <a href="${pageContext.request.contextPath}/controller/cart/">
            Cart
        </a>

    </div>

</nav>