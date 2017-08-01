DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  (to_timestamp('30 MAY 2015 10:00', 'DD Mon YYYY HH24:MI'), 'Завтрак', 500, 100000),
  (to_timestamp('30 MAY 2015 13:00', 'DD Mon YYYY HH24:MI'), 'Обед', 1000, 100000),
  (to_timestamp('30 MAY 2015 20:00', 'DD Mon YYYY HH24:MI'), 'Ужин', 500, 100000),
  (to_timestamp('31 MAY 2015 10:00', 'DD Mon YYYY HH24:MI'), 'Завтрак', 1000, 100000),
  (to_timestamp('31 MAY 2015 13:00', 'DD Mon YYYY HH24:MI'), 'Обед', 500, 100000),
  (to_timestamp('31 MAY 2015 20:00', 'DD Mon YYYY HH24:MI'), 'Ужин', 510, 100000),
  (to_timestamp('01 JUN 2015 14:00', 'DD Mon YYYY HH24:MI'), 'Админ ланч', 510, 100001),
  (to_timestamp('01 JUN 2015 21:00', 'DD Mon YYYY HH24:MI'), 'Админ ужин', 1500, 100001)
;
