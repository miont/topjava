package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles("datajpa")
public class UserServiceDataJpaTest extends UserServiceTest {

    @Test
    public void testGetWithMeal() throws Exception {
        User user = service.get(USER_ID);
        UserTestData.MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MEALS, user.getMeals());
    }
}


