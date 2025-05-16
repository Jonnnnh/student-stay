package com.example.studentstay.service;

import com.example.studentstay.dao.RoomDao;
import com.example.studentstay.execption.BusinessException;
import com.example.studentstay.model.Room;

import java.time.LocalDate;
import java.util.List;

public class RoomService {
    private final RoomDao roomDao;

    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public Room create(Room r) {
        roomDao.create(r);
        return r;
    }

    public Room update(Room r) {
        roomDao.update(r);
        return r;
    }

    public void delete(Long id) {
        try {
            roomDao.delete(id);
        } catch (Exception ex) {
            throw new BusinessException("Не удалось удалить комнату: " + ex.getMessage());
        }
    }

    public int getOccupiedCount(Long roomId) {
        return roomDao.getOccupiedCount(roomId);
    }

    public int getFreeCount(Long roomId) {
        return roomDao.getFreeCount(roomId);
    }

    public List<Room> findByFilter(Long buildingId,
                                   int minCapacity,
                                   int maxCapacity,
                                   LocalDate date) {
        return roomDao.findByFilter(buildingId, minCapacity, maxCapacity, date);
    }

    public Room findById(Long id) {
        return roomDao.findById(id);
    }

    public List<Room> findAll() {
        return roomDao.findAll();
    }

    public List<Room> findAllWithFreeCount() {
        List<Room> rooms = roomDao.findAll();
        for (Room r : rooms) {
            r.setFreeCount(roomDao.getFreeCount(r.getId()));
        }
        return rooms;
    }
}