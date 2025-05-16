package com.example.studentstay.web;

import com.example.studentstay.model.Assignment;
import com.example.studentstay.service.AssignmentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/assignments")
public class AssignmentListServlet extends HttpServlet {
    private AssignmentService assignmentService;

    @Override
    public void init() {
        assignmentService = (AssignmentService) getServletContext().getAttribute("assignmentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Assignment> list = assignmentService.findActive();
        req.setAttribute("assignments", list);
        req.getRequestDispatcher("/WEB-INF/views/assignments.jsp")
                .forward(req, resp);
    }
}