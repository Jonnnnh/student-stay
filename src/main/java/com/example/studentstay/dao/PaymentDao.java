package com.example.studentstay.dao;

import com.example.studentstay.jdbc.Executor;
import com.example.studentstay.jdbc.ReflectiveResultSetMapper;
import com.example.studentstay.model.Payment;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PaymentDao extends AbstractCrudDao<Payment, Long> {
    public PaymentDao(Executor executor) {
        super(executor, Payment.class);
    }

    @Override
    protected String getTableName() {
        return "payments";
    }

    @Override
    public void create(Payment p) throws SQLException {
        String sql = "INSERT INTO payments (student_id, amount, payment_date, description) VALUES (?, ?, ?, ?)";
        executor.executeUpdate(sql,
                p.getStudentId(),
                p.getAmount(),
                Date.valueOf(p.getPaymentDate()),
                p.getDescription());
    }

    @Override
    public void update(Payment p) throws SQLException {
        String sql = "UPDATE payments SET student_id=?, amount=?, payment_date=?, description=? WHERE id=?";
        executor.executeUpdate(sql,
                p.getStudentId(),
                p.getAmount(),
                Date.valueOf(p.getPaymentDate()),
                p.getDescription(),
                p.getId());
    }

    public List<Payment> findByStudent(long studentId) throws SQLException {
        String sql = "SELECT * FROM payments WHERE student_id = ? ORDER BY payment_date DESC";
        return executor.executeQuery(
                sql,
                new ReflectiveResultSetMapper<>(Payment.class),
                studentId
        );
    }

    public BigDecimal getTotalByStudent(long studentId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(amount),0) FROM payments WHERE student_id = ?";
        BigDecimal sum = executor.executeSingleResult(
                sql,
                (rs) -> rs.getBigDecimal(1),
                studentId
        );
        return sum != null ? sum : BigDecimal.ZERO;
    }
}