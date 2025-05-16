package com.example.studentstay.listener;

import com.example.studentstay.connection.DriverLoader;
import com.example.studentstay.orm.dialect.PostgreSqlDialect;
import com.example.studentstay.orm.session.EntityManager;
import com.example.studentstay.orm.session.SessionFactory;
import com.example.studentstay.orm.util.ConnectionProvider;
import com.example.studentstay.dao.*;
import com.example.studentstay.service.*;

public class ApplicationContextFactory {

    public static ApplicationContext create() {
        String driver = System.getenv("JDBC_DRIVER") != null
                ? System.getenv("JDBC_DRIVER")
                : "org.postgresql.Driver";
        DriverLoader.load(driver);

        SessionFactory sessionFactory = new SessionFactory(
                new ConnectionProvider(),
                new PostgreSqlDialect()
        );
        EntityManager em = sessionFactory.createEntityManager();

        EmployeeDao     employeeDao     = new EmployeeDao(em);
        RoleDao         roleDao         = new RoleDao(em);
        EmployeeRoleDao employeeRoleDao = new EmployeeRoleDao(em);
        BuildingDao     buildingDao     = new BuildingDao(em);
        RoomDao         roomDao         = new RoomDao(em);
        StudentDao      studentDao      = new StudentDao(em);
        AssignmentDao   assignmentDao   = new AssignmentDao(em);
        PaymentDao      paymentDao      = new PaymentDao(em);

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