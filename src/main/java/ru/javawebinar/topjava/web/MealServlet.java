package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImplList;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private MealDao dao = new MealDaoImplList();

    private static int CALORIES_DAY_LIMIT = 2000;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("list meals");
        log.debug(FORMATTER.toString());

        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_DAY_LIMIT));
        request.setAttribute("formatter", FORMATTER);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}
