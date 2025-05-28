package com.example.studentstay.web;

import com.example.studentstay.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/studentDelete")
public class StudentDeleteServlet extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init() {
        studentService = (StudentService) getServletContext().getAttribute("studentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sid = req.getParameter("id");
        if (sid != null) {
            studentService.delete(Long.valueOf(sid));
        }
        resp.sendRedirect(req.getContextPath() + "/students");
    }
}