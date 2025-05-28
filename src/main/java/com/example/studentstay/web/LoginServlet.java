package com.example.studentstay.web;

import com.example.studentstay.execption.BusinessException;
import com.example.studentstay.model.Employee;
import com.example.studentstay.service.AuthenticationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/", "/login"})
public class LoginServlet extends HttpServlet {
    private AuthenticationService authService;

    @Override
    public void init() {
        authService = (AuthenticationService) getServletContext().getAttribute("authService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            Employee user = authService.login(username, password);
            HttpSession session = req.getSession(true);
            session.setAttribute("employee", user);
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } catch (BusinessException ex) {
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}