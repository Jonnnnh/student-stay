package com.example.studentstay.web;

import com.example.studentstay.model.Assignment;
import com.example.studentstay.service.AssignmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/assignmentTransfer")
public class AssignmentTransferServlet extends HttpServlet {
    private AssignmentService assignmentService;

    @Override
    public void init() {
        assignmentService = (AssignmentService) getServletContext()
                .getAttribute("assignmentService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long assignmentId = Long.valueOf(req.getParameter("assignmentId"));
        Long newRoomId    = Long.valueOf(req.getParameter("newRoomId"));
        LocalDate date    = LocalDate.parse(req.getParameter("transferDate"));
        assignmentService.transfer(assignmentId, newRoomId, date);
        resp.sendRedirect(req.getContextPath() + "/assignments");
    }
}