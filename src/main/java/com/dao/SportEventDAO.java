package com.dao;


import com.model.SportEvent;
import com.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pawel on 2017-04-11.
 */
public class SportEventDAO extends GenericDAO {

    public SportEventDAO() {
        super(SportEvent.class);
    }

    public void delete(SportEvent sportEvent) {
        super.delete(sportEvent.getId(), SportEvent.class);
    }

    @SuppressWarnings("unchecked")
    public List<SportEvent> findOrganisedEvents(int loggedUserID){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userID", loggedUserID);

        return super.findList(SportEvent.FIND_ORGANISED_EVENTS, parameters);
    }

    @SuppressWarnings("unchecked")
    public List<SportEvent> findParticipateEvents(int loggedUserID){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userID", loggedUserID);

        return super.findList(SportEvent.FIND_PARTICIPATE_EVENTS, parameters);
    }

    @SuppressWarnings("unchecked")
    public List<SportEvent> searchEvents(SportEvent sportEvent){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("city", sportEvent.getCity());
        parameters.put("sportCategory", sportEvent.getSportCategory());
        //parameters.put("date", sportEvent.getDate());

        return super.findList(SportEvent.SEARCH_EVENTS, parameters);
    }

    @SuppressWarnings("unchecked")
    public List<SportEvent> findEventsWithUser(int userID){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userID", userID);

        return super.findList(SportEvent.FIND_EVENTS_WITH_USER, parameters);
    }
}
