package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final DataSource dataSource;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.dataSource = dataSource;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        if(users.size() > 0){
            users.get(0).setRoles(getUserRoles(id));
        }
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        if(users.size() > 0){
            users.get(0).setRoles(getUserRoles(users.get(0).getId()));
        }
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {

        Map<Integer,Set<Role>> userRoleMap = getAllRoles();

        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);

        for(User u : users) {
            u.setRoles(userRoleMap.get(u.getId()));
        }

        return users;
    }

    private Map<Integer,Set<Role>> getAllRoles() {
        Map<Integer,Set<Role>> userRoleMap = new HashMap<>();
        try(Connection conn = dataSource.getConnection()){
            Statement st = conn.createStatement();
            String query = "SELECT * FROM user_roles";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                int userId = rs.getInt("user_id");
                String role = rs.getString("role");
                userRoleMap.putIfAbsent(userId, new HashSet<>());
                userRoleMap.get(userId).add(Role.valueOf(role));
            }
        }
        catch (SQLException e){
        }

        return userRoleMap;
    }

    private Set<Role> getUserRoles(int userId) {
        Set<Role> roles = new HashSet<>();
        try(Connection conn = dataSource.getConnection()){
            Statement st = conn.createStatement();
            String query = String.format("SELECT role FROM user_roles WHERE user_id=%d", userId);
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                String role = rs.getString("role");
                roles.add(Role.valueOf(role));
            }
        }
        catch (SQLException e){
        }
        return roles;
    }

}
