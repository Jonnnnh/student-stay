package com.example.studentstay.dao;

import com.example.studentstay.jdbc.Executor;
import com.example.studentstay.jdbc.ReflectiveResultSetMapper;
import com.example.studentstay.model.Role;

import java.sql.SQLException;

public class RoleDao extends AbstractCrudDao<Role, Integer> {
    public RoleDao(Executor executor) {
        super(executor, Role.class);
    }

    @Override
    protected String getTableName() {
        return "roles";
    }

    @Override
    public void create(Role r) throws SQLException {
        String sql = "INSERT INTO roles (name) VALUES (?)";
        executor.executeUpdate(sql, r.getName());
    }

    @Override
    public void update(Role r) throws SQLException {
        String sql = "UPDATE roles SET name = ? WHERE id = ?";
        executor.executeUpdate(sql, r.getName(), r.getId());
    }

    public Role findByName(String name) throws SQLException {
        String sql = "SELECT * FROM roles WHERE name = ?";
        return executor.executeSingleResult(
                sql,
                new ReflectiveResultSetMapper<>(Role.class),
                name
        );
    }
}