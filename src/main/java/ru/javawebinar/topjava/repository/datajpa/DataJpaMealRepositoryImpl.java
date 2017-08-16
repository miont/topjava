package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    @PersistenceContext
    EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        if(!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(em.getReference(User.class, userId));
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {

        if (get(id, userId) == null) {
            return false;
        }

        return crudRepository.delete(id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {

        Meal meal = crudRepository.findOne(id);
        return (meal != null && meal.getUser().getId() == userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {

        return filterByUserId(crudRepository.findAll(new Sort(Sort.Direction.DESC, "dateTime")),
                userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {

        return filterByUserId(crudRepository.findByDateTimeBetweenOrderByDateTimeDesc(startDate, endDate),
                userId);
    }

    private List<Meal> filterByUserId(List<Meal> meals, int userId) {
        return meals
                .stream()
                .filter(m -> m.getUser().getId() == userId)
                .collect(Collectors.toList());
    }
}
