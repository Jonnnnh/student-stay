package com.example.studentstay.dao;

import com.example.studentstay.model.Building;
import com.example.studentstay.model.Room;
import com.example.studentstay.orm.query.CriteriaBuilder;
import com.example.studentstay.orm.query.CriteriaQuery;
import com.example.studentstay.orm.query.Query;
import com.example.studentstay.orm.query.Root;
import com.example.studentstay.orm.repository.JdbcRepository;
import com.example.studentstay.orm.repository.Repository;
import com.example.studentstay.orm.session.EntityManager;

import java.util.List;

public class BuildingDao {
    private final Repository<Building, Long> repo;
    private final EntityManager em;
    private final CriteriaBuilder cb = new CriteriaBuilder();

    public BuildingDao(EntityManager em) {
        this.em   = em;
        this.repo = new JdbcRepository<>(em, Building.class);
    }

    public Building findById(Long id) {
        return repo.find(id);
    }

    public List<Building> findAll() {
        return repo.findAll();
    }

    public Building create(Building b) {
        repo.save(b);
        return b;
    }

    public Building update(Building b) {
        repo.save(b);
        return b;
    }

    public void delete(Long id) {
        CriteriaQuery<Room> cq = cb.createQuery(Room.class);
        Root<Room> root = cq.from(Room.class);
        cq.where(cb.equal(root.get("buildingId"), id));
        List<Room> rooms = new Query<>(em, cq).getResultList();
        if (!rooms.isEmpty()) {
            throw new IllegalStateException("Нельзя удалить корпус: в нём есть комнаты");
        }
        Building b = repo.find(id);
        if (b != null) {
            repo.delete(b);
        }
    }
}