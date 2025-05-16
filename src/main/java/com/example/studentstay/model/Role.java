package com.example.studentstay.model;

import com.example.studentstay.orm.annotation.*;

@Entity(name = "roles")
public class Role {

    @Id
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}