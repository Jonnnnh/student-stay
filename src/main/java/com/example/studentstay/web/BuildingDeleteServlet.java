package com.example.studentstay.web;

import com.example.studentstay.service.BuildingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/buildingDelete")
public class BuildingDeleteServlet extends HttpServlet {
    private BuildingService buildingService;

    @Override
    public void init() {
        buildingService = (BuildingService) getServletContext().getAttribute("buildingService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sid = req.getParameter("id");
        if (sid != null) {
            buildingService.delete(Long.valueOf(sid));
        }
        resp.sendRedirect(req.getContextPath() + "/buildings");
    }
}