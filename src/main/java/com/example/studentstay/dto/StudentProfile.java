package com.example.studentstay.dto;


import com.example.studentstay.model.Assignment;
import com.example.studentstay.model.Payment;
import com.example.studentstay.model.Student;

import java.util.List;

public class StudentProfile {
    private final Student student;
    private final List<Assignment> assignments;
    private final List<Payment> payments;

    public StudentProfile(Student student,
                          List<Assignment> assignments,
                          List<Payment> payments) {
        this.student = student;
        this.assignments = assignments;
        this.payments = payments;
    }

    public Student getStudent() {
        return student;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}