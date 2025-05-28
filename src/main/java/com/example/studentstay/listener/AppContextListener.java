package com.example.studentstay.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        try {
            ApplicationContext appCtx = ApplicationContextFactory.create();
            ctx.setAttribute("authService", appCtx.authService());
            ctx.setAttribute("buildingService", appCtx.buildingService());
            ctx.setAttribute("roomService", appCtx.roomService());
            ctx.setAttribute("studentService", appCtx.studentService());
            ctx.setAttribute("assignmentService", appCtx.assignmentService());
            ctx.setAttribute("paymentService", appCtx.paymentService());
            ctx.setAttribute("dashboardService", appCtx.dashboardService());
            ctx.log("AppContextListener initialization successful");
        } catch (Exception ex) {
            ctx.log("Error during AppContextListener initialization", ex);
            throw new RuntimeException("Listener initialization failed", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}