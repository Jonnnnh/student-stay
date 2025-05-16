package com.example.studentstay.dao;

import com.example.studentstay.jdbc.Executor;
import com.example.studentstay.jdbc.ReflectiveResultSetMapper;
import com.example.studentstay.model.Room;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RoomDao extends AbstractCrudDao<Room, Long> {
    public RoomDao(Executor executor) {
        super(executor, Room.class);
    }

    @Override
    protected String getTableName() {
        return "rooms";
    }

    @Override
    public void create(Room r) throws SQLException {
        String sql = "INSERT INTO rooms (building_id, room_number, capacity) VALUES (?, ?, ?)";
        executor.executeUpdate(sql, r.getBuildingId(), r.getRoomNumber(), r.getCapacity());
    }

    @Override
    public void update(Room r) throws SQLException {
        String sql = "UPDATE rooms SET building_id = ?, room_number = ?, capacity = ? WHERE id = ?";
        executor.executeUpdate(sql,
                r.getBuildingId(), r.getRoomNumber(), r.getCapacity(), r.getId());
    }

    public int getOccupiedCount(long roomId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM assignments WHERE room_id = ? AND leave_date IS NULL";
        Integer cnt = executor.executeSingleResult(
                sql,
                (rs) -> rs.getInt(1),
                roomId
        );
        return cnt == null ? 0 : cnt;
    }

    public int getFreeCount(long roomId) throws SQLException {
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
        Integer free = executor.executeSingleResult(
                sql,
                (rs) -> rs.getInt(1),
                roomId
        );
        return free == null ? 0 : free;
    }

    public List<Room> findByFilter(long buildingId, int minCap, int maxCap, LocalDate date) throws SQLException {
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
                   ORDER BY r.id
                """;
        Date d = Date.valueOf(date);
        return executor.executeQuery(
                sql,
                new ReflectiveResultSetMapper<>(Room.class),
                d, d, buildingId, minCap, maxCap
        );
    }
}