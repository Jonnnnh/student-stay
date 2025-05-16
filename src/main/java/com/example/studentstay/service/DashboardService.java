package com.example.studentstay.service;

import com.example.studentstay.dao.AssignmentDao;
import com.example.studentstay.dao.RoomDao;
import com.example.studentstay.dao.StudentDao;
import com.example.studentstay.model.Assignment;
import com.example.studentstay.model.Room;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardService {
    private final RoomDao roomDao;
    private final AssignmentDao assignmentDao;
    private final StudentDao studentDao;

    public DashboardService(RoomDao roomDao,
                            AssignmentDao assignmentDao,
                            StudentDao studentDao) {
        this.roomDao       = roomDao;
        this.assignmentDao = assignmentDao;
        this.studentDao    = studentDao;
    }

    public double getOccupancyPercentage() {
        List<Room> rooms = roomDao.findAll();
        int totalPlaces = rooms.stream()
                .mapToInt(Room::getCapacity)
                .sum();
        int occupied = rooms.stream()
                .mapToInt(r -> roomDao.getOccupiedCount(r.getId()))
                .sum();
        return totalPlaces == 0 ? 0.0 : 100.0 * occupied / totalPlaces;
    }

    public double getAverageStayDays() {
        List<Assignment> all = assignmentDao.findAll();
        List<Long> durations = all.stream()
                .filter(a -> a.getLeaveDate() != null)
                .map(a -> Duration.between(
                                a.getAssignDate().atStartOfDay(),
                                a.getLeaveDate().atStartOfDay()
                        ).toDays()
                )
                .toList();
        return durations.isEmpty()
                ? 0.0
                : durations.stream().mapToLong(Long::longValue).average().orElse(0.0);
    }

    public Map<String, List<Assignment>> getRecentEvents(int limit) {
        List<Assignment> all = assignmentDao.findAll();
        List<Assignment> recentAssign = all.stream()
                .sorted(Comparator.comparing(Assignment::getAssignDate).reversed())
                .limit(limit)
                .collect(Collectors.toList());
        List<Assignment> recentLeaves = all.stream()
                .filter(a -> a.getLeaveDate() != null)
                .sorted(Comparator.comparing(Assignment::getLeaveDate).reversed())
                .limit(limit)
                .collect(Collectors.toList());
        Map<String, List<Assignment>> m = new HashMap<>();
        m.put("assignments", recentAssign);
        m.put("leaves", recentLeaves);
        return m;
    }

    public List<Long> getWarnings() {
        LocalDate now = LocalDate.now();
        return assignmentDao.findActive().stream()
                .filter(a -> a.getAssignDate().plusMonths(6).isBefore(now))
                .map(Assignment::getStudentId)
                .distinct()
                .collect(Collectors.toList());
    }
}