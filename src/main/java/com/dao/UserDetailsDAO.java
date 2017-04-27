package com.dao;

import com.model.UserDetails;

/**
 * Created by Pawel on 2017-03-31.
 */
public class UserDetailsDAO extends GenericDAO {


    public UserDetailsDAO() {
        super(UserDetails.class);
    }

    @Override
    public Object find(int entityID) {
        Object object =  super.find(entityID);
        if(object==null){
            object=new Object();
        }
        return object;
    }
}
