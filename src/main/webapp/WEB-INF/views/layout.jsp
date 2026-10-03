<!DOCTYPE html>
<html>
<head>
    <title>Webshop</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/navbar.css"
          type="text/css" />

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/itemDisplay.css"
          type="text/css" />
</head>

<body>
    <%@ include file="/fragments/navbar.jsp" %>
    
    <main>
        <jsp:include page="${contentPage}" />
    </main>

</body>
</html>