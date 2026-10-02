<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<jsp:useBean id="cart"--%>
<%--             class="org.example.ui.ShoppingCart"--%>
<%--             scope="session"/>--%>

<%--<%--%>
<%--    String itemID = request.getParameter("itemID");--%>

<%--    if (itemID != null) {--%>
<%--        cart.addItem(itemID);--%>
<%--    }--%>

<%--%>--%>


<h2> Your cart </h2>

<c:forEach var="item" items="${cart.items}">
    <div>
            ${item.key}
        x ${item.value}
    </div>
</c:forEach>