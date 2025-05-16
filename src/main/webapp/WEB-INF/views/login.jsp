<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body class="bg-light">
<div class="container">
    <div class="row justify-content-center align-items-center" style="height:100vh">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h3 class="card-title text-center mb-4">Авторизация</h3>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                                ${error}
                        </div>
                    </c:if>

                    <form method="post" action="${pageContext.request.contextPath}/login">
                        <div class="mb-3">
                            <label for="username" class="form-label">Логин</label>
                            <input
                                    type="text" class="form-control" id="username"
                                    name="username" required autofocus>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input
                                    type="password" class="form-control" id="password"
                                    name="password" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">
                            Войти
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>