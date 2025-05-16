package com.example.studentstay.web;

import com.example.studentstay.model.Room;
import com.example.studentstay.service.RoomService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/roomSave")
public class RoomSaveServlet extends HttpServlet {
    private RoomService roomService;

    @Override
    public void init() {
        roomService = (RoomService) getServletContext().getAttribute("roomService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sid = req.getParameter("id");
        Room r = new Room();
        if (sid != null && !sid.isEmpty()) {
            r.setId(Long.valueOf(sid));
        }
        r.setBuildingId(Long.valueOf(req.getParameter("buildingId")));
        r.setRoomNumber(req.getParameter("roomNumber"));
        r.setCapacity(Integer.parseInt(req.getParameter("capacity")));

        if (r.getId() == null) {
            roomService.create(r);
        } else {
            roomService.update(r);
        }
        resp.sendRedirect(req.getContextPath() + "/rooms");
    }
}