<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список студентов</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Студенты</h2>
        <a href="${pageContext.request.contextPath}/studentForm" class="btn btn-success">
            + Добавить студента
        </a>
    </div>

    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Дата рождения</th>
            <th>Email</th>
            <th>Телефон</th>
            <th>Действия</th>
            <th>Платежи</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${students}">
            <tr>
                <td>${s.id}</td>
                <td>${s.firstName}</td>
                <td>${s.lastName}</td>
                <td>${s.dateOfBirth}</td>
                <td>${s.email}</td>
                <td>${s.phone}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/studentForm?id=${s.id}"
                       class="btn btn-sm btn-primary">Ред.</a>
                    <a href="${pageContext.request.contextPath}/studentDelete?id=${s.id}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Удалить студента?');">Уд.</a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/payments?studentId=${s.id}"
                       class="btn btn-sm btn-secondary">
                        Платежи
                    </a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty students}">
            <tr><td colspan="7" class="text-center">Нет студентов</td></tr>
        </c:if>
        </tbody>
    </table>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>