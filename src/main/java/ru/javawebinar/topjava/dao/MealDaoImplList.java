package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealDaoImplList implements MealDao {

    private List<Meal> mealList;

    public MealDaoImplList() {
        mealList = MealsUtil.getTestMeals();
    }

    public List<Meal> getAllMeals() {
        return mealList;
    }

}

