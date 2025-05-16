package com.example.studentstay.service;

import com.example.studentstay.dao.PaymentDao;
import com.example.studentstay.model.Payment;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private final PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public Payment add(Payment p) {
        try {
            paymentDao.create(p);
            return p;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Payment update(Payment p) {
        try {
            paymentDao.update(p);
            return p;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            paymentDao.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Payment> getByStudent(Long studentId) {
        try {
            return paymentDao.findByStudent(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getTotalForStudent(Long studentId) {
        try {
            return paymentDao.getTotalByStudent(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}