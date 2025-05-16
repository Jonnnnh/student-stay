<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${student != null ? 'Редактировать студента' : 'Новый студент'}</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
    <h2>${student != null ? 'Редактировать студента' : 'Добавить студента'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/studentSave">
        <c:if test="${student != null}">
            <input type="hidden" name="id" value="${student.id}"/>
        </c:if>
        <div class="row g-3">
            <div class="col-md-4">
                <label for="firstName" class="form-label">Имя</label>
                <input type="text" id="firstName" name="firstName" class="form-control"
                       value="${student.firstName}" required/>
            </div>
            <div class="col-md-4">
                <label for="lastName" class="form-label">Фамилия</label>
                <input type="text" id="lastName" name="lastName" class="form-control"
                       value="${student.lastName}" required/>
            </div>
            <div class="col-md-4">
                <label for="dob" class="form-label">Дата рождения</label>
                <input type="date" id="dob" name="dob" class="form-control"
                       value="${student.dateOfBirth}" required/>
            </div>
            <div class="col-md-6">
                <label for="email" class="form-label">Email</label>
                <input type="email" id="email" name="email" class="form-control"
                       value="${student.email}"/>
            </div>
            <div class="col-md-6">
                <label for="phone" class="form-label">Телефон</label>
                <input type="text" id="phone" name="phone" class="form-control"
                       value="${student.phone}"/>
            </div>
        </div>
        <div class="mt-4">
            <button type="submit" class="btn btn-primary">
                ${student != null ? 'Сохранить' : 'Создать'}
            </button>
            <a href="${pageContext.request.contextPath}/students" class="btn btn-link">Отмена</a>
        </div>
    </form>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>