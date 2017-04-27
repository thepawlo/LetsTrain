package com.dao;

import com.model.City;
import com.model.SportCategory;

/**
 * Created by Pawel on 2017-04-11.
 */
public class CityDAO extends GenericDAO {

    public CityDAO() {
        super(City.class);
    }

    public void delete(City city) {
        super.delete(city.getId(), City.class);
    }
}
