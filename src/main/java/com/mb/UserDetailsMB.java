package com.mb;

import com.facade.UserDetailsFacade;
import com.model.UserDetails;




import javax.faces.bean.ManagedBean;

import javax.servlet.http.Part;




@ManagedBean
public class UserDetailsMB extends AbstractMB {


    private UserDetails userDetails;
    private UserDetailsFacade userDetailsFacade;



    public UserDetails getUserDetails() {
        if(userDetails==null){
            userDetails=new UserDetails();
        }
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    private void resetUserDetails() {
        userDetails = new UserDetails();
    }

    public UserDetailsFacade getUserDetailsFacade() {
        if (userDetailsFacade == null) {
            userDetailsFacade = new UserDetailsFacade();
        }

        return userDetailsFacade;
    }


}
