package com.example.studentstay.service;

import com.example.studentstay.dao.PaymentDao;
import com.example.studentstay.dao.StudentDao;
import com.example.studentstay.execption.BusinessException;
import com.example.studentstay.model.Student;
import com.example.studentstay.dto.StudentProfile;

import java.time.LocalDate;
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
        studentDao.create(s);
        return s;
    }

    public Student update(Student s) {
        studentDao.update(s);
        return s;
    }

    public void delete(Long studentId) {
        boolean hasActive = studentDao.findAssignments(studentId)
                .stream()
                .anyMatch(a -> a.getLeaveDate() == null);
        if (hasActive) {
            throw new BusinessException("Нельзя удалить студента: есть активные заселения");
        }
        studentDao.delete(studentId);
    }

    public Student findById(Long id) {
        return studentDao.findById(id);
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }

    public List<Student> findByLastName(String pattern) {
        return studentDao.findByLastName(pattern);
    }

    public List<Student> findByDobRange(LocalDate from, LocalDate to) {
        return studentDao.findByDobRange(from, to);
    }

    public StudentProfile getProfile(Long studentId) {
        Student s = studentDao.findById(studentId);
        if (s == null) {
            throw new BusinessException("Студент не найден");
        }
        var assignments = studentDao.findAssignments(studentId);
        var payments = paymentDao.findByStudent(studentId);
        return new StudentProfile(s, assignments, payments);
    }
}