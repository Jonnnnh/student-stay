package com.example.studentstay.web;

import com.example.studentstay.model.Building;
import com.example.studentstay.service.BuildingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/buildings")
public class BuildingListServlet extends HttpServlet {
    private BuildingService buildingService;

    @Override
    public void init() {
        buildingService = (BuildingService) getServletContext().getAttribute("buildingService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Building> list = buildingService.findAll();
        req.setAttribute("buildings", list);
        req.getRequestDispatcher("/WEB-INF/views/buildings.jsp")
                .forward(req, resp);
    }
}