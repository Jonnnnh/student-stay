package com.example.studentstay.web;

import com.example.studentstay.service.AssignmentService;
import com.example.studentstay.service.StudentService;
import com.example.studentstay.service.RoomService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/assignmentForm")
public class AssignmentFormServlet extends HttpServlet {
    private AssignmentService assignmentService;
    private StudentService studentService;
    private RoomService roomService;

    @Override
    public void init() {
        assignmentService = (AssignmentService) getServletContext().getAttribute("assignmentService");
        studentService = (StudentService) getServletContext().getAttribute("studentService");
        roomService = (RoomService) getServletContext().getAttribute("roomService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("students", studentService.findAll());
        req.setAttribute("rooms",    roomService.findAll());
        req.getRequestDispatcher("/WEB-INF/views/assignmentForm.jsp")
                .forward(req, resp);
    }
}