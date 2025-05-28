package com.example.studentstay.dao;

import com.example.studentstay.model.Role;
import com.example.studentstay.orm.session.EntityManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRoleDao {
    private final EntityManager em;

    public EmployeeRoleDao(EntityManager em) {
        this.em = em;
    }

    public void assignRole(long employeeId, int roleId) {
        String sql = "INSERT INTO employee_roles(employee_id, role_id) VALUES(?, ?)";
        try (Connection conn = em.getConnectionProvider().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, employeeId);
            ps.setInt(2, roleId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось назначить роль", e);
        }
    }

    public void removeRole(long employeeId, int roleId) {
        String sql = "DELETE FROM employee_roles WHERE employee_id = ? AND role_id = ?";
        try (Connection conn = em.getConnectionProvider().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, employeeId);
            ps.setInt(2, roleId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось удалить роль", e);
        }
    }

    public List<Role> findRolesByEmployee(long employeeId) {
        String sql = """
                SELECT r.*
                  FROM roles r
                  JOIN employee_roles er ON r.id = er.role_id
                 WHERE er.employee_id = ?
                """;
        try (Connection conn = em.getConnectionProvider().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Role> roles = new ArrayList<>();
                while (rs.next()) {
                    roles.add(em.getDialect().mapRowToEntity(rs, Role.class));
                }
                return roles;
            }

        } catch (Exception ex) {
            throw new RuntimeException("Не удалось загрузить роли для сотрудника " + employeeId, ex);
        }
    }
}