package com.dao;

import com.model.SportCategory;

/**
 * Created by Pawel on 2017-04-05.
 */
public class SportCategoryDAO extends GenericDAO{


    public SportCategoryDAO() {
        super(SportCategory.class);
    }

    public void delete(SportCategory sportCategory) {
        super.delete(sportCategory.getId(), SportCategory.class);
    }
}
