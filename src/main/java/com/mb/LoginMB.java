package com.mb;

import com.facade.UserFacade;
import com.model.Role;
import com.model.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@ManagedBean
public class LoginMB extends AbstractMB {
    @ManagedProperty(value = UserMB.INJECTION_NAME)
    private UserMB userMB;

    private String login;
    private String password;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        UserFacade userFacade = new UserFacade();

        User user = userFacade.isValidLogin(login, password);

        if(user != null){
            userMB.setUser(user);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().setAttribute("user", user);
            return "/pages/protected/index.xhtml";
        }

        displayErrorMessageToUser("Check your login/password");

        return null;
    }

    public UserMB getUserMB() {
        return userMB;
    }

    public void setUserMB(UserMB userMB) {
        this.userMB = userMB;
    }

    public void resetUser(){
        userMB = new UserMB();
    }
}
