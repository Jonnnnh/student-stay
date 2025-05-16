package com.example.studentstay.service;

import com.example.studentstay.dao.PaymentDao;
import com.example.studentstay.dao.StudentDao;
import com.example.studentstay.execption.BusinessException;
import com.example.studentstay.model.Student;
import com.example.studentstay.model.StudentProfile;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final StudentDao studentDao;
    private final PaymentDao paymentDao;

    public StudentService(StudentDao studentDao,
                          PaymentDao paymentDao) {
        this.studentDao = studentDao;
        this.paymentDao = paymentDao;
    }

    public Student create(Student s) {
        try {
            studentDao.create(s);
            return s;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student update(Student s) {
        try {
            studentDao.update(s);
            return s;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long studentId) {
        try {
            boolean hasActive = studentDao.findAssignments(studentId)
                    .stream()
                    .anyMatch(a -> a.getLeaveDate() == null);
            if (hasActive) {
                throw new BusinessException("Нельзя удалить студента: есть активные заселения");
            }
            studentDao.delete(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student findById(Long id) {
        try {
            return studentDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> findAll() {
        try {
            return studentDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> findByLastName(String pattern) {
        try {
            return studentDao.findByLastName(pattern);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> findByDobRange(java.time.LocalDate from,
                                        java.time.LocalDate to) {
        try {
            return studentDao.findByDobRange(from, to);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StudentProfile getProfile(Long studentId) {
        try {
            Student s = studentDao.findById(studentId);
            if (s == null) throw new BusinessException("Студент не найден");
            List var1 = studentDao.findAssignments(studentId);
            List var2 = paymentDao.findByStudent(studentId);
            return new StudentProfile(s, var1, var2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}