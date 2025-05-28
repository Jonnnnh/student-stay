package com.example.studentstay.web;

import com.example.studentstay.model.Payment;
import com.example.studentstay.service.PaymentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/payments")
public class PaymentListServlet extends HttpServlet {
    private PaymentService paymentService;

    @Override
    public void init() {
        paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sid = req.getParameter("studentId");
        if (sid == null || sid.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/students");
            return;
        }

        Long studentId;
        try {
            studentId = Long.valueOf(sid);
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный studentId: " + sid);
            return;
        }
        List<Payment> list = paymentService.getByStudent(studentId);
        req.setAttribute("payments", list);
        req.getRequestDispatcher("/WEB-INF/views/payments.jsp")
                .forward(req, resp);
    }
}