package com.example.studentstay.service;

import com.example.studentstay.dao.BuildingDao;
import com.example.studentstay.execption.BusinessException;
import com.example.studentstay.model.Building;

import java.util.List;

public class BuildingService {
    private final BuildingDao buildingDao;

    public BuildingService(BuildingDao buildingDao) {
        this.buildingDao = buildingDao;
    }

    public Building create(Building b) {
        buildingDao.create(b);
        return b;
    }

    public Building update(Building b) {
        buildingDao.update(b);
        return b;
    }

    public void delete(Long id) {
        try {
            buildingDao.delete(id);
        } catch (BusinessException be) {
            throw be;
        }
    }

    public Building findById(Long id) {
        return buildingDao.findById(id);
    }

    public List<Building> findAll() {
        return buildingDao.findAll();
    }
}