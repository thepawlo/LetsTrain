package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Pawel on 2017-04-10.
 */
@Entity
public class City implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String name;

    @OneToMany(targetEntity = SportEvent.class, mappedBy = "city", cascade = CascadeType.ALL)
    private List<SportEvent> sportEvents;

    @OneToMany(targetEntity = UserDetails.class, mappedBy = "city", cascade = CascadeType.ALL)
    private List<UserDetails> userDetailsList;

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

    public List<SportEvent> getSportEvents() {
        return sportEvents;
    }

    public void setSportEvents(List<SportEvent> sportEvents) {
        this.sportEvents = sportEvents;
    }

    public List<UserDetails> getUserDetailsList() {
        return userDetailsList;
    }

    public void setUserDetailsList(List<UserDetails> userDetailsList) {
        this.userDetailsList = userDetailsList;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof City) {
            City city = (City) o;
            return city.getId() == id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
