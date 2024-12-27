package com.tokioschool.flightapp.runner;

import com.tokioschool.flightapp.domain.Beer;
import com.tokioschool.flightapp.domain.Main;
import com.tokioschool.flightapp.domain.Menu;
import com.tokioschool.flightapp.projection.BeerStyleCountAggregate;
import com.tokioschool.flightapp.repository.BeerDao;
import com.tokioschool.flightapp.repository.MenuDao;
import com.tokioschool.flightapp.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    private final BeerDao beerDao;

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
        vegetarianMenu.setVegetarian(false); // comentar para no alterar en el resto de pruebas
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

        // filtar por valores numericos
        List<Menu> byCaloriesGreaterThan = menuService.findByCaloriesGreaterThan(BigDecimal.valueOf(650));
        log.info("Calories gt 650: {}",byCaloriesGreaterThan.size());

        // calcular la media de calorias
        Double averageCalories = menuService.findByCaloriesAverage();
        log.info("Average calories: {}",averageCalories);

        // Filtrar por un criterio nested y devolver (projectoin) los camos que hacen matching
        List<Menu> menusWithMainsWithLettuce = menuDao.findMainsByIngredient("Lettuce");
        log.info("Menus with mains lettuce: {}", menusWithMainsWithLettuce.size());
        // minute 27
        Menu menuAndMainsWithLettuce = menusWithMainsWithLettuce.getFirst();
        Menu menuAndMainsWithLettuceAndOthers = menuDao.findById( menuAndMainsWithLettuce.getId() ).get();
        log.info("Menus mains complete: {}", menuAndMainsWithLettuce.getMains());
        log.info("Menus mains lettuce: {}", menuAndMainsWithLettuceAndOthers.getMains());

        // invlaid filter by dbref
        //List<Menu> lightLager = menuDao.findByBeerStyleIgnoreCase("Light Lager");
        //log.info("Menu with beer style: {}",lightLager.size());

        // cerveza más comun (projection)
        List<BeerStyleCountAggregate> beerStyleCountAggregates = beerDao.countByStyle();
        log.info("Beer Aggregate by Style: {}",beerStyleCountAggregates);

        // get beer by style
        List<Beer> beersWithStyle = beerDao.findByStyleIgnoreCase( beerStyleCountAggregates.getFirst().getStyle() );
        List<Beer> beersWithNotStyle = beerDao.findByStyleIsNotIgnoreCase( beerStyleCountAggregates.getFirst().getStyle() );
        log.info("Beer with style ({}) vs not: {} / {}",
                beerStyleCountAggregates.getFirst().getStyle(),
                beersWithStyle.size(),
                beersWithNotStyle.size());
    }
}
