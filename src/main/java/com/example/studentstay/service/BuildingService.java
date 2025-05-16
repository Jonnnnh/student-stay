package com.example.studentstay.service;

import com.example.studentstay.dao.BuildingDao;
import com.example.studentstay.execption.BusinessException;
import com.example.studentstay.model.Building;

import java.sql.SQLException;
import java.util.List;

public class BuildingService {
    private final BuildingDao buildingDao;

    public BuildingService(BuildingDao buildingDao) {
        this.buildingDao = buildingDao;
    }

    public Building create(Building b) {
        try {
            buildingDao.create(b);
            return b;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Building update(Building b) {
        try {
            buildingDao.update(b);
            return b;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            buildingDao.delete(id);
        } catch (SQLException sqle) {
            throw new BusinessException(sqle.getMessage());
        }
    }

    public Building findById(Long id) {
        try {
            return buildingDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Building> findAll() {
        try {
            return buildingDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}