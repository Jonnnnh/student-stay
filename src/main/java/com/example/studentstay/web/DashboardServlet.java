package com.example.studentstay.web;

import com.example.studentstay.service.DashboardService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private DashboardService dashboardService;

    @Override
    public void init() {
        dashboardService = (DashboardService) getServletContext().getAttribute("dashboardService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("occupancyPct", dashboardService.getOccupancyPercentage());
        req.setAttribute("avgStayDays", dashboardService.getAverageStayDays());
        req.setAttribute("warnings", dashboardService.getWarnings());
        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
                .forward(req, resp);
    }
}