package com.example.studentstay.web;

import com.example.studentstay.model.Assignment;
import com.example.studentstay.model.Room;
import com.example.studentstay.service.AssignmentService;
import com.example.studentstay.service.RoomService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/transferForm")
public class TransferFormServlet extends HttpServlet {
    private AssignmentService assignmentService;
    private RoomService roomService;

    @Override
    public void init() {
        assignmentService = (AssignmentService) getServletContext()
                .getAttribute("assignmentService");
        roomService = (RoomService) getServletContext()
                .getAttribute("roomService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String aid = req.getParameter("assignmentId");
        if (aid == null || aid.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/assignments");
            return;
        }
        long assignmentId = Long.parseLong(aid);
        Assignment a = assignmentService.findById(assignmentId);
        req.setAttribute("assignment", a);
        List<Room> rooms = roomService.findAll();
        req.setAttribute("rooms", rooms);
        req.getRequestDispatcher("/WEB-INF/views/transferForm.jsp")
                .forward(req, resp);
    }
}