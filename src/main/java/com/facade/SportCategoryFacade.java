package com.facade;

import com.dao.SportCategoryDAO;
import com.model.SportCategory;
import com.model.User;

import java.util.List;

/**
 * Created by Pawel on 2017-04-05.
 */
public class SportCategoryFacade {

    private SportCategoryDAO sportCategoryDAO = new SportCategoryDAO();

    public void createSportCategory(SportCategory sportCategory) {
        sportCategoryDAO.beginTransaction();
        sportCategoryDAO.save(sportCategory);
        sportCategoryDAO.commitAndCloseTransaction();
    }

    public List<SportCategory> listAll() {
        sportCategoryDAO.beginTransaction();
        List<SportCategory> result = sportCategoryDAO.findAll();
        sportCategoryDAO.closeTransaction();
        return result;
    }

    public void deleteSport(SportCategory sportCategory) {
        sportCategoryDAO.beginTransaction();
        SportCategory persistedSportCategory = (SportCategory) sportCategoryDAO.findReferenceOnly(sportCategory.getId());
        sportCategoryDAO.delete(persistedSportCategory);
        sportCategoryDAO.commitAndCloseTransaction();
    }


    public SportCategory findSportCategory(int sportCatId) {
        sportCategoryDAO.beginTransaction();
        SportCategory sportCategory = (SportCategory) sportCategoryDAO.find(sportCatId);
        sportCategoryDAO.closeTransaction();
        return sportCategory;
    }
}
