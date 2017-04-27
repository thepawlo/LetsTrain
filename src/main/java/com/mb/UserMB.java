package com.mb;

import com.facade.SportCategoryFacade;
import com.facade.SportEventFacade;
import com.facade.UserDetailsFacade;
import com.facade.UserFacade;
import com.model.*;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SessionScoped
@ManagedBean(name="userMB")
public class UserMB extends  AbstractMB implements Serializable {

    public static final String INJECTION_NAME = "#{userMB}";

    private User user;
    private UserDetails userDetails;
    private UserFacade userFacade;
    private UserDetailsFacade userDetailsFacade;
    private User logInUser;   //logged user in current session
    private SportCategory sportCategory;  //actual sport category chosen by user from a list
    private List<SportCategory> sportsList;


    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetails getUserDetails() {
        if (userDetails == null) {
            userDetails = new UserDetails();
        }
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
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

    public UserDetailsFacade getUserDetailsFacade() {
        if (userDetailsFacade == null) {
            userDetailsFacade = new UserDetailsFacade();
        }
        return userDetailsFacade;
    }

    public void setUserDetailsFacade(UserDetailsFacade userDetailsFacade) {
        this.userDetailsFacade = userDetailsFacade;
    }

    public User getLogInUser() {
        return logInUser;
    }

    public void setLogInUser(User logInUser) {
        this.logInUser = logInUser;
    }

    public SportCategory getSportCategory() {
        return sportCategory;
    }

    public void setSportCategory(SportCategory sportCategory) {
        this.sportCategory = sportCategory;
    }

    public List<SportCategory> getSportsList() {
        return sportsList=getLoggedUserSportsList();
    }

    public void setSportsList(List<SportCategory> sportsList) {
        this.sportsList = sportsList;
    }

    public Role[] getRoles() {
        return Role.values();
    }

    public boolean isAdmin() {
        return user.isAdmin();
    }

    public boolean isDefaultUser() {
        return user.isUser();
    }

    public String logOut() {
        getRequest().getSession().invalidate();
        return "/pages/public/loginOrRegister.xhtml";
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public String register() {
        try {
            userFacade = new UserFacade();
            userDetailsFacade = new UserDetailsFacade();
            userDetails = new UserDetails();

            if (userFacade.isLoginFree(user.getLogin())) {
                userFacade.createUser(user);
                userDetailsFacade.createUserDetails(userDetails);
                getUserFacade().addUserDetailsToUser(userDetails.getId(),user.getId());
                closeDialog();
                displayInfoMessageToUser("Register With Success");
                resetUser();
                resetUserDetails();
                return "/pages/public/loginOrRegister.xhtml";
            }
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("This login is taken. Please change your username!");
            e.printStackTrace();
        }
        return null;
    }

    public void resetUser() {
        user = new User();
    }

    private void resetUserDetails() {
        userDetails = new UserDetails();
    }

    //Method return logged user in current session
    public User getLoggedInUser(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        logInUser = (User) request.getSession().getAttribute("user");
        return logInUser;
    }

    //Method to update details of logged user - from user account
    public void update() {
        try {
            getUserFacade().updateUser(logInUser);
            getUserDetailsFacade().updateUserDetails(getLoggedInUser().getUserDetails());
            displayInfoMessageToUser("Updated With Success");
            resetUserDetails();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not update. Try again later");
            e.printStackTrace();
        }
    }

    //Method return list of favourite sports od the logged user
    public List<SportCategory> getLoggedUserSportsList() {
        sportsList = getLoggedInUser().getSportsList();
        return sportsList;
    }

    //Method return all available sport categories for logged user, which are not on his list of favourite sports
    public List<SportCategory> getSportCategories() {
        List<SportCategory> queryResult = new ArrayList<SportCategory>();
        SportCategoryFacade sportCategoryFacade = new SportCategoryFacade();
        List<SportCategory> allSports = sportCategoryFacade.listAll();
        if (allSports == null || allSports.isEmpty()) {
            allSports = new ArrayList<SportCategory>();
        }
        allSports.removeAll(sportsList);
        for (SportCategory sp : allSports) {
            queryResult.add(sp);
        }
        return queryResult;
    }

    public void addUserSportCategory(){
        try {
            getUserFacade().addSportCategoryToUser(sportCategory.getId(),logInUser.getId());
            closeDialog();
            displayInfoMessageToUser("New Sport Category added with Success");
            resetSportCategory();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not add. Try again later");
            e.printStackTrace();
        }
    }

    public void removeUserSportCategory() {
        try {
            getUserFacade().removeSportCategoryToUser(sportCategory.getId(),logInUser.getId());
            closeDialog();
            displayInfoMessageToUser("Removed with Success");
            resetSportCategory();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not remove this category. Try again later");
            e.printStackTrace();
        }
    }

    public void resetSportCategory(){
        sportCategory = new SportCategory();
    }


}
