<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>My Profile</h1>

<p>Welcome, ${sessionScope.user.username}! Your role: ${sessionScope.user.role}</p>

<form method="post"
      action="${pageContext.request.contextPath}/controller/profile/logout">

    <button type="submit">Log out</button>
</form>

<c:if test="${sessionScope.user.role == 'ADMIN'}">
    <h2>Admin functions</h2>
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/admin/users">
                Administrate Users
            </a>
        <li>
            <a href="${pageContext.request.contextPath}/admin/products">
                Administrate Products
            </a>
        </li>
    </ul>
</c:if>