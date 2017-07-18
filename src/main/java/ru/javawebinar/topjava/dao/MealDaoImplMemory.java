package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealDaoImplMemory implements MealDao {

    private Map<Long,Meal> mealMap;

    public MealDaoImplMemory() {
        mealMap = MealsUtil.getTestMealsMap();
    }

    public List<Meal> getAllMeals() {

        return new ArrayList<>(mealMap.values());
    }

    @Override
    public void addMeal(Meal meal) {

        mealMap.put(meal.getId(), meal);
    }

    @Override
    public Meal getMeal(long id) {

        return mealMap.get(id);
    }

    @Override
    public void updateMeal(Meal meal) {

        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(long id) {

        mealMap.remove(id);
    }

}

