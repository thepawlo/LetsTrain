package com.facade;

import com.dao.CityDAO;
import com.dao.UserDetailsDAO;
import com.model.City;
import com.model.UserDetails;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Pawel on 2017-03-31.
 */
public class UserDetailsFacade {

    private UserDetailsDAO userDetailsDAO = new UserDetailsDAO();
    private CityDAO cityDAO = new CityDAO();

    public void createUserDetails(UserDetails userDetails){
        userDetailsDAO.beginTransaction();
        userDetailsDAO.save(userDetails);
        userDetailsDAO.commitAndCloseTransaction();
    }

    public void updateUserDetails(UserDetails userDetails){
        userDetailsDAO.beginTransaction();
        cityDAO.joinTransaction();
        UserDetails updatedUserDetails = (UserDetails) userDetailsDAO.find(userDetails.getId());
        City city = (City) cityDAO.find(userDetails.getCity().getId());
        updatedUserDetails.setAge(userDetails.getAge());
        updatedUserDetails.setCity(city);
        city.getUserDetailsList().add(updatedUserDetails);
        userDetailsDAO.commitAndCloseTransaction();
    }
}
