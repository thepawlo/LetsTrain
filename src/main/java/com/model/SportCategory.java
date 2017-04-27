package com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class SportCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "sportsList")
    private List<User> users;

    @OneToMany(targetEntity = SportEvent.class, mappedBy = "sportCategory", cascade = CascadeType.ALL)
    private List<SportEvent> sportEvents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SportCategory) {
            SportCategory sportCategory = (SportCategory) o;
            return sportCategory.getId() == id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public List<SportEvent> getSportEvents() {
        return sportEvents;
    }

    public void setSportEvents(List<SportEvent> sportEvents) {
        this.sportEvents = sportEvents;
    }
}