package com.mb;

import com.facade.SportEventFacade;
import com.facade.UserFacade;
import com.model.SportEvent;
import com.model.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 2017-04-24.
 */

@ManagedBean
@ViewScoped
public class UserManagerMB {

    public static final String USER_DETAILS = "user";

    private User user;
    private UserFacade userFacade;
    private List<User> allUsersList;
    private List<SportEvent> allEventsWithUserList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserFacade getUserFacade() {
        if (userFacade == null) {
            userFacade = new UserFacade();
        }
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public List<User> getAllUsersList() {
        getAllUsers();
        return allUsersList;
    }

    public void setAllUsersList(List<User> allUsersList) {
        this.allUsersList = allUsersList;
    }

    public List<SportEvent> getAllEventsWithUserList() {
        return allEventsWithUserList;
    }

    public void setAllEventsWithUserList(List<SportEvent> allEventsWithUserList) {
        this.allEventsWithUserList = allEventsWithUserList;
    }

    public List<User> getAllUsers() {
        if (allUsersList == null) {
            loadUsers();
        }
        return allUsersList;
    }

    private void loadUsers() {
        allUsersList = getUserFacade().listAll();
        if (allUsersList == null) {
            allUsersList = new ArrayList<User>();
        }
    }

    public String showUser() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(USER_DETAILS, user);
        return "/pages/protected/user/userDetails.xhtml?faces-redirect=true";
    }

    public List<SportEvent> getAllEventsWithUser() {
        SportEventFacade sportEventFacade = new SportEventFacade();
        if (allEventsWithUserList == null) {
            allEventsWithUserList = sportEventFacade.allEventsWithUser(user);
        }
        return allEventsWithUserList;
    }

    public User loadUserDetails(){
        if(user==null){
            user = (User) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(USER_DETAILS);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().keep(USER_DETAILS);
        }
        return user;
    }
}
