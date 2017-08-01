package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

//    static {
//
//    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(USER_DINNER_1_ID, USER_ID);
        MATCHER.assertEquals(USER_DINNER_1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(USER_DINNER_1_ID, ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(ADMIN_SUPPER_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_LUNCH), service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(USER_BREAKFAST_1_ID, ADMIN_ID);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 14, 0), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_BREAKFAST_1, USER_DINNER_1), meals);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Meal> allMeals = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_SUPPER_2, USER_DINNER_2, USER_BREAKFAST_2, USER_SUPPER_1, USER_DINNER_1, USER_BREAKFAST_1),
                allMeals);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(ADMIN_LUNCH);
        updated.setCalories(777);
        updated.setDescription("Ланч бога");
        service.update(updated, ADMIN_ID);
        MATCHER.assertEquals(updated, service.get(ADMIN_LUNCH_ID, ADMIN_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Полдник", 999);
        Meal created = service.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, ADMIN_SUPPER, ADMIN_LUNCH), service.getAll(ADMIN_ID));
    }

    @Test(expected = DataAccessException.class)
    public void testSaveDuplicateDatetime() throws Exception {
        service.save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч2", 500), ADMIN_ID);
    }

}