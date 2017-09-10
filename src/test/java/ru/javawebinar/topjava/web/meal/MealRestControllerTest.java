package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertListEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(WITH_EXCEED_MATCHER.contentListMatcher(MealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), USER.getCaloriesPerDay())));
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = getCreated();
        ResultActions action =
            mockMvc.perform(post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(expected)))
                    .andDo(print())
                    .andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        MATCHER.assertEquals(expected, returned);
        List<Meal> expectedMeals = new ArrayList<>(MEALS);
        expectedMeals.add(0, expected);
        WITH_EXCEED_MATCHER.assertListEquals(MealsUtil.getWithExceeded(expectedMeals, AuthorizedUser.getCaloriesPerDay()), MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
        List<Meal> meals = Arrays.asList(MEALS.toArray(new Meal[MEALS.size()]));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, USER_ID));
    }

//    @Test
//    public void testGetBetween() throws Exception {
//        mockMvc.perform(get(REST_URL + "filter")
//                .param("startDateTime", "2015-05-30T00:00:00")
//                .param("endDateTime", "2015-05-30T23:59:00")
//        )
//        .andDo(print())
//        .andExpect(status().isOk());
//    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDate", LocalDate.of(2015, Month.MAY, 30).toString())
                .param("startTime", LocalTime.of(0,0,0).toString())
                .param("endDate", LocalDate.of(2015, Month.MAY, 30).toString())
                .param("endTime", LocalTime.of(23,59,0).toString())
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
