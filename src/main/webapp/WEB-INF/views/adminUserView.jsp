<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2 class="adminUserView-title">Users</h2>

<div class="user-list">
    <c:forEach var="user" items="${userList}">
        <div class="user-row">
            <div class="user-info">
                <span class="user-name">Username: ${user.username}</span>
                <span class="user-role">Role: ${user.role}</span>
            </div>

            <a class="edit-button" href="${pageContext.request.contextPath}/controller/admin/editUser?id=${user.id}">
                Edit
            </a>
        </div>
    </c:forEach>
</div>