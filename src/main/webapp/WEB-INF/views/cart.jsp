<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2 class="cart-title">Your Cart</h2>

<div class="cart">
    <c:forEach var="item" items="${cart.items}">
        <div class="cart-item">

            <div class="cart-item-info">
                <span class="cart-item-name">${item.key.name}</span>
                <span class="cart-item-quantity">x${item.value}</span>
                <span class="cart-item-total"> ${item.key.price * item.value} SEK </span>
            </div>

            <form action="${pageContext.request.contextPath}/controller/cart/remove"
                  method="post">
                <input type="hidden"
                       name="itemId"
                       value="${item.key.id}">

                <button class="remove-button" type="submit">
                    Remove
                </button>
            </form>

        </div>
    </c:forEach>

    <c:choose>
        <c:when test="${not empty cart.items}">
            <div class="checkout-bar">
                <span>Total: ${totalPrice} SEK</span>
                <a href="${pageContext.request.contextPath}/controller/order">
                    Proceed to checkout
                </a>
            </div>
        </c:when>

        <c:otherwise>
            <div class="empty-container">
                <p>Your Cart is empty!</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>