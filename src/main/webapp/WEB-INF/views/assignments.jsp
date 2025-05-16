<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8"/>
    <title>Текущие заселения</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet"/>
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container my-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="mb-0">Текущие заселения</h2>
        <a href="${pageContext.request.contextPath}/assignmentForm"
           class="btn btn-success">
            + Новое заселение
        </a>
    </div>

    <div class="table-responsive">
        <table class="table table-striped table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th style="width:5%">#</th>
                <th style="width:15%">Студент</th>
                <th style="width:15%">Комната</th>
                <th style="width:20%">Заселён</th>
                <th style="width:20%">Выезд</th>
                <th style="width:25%">Действия</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="a" items="${assignments}">
                <tr>
                    <td>${a.id}</td>
                    <td>${a.studentId}</td>
                    <td>${a.roomId}</td>
                    <td>${a.assignDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty a.leaveDate}">
                                ${a.leaveDate}
                            </c:when>
                            <c:otherwise>
                                —
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <div class="d-flex align-items-center gap-2">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/assignmentLeave"
                                  class="d-flex">
                                <input type="hidden" name="assignmentId" value="${a.id}"/>
                                <div class="input-group input-group-sm">
                                    <input type="date" name="leaveDate"
                                           class="form-control"
                                           required/>
                                    <button type="submit" class="btn btn-warning">
                                        Выезд
                                    </button>
                                </div>
                            </form>
                            <a href="${pageContext.request.contextPath}/transferForm?assignmentId=${a.id}"
                               class="btn btn-info btn-sm">
                                Переселить
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty assignments}">
                <tr>
                    <td colspan="6" class="text-center">Нет активных заселений</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>