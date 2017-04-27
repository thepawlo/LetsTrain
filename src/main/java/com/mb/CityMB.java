package com.mb;

import com.facade.CityFacade;
import com.model.City;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 2017-04-11.
 */

@ManagedBean
@ApplicationScoped
public class CityMB extends AbstractMB{

    private CityFacade cityFacade;
    private City city;
    private List<City> citiesList;


    public CityFacade getCityFacade() {
        if(cityFacade==null){
            cityFacade = new CityFacade();
        }
        return cityFacade;
    }

    public void setCityFacade(CityFacade cityFacade) {
        this.cityFacade = cityFacade;
    }

    public City getCity() {
        if(city==null){
            city=new City();
        }
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<City> getCitiesList() {
        if(citiesList==null){
            citiesList = new ArrayList<City>();
        }
        return citiesList;
    }

    public void setCitiesList(List<City> citiesList) {
        this.citiesList = citiesList;
    }

    public List<City> getAllCities() {
        if (citiesList == null) {
            loadCities();
        }

        return citiesList;
    }

    public void createCity(){
        try {
            getCityFacade().createCity(city);
            closeDialog();
            displayInfoMessageToUser(" New City added with Success");
            loadCities();
            resetCity();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not create. Try again later");
            e.printStackTrace();
        }
    }

    private void loadCities() {
        citiesList = getCityFacade().listAll();
        if(citiesList==null){
            citiesList=new ArrayList<City>();
        }
    }

    public void resetCity(){
        city = new City();
    }

    public void deleteCity(){
        try {
            getCityFacade().deleteCity(city);
            closeDialog();
            displayInfoMessageToUser("Deleted with Success");
            loadCities();
            resetCity();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not delete. Try again later");
            e.printStackTrace();
        }
    }

}
