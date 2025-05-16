package com.example.studentstay.dao;

import com.example.studentstay.jdbc.Executor;
import com.example.studentstay.jdbc.ReflectiveResultSetMapper;
import com.example.studentstay.model.Student;
import com.example.studentstay.model.Assignment;
import com.example.studentstay.model.Payment;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StudentDao extends AbstractCrudDao<Student, Long> {
    public StudentDao(Executor executor) {
        super(executor, Student.class);
    }

    @Override
    protected String getTableName() {
        return "students";
    }

    @Override
    public void create(Student s) throws SQLException {
        String sql = """
                INSERT INTO students 
                  (first_name, last_name, date_of_birth, email, phone) 
                VALUES (?, ?, ?, ?, ?)
                """;
        executor.executeUpdate(sql,
                s.getFirstName(),
                s.getLastName(),
                Date.valueOf(s.getDateOfBirth()),
                s.getEmail(),
                s.getPhone());
    }

    @Override
    public void update(Student s) throws SQLException {
        String sql = """
                UPDATE students SET 
                  first_name = ?, last_name = ?, date_of_birth = ?, email = ?, phone = ?
                WHERE id = ?
                """;
        executor.executeUpdate(sql,
                s.getFirstName(),
                s.getLastName(),
                Date.valueOf(s.getDateOfBirth()),
                s.getEmail(),
                s.getPhone(),
                s.getId());
    }

    public List<Student> findByLastName(String lastNamePattern) throws SQLException {
        String sql = "SELECT * FROM students WHERE last_name LIKE ? ORDER BY last_name";
        return executor.executeQuery(
                sql,
                new ReflectiveResultSetMapper<>(Student.class),
                "%" + lastNamePattern + "%"
        );
    }

    public List<Student> findByDobRange(LocalDate from, LocalDate to) throws SQLException {
        String sql = "SELECT * FROM students WHERE date_of_birth BETWEEN ? AND ? ORDER BY date_of_birth";
        return executor.executeQuery(
                sql,
                new ReflectiveResultSetMapper<>(Student.class),
                Date.valueOf(from),
                Date.valueOf(to)
        );
    }

    public List<Assignment> findAssignments(long studentId) throws SQLException {
        String sql = "SELECT * FROM assignments WHERE student_id = ? ORDER BY assign_date DESC";
        return executor.executeQuery(
                sql,
                new ReflectiveResultSetMapper<>(Assignment.class),
                studentId
        );
    }

    public List<Payment> findPayments(long studentId) throws SQLException {
        String sql = "SELECT * FROM payments WHERE student_id = ? ORDER BY payment_date DESC";
        return executor.executeQuery(
                sql,
                new ReflectiveResultSetMapper<>(Payment.class),
                studentId
        );
    }
}