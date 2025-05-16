package com.example.studentstay.listener;

import com.example.studentstay.service.*;

public record ApplicationContext(
        AuthenticationService authService,
        BuildingService buildingService,
        RoomService roomService,
        StudentService studentService,
        AssignmentService assignmentService,
        PaymentService paymentService,
        DashboardService dashboardService
) {}