package com.example.studentstay.web;

import com.example.studentstay.service.PaymentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/paymentDelete")
public class PaymentDeleteServlet extends HttpServlet {
    private PaymentService paymentService;

    @Override
    public void init() {
        paymentService = (PaymentService) getServletContext().getAttribute("paymentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Long sid = Long.valueOf(req.getParameter("studentId"));
        paymentService.delete(id);
        resp.sendRedirect(req.getContextPath()
                + "/payments?studentId=" + sid);
    }
}