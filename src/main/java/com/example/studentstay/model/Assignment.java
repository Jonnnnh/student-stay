package com.example.studentstay.model;

import com.example.studentstay.orm.annotation.*;


import java.time.LocalDate;

@Entity(name = "assignments")
public class Assignment {
    @Id
    private Long id;
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    @Column(name = "room_id", nullable = false)
    private Long roomId;
    @Column(name = "assign_date", nullable = false)
    private LocalDate assignDate;
    @Column(name = "leave_date")
    private LocalDate leaveDate;

    public Assignment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(LocalDate assignDate) {
        this.assignDate = assignDate;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
    }
}