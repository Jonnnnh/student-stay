package com.example.studentstay.service;

import com.example.studentstay.dao.RoomDao;
import com.example.studentstay.execption.BusinessException;
import com.example.studentstay.model.Room;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private final RoomDao roomDao;

    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public Room create(Room r) {
        try {
            roomDao.create(r);
            return r;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Room update(Room r) {
        try {
            roomDao.update(r);
            return r;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            roomDao.delete(id);
        } catch (SQLException e) {
            throw new BusinessException("Не удалось удалить комнату: " + e.getMessage());
        }
    }

    public int getOccupiedCount(Long roomId) {
        try {
            return roomDao.getOccupiedCount(roomId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getFreeCount(Long roomId) {
        try {
            return roomDao.getFreeCount(roomId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Room> findByFilter(Long buildingId,
                                   int minCapacity,
                                   int maxCapacity,
                                   LocalDate date) {
        try {
            return roomDao.findByFilter(buildingId, minCapacity, maxCapacity, date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Room findById(Long id) {
        try {
            return roomDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Room> findAll() {
        try {
            return roomDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Room> findAllWithFreeCount() {
        try {
            List<Room> rooms = roomDao.findAll();
            List<Room> enriched = new ArrayList<>(rooms.size());
            for (Room r : rooms) {
                int free = roomDao.getFreeCount(r.getId());
                r.setFreeCount(free);
                enriched.add(r);
            }
            return enriched;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить список комнат", e);
        }
    }
}