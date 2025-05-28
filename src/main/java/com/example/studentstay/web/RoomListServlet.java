package com.example.studentstay.web;

import com.example.studentstay.model.Room;
import com.example.studentstay.service.RoomService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/rooms")
public class RoomListServlet extends HttpServlet {
    private RoomService roomService;

    @Override
    public void init() {
        roomService = (RoomService) getServletContext().getAttribute("roomService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Room> list = roomService.findAllWithFreeCount();
        req.setAttribute("rooms", list);
        req.getRequestDispatcher("/WEB-INF/views/rooms.jsp")
                .forward(req, resp);
    }
}