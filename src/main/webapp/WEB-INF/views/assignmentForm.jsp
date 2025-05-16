<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Новое заселение</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
    <h2>Оформить заселение</h2>
    <form method="post" action="${pageContext.request.contextPath}/assignmentSave">
        <div class="mb-3">
            <label for="studentId" class="form-label">Студент</label>
            <select id="studentId" name="studentId" class="form-select" required>
                <option value="">— выберите —</option>
                <c:forEach var="s" items="${students}">
                    <option value="${s.id}">${s.firstName} ${s.lastName} (ID:${s.id})</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="roomId" class="form-label">Комната</label>
            <select id="roomId" name="roomId" class="form-select" required>
                <option value="">— выберите —</option>
                <c:forEach var="r" items="${rooms}">
                    <option value="${r.id}">
                        Корпус ${r.buildingId}, №${r.roomNumber} (ID:${r.id})
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="assignDate" class="form-label">Дата заселения</label>
            <input type="date" id="assignDate" name="assignDate"
                   class="form-control" required
                   value="${param.assignDate != null ? param.assignDate : ''}"/>
        </div>
        <button type="submit" class="btn btn-primary">Заселить</button>
        <a href="${pageContext.request.contextPath}/assignments" class="btn btn-link">Отмена</a>
    </form>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>