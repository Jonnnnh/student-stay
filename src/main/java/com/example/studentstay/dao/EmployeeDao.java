package com.example.studentstay.dao;

import com.example.studentstay.jdbc.Executor;
import com.example.studentstay.jdbc.ReflectiveResultSetMapper;
import com.example.studentstay.model.Employee;

import java.sql.SQLException;

public class EmployeeDao extends AbstractCrudDao<Employee, Long> {
    public EmployeeDao(Executor executor) {
        super(executor, Employee.class);
    }

    @Override
    protected String getTableName() {
        return "employees";
    }

    @Override
    public void create(Employee e) throws SQLException {
        String sql = "INSERT INTO employees " +
                "(username, password, first_name, last_name, email) " +
                "VALUES (?, ?, ?, ?, ?)";
        executor.executeUpdate(sql,
                e.getUsername(),
                e.getPassword(),
                e.getFirstName(),
                e.getLastName(),
                e.getEmail());
    }

    @Override
    public void update(Employee e) throws SQLException {
        String sql = "UPDATE employees SET " +
                "username = ?, password = ?, first_name = ?, last_name = ?, email = ? " +
                "WHERE id = ?";
        executor.executeUpdate(sql,
                e.getUsername(),
                e.getPassword(),
                e.getFirstName(),
                e.getLastName(),
                e.getEmail(),
                e.getId());
    }

    public Employee findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM employees WHERE username = ?";
        return executor.executeSingleResult(
                sql,
                new ReflectiveResultSetMapper<>(Employee.class),
                username
        );
    }
}