<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Current Orders</h1>

<div class="orders-list">
    <c:forEach var="order" items="${orderList}">
        <div class="order-item">

            <div class="order-item-info">
                <span class="order-id">Id: ${order.id}</span>
                <span class="order-item-name">Placed by: ${order.username}</span>
                <span class="order-status">Status: ${order.status}</span>
            </div>

            <form action="${pageContext.request.contextPath}/warehouse/assign"
                  method="post">
                <input type="hidden"
                       name="orderId"
                       value="${order.id}">

                <button class="Assign-button" type="submit">
                    Assign to me
                </button>
            </form>

        </div>
    </c:forEach>
</div>