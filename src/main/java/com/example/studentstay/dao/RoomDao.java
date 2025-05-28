package com.example.studentstay.dao;

import com.example.studentstay.model.Room;
import com.example.studentstay.orm.repository.JdbcRepository;
import com.example.studentstay.orm.repository.Repository;
import com.example.studentstay.orm.session.EntityManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    private final Repository<Room, Long> repo;
    private final EntityManager em;

    public RoomDao(EntityManager em) {
        this.em = em;
        this.repo = new JdbcRepository<>(em, Room.class);
    }

    public Room findById(Long id) {
        return repo.find(id);
    }

    public List<Room> findAll() {
        return repo.findAll();
    }

    public Room create(Room r) {
        repo.save(r);
        return r;
    }

    public Room update(Room r) {
        repo.save(r);
        return r;
    }

    public void delete(Long id) {
        Room r = repo.find(id);
        if (r != null) {
            repo.delete(r);
        }
    }

    public int getOccupiedCount(Long roomId) {
        String sql = "SELECT COUNT(*) FROM assignments WHERE room_id = ? AND leave_date IS NULL";
        try (Connection conn = em.getConnectionProvider().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Не удалось посчитать занятые места в комнате " + roomId, ex);
        }
    }

    public int getFreeCount(Long roomId) {
        String sql = """
                   SELECT r.capacity - COALESCE(a.cnt,0)
                     FROM rooms r
                LEFT JOIN (
                       SELECT room_id, COUNT(*) AS cnt
                         FROM assignments
                        WHERE leave_date IS NULL
                     GROUP BY room_id
                   ) a ON r.id = a.room_id
                    WHERE r.id = ?
                """;
        try (Connection conn = em.getConnectionProvider().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Не удалось посчитать свободные места в комнате " + roomId, ex);
        }
    }

    public List<Room> findByFilter(Long buildingId, int minCap, int maxCap, LocalDate date) {
        String sql = """
                   SELECT r.*
                     FROM rooms r
                LEFT JOIN (
                       SELECT room_id, COUNT(*) AS cnt
                         FROM assignments
                        WHERE assign_date <= ?
                          AND (leave_date IS NULL OR leave_date >= ?)
                     GROUP BY room_id
                   ) a ON r.id = a.room_id
                    WHERE r.building_id = ?
                      AND r.capacity BETWEEN ? AND ?
                      AND (r.capacity - COALESCE(a.cnt,0)) > 0
                """;
        try (Connection conn = em.getConnectionProvider().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(date));
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setLong(3, buildingId);
            ps.setInt(4, minCap);
            ps.setInt(5, maxCap);

            List<Room> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(em.getDialect().mapRowToEntity(rs, Room.class));
                }
            }
            return list;

        } catch (Exception ex) {
            throw new RuntimeException("Ошибка при фильтрации комнат", ex);
        }
    }
}