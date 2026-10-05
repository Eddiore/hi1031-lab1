
<h2 class="adminProductView-title">Edit User: ${selectedItem.name}</h2>

<div class="edit-product">
    <form action="${pageContext.request.contextPath}/controller/admin/updateProduct"
          method="post">

        <input type="hidden" name="id" value="${selectedItem.id}">
        <input type="hidden" name="category" value="${selectedItem.category}">

        <div class="form-group">
            <label for="name">Name:</label>

            <input type="text"
                   id="name"
                   name="name"
                   value="${selectedItem.name}"
                   required>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>

            <input type="text"
                   id="description"
                   name="description"
                   value="${selectedItem.description}"
                   required>
        </div>

        <div class="form-group">
            <label for="price">Price:</label>

            <input type="text"
                   id="price"
                   name="price"
                   value="${selectedItem.price}"
                   required>
        </div>

        <div class="form-group">
            <label for="stock">Stock:</label>

            <input type="text"
                   id="stock"
                   name="stock"
                   value="${selectedItem.stock}"
                   required>
        </div>

        <button type="submit">
            Save
        </button>

    </form>

    <form action="${pageContext.request.contextPath}/controller/admin/products"
          method="get">

        <button type="submit">
            Cancel
        </button>

    </form>

</div>