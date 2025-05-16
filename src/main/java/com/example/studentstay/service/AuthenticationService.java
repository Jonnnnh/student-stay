package com.example.studentstay.service;

import com.example.studentstay.dao.EmployeeDao;
import com.example.studentstay.dao.EmployeeRoleDao;
import com.example.studentstay.dao.RoleDao;
import com.example.studentstay.execption.BusinessException;
import com.example.studentstay.model.Employee;
import com.example.studentstay.model.Role;

import java.util.List;
import java.util.Objects;

public class AuthenticationService {
    private final EmployeeDao employeeDao;
    private final RoleDao roleDao;
    private final EmployeeRoleDao employeeRoleDao;

    public AuthenticationService(EmployeeDao employeeDao,
                                 RoleDao roleDao,
                                 EmployeeRoleDao employeeRoleDao) {
        this.employeeDao      = employeeDao;
        this.roleDao          = roleDao;
        this.employeeRoleDao  = employeeRoleDao;
    }

    public Employee register(String username, String rawPassword,
                             String firstName, String lastName, String email) {
        if (employeeDao.findByUsername(username) != null) {
            throw new BusinessException("Пользователь с таким username уже существует");
        }
        Employee e = new Employee();
        e.setUsername(username);
        e.setPassword(rawPassword);
        e.setFirstName(firstName);
        e.setLastName(lastName);
        e.setEmail(email);
        employeeDao.create(e);
        return employeeDao.findByUsername(username);
    }

    public Employee login(String username, String rawPassword) {
        Employee e = employeeDao.findByUsername(username);
        if (e == null || !Objects.equals(e.getPassword(), rawPassword)) {
            throw new BusinessException("Неверный логин или пароль");
        }
        return e;
    }

    public void assignRole(Long employeeId, String roleName) {
        Role r = roleDao.findByName(roleName);
        if (r == null) {
            throw new BusinessException("Роль не найдена: " + roleName);
        }
        employeeRoleDao.assignRole(employeeId, r.getId());
    }

    public void revokeRole(Long employeeId, String roleName) {
        Role r = roleDao.findByName(roleName);
        if (r != null) {
            employeeRoleDao.removeRole(employeeId, r.getId());
        }
    }

    public List<Role> getRoles(Long employeeId) {
        return employeeRoleDao.findRolesByEmployee(employeeId);
    }

    public boolean hasRole(Long employeeId, String roleName) {
        return getRoles(employeeId)
                .stream()
                .map(Role::getName)
                .anyMatch(n -> n.equalsIgnoreCase(roleName));
    }
}