package com.example.studentstay.dao;

import com.example.studentstay.jdbc.Executor;
import com.example.studentstay.jdbc.ReflectiveResultSetMapper;
import com.example.studentstay.model.Role;

import java.sql.SQLException;
import java.util.List;

public class EmployeeRoleDao {
    private final Executor executor;
    private final ReflectiveResultSetMapper<Role> roleMapper;

    public EmployeeRoleDao(Executor executor) {
        this.executor = executor;
        this.roleMapper = new ReflectiveResultSetMapper<>(Role.class);
    }

    public void assignRole(long employeeId, int roleId) throws SQLException {
        String sql = "INSERT INTO employee_roles (employee_id, role_id) VALUES (?, ?)";
        executor.executeUpdate(sql, employeeId, roleId);
    }

    public void removeRole(long employeeId, int roleId) throws SQLException {
        String sql = "DELETE FROM employee_roles WHERE employee_id = ? AND role_id = ?";
        executor.executeUpdate(sql, employeeId, roleId);
    }

    public List<Role> findRolesByEmployee(long employeeId) throws SQLException {
        String sql = """
                SELECT r.* 
                  FROM roles r
                  JOIN employee_roles er ON r.id = er.role_id
                 WHERE er.employee_id = ?
                """;
        return executor.executeQuery(sql, roleMapper, employeeId);
    }
}