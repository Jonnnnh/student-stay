package com.example.studentstay.web;

import com.example.studentstay.model.Student;
import com.example.studentstay.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/studentSave")
public class StudentSaveServlet extends HttpServlet {
    private StudentService studentService;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ISO_DATE;

    @Override
    public void init() {
        studentService = (StudentService) getServletContext().getAttribute("studentService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sid = req.getParameter("id");
        Student s = new Student();
        if (sid != null && !sid.isEmpty()) {
            s.setId(Long.valueOf(sid));
        }
        s.setFirstName(req.getParameter("firstName"));
        s.setLastName(req.getParameter("lastName"));
        try {
            s.setDateOfBirth(LocalDate.parse(req.getParameter("dob"), DTF));
        } catch (DateTimeParseException ex) {
            throw new ServletException("Invalid date format", ex);
        }
        s.setEmail(req.getParameter("email"));
        s.setPhone(req.getParameter("phone"));

        if (s.getId() == null) {
            studentService.create(s);
        } else {
            studentService.update(s);
        }
        resp.sendRedirect(req.getContextPath() + "/students");
    }
}