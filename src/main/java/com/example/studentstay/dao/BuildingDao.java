package com.example.studentstay.dao;

import com.example.studentstay.jdbc.Executor;
import com.example.studentstay.model.Building;

import java.sql.SQLException;

public class BuildingDao extends AbstractCrudDao<Building, Long> {
    public BuildingDao(Executor executor) {
        super(executor, Building.class);
    }

    @Override
    protected String getTableName() {
        return "buildings";
    }

    @Override
    public void create(Building b) throws SQLException {
        String sql = "INSERT INTO buildings (name, address) VALUES (?, ?)";
        executor.executeUpdate(sql, b.getName(), b.getAddress());
    }

    @Override
    public void update(Building b) throws SQLException {
        String sql = "UPDATE buildings SET name = ?, address = ? WHERE id = ?";
        executor.executeUpdate(sql, b.getName(), b.getAddress(), b.getId());
    }

    public boolean hasRooms(long buildingId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM rooms WHERE building_id = ?";
        Integer cnt = executor.executeSingleResult(
                sql,
                (rs) -> rs.getInt(1),
                buildingId
        );
        return cnt != null && cnt > 0;
    }

    @Override
    public void delete(Long id) throws SQLException {
        if (hasRooms(id)) {
            throw new SQLException("Нельзя удалить корпус: в нём остались комнаты");
        }
        super.delete(id);
    }
}