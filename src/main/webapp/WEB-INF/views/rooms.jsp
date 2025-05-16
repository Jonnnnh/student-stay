<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список комнат</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Комнаты</h2>
        <a href="${pageContext.request.contextPath}/roomForm" class="btn btn-success">
            + Добавить комнату
        </a>
    </div>

    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Корпус (ID)</th>
            <th>Номер</th>
            <th>Вместимость</th>
            <th>Занято</th>
            <th>Свободно</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="r" items="${rooms}">
            <tr>
                <td>${r.id}</td>
                <td>${r.buildingId}</td>
                <td>${r.roomNumber}</td>
                <td>${r.capacity}</td>
                <td>
                    <c:out value="${r.freeCount != null ? r.capacity - r.freeCount : '-'}"/>
                </td>
                <td>
                    <c:out value="${r.freeCount != null ? r.freeCount : '-'}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/roomForm?id=${r.id}"
                       class="btn btn-sm btn-primary">Ред.</a>
                    <a href="${pageContext.request.contextPath}/roomDelete?id=${r.id}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Удалить комнату?');">Уд.</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty rooms}">
            <tr><td colspan="7" class="text-center">Нет комнат</td></tr>
        </c:if>
        </tbody>
    </table>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>