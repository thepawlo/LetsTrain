package com.facade;

import com.dao.CityDAO;
import com.model.City;
import com.model.SportEvent;

import java.util.List;

/**
 * Created by Pawel on 2017-04-11.
 */
public class CityFacade {

    private CityDAO cityDAO = new CityDAO();

    public void createCity(City city) {
        cityDAO.beginTransaction();
        cityDAO.save(city);
        cityDAO.commitAndCloseTransaction();
    }

    public void deleteCity(City city) {
        cityDAO.beginTransaction();
        City persistedCity = (City) cityDAO.findReferenceOnly(city.getId());
        cityDAO.delete(persistedCity);
        cityDAO.commitAndCloseTransaction();
    }

    public List<City> listAll() {
        cityDAO.beginTransaction();
        List<City> result = cityDAO.findAll();
        cityDAO.closeTransaction();
        return result;
    }

    public City findCity(int cityId) {
        cityDAO.beginTransaction();
        City city = (City) cityDAO.find(cityId);
        cityDAO.closeTransaction();
        return city;
    }
}
