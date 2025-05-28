package com.example.studentstay.dao;

import com.example.studentstay.model.Role;
import com.example.studentstay.orm.query.CriteriaBuilder;
import com.example.studentstay.orm.query.CriteriaQuery;
import com.example.studentstay.orm.query.Query;
import com.example.studentstay.orm.query.Root;
import com.example.studentstay.orm.repository.JdbcRepository;
import com.example.studentstay.orm.repository.Repository;
import com.example.studentstay.orm.session.EntityManager;

import java.util.List;

public class RoleDao {
    private final Repository<Role, Integer> repo;
    private final EntityManager em;
    private final CriteriaBuilder cb = new CriteriaBuilder();

    public RoleDao(EntityManager em) {
        this.em = em;
        this.repo = new JdbcRepository<>(em, Role.class);
    }

    public Role findById(Integer id) {
        return repo.find(id);
    }

    public List<Role> findAll() {
        return repo.findAll();
    }

    public Role create(Role r) {
        repo.save(r);
        return r;
    }

    public Role update(Role r) {
        repo.save(r);
        return r;
    }

    public void delete(Integer id) {
        var r = repo.find(id);
        if (r != null) repo.delete(r);
    }

    public Role findByName(String name) {
        CriteriaQuery<Role> cq = cb.createQuery(Role.class);
        Root<Role> root = cq.from(Role.class);
        cq.where(cb.equal(root.get("name"), name));
        return new Query<>(em, cq).getSingleResult();
    }
}