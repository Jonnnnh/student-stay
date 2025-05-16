package com.example.studentstay.web;

import com.example.studentstay.model.Student;
import com.example.studentstay.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet("/studentForm")
public class StudentFormServlet extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init() {
        studentService = (StudentService) getServletContext().getAttribute("studentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            Student s = studentService.findById(Long.valueOf(id));
            req.setAttribute("student", s);
        }
        req.getRequestDispatcher("/WEB-INF/views/studentForm.jsp")
                .forward(req, resp);
    }
}