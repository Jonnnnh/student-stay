package com.example.studentstay.dao;

import com.example.studentstay.model.Assignment;
import com.example.studentstay.model.Payment;
import com.example.studentstay.model.Student;
import com.example.studentstay.orm.query.CriteriaBuilder;
import com.example.studentstay.orm.query.CriteriaQuery;
import com.example.studentstay.orm.query.Query;
import com.example.studentstay.orm.repository.JdbcRepository;
import com.example.studentstay.orm.repository.Repository;
import com.example.studentstay.orm.session.EntityManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private final Repository<Student, Long> repo;
    private final EntityManager em;
    private final CriteriaBuilder cb = new CriteriaBuilder();

    public StudentDao(EntityManager em) {
        this.em   = em;
        this.repo = new JdbcRepository<>(em, Student.class);
    }

    public Student findById(Long id) {
        return repo.find(id);
    }

    public List<Student> findAll() {
        return repo.findAll();
    }

    public Student create(Student s) {
        repo.save(s);
        return s;
    }

    public Student update(Student s) {
        repo.save(s);
        return s;
    }

    public void delete(Long id) {
        Student s = repo.find(id);
        if (s != null) repo.delete(s);
    }

    public List<Student> findByLastName(String lastNamePattern) {
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        var root = cq.from(Student.class);
        cq.where(cb.equal(root.get("lastName"), lastNamePattern));
        return new Query<>(em, cq).getResultList();
    }

    public List<Student> findByDobRange(LocalDate from, LocalDate to) {
        String sql = "SELECT * FROM students WHERE date_of_birth BETWEEN ? AND ? ORDER BY date_of_birth";
        try (Connection conn = em.getConnectionProvider().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(from));
            ps.setDate(2, java.sql.Date.valueOf(to));

            List<Student> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(em.getDialect().mapRowToEntity(rs, Student.class));
                }
            }
            return list;
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка выборки по диапазону дат рождения", ex);
        }
    }

    public List<Assignment> findAssignments(Long studentId) {
        String sql = "SELECT * FROM assignments WHERE student_id = ? ORDER BY assign_date DESC";
        try (Connection conn = em.getConnectionProvider().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, studentId);

            List<Assignment> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(em.getDialect().mapRowToEntity(rs, Assignment.class));
                }
            }
            return list;
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка выборки заселений студента", ex);
        }
    }

    public List<Payment> findPayments(Long studentId) {
        CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);
        var root = cq.from(Payment.class);
        cq.where(cb.equal(root.get("studentId"), studentId));
        return new Query<>(em, cq).getResultList();
    }
}