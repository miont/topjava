package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER;

@ActiveProfiles("datajpa")
public class MealServiceDataJpaTest extends MealServiceTest {

    @Test
    public void testGetWithUser()throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MealTestData.MATCHER.assertEquals(ADMIN_MEAL1, actual);
        UserTestData.MATCHER.assertEquals(ADMIN, actual.getUser());
    }
}
