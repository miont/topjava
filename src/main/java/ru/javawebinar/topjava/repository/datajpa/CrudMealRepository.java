package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Override
    List<Meal> findAll(Sort sort);

    @Override
    @Transactional
    Meal save(Meal meal);

    @Override
    Meal findOne(Integer id);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.id=:id")
    Meal findOneWithUser(@Param("id") Integer id);

    List<Meal> findByDateTimeBetweenOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate);
}
