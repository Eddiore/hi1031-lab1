<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>GOODS AND WARES</h1>
<div class="item-grid">
    <c:forEach items="${productList}" var="item">
        <div class="item-card">
            <div class="item-image"></div>

            <div class="item-content">
                <h3>${item.name}</h3>

                <p class="description">
                    ${item.description}
                </p>

                <c:choose>
                    <c:when test="${item.stock == 0}">
                        <span>Out of stock</span>
                    </c:when>

                    <c:when test="${item.stock <= 5}">
                        <span>Only ${item.stock} left!</span>
                    </c:when>

                    <c:otherwise>
                        <span>${item.stock} in stock</span>
                    </c:otherwise>
                </c:choose>

                <div class="item-footer">
                    <span class="price">
                        ${item.price} SEK
                    </span>

                    <form action="${pageContext.request.contextPath}/controller/cart/add" method="post">
                        <input type="hidden" name="itemId" value="${item.id}">
                        <button type="submit">Add to Cart</button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>