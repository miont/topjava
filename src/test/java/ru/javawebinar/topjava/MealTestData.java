package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {

    public static final int START_SEQ_MEAL = START_SEQ + 2;
    public static final int USER_BREAKFAST_1_ID = START_SEQ_MEAL;
    public static final int USER_DINNER_1_ID = START_SEQ_MEAL + 1;
    public static final int USER_SUPPER_1_ID = START_SEQ_MEAL + 2;
    public static final int USER_BREAKFAST_2_ID = START_SEQ_MEAL + 3;
    public static final int USER_DINNER_2_ID = START_SEQ_MEAL + 4;
    public static final int USER_SUPPER_2_ID = START_SEQ_MEAL + 5;
    public static final int ADMIN_LUNCH_ID = START_SEQ_MEAL + 6;
    public static final int ADMIN_SUPPER_ID = START_SEQ_MEAL + 7;

    public static final Meal USER_BREAKFAST_1 = new Meal(USER_BREAKFAST_1_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_DINNER_1 = new Meal(USER_DINNER_1_ID, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_SUPPER_1 = new Meal(USER_SUPPER_1_ID, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_BREAKFAST_2 = new Meal(USER_BREAKFAST_2_ID, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal USER_DINNER_2 = new Meal(USER_DINNER_2_ID, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal USER_SUPPER_2 = new Meal(USER_SUPPER_2_ID, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal ADMIN_LUNCH = new Meal(ADMIN_LUNCH_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_SUPPER = new Meal(ADMIN_SUPPER_ID, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>(
            ((expected, actual) -> Objects.equals(expected.toString(), actual.toString()))
    );

}
