package com.example.studentstay.web;

import com.example.studentstay.model.Room;
import com.example.studentstay.service.BuildingService;
import com.example.studentstay.service.RoomService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/roomForm")
public class RoomFormServlet extends HttpServlet {
    private RoomService roomService;
    private BuildingService buildingService;

    @Override
    public void init() {
        roomService = (RoomService) getServletContext().getAttribute("roomService");
        buildingService = (BuildingService) getServletContext().getAttribute("buildingService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("buildings", buildingService.findAll());
        String id = req.getParameter("id");
        if (id != null) {
            Room r = roomService.findById(Long.valueOf(id));
            req.setAttribute("room", r);
        }
        req.getRequestDispatcher("/WEB-INF/views/roomForm.jsp")
                .forward(req, resp);
    }
}