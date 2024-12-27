package com.tokioschool.flightapp.runner;

import com.tokioschool.flightapp.domain.Main;
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

        // Filtar los menus vegetarias y ordernalos por titulo
        List<Menu> vegetarianMenus = menuService.findByVegetarianIsTrueOrderByTitle();
        log.info("Vegetarian menus: {}, first: {}, last: {} ",
                vegetarianMenus.size(),
                vegetarianMenus.getFirst().getTitle(),
                vegetarianMenus.getLast().getTitle());

        // Ordenar por campos nested en los documentos
        Menu menu11 = menuService.findById( menusId.get(11) );
        log.info("Menu 11 non-ordered: {}-{}",
                menu11.getTitle(),
                menu11.getMains().stream().map(Main::getName).toList());

        Menu menu11Ordered = menuService.findByIdWithMainsOrdered( menusId.get(11) );
        log.info("Menu 11 ordered: {}-{}",
                menu11Ordered.getTitle(),
                menu11Ordered.getMains().stream().map(Main::getName).toList());

        // Modify documents, via insert
        Menu vegetarianMenu = vegetarianMenus.get(0);
        vegetarianMenu.setVegetarian(false);
        menuService.updatedMenu(vegetarianMenu);

        long countByVegetarianIsTrue = menuDao.countByVegetarianIsTrue();
        log.info("Vegetarian menus: {}",countByVegetarianIsTrue);

        // filtrar por texto, en un camo nested
        List<Menu> pizzaMenusByMainName = menuService.findByMainsNameCaseSensitive("pizza");
        log.info("Pizza menus case-sensitive: {}",pizzaMenusByMainName.size());

        List<Menu> pizzaMenusByMainName2 = menuService.findByMainsNameCaseSensitive("Pizza");
        log.info("Pizza menus case-sensitive: {}",pizzaMenusByMainName2.size());

        List<Menu> pizzaMenusByMainNameCI = menuService.findByMainsNameCaseInsensitive("pizza");
        log.info("Pizza menus case-insensitive: {}",pizzaMenusByMainNameCI.size());
    }
}
