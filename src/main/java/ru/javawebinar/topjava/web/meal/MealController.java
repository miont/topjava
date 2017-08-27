package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
@RequestMapping("/meals")
public class MealController{

    private final MealService service;

    @Autowired
    public MealController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        int userId = AuthorizedUser.id();
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(Model model, HttpServletRequest request) {
        Meal meal = get(getId(request));
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        int userId = AuthorizedUser.id();
        service.delete(getId(request), userId);
        return "redirect:/meals";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) throws IOException{
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            checkNew(meal);
            service.create(meal, AuthorizedUser.id());
        } else {
            assureIdConsistent(meal, getId(request));
            service.update(meal, AuthorizedUser.id());
        }
        return String.format("redirect:/meals");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String getBetween(Model model, HttpServletRequest request) {
        int userId = AuthorizedUser.id();
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        model.addAttribute("meals",
            MealsUtil.getFilteredWithExceeded(service.getBetweenDates(
                    startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                    endDate != null ? endDate : DateTimeUtil.MAX_DATE,
                    userId),
                    startTime != null ? startTime : LocalTime.MIN,
                    endTime != null ? endTime : LocalTime.MAX,
                    AuthorizedUser.getCaloriesPerDay())
        );
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
