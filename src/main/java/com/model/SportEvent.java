package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Pawel on 2017-04-10.
 */

@Entity
@NamedQueries({
        @NamedQuery(name = "SportEvent.findOrganisedEvents", query = "select se from SportEvent se where se.organizer.id = :userID"),
        @NamedQuery(name = "SportEvent.findParticipateEvents", query = "select se from SportEvent se left join se.participants " +
                "u where u.id = :userID"),
        @NamedQuery(name = "SportEvent.searchEvents", query = "select se from SportEvent se where se.city = :city " +
                "and se.sportCategory = :sportCategory"),
        @NamedQuery(name = "SportEvent.findEventsWithUser", query = "select se from SportEvent se left join se.participants " +
                "u where se.organizer.id = :userID or u.id = :userID")
})
public class SportEvent implements Serializable {

    public static final String FIND_ORGANISED_EVENTS = "SportEvent.findOrganisedEvents";
    public static final String FIND_PARTICIPATE_EVENTS = "SportEvent.findParticipateEvents";
    public static final String SEARCH_EVENTS = "SportEvent.searchEvents";
    public static final String FIND_EVENTS_WITH_USER = "SportEvent.findEventsWithUser";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "organizerID")
    private User organizer;

    private String title;

    private int amountOfParticipants;

    @ManyToMany
    private List<User> participants;

    @Column(name = "startTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "cityID")
    private City city;

    @ManyToOne
    @JoinColumn(name = "sportCatID")
    private SportCategory sportCategory;

    @Lob
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmountOfParticipants() {
        return amountOfParticipants;
    }

    public void setAmountOfParticipants(int amountOfParticipants) {
        this.amountOfParticipants = amountOfParticipants;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public SportCategory getSportCategory() {
        return sportCategory;
    }

    public void setSportCategory(SportCategory sportCategory) {
        this.sportCategory = sportCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
