
<h2 class="adminUserView-title">Edit User: ${selectedUser.username}</h2>

<div class="edit-user">
    <form action="${pageContext.request.contextPath}/controller/admin/updateUser"
          method="post">

        <input type="hidden" name="id" value="${selectedUser.id}">
        <input type="hidden" name="passwordHash" value="${selectedUser.passwordHash}">

        <div class="form-group">
            <label for="username">Username:</label>

            <input type="text"
                   id="username"
                   name="username"
                   value="${selectedUser.username}"
                   required>
        </div>

        <div class="form-group">
            <label for="role">Role:</label>

            <select id="role" name="role">
                <option value="CUSTOMER"
                ${selectedUser.role == 'CUSTOMER' ? 'selected' : ''}>
                    CUSTOMER
                </option>

                <option value="STAFF"
                ${selectedUser.role == 'STAFF' ? 'selected' : ''}>
                    STAFF
                </option>

                <option value="ADMIN"
                ${selectedUser.role == 'ADMIN' ? 'selected' : ''}>
                    ADMIN
                </option>
            </select>
        </div>

        <button type="submit">
            Save
        </button>

    </form>

    <form action="${pageContext.request.contextPath}/controller/admin/users"
          method="get">

        <button type="submit">
            Cancel
        </button>

    </form>

</div>