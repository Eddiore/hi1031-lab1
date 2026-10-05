<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>ORDER!!!</h1>

<div class="order-list">
    <c:forEach var="item" items="${orderMap}">
        <div class="order-map-item">
            <div class="cart-item-info">
                <span class="cart-item-name">${item.key.name}</span>
                <span class="cart-item-quantity">x${item.value}</span>
                <span class="cart-item-total"> ${item.key.price * item.value} SEK </span>
            </div>
        </div>
    </c:forEach>
</div>

<c:choose>
    <c:when test="${not empty failedOrderMsg}">
        <p>${failedOrderMsg}</p>
    </c:when>

    <c:otherwise>
        <div class="ckeckout-container">
            <h1>Checkout</h1>
            <p>Your total will be: ${priceTotal} SEK</p>

            <form action="${pageContext.request.contextPath}/order" method="post">
                <button type="submit">
                    Place order
                </button>
            </form>
        </div>
    </c:otherwise>
</c:choose>

