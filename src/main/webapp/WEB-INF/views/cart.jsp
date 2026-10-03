<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2> Your cart </h2>

<c:forEach var="item" items="${cart.items}">
    <div>
            ${item.key.name}
        x ${item.value}
    </div>
</c:forEach>