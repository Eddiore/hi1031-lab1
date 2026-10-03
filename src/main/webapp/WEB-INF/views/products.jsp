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