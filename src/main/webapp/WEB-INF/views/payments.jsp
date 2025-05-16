<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Платежи студента</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
    <h2 class="mb-3">Платежи студента ID: ${param.studentId}</h2>

    <div class="card mb-4">
        <div class="card-header">Новый платеж</div>
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/paymentSave">
                <input type="hidden" name="studentId" value="${param.studentId}"/>
                <div class="row g-3">
                    <div class="col-md-3">
                        <label for="paymentDate" class="form-label">Дата</label>
                        <input type="date" id="paymentDate" name="paymentDate"
                               class="form-control" required/>
                    </div>
                    <div class="col-md-3">
                        <label for="amount" class="form-label">Сумма</label>
                        <input type="number" step="0.01" id="amount" name="amount"
                               class="form-control" required/>
                    </div>
                    <div class="col-md-4">
                        <label for="description" class="form-label">Описание</label>
                        <input type="text" id="description" name="description"
                               class="form-control"/>
                    </div>
                    <div class="col-md-2 align-self-end">
                        <button type="submit" class="btn btn-success w-100">Добавить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Описание</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${payments}">
            <tr>
                <td>${p.id}</td>
                <td>${p.paymentDate}</td>
                <td>${p.amount}</td>
                <td>${p.description}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/paymentDelete?id=${p.id}&studentId=${param.studentId}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Удалить платеж?');">
                        Уд.
                    </a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty payments}">
            <tr>
                <td colspan="5" class="text-center">Нет платежей</td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <a href="${pageContext.request.contextPath}/students" class="btn btn-link">
        ← назад к списку студентов
    </a>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>