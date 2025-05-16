<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand"
           href="${pageContext.request.contextPath}/dashboard">
            StudentStay
        </a>
        <button class="navbar-toggler" type="button"
                data-bs-toggle="collapse"
                data-bs-target="#mainNavbar"
                aria-controls="mainNavbar"
                aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="mainNavbar">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/dashboard"
                       class="nav-link ${fn:endsWith(pageContext.request.requestURI,'/dashboard') ? 'active' : ''}">
                        Панель
                    </a>
                </li>

                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/students"
                       class="nav-link ${fn:endsWith(pageContext.request.requestURI,'/students') ? 'active' : ''}">
                        Студенты
                    </a>
                </li>

                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/buildings"
                       class="nav-link ${fn:endsWith(pageContext.request.requestURI,'/buildings') ? 'active' : ''}">
                        Корпуса
                    </a>
                </li>

                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/rooms"
                       class="nav-link ${fn:endsWith(pageContext.request.requestURI,'/rooms') ? 'active' : ''}">
                        Комнаты
                    </a>
                </li>

                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/assignments"
                       class="nav-link ${fn:endsWith(pageContext.request.requestURI,'/assignments') ? 'active' : ''}">
                        Заселения
                    </a>
                </li>
            </ul>

            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/logout">
                        Выйти
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>