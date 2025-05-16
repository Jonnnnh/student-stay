<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Переселение студента</title>
  <link
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body>
<jsp:include page="partials/header.jsp"/>

<div class="container mt-4">
  <h2>Переселение (заселение #${assignment.id})</h2>
  <form method="post"
        action="${pageContext.request.contextPath}/assignmentTransfer">
    <input type="hidden" name="assignmentId" value="${assignment.id}"/>

    <div class="mb-3">
      <label for="newRoomId" class="form-label">Новая комната</label>
      <select id="newRoomId" name="newRoomId" class="form-select" required>
        <c:forEach var="r" items="${rooms}">
          <option value="${r.id}">${r.id} — ${r.roomNumber} (корпус ${r.buildingId})</option>
        </c:forEach>
      </select>
    </div>

    <div class="mb-3">
      <label for="transferDate" class="form-label">Дата переселения</label>
      <input type="date" id="transferDate" name="transferDate"
             class="form-control" required/>
    </div>

    <button type="submit" class="btn btn-primary">Подтвердить</button>
    <a href="${pageContext.request.contextPath}/assignments"
       class="btn btn-secondary ms-2">Отмена</a>
  </form>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>