<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${building != null ? 'Редактировать корпус' : 'Новый корпус'}</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
    <h2>${building != null ? 'Редактировать корпус' : 'Добавить корпус'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/buildingSave">
        <c:if test="${building != null}">
            <input type="hidden" name="id" value="${building.id}"/>
        </c:if>
        <div class="mb-3">
            <label for="name" class="form-label">Название корпуса</label>
            <input type="text" id="name" name="name" class="form-control"
                   value="${building != null ? building.name : ''}" required/>
        </div>
        <div class="mb-3">
            <label for="address" class="form-label">Адрес</label>
            <input type="text" id="address" name="address" class="form-control"
                   value="${building != null ? building.address : ''}" required/>
        </div>
        <button type="submit" class="btn btn-primary">
            ${building != null ? 'Сохранить' : 'Создать'}
        </button>
        <a href="${pageContext.request.contextPath}/buildings" class="btn btn-link">Отмена</a>
    </form>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>