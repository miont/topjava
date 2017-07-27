package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("John Cena", "cena@somemail.com", "cena1234",3000, true, Role.ROLE_USER),
            new User("Bob Ross", "bobross@somemail.com", "bobhere",2100, true, Role.ROLE_USER),
            new User("Chuck Norris", "chucknorris@somemail.com", "imtheone",4000, true, Role.ROLE_USER, Role.ROLE_ADMIN)
            );
}
