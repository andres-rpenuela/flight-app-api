package com.tokioschool.flightapp.service.impl;

import com.github.javafaker.Faker;
import com.tokioschool.flightapp.domain.Beer;
import com.tokioschool.flightapp.domain.Ingredient;
import com.tokioschool.flightapp.domain.Main;
import com.tokioschool.flightapp.domain.Menu;
import com.tokioschool.flightapp.repository.BeerDao;
import com.tokioschool.flightapp.repository.MenuDao;
import com.tokioschool.flightapp.service.MenuService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao;
    private final BeerDao beerDao;
    private final MongoTemplate mongoTemplate;
    private static Faker faker;
    private static SecureRandom secureRandom;

    @PostConstruct
    private void postConstructor(){
        faker = new Faker();
        secureRandom = new SecureRandom();
    }

    @Override
    public Collection<Menu> createRandomMenus() {
        List<Beer> randomBeers = createdRandomBeers();

        List<Menu> menusToCreate = IntStream.range(0,100)
                .mapToObj(i->
                    Menu.builder()
                            .title("menu-%02d".formatted(i))
                            .created(Instant.now())
                            .mains(createRandomMains(i))
                            .calories(BigDecimal.valueOf(secureRandom.nextDouble(300,1000)))
                            .vegetarian(secureRandom.nextBoolean())
                            .beer( randomBeers.get( secureRandom.nextInt( randomBeers.size() )) )
                            .build()
                ).toList();

        return menuDao.insert(menusToCreate);
    }

    @Override
    public List<String> getMenusId() {
        Query query = new Query();
        query.fields().include("_id");

        return mongoTemplate.find(query,Menu.class)
                .stream()
                .map(Menu::getId)
                .toList();
    }

    @Override
    public List<Menu> findByVegetarianIsTrueOrderByTitle() {
        return menuDao.findByVegetarianIsTrueOrderByTitle();
    }

    @Override
    public Menu findById(String id) {
        return menuDao.findById(id).orElseGet(()->null);
    }

    @Override
    public Menu findByIdWithMainsOrdered(String id) {
        return menuDao.findByIdWithMainsOrdered(id).orElseGet(() -> null);
    }

    @Override
    public void updatedMenu(Menu vegetarianMenu) {
        // esto da error porque contiente un "id" el objeto
        //menuDao.insert(vegetarianMenu);

        // para ello, se usa
        menuDao.save(vegetarianMenu);
    }

    @Override
    public List<Menu> findByMainsNameCaseSensitive(String name) {
        return menuDao.findByMainsName(name);
    }

    @Override
    public List<Menu> findByMainsNameCaseInsensitive(String name) {
        //return menuDao.findByMainsNameIgnoreCase(name);
        return menuDao.findByMainsNameCaseInsensitive(name);
    }

    @Override
    public List<Menu> findByCaloriesGreaterThan(BigDecimal calories) {
        return menuDao.findByCaloriesGreaterThan(calories);
    }

    @Override
    public Double findByCaloriesAverage() {
        return menuDao.findByCaloriesAverage();
    }


    private List<Main> createRandomMains(int i){
        return IntStream.range(0,i).mapToObj(
                j -> Main.builder()
                        .name(faker.food().dish())
                        .ingredients(
                                List.of(
                                        Ingredient.builder().name(faker.food().ingredient()).build(),
                                        Ingredient.builder().name(faker.food().ingredient()).build(),
                                        Ingredient.builder().name(faker.food().vegetable()).build(),
                                        Ingredient.builder().name(faker.food().spice()).build(),
                                        Ingredient.builder().name(faker.food().measurement()).build(),
                                        Ingredient.builder().name(faker.food().fruit()).build()
                                )
                        )
                        .build()
        ).toList();
    }

    public List<Beer> createdRandomBeers(){
        List<Beer> beers = IntStream.range(0,50).mapToObj(
                j -> Beer.builder()
                        .name(faker.beer().name())
                        .style(faker.beer().style())
                        .build()
        ).toList();

        return beerDao.saveAll(beers);
    }
}
