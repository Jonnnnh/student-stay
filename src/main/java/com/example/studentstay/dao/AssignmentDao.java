package com.example.studentstay.dao;

import com.example.studentstay.model.Assignment;
import com.example.studentstay.orm.query.CriteriaBuilder;
import com.example.studentstay.orm.query.CriteriaQuery;
import com.example.studentstay.orm.query.Query;
import com.example.studentstay.orm.query.Root;
import com.example.studentstay.orm.repository.JdbcRepository;
import com.example.studentstay.orm.repository.Repository;
import com.example.studentstay.orm.session.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class AssignmentDao {
    private final Repository<Assignment, Long> repo;
    private final EntityManager em;
    private final CriteriaBuilder cb = new CriteriaBuilder();

    public AssignmentDao(EntityManager em) {
        this.em = em;
        this.repo = new JdbcRepository<>(em, Assignment.class);
    }

    public Assignment findById(Long id) {
        return repo.find(id);
    }

    public List<Assignment> findAll() {
        return repo.findAll();
    }

    public Assignment create(Assignment a) {
        repo.save(a);
        return a;
    }

    public Assignment update(Assignment a) {
        repo.save(a);
        return a;
    }

    public void delete(Long id) {
        Assignment a = repo.find(id);
        if (a != null) repo.delete(a);
    }

    public void leave(Long assignmentId, LocalDate leaveDate) {
        Assignment a = repo.find(assignmentId);
        if (a != null) {
            a.setLeaveDate(leaveDate);
            repo.save(a);
        }
    }

    public List<Assignment> findActive() {
        CriteriaQuery<Assignment> cq = cb.createQuery(Assignment.class);
        Root<Assignment> root = cq.from(Assignment.class);
        cq.where(cb.isNull(root.get("leaveDate")));
        return new Query<>(em, cq).getResultList();
    }
}