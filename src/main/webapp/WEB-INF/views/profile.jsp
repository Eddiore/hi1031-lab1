<h1>My Profile</h1>

<p>Welcome, ${sessionScope.user.username}! Your role: ${sessionScope.user.role}</p>

<%--<p>Email: ${sessionScope.user.email}</p>--%>

<form method="post"
      action="${pageContext.request.contextPath}/controller/profile/logout">

    <button type="submit">Log out</button>

</form>