package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private UserRepository repository = new InMemoryUserRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");

        if (request.getParameter("id") != null) {
            String idStr = request.getParameter("id");
            int id = !idStr.isEmpty() ? Integer.valueOf(idStr) : 1;
            AuthorizedUser.setId(id);
            response.sendRedirect("/topjava");
        }
        else {
            request.setAttribute("users", repository.getAll());
            request.getRequestDispatcher("/users.jsp").forward(request, response);
        }
    }
}
