package com.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@NamedQuery(name = "User.findUserByLogin", query = "select u from User u where u.login = :login")

public class User implements Serializable {

    public static final String FIND_BY_LOGIN = "User.findUserByLogin";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String login;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    private List<SportCategory> sportsList;

    @ManyToMany(mappedBy = "participants")
    private List<SportEvent> sportEventsList;

    @OneToOne
    @JoinColumn(name = "userDetailsId")
    private UserDetails userDetails;

    @OneToMany(targetEntity = SportEvent.class, mappedBy = "organizer", cascade = CascadeType.ALL)
    private List<SportEvent> sportEvents;


    public boolean isAdmin() {
        return Role.ADMIN.equals(role);
    }

    public boolean isUser() {
        return Role.USER.equals(role);
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return user.getId() == id;
        }

        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String email) {
        this.login = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<SportCategory> getSportsList() {
        return sportsList;
    }

    public void setSportsList(List<SportCategory> sportsList) {
        this.sportsList = sportsList;
    }

    public List<SportEvent> getSportEventsList() {
        return sportEventsList;
    }

    public void setSportEventsList(List<SportEvent> sportEventsList) {
        this.sportEventsList = sportEventsList;
    }

    public List<SportEvent> getSportEvents() {
        return sportEvents;
    }

    public void setSportEvents(List<SportEvent> sportEvents) {
        this.sportEvents = sportEvents;
    }
}
