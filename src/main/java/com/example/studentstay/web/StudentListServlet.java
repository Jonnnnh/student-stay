package com.example.studentstay.web;

import com.example.studentstay.model.Student;
import com.example.studentstay.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class StudentListServlet extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init() {
        studentService = (StudentService) getServletContext().getAttribute("studentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Student> list = studentService.findAll();
        req.setAttribute("students", list);
        req.getRequestDispatcher("/WEB-INF/views/students.jsp")
                .forward(req, resp);
    }
}