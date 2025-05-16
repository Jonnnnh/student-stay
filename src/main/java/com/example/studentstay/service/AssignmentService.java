package com.example.studentstay.service;

import com.example.studentstay.dao.AssignmentDao;
import com.example.studentstay.model.Assignment;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AssignmentService {
    private final AssignmentDao assignmentDao;

    public AssignmentService(AssignmentDao assignmentDao) {
        this.assignmentDao = assignmentDao;
    }

    public Assignment assign(Long studentId, Long roomId, LocalDate date) {
        try {
            Assignment a = new Assignment();
            a.setStudentId(studentId);
            a.setRoomId(roomId);
            a.setAssignDate(date);
            assignmentDao.create(a);
            return a;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void leave(Long assignmentId, LocalDate leaveDate) {
        try {
            assignmentDao.leave(assignmentId, leaveDate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Assignment transfer(Long currentAssignmentId,
                               Long newRoomId,
                               LocalDate transferDate) {
        try {
            assignmentDao.leave(currentAssignmentId, transferDate);
            Assignment old = assignmentDao.findById(currentAssignmentId);
            Assignment next = new Assignment();
            next.setStudentId(old.getStudentId());
            next.setRoomId(newRoomId);
            next.setAssignDate(transferDate);
            assignmentDao.create(next);
            return next;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Assignment findById(Long id) {
        try {
            return assignmentDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Assignment> findActive() {
        try {
            return assignmentDao.findActive();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Assignment> findByRoom(Long roomId) {
        try {
            return assignmentDao.findByRoom(roomId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}