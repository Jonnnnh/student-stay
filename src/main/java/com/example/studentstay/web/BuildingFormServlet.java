package com.example.studentstay.web;

import com.example.studentstay.model.Building;
import com.example.studentstay.service.BuildingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/buildingForm")
public class BuildingFormServlet extends HttpServlet {
    private BuildingService buildingService;

    @Override
    public void init() {
        buildingService = (BuildingService) getServletContext().getAttribute("buildingService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            Building b = buildingService.findById(Long.valueOf(id));
            req.setAttribute("building", b);
        }
        req.getRequestDispatcher("/WEB-INF/views/buildingForm.jsp")
                .forward(req, resp);
    }
}