package com.tokioschool.flightapp.service;

import com.tokioschool.flightapp.domain.Menu;

import java.util.Collection;

public interface MenuService {
    Collection<Menu> createRandomMenus();
}
