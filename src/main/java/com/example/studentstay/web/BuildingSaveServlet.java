package com.example.studentstay.web;

import com.example.studentstay.model.Building;
import com.example.studentstay.service.BuildingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/buildingSave")
public class BuildingSaveServlet extends HttpServlet {
    private BuildingService buildingService;

    @Override
    public void init() {
        buildingService = (BuildingService) getServletContext().getAttribute("buildingService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sid = req.getParameter("id");
        Building b = new Building();
        if (sid != null && !sid.isEmpty()) {
            b.setId(Long.valueOf(sid));
        }
        b.setName(req.getParameter("name"));
        b.setAddress(req.getParameter("address"));

        if (b.getId() == null) {
            buildingService.create(b);
        } else {
            buildingService.update(b);
        }
        resp.sendRedirect(req.getContextPath() + "/buildings");
    }
}