package com.example.studentstay.dao;

import com.example.studentstay.jdbc.Executor;
import com.example.studentstay.jdbc.ReflectiveResultSetMapper;
import com.example.studentstay.model.Assignment;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AssignmentDao extends AbstractCrudDao<Assignment, Long> {
    public AssignmentDao(Executor executor) {
        super(executor, Assignment.class);
    }

    @Override
    protected String getTableName() {
        return "assignments";
    }

    @Override
    public void create(Assignment a) throws SQLException {
        String sql = "INSERT INTO assignments (student_id, room_id, assign_date, leave_date) VALUES (?, ?, ?, ?)";
        executor.executeUpdate(sql,
                a.getStudentId(),
                a.getRoomId(),
                Date.valueOf(a.getAssignDate()),
                a.getLeaveDate() != null ? Date.valueOf(a.getLeaveDate()) : null);
    }

    @Override
    public void update(Assignment a) throws SQLException {
        String sql = "UPDATE assignments SET student_id=?, room_id=?, assign_date=?, leave_date=? WHERE id=?";
        executor.executeUpdate(sql,
                a.getStudentId(),
                a.getRoomId(),
                Date.valueOf(a.getAssignDate()),
                a.getLeaveDate() != null ? Date.valueOf(a.getLeaveDate()) : null,
                a.getId());
    }

    public void leave(long assignmentId, LocalDate leaveDate) throws SQLException {
        String sql = "UPDATE assignments SET leave_date = ? WHERE id = ?";
        executor.executeUpdate(sql, Date.valueOf(leaveDate), assignmentId);
    }

    public List<Assignment> findActive() throws SQLException {
        String sql = "SELECT * FROM assignments WHERE leave_date IS NULL ORDER BY assign_date";
        return executor.executeQuery(
                sql,
                new ReflectiveResultSetMapper<>(Assignment.class)
        );
    }

    public List<Assignment> findByRoom(long roomId) throws SQLException {
        String sql = "SELECT * FROM assignments WHERE room_id = ? ORDER BY assign_date DESC";
        return executor.executeQuery(
                sql,
                new ReflectiveResultSetMapper<>(Assignment.class),
                roomId
        );
    }
}