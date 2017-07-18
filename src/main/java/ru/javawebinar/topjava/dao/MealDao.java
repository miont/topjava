package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    List<Meal> getAllMeals();

    void addMeal(Meal meal);

    Meal getMeal(long id);

    void updateMeal(Meal meal);

    void deleteMeal(long id);

}
