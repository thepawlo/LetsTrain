package com.mb;


import com.facade.SportEventFacade;
import com.model.SportEvent;
import com.model.User;
import com.sun.faces.context.flash.ELFlash;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.ejb.Schedule;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Pawel on 2017-04-12.
 */

@ManagedBean
@ViewScoped
public class SportEventMB extends AbstractMB implements Serializable {

    public static final String SPORT_EVENT_TO_CONFIRM = "sportEvent";

    private SportEventFacade sportEventFacade;
    private SportEvent sportEvent;
    private SportEvent sportEventDetail;
    private SportEvent chosenEvent;
    private SportEvent searchedEvent;
    private List<SportEvent> sportEventsList;
    private List<SportEvent> organisedEventsList;
    private List<SportEvent> participateEventsList;
    private List<SportEvent> searchedEventsList;

    public SportEventFacade getSportEventFacade() {
        if (sportEventFacade == null) {
            sportEventFacade = new SportEventFacade();
        }
        return sportEventFacade;
    }

    public void setSportEventFacade(SportEventFacade sportEventFacade) {
        this.sportEventFacade = sportEventFacade;
    }

    public SportEvent getSportEvent() {
        if (sportEvent == null) {
            sportEvent = new SportEvent();
        }
        return sportEvent;
    }

    public void setSportEvent(SportEvent sportEvent) {
        this.sportEvent = sportEvent;
    }

    public SportEvent getSportEventDetail() {
        if (sportEventDetail == null) {
            if (sportEvent == null) {
                sportEvent = (SportEvent) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(SPORT_EVENT_TO_CONFIRM);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().keep(SPORT_EVENT_TO_CONFIRM);
            }
            sportEventDetail = getSportEventFacade().findSportEvent(sportEvent.getId());
        }

        return sportEventDetail;
    }

    public void setSportEventDetail(SportEvent sportEventDetail) {
        this.sportEventDetail = sportEventDetail;
    }

    public SportEvent getChosenEvent() {
        if (chosenEvent == null) {
            chosenEvent = new SportEvent();
        }
        return chosenEvent;
    }

    public void setChosenEvent(SportEvent chosenEvent) {
        this.chosenEvent = chosenEvent;
    }

    public List<SportEvent> getSportEvents() {
        if (sportEventsList == null) {
            sportEventsList = new ArrayList<SportEvent>();
        }
        return sportEventsList;
    }

    public void setSportEvents(List<SportEvent> sportEventsList) {
        this.sportEventsList = sportEventsList;
    }

    public SportEvent getSearchedEvent() {
        if (searchedEvent == null) {
            searchedEvent = new SportEvent();
        }
        return searchedEvent;
    }

    public void setSearchedEvent(SportEvent searchedEvent) {
        this.searchedEvent = searchedEvent;
    }

    public List<SportEvent> getOrganisedEventsList() {
        return organisedEventsList;
    }

    public void setOrganisedEventsList(List<SportEvent> organisedEventsList) {
        this.organisedEventsList = organisedEventsList;
    }

    public List<SportEvent> getParticipateEventsList() {
        return participateEventsList;
    }

    public void setParticipateEventsList(List<SportEvent> participateEventsList) {
        this.participateEventsList = participateEventsList;
    }

    public List<SportEvent> getSearchedEventsList() {
        if (searchedEventsList == null) {
            searchedEventsList = getSportEventFacade().listAll();
        }
        return searchedEventsList;
    }

    public void setSearchedEventsList(List<SportEvent> searchedEventsList) {
        this.searchedEventsList = searchedEventsList;
    }

    //Method return logged user in current session
    public User getLoggedInUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        User logInUser = (User) request.getSession().getAttribute("user");
        return logInUser;
    }

    public void createSportEvent() {
        try {
            sportEvent.setOrganizer(getLoggedInUser());
            getSportEventFacade().createSportEvent(sportEvent);
            displayInfoMessageToUser(" New Sport Event added with Success");
            loadSportEvents();
            resetSportEvent();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not create. Try again later");
            e.printStackTrace();
        }
    }

    public void resetSportEvent() {
        sportEvent = new SportEvent();
    }

    public List<SportEvent> getAllSportsEvents() {
        if (sportEventsList == null) {
            loadSportEvents();
        }
        return sportEventsList;
    }

    private void loadSportEvents() {
        sportEventsList = getSportEventFacade().listAll();
        if (sportEventsList == null) {
            sportEventsList = new ArrayList<SportEvent>();
        }
        getSearchedEventsList();
    }

    public String showDetails() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(SPORT_EVENT_TO_CONFIRM, sportEvent);
        return "/pages/protected/user/sportEvents/detailsOfSportEvent.xhtml?faces-redirect=true";
    }

    public String resetDetails() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(SPORT_EVENT_TO_CONFIRM, chosenEvent);
        return "/pages/protected/user/sportEvents/detailsOfSportEvent.xhtml?faces-redirect=true";
    }

    public void resetChosenEvent() {
        chosenEvent = new SportEvent();
    }

    public void addParticipant() {
        try {
            getSportEventFacade().addParticipantToSportEvent(getLoggedInUser().getId(), chosenEvent.getId());
            closeDialog();
            displayInfoMessageToUser("You are successfully added to this Event");
            resetSportEventDetail();
            resetDetails();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not add you to this Event. Try again later");
            e.printStackTrace();
        }
    }

    public void deleteSportEvent() {
        try {
            getSportEventFacade().deleteSportEvent(sportEvent);
            closeDialog();
            displayInfoMessageToUser("Deleted with Success");
            loadSportEvents();
            resetSportEvent();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not delete. Try again later");
            e.printStackTrace();
        }
    }

    public String editSportEvent() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(SPORT_EVENT_TO_CONFIRM, sportEvent);
        System.out.println(sportEvent + " --editSPortEvent");
        return "/pages/protected/user/sportEvents/editSportEvent.xhtml?faces-redirect=true";
    }

    //Method to update details of SportEvent
    public void updateSportEvent() {
        try {
            getSportEventFacade().updateSportEvent(sportEventDetail);
            displayInfoMessageToUser("Updated With Success");
            resetSportEventDetail();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not update. Try again later");
            e.printStackTrace();
        }
    }

    public void resetSportEventDetail() {
        sportEventDetail = null;
    }

    public boolean checkUser() {
        if (getLoggedInUser().equals(getSportEventDetail().getOrganizer())
                || getSportEventDetail().getParticipants().contains(getLoggedInUser())) {
            return true;
        }
        return false;
    }

    public List<SportEvent> getAllOrganisedEvents() {
        if (organisedEventsList == null) {
            organisedEventsList = getSportEventFacade().allOrganisedEvents(getLoggedInUser());
        }
        return organisedEventsList;
    }

    public List<SportEvent> getAllParticipateEvents() {
        if (participateEventsList == null) {
            participateEventsList = getSportEventFacade().allParticipateEvents(getLoggedInUser());
        }
        return participateEventsList;
    }

    public List<SportEvent> searchEvents() {
        System.out.println(searchedEventsList);
        searchedEventsList = getSportEventFacade().searchEvents(getSearchedEvent());
        return searchedEventsList;
    }

    public void cleanupOldEvents() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        Iterator iterator = getAllSportsEvents().iterator();
        while (iterator.hasNext()) {
            SportEvent se = (SportEvent) iterator.next();
            if (se.getDate().before(date)) {
                getSportEventFacade().deleteSportEvent(se);
                loadSportEvents();
                resetSportEvent();
            }
        }

    }

}
