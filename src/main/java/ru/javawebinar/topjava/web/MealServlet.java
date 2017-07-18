package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImplMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private MealDao dao = new MealDaoImplMemory();

    private static int CALORIES_DAY_LIMIT = 2000;
    private static String ADD_OR_EDIT = "/mealForm.jsp";
    private static String LIST = "/meals.jsp";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet");

        String action = request.getParameter("action");
        if(action == null) {
            action = "";
        }
        String forwardPath = "";
        log.debug(action);

        if(action.equalsIgnoreCase("add")) {
            forwardPath = ADD_OR_EDIT;
        }
        else if(action.equalsIgnoreCase("edit")) {
            Long id = Long.parseLong(request.getParameter("id"));
            request.setAttribute("meal", dao.getMeal(id));
            forwardPath = ADD_OR_EDIT;
        }

        else { // Delete or list (default)

            if(action.equalsIgnoreCase("delete")) {
                Long id = Long.parseLong(request.getParameter("id"));
                dao.deleteMeal(id);
            }

            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_DAY_LIMIT));
            request.setAttribute("formatter", FORMATTER);
            forwardPath = LIST;
        }

        request.getRequestDispatcher(forwardPath).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost");
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String dateTime = request.getParameter("dateTime");
        String idString = request.getParameter("id");

        log.debug(dateTime);
        log.debug(LocalDateTime.parse(dateTime).toString());

        if(idString == null || idString.isEmpty()) {
            dao.addMeal(new Meal(LocalDateTime.parse(dateTime), description, calories));
        }
        else {
            dao.updateMeal(
                    new Meal(Long.parseLong(idString), LocalDateTime.parse(dateTime), description, calories)
            );
        }

        log.debug(new Meal(LocalDateTime.parse(dateTime), description, calories).toString());

        response.sendRedirect("meals");
    }
}
