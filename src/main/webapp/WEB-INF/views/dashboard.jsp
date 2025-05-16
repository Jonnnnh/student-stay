<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Панель администратора</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
    <div class="row gy-3">
        <div class="col-md-6">
            <div class="card text-white bg-primary h-100">
                <div class="card-body">
                    <h5 class="card-title">Процент заполненности</h5>
                    <p class="display-4 mb-0">
                        <fmt:formatNumber
                                value="${occupancyPct}"
                                type="number"
                                minFractionDigits="3"
                                maxFractionDigits="3"
                        />%
                    </p>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card text-white bg-success h-100">
                <div class="card-body">
                    <h5 class="card-title">Средняя длительность проживания</h5>
                    <p class="display-4 mb-0">${avgStayDays} дн.</p>
                </div>
            </div>
        </div>
    </div>

    <div class="card mt-4">
        <div class="card-header">
            Предупреждения: проживание более 6 месяцев
        </div>
        <ul class="list-group list-group-flush">
            <c:choose>
                <c:when test="${not empty warnings}">
                    <c:forEach var="sid" items="${warnings}">
                        <li class="list-group-item">
                            Студент ID: ${sid}
                        </li>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <li class="list-group-item">Нет предупреждений</li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>