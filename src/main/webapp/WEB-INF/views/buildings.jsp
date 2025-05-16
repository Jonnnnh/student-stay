<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список корпусов</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Корпуса</h2>
        <a href="${pageContext.request.contextPath}/buildingForm" class="btn btn-success">
            + Добавить корпус
        </a>
    </div>
    <table class="table table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Адрес</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${buildings}">
            <tr>
                <td>${b.id}</td>
                <td>${b.name}</td>
                <td>${b.address}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/buildingForm?id=${b.id}"
                       class="btn btn-sm btn-primary">Ред.</a>
                    <a href="${pageContext.request.contextPath}/buildingDelete?id=${b.id}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Удалить корпус?');">Уд.</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty buildings}">
            <tr><td colspan="4" class="text-center">Нет корпусов</td></tr>
        </c:if>
        </tbody>
    </table>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>