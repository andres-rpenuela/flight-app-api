package com.tokioschool.flightapp.runner;

import com.tokioschool.flightapp.domain.Menu;
import com.tokioschool.flightapp.repository.MenuDao;
import com.tokioschool.flightapp.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Tarea que se ejecuta cuando inicia la aplicaiocn de menu service, y
 * cuando finaliza, se cierra la aplicación, es la aplicaicon
 * mas basica de spring boot
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationTaskService implements ApplicationRunner {

    private final MenuService menuService;
    private final MenuDao menuDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationTaskService started");

        // comprueba si hay menu creado o no
        long countMenus = menuDao.count();
        log.info("Count menus: {}",countMenus);

        if(countMenus == 0){
            Collection<Menu> randomMenus = menuService.createRandomMenus();
            Menu menu10 = menuDao.findById(randomMenus
                    .toArray(new Menu[0])[10]
                    .getId()
            ).orElseGet(()->null);
            log.info("Menu 10 random: {}",menu10);
        }

        // Reucperar los ids de menus
        List<String> menusId = menuService.getMenusId();
        log.info("Menus ids: {}",String.join(", ",menusId));
    }
}
