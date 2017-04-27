package com.facade;

import com.dao.SportEventDAO;
import com.dao.UserDAO;
import com.model.SportCategory;
import com.model.SportEvent;
import com.model.User;

import java.util.List;

/**
 * Created by Pawel on 2017-04-11.
 */
public class SportEventFacade {

    private SportEventDAO sportEventDAO = new SportEventDAO();
    private UserDAO userDAO = new UserDAO();

    public void createSportEvent(SportEvent sportEvent) {
        sportEventDAO.beginTransaction();
        sportEventDAO.save(sportEvent);
        sportEventDAO.commitAndCloseTransaction();
    }

    public List<SportEvent> listAll() {
        sportEventDAO.beginTransaction();
        List<SportEvent> result = sportEventDAO.findAll();
        sportEventDAO.closeTransaction();
        return result;
    }

    public List<SportEvent> allOrganisedEvents(User loggedUser) {
        sportEventDAO.beginTransaction();
        List<SportEvent> result = sportEventDAO.findOrganisedEvents(loggedUser.getId());
        sportEventDAO.closeTransaction();
        return result;
    }

    public List<SportEvent> allParticipateEvents(User loggedUser) {
        sportEventDAO.beginTransaction();
        List<SportEvent> result = sportEventDAO.findParticipateEvents(loggedUser.getId());
        sportEventDAO.closeTransaction();
        return result;
    }

    public List<SportEvent> allEventsWithUser(User user) {
        sportEventDAO.beginTransaction();
        List<SportEvent> result = sportEventDAO.findEventsWithUser(user.getId());
        sportEventDAO.closeTransaction();
        return result;
    }

    public List<SportEvent> searchEvents(SportEvent searchedSportEvent) {
        sportEventDAO.beginTransaction();
        List<SportEvent> result = sportEventDAO.searchEvents(searchedSportEvent);
        sportEventDAO.closeTransaction();
        return result;
    }

    public void deleteSportEvent(SportEvent sportEvent) {
        sportEventDAO.beginTransaction();
        SportEvent persistedSportEvent = (SportEvent) sportEventDAO.findReferenceOnly(sportEvent.getId());
        sportEventDAO.delete(persistedSportEvent);
        sportEventDAO.commitAndCloseTransaction();
    }


    public SportEvent findSportEvent(int sportEvId) {
        sportEventDAO.beginTransaction();
        SportEvent sportEvent = (SportEvent) sportEventDAO.find(sportEvId);
        sportEventDAO.closeTransaction();
        return sportEvent;
    }

    public void updateSportEvent(SportEvent sportEvent){
        sportEventDAO.beginTransaction();
        SportEvent updatedSportEvent = (SportEvent) sportEventDAO.find(sportEvent.getId());
        updatedSportEvent.setCity(sportEvent.getCity());
        updatedSportEvent.setSportCategory(sportEvent.getSportCategory());
        updatedSportEvent.setDate(sportEvent.getDate());
        updatedSportEvent.setAmountOfParticipants(sportEvent.getAmountOfParticipants());
        updatedSportEvent.setDescription(sportEvent.getDescription());
        sportEventDAO.commitAndCloseTransaction();
    }

    public void addParticipantToSportEvent(int participantId, int sportEventId){
        sportEventDAO.beginTransaction();
        userDAO.joinTransaction();
        SportEvent sportEvent = (SportEvent) sportEventDAO.find(sportEventId);
        User participant = userDAO.find(participantId);
        participant.getSportEventsList().add(sportEvent);
        sportEvent.getParticipants().add(participant);
        sportEventDAO.commitAndCloseTransaction();
    }
}
