package com.example.studentstay.web;

import com.example.studentstay.service.AssignmentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/assignmentLeave")
public class AssignmentLeaveServlet extends HttpServlet {
    private AssignmentService assignmentService;

    @Override
    public void init() {
        assignmentService = (AssignmentService) getServletContext().getAttribute("assignmentService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long aid = Long.valueOf(req.getParameter("assignmentId"));
        LocalDate date = LocalDate.parse(req.getParameter("leaveDate"));

        assignmentService.leave(aid, date);
        resp.sendRedirect(req.getContextPath() + "/assignments");
    }
}