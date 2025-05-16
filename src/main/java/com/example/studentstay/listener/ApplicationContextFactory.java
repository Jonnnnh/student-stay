package com.example.studentstay.listener;

import com.example.studentstay.config.DatabaseConfig;
import com.example.studentstay.connection.ConnectionStrategy;
import com.example.studentstay.connection.DatabaseConnection;
import com.example.studentstay.connection.DriverManagerStrategy;
import com.example.studentstay.jdbc.Executor;
import com.example.studentstay.dao.*;
import com.example.studentstay.service.*;

public class ApplicationContextFactory {

    public static ApplicationContext create() {
        String url      = System.getenv("JDBC_URL");
        String user     = System.getenv("JDBC_USER");
        String password = System.getenv("JDBC_PASSWORD");
        String driver   = System.getenv("JDBC_DRIVER") != null
                ? System.getenv("JDBC_DRIVER")
                : "org.postgresql.Driver";

        DatabaseConfig dbConfig = new DatabaseConfig(url, user, password);
        ConnectionStrategy strategy = new DriverManagerStrategy();
        DatabaseConnection connectionProvider =
                new DatabaseConnection(dbConfig, strategy, driver);
        Executor executor = new Executor(connectionProvider);

        EmployeeDao       employeeDao     = new EmployeeDao(executor);
        RoleDao           roleDao         = new RoleDao(executor);
        EmployeeRoleDao   employeeRoleDao = new EmployeeRoleDao(executor);
        BuildingDao       buildingDao     = new BuildingDao(executor);
        RoomDao           roomDao         = new RoomDao(executor);
        StudentDao        studentDao      = new StudentDao(executor);
        AssignmentDao     assignmentDao   = new AssignmentDao(executor);
        PaymentDao        paymentDao      = new PaymentDao(executor);

        AuthenticationService authService     =
                new AuthenticationService(employeeDao, roleDao, employeeRoleDao);
        BuildingService       buildingService =
                new BuildingService(buildingDao);
        RoomService           roomService     =
                new RoomService(roomDao);
        StudentService        studentService  =
                new StudentService(studentDao, paymentDao);
        AssignmentService     assignmentService =
                new AssignmentService(assignmentDao);
        PaymentService        paymentService  =
                new PaymentService(paymentDao);
        DashboardService      dashboardService =
                new DashboardService(roomDao, assignmentDao, studentDao);

        return new ApplicationContext(
                authService,
                buildingService,
                roomService,
                studentService,
                assignmentService,
                paymentService,
                dashboardService
        );
    }
}