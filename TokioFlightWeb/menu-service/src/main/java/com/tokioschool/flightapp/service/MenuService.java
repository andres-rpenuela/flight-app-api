package com.tokioschool.flightapp.service;

import com.tokioschool.flightapp.domain.Menu;

import java.util.Collection;
import java.util.List;

public interface MenuService {
    Collection<Menu> createRandomMenus();

    List<String> getMenusId();

    List<Menu> findByVegetarianIsTrueOrderByTitle();

    Menu findById(String id);

    Menu findByIdWithMainsOrdered(String id);

    void updatedMenu(Menu vegetarianMenu);

    List<Menu> findByMainsNameCaseSensitive(String name);

    List<Menu> findByMainsNameCaseInsensitive(String name);
}
