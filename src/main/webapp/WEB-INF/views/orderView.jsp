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


<div class="ckeckout-container">
    <h1>Checkout</h1>
    <p>Your total will be: ${priceTotal} SEK</p>

    <form action="${pageContext.request.contextPath}/order" method="post">
        <button type="submit">
            Place order
        </button>
    </form>

<%--    <form action="${pageContext.request.contextPath}/order" method="post">--%>
<%--        <div class="form-group">--%>
<%--            <label for="firstName">First name*</label>--%>
<%--            <input--%>
<%--                    type="text"--%>
<%--                    id="firstName"--%>
<%--                    name="firstName"--%>
<%--                    required>--%>
<%--        </div>--%>
<%--        <div class="form-group">--%>
<%--            <label for="lastName">Last name*</label>--%>
<%--            <input--%>
<%--                    type="text"--%>
<%--                    id="lastName"--%>
<%--                    name="lastName"--%>
<%--                    required>--%>
<%--        </div>--%>
<%--        <div class="form-group">--%>
<%--            <label for="address">Address*</label>--%>
<%--            <input--%>
<%--                    type="text"--%>
<%--                    id="address"--%>
<%--                    name="address"--%>
<%--                    placeholder="Street name and number"--%>
<%--                    required>--%>
<%--        </div>--%>

<%--        <div class="row">--%>

<%--            <div class="form-group">--%>
<%--                <label for="postalCode">Postal code*</label>--%>
<%--                <input--%>
<%--                        type="text"--%>
<%--                        id="postalCode"--%>
<%--                        name="postalCode"--%>
<%--                        placeholder="123 45"--%>
<%--                        required>--%>
<%--            </div>--%>

<%--            <div class="form-group">--%>
<%--                <label for="city">City*</label>--%>
<%--                <input--%>
<%--                        type="text"--%>
<%--                        id="city"--%>
<%--                        name="city"--%>
<%--                        required>--%>
<%--            </div>--%>

<%--        </div>--%>

<%--        <div class="form-group">--%>
<%--            <label for="country">Country*</label>--%>
<%--            <input--%>
<%--                    type="text"--%>
<%--                    id="country"--%>
<%--                    name="country"--%>
<%--                    value="Sweden"--%>
<%--                    required>--%>
<%--        </div>--%>

<%--        <div class="form-group">--%>
<%--            <label for="phone">Phone number</label>--%>
<%--            <input--%>
<%--                    type="tel"--%>
<%--                    id="phone"--%>
<%--                    name="phone">--%>
<%--        </div>--%>

<%--        <button type="submit">--%>
<%--            Place order--%>
<%--        </button>--%>
<%--    </form>--%>
</div>