package com.example.studentstay.web;

import com.example.studentstay.service.AssignmentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/assignmentSave")
public class AssignmentSaveServlet extends HttpServlet {
    private AssignmentService assignmentService;

    @Override
    public void init() {
        assignmentService = (AssignmentService) getServletContext().getAttribute("assignmentService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long studentId = Long.valueOf(req.getParameter("studentId"));
        Long roomId = Long.valueOf(req.getParameter("roomId"));
        LocalDate date = LocalDate.parse(req.getParameter("assignDate"));

        assignmentService.assign(studentId, roomId, date);
        resp.sendRedirect(req.getContextPath() + "/assignments");
    }
}