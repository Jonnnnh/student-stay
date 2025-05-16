package com.example.studentstay.dao;

import com.example.studentstay.model.Employee;
import com.example.studentstay.orm.query.CriteriaBuilder;
import com.example.studentstay.orm.query.CriteriaQuery;
import com.example.studentstay.orm.query.Query;
import com.example.studentstay.orm.query.Root;
import com.example.studentstay.orm.repository.JdbcRepository;
import com.example.studentstay.orm.repository.Repository;
import com.example.studentstay.orm.session.EntityManager;

import java.util.List;

public class EmployeeDao {
    private final Repository<Employee, Long> repo;
    private final EntityManager em;
    private final CriteriaBuilder cb = new CriteriaBuilder();

    public EmployeeDao(EntityManager em) {
        this.em   = em;
        this.repo = new JdbcRepository<>(em, Employee.class);
    }

    public Employee findById(Long id) {
        return repo.find(id);
    }

    public List<Employee> findAll() {
        return repo.findAll();
    }

    public Employee create(Employee e) {
        repo.save(e);
        return e;
    }

    public Employee update(Employee e) {
        repo.save(e);
        return e;
    }

    public void delete(Long id) {
        Employee e = repo.find(id);
        if (e != null) repo.delete(e);
    }

    public Employee findByUsername(String username) {
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> root = cq.from(Employee.class);
        cq.where(cb.equal(root.get("username"), username));
        return new Query<>(em, cq).getSingleResult();
    }
}