package com.converter;

import com.facade.CityFacade;
import com.model.City;


import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Created by Pawel on 2017-04-11.
 */

@ManagedBean
@ApplicationScoped
@FacesConverter(forClass = City.class)
public class CityConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        CityFacade cityFacade = new CityFacade();
        int cityId;

        try {
            cityId = Integer.parseInt(s);
        } catch (NumberFormatException exception) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Choose your city", "Choose your city"));
        }

        return cityFacade.findCity(cityId);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "";
        }
        City  city = (City) o;
        return String.valueOf(city.getId());
    }
}
