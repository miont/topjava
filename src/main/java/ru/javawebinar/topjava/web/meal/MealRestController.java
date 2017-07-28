package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public void setService(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(AuthorizedUser.id());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        checkIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAllWithExceeded() {
        return MealsUtil.getWithExceeded((Collection<Meal>) getAll(), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFilteredWithExceeded(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getFilteredWithExceeded(getAll(), startDate, startTime, endDate, endTime, AuthorizedUser.getCaloriesPerDay());
    }

}