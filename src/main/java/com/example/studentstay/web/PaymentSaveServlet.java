package com.example.studentstay.web;

import com.example.studentstay.model.Payment;
import com.example.studentstay.service.PaymentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/paymentSave")
public class PaymentSaveServlet extends HttpServlet {
    private PaymentService paymentService;

    @Override
    public void init() {
        paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Payment p = new Payment();
        p.setStudentId(Long.valueOf(req.getParameter("studentId")));
        p.setAmount(new BigDecimal(req.getParameter("amount")));
        p.setPaymentDate(LocalDate.parse(req.getParameter("paymentDate")));
        p.setDescription(req.getParameter("description"));

        paymentService.add(p);
        resp.sendRedirect(req.getContextPath()
                + "/payments?studentId=" + p.getStudentId());
    }
}