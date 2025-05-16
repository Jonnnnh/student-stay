<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${room != null ? 'Редактировать комнату' : 'Новая комната'}</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
    <h2>${room != null ? 'Редактировать комнату' : 'Добавить комнату'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/roomSave">
        <c:if test="${room != null}">
            <input type="hidden" name="id" value="${room.id}"/>
        </c:if>
        <div class="mb-3">
            <label for="buildingId" class="form-label">Корпус</label>
            <select id="buildingId" name="buildingId" class="form-select" required>
                <option value="">— выберите корпус —</option>
                <c:forEach var="b" items="${buildings}">
                    <option value="${b.id}"
                            <c:if test="${room != null && room.buildingId == b.id}">selected</c:if>>
                            ${b.name} (ID:${b.id})
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="roomNumber" class="form-label">Номер комнаты</label>
            <input type="text" id="roomNumber" name="roomNumber" class="form-control"
                   value="${room.roomNumber}" required/>
        </div>
        <div class="mb-3">
            <label for="capacity" class="form-label">Вместимость</label>
            <input type="number" id="capacity" name="capacity" class="form-control"
                   value="${room.capacity}" min="1" required/>
        </div>
        <button type="submit" class="btn btn-primary">
            ${room != null ? 'Сохранить' : 'Создать'}
        </button>
        <a href="${pageContext.request.contextPath}/rooms" class="btn btn-link">Отмена</a>
    </form>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>