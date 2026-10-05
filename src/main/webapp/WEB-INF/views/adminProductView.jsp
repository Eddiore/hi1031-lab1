<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2 class="adminProductView-title">Products</h2>

<div class="product-list">
    <c:forEach var="item" items="${productList}">
        <div class="product-row">
            <div class="product-info">
                <span class="product-name">Name: ${item.name}</span>
                <span class="product-description">Description: ${item.description}</span>
            </div>

            <a class="editProduct-button" href="${pageContext.request.contextPath}/controller/admin/editProduct?id=${item.id}">
                Edit
            </a>
        </div>
    </c:forEach>
</div>
