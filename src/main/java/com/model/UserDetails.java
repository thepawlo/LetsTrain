package com.model;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.io.File;

/**
 * Created by Pawel on 2017-03-30.
 */
@Entity
public class UserDetails {

    @Id
    @GeneratedValue
    private int id;

    @Lob
    private byte[] avatar;

    private int age;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

    @OneToOne(mappedBy = "userDetails")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) o;
            return userDetails.getId() == id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
