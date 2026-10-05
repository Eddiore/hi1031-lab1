<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>My Profile</h1>

<p>Welcome, ${sessionScope.user.username}! Your role: ${sessionScope.user.role}</p>

<c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'STAFF'}">
    <h3>Assigned Orders</h3>
    <c:forEach var="order" items="${assignedOrders}">
        <div class="order-item">

            <div class="order-item-info">
                <span class="order-id">Id: ${order.id}</span>
                <span class="order-item-name">Placed by: ${order.username}</span>
                <span class="order-status">Status: ${order.status}</span>
            </div>

            <c:if test="${order.status == 'PACKING'}">
                <form action="${pageContext.request.contextPath}/warehouse/package"
                      method="post">
                    <input type="hidden"
                           name="orderId"
                           value="${order.id}">

                    <button class="Assign-button" type="submit">
                        Package
                    </button>
                </form>
            </c:if>

        </div>
    </c:forEach>
</c:if>


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

<c:if test="${sessionScope.user.role == 'ADMIN' || sessionScope.user.role == 'STAFF'}">
    <h2>Staff Functions</h2>
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/warehouse">
                View Orders
            </a>
        </li>
    </ul>
</c:if>