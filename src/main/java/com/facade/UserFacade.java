package com.facade;

import com.dao.SportCategoryDAO;
import com.dao.UserDAO;
import com.dao.UserDetailsDAO;
import com.model.SportCategory;
import com.model.User;
import com.model.UserDetails;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserFacade {
    private UserDAO userDAO = new UserDAO();
    private UserDetailsDAO userDetailsDAO = new UserDetailsDAO();
    private SportCategoryDAO  sportCategoryDAO= new SportCategoryDAO();

    public User findUser(String login){
       return userDAO.findUserByLogin(login);
    }

    public User isValidLogin(String login, String password) {
        userDAO.beginTransaction();
        User user = userDAO.findUserByLogin(login);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    public boolean isLoginFree(String login){
        userDAO.beginTransaction();
        User user = userDAO.findUserByLogin(login);
        if(user == null){
            return true;
        }
        return false;
    }

    public void createUser(User user) {
        userDAO.beginTransaction();
        userDAO.save(user);
        userDAO.commitAndCloseTransaction();
    }

    public void updateUser(User user){
        userDAO.beginTransaction();
        User updatedUser = (User) userDAO.find(user.getId());
        updatedUser.setName(user.getName());
        userDAO.commitAndCloseTransaction();
    }

    public List<User> listAll() {
        userDAO.beginTransaction();
        List<User> result = userDAO.findAll();
        userDAO.closeTransaction();
        return result;
    }

    public void addUserDetailsToUser(int userDetailsId, int userId){
        userDAO.beginTransaction();
        userDetailsDAO.joinTransaction();
        UserDetails userDetails = (UserDetails) userDetailsDAO.find(userDetailsId);
        User user = userDAO.find(userId);
        user.setUserDetails(userDetails);
        userDetails.setUser(user);
        userDAO.commitAndCloseTransaction();
    }

    public void addSportCategoryToUser(int sportCatId, int userId) {
        userDAO.beginTransaction();
        sportCategoryDAO.joinTransaction();
        SportCategory sportCategory = (SportCategory) sportCategoryDAO.find(sportCatId);
        User user = userDAO.find(userId);
        user.getSportsList().add(sportCategory);
        sportCategory.getUsers().add(user);
        updateUserSportsList(user);
        userDAO.commitAndCloseTransaction();
    }

    public void removeSportCategoryToUser(int sportCatId, int userId) {
        userDAO.beginTransaction();
        sportCategoryDAO.joinTransaction();
        SportCategory sportCategory = (SportCategory) sportCategoryDAO.find(sportCatId);
        User user = userDAO.find(userId);
        user.getSportsList().remove(sportCategory);
        sportCategory.getUsers().remove(user);
        updateUserSportsList(user);
        userDAO.commitAndCloseTransaction();
    }

    private void updateUserSportsList(User user) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().setAttribute("user", user);
    }
}
