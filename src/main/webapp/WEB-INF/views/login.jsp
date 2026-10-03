<h1>LOGIN</h1>

<form method="post"
      action="${pageContext.request.contextPath}/controller/login">

    <label for="username">Username:</label>
    <input type="text"
           id="username"
           name="username"
           required>

    <label for="password">Password:</label>
    <input type="password"
           id="password"
           name="password"
           required>

    <button type="submit">Login</button>

</form>

<c:if test="${not empty loginError}">
    <p>${loginError}</p>
</c:if>