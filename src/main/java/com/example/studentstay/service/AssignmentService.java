package com.example.studentstay.service;

import com.example.studentstay.dao.AssignmentDao;
import com.example.studentstay.model.Assignment;
import com.example.studentstay.orm.transaction.TransactionManager;

import java.time.LocalDate;
import java.util.List;

public class AssignmentService {
    private final AssignmentDao assignmentDao;

    public AssignmentService(AssignmentDao assignmentDao) {
        this.assignmentDao = assignmentDao;
    }

    public Assignment assign(Long studentId, Long roomId, LocalDate date) {
        Assignment a = new Assignment();
        a.setStudentId(studentId);
        a.setRoomId(roomId);
        a.setAssignDate(date);
        assignmentDao.create(a);
        return a;
    }

    public void leave(Long assignmentId, LocalDate leaveDate) {
        assignmentDao.leave(assignmentId, leaveDate);
    }

    public void transfer(Long currentAssignmentId,
                         Long newRoomId,
                         LocalDate transferDate) {
        TransactionManager.begin();
        try {
            assignmentDao.leave(currentAssignmentId, transferDate);
            Assignment old = assignmentDao.findById(currentAssignmentId);
            Assignment next = new Assignment();
            next.setStudentId(old.getStudentId());
            next.setRoomId(newRoomId);
            next.setAssignDate(transferDate);
            assignmentDao.create(next);
            TransactionManager.commit();
        } catch (RuntimeException ex) {
            TransactionManager.rollback();
            throw ex;
        }
    }

    public Assignment findById(Long id) {
        return assignmentDao.findById(id);
    }

    public List<Assignment> findActive() {
        return assignmentDao.findActive();
    }
}