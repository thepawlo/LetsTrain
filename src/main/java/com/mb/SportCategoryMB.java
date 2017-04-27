package com.mb;

import com.facade.SportCategoryFacade;
import com.model.SportCategory;


import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 2017-04-05.
 */

@ManagedBean
@ApplicationScoped
public class SportCategoryMB extends AbstractMB implements Serializable{

    private SportCategory sportCategory;
    private List<SportCategory> sportsList;
    private SportCategoryFacade sportCategoryFacade;

    public SportCategory getSportCategory() {
        if(sportCategory==null){
            sportCategory=new SportCategory();
        }
        return sportCategory;
    }

    public void setSportCategory(SportCategory sportCategory) {
        this.sportCategory = sportCategory;
    }

    public List<SportCategory> getSportsList() {
        if(sportsList==null){
            sportsList= new ArrayList<SportCategory>();
        }
        return sportsList;
    }

    public void setSportsList(List<SportCategory> sportsList) {
        this.sportsList = sportsList;
    }

    public SportCategoryFacade getSportCategoryFacade() {
        if(sportCategoryFacade==null){
            sportCategoryFacade = new SportCategoryFacade();
        }
        return sportCategoryFacade;
    }

    public void setSportCategoryFacade(SportCategoryFacade sportCategoryFacade) {
        this.sportCategoryFacade = sportCategoryFacade;
    }

    public List<SportCategory> getAllSportsCategories() {
        if (sportsList == null) {
            loadSportCategories();
        }

        return sportsList;
    }

    private void loadSportCategories() {
        sportsList = getSportCategoryFacade().listAll();
        if(sportsList==null){
            sportsList=new ArrayList<SportCategory>();
        }
    }

    public void resetSportCategory(){
        sportCategory = new SportCategory();
    }


    public void createSportCategory(){
        try {
            getSportCategoryFacade().createSportCategory(sportCategory);
            closeDialog();
            displayInfoMessageToUser(" New Sport Category added with Success");
            loadSportCategories();
            resetSportCategory();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not create. Try again later");
            e.printStackTrace();
        }
    }

    public void deleteSportCategory() {
        try {
            getSportCategoryFacade().deleteSport(sportCategory);
            closeDialog();
            displayInfoMessageToUser("Deleted with Sucess");
            loadSportCategories();
            resetSportCategory();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("Ops, we could not delete. Try again later");
            e.printStackTrace();
        }
    }
}
