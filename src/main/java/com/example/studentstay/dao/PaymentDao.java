package com.example.studentstay.dao;

import com.example.studentstay.model.Payment;
import com.example.studentstay.orm.query.CriteriaBuilder;
import com.example.studentstay.orm.query.CriteriaQuery;
import com.example.studentstay.orm.query.Query;
import com.example.studentstay.orm.query.Root;
import com.example.studentstay.orm.repository.JdbcRepository;
import com.example.studentstay.orm.repository.Repository;
import com.example.studentstay.orm.session.EntityManager;

import java.math.BigDecimal;
import java.util.List;

public class PaymentDao {
    private final Repository<Payment, Long> repo;
    private final EntityManager em;
    private final CriteriaBuilder cb = new CriteriaBuilder();

    public PaymentDao(EntityManager em) {
        this.em   = em;
        this.repo = new JdbcRepository<>(em, Payment.class);
    }

    public Payment findById(Long id) {
        return repo.find(id);
    }

    public List<Payment> findAll() {
        return repo.findAll();
    }

    public Payment create(Payment p) {
        repo.save(p);
        return p;
    }

    public Payment update(Payment p) {
        repo.save(p);
        return p;
    }

    public void delete(Long id) {
        var p = repo.find(id);
        if (p != null) repo.delete(p);
    }

    public List<Payment> findByStudent(long studentId) {
        CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);
        Root<Payment> root = cq.from(Payment.class);
        cq.where(cb.equal(root.get("studentId"), studentId));
        return new Query<>(em, cq).getResultList();
    }

    public BigDecimal getTotalByStudent(long studentId) {
        String sql = "SELECT COALESCE(SUM(amount),0) FROM payments WHERE student_id=?";
        try (var conn = em.getConnectionProvider().getConnection();
             var ps   = conn.prepareStatement(sql)) {
            ps.setLong(1, studentId);
            try (var rs = ps.executeQuery()) {
                return rs.next()
                        ? rs.getBigDecimal(1)
                        : BigDecimal.ZERO;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}