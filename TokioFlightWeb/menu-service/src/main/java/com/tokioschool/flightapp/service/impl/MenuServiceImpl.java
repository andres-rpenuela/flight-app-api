package com.tokioschool.flightapp.service.impl;

import com.github.javafaker.Faker;
import com.tokioschool.flightapp.domain.Ingredient;
import com.tokioschool.flightapp.domain.Main;
import com.tokioschool.flightapp.domain.Menu;
import com.tokioschool.flightapp.repository.MenuDao;
import com.tokioschool.flightapp.service.MenuService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuDao menuDao;
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

        List<Menu> menusToCreate = IntStream.range(0,100)
                .mapToObj(i->
                    Menu.builder()
                            .title("menu-%02d".formatted(i))
                            .created(Instant.now())
                            .mains(createRandomMains(i))
                            .calories(BigDecimal.valueOf(secureRandom.nextDouble(300,1000)))
                            .vegetarian(secureRandom.nextBoolean())
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
}
