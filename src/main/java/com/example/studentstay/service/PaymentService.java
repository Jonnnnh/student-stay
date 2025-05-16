package com.example.studentstay.service;

import com.example.studentstay.dao.PaymentDao;
import com.example.studentstay.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public class PaymentService {
    private final PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public Payment add(Payment p) {
        paymentDao.create(p);
        return p;
    }

    public Payment update(Payment p) {
        paymentDao.update(p);
        return p;
    }

    public void delete(Long id) {
        paymentDao.delete(id);
    }

    public List<Payment> getByStudent(Long studentId) {
        return paymentDao.findByStudent(studentId);
    }

    public BigDecimal getTotalForStudent(Long studentId) {
        return paymentDao.getTotalByStudent(studentId);
    }
}