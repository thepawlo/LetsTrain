package com.converter;


import com.facade.SportCategoryFacade;
import com.model.SportCategory;


import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Created by Pawel on 2017-04-05.
 */
@ManagedBean
@ApplicationScoped
@FacesConverter(forClass = SportCategory.class)
public class SportCategoryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        SportCategoryFacade sportCategoryFacade = new SportCategoryFacade();
        int sportCatId;
        try {
            sportCatId = Integer.parseInt(s);
        } catch (NumberFormatException exception) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Choose your sports", "Choose your sports"));
        }
        return sportCategoryFacade.findSportCategory(sportCatId);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "";
        }
        SportCategory  sportCategory = (SportCategory) o;
        return String.valueOf(sportCategory.getId());
    }
}
