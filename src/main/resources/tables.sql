/*В папці WEB-INF, в файлі jdbc.properties простав свої jdbc.username і jdbc.password*/

CREATE DATABASE tasty_lunch DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

USE tasty_lunch;

CREATE TABLE users (
username VARCHAR(50) NOT NULL PRIMARY KEY,
password VARCHAR(50) NOT NULL,
enabled  BOOLEAN     NOT NULL
);
CREATE TABLE authorities (
username  VARCHAR(50) NOT NULL,
authority VARCHAR(50) NOT NULL,
CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

/*1 - Якщо хочеш проходити процедуру реєстрації, отримувати пароль на e-mail, то вибери цей варіант і впиши свій e-mail*/
INSERT INTO users VALUES ('YOUR_EMAIL', 1234, 0);
INSERT INTO authorities VALUES ('YOUR_EMAIL', 'ROLE_ADMIN');
/*1 - Кінець*/

/*2 - Якщо НЕ хочеш проходити процедуру реєстрації, то вибери цей варіант (пароль діставай з таблиці)*/
INSERT INTO users VALUES ('admin@admin', 1234, 1);
INSERT INTO authorities VALUES ('admin@admin', 'ROLE_ADMIN');
/*дальше запускаєш прогу, щоб Хібер створив таблиці, а потім ще ось цю штуку*/
INSERT INTO tb_user (loginEmail) VALUES ('admin@admin');
/*2 - Кінець*/

/*--------------------------------------------------------------------------------------------*/
/*----------------------------------- ПРОБИ ------------------------------------*/
/*--------------------------------------------------------------------------------------------*/


CREATE TABLE IF NOT EXISTS TB_USER (
  id         INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  loginEmail VARCHAR(30),
  password   VARCHAR(20),
  name       VARCHAR(30) NOT NULL,
  lastName   VARCHAR(30) NOT NULL,
  groupId    VARCHAR(50),
  reg_date   TIMESTAMP
)
  CHARACTER SET utf8;

SELECT
  dish.id,
  dish.name
FROM dish, order_dish, ordersofusers, user
WHERE user.id = user_id AND dish.id = dish_id AND ordersofusers.id = order_id AND user.loginEmail = 'admin@admin';


/*Проби*/
SELECT tb_dish.name
FROM TB_DISH, tb_order_dish, tb_order
WHERE tb_order_dish.order_id = tb_order.id AND tb_order_dish.dish_id = TB_DISH.id AND
      TB_DISH.dateStartOfNextWeek > CURDATE();
/*З вибором дня*/
SELECT
  tb_dish.name,
  COUNT(*) AS total
FROM TB_DISH, tb_order_dish, tb_order
WHERE tb_order_dish.order_id = tb_order.id AND tb_order_dish.dish_id = TB_DISH.id AND
      TB_DISH.dateStartOfNextWeek > CURDATE() AND TB_DISH.dayOfWeek = 'friday'
GROUP BY tb_dish.name;
/*Без вибору дня*/
SELECT
  tb_dish.name,
  COUNT(*) AS total
FROM TB_DISH, tb_order_dish, tb_order
WHERE tb_order_dish.order_id = tb_order.id AND tb_order_dish.dish_id = TB_DISH.id AND
      TB_DISH.dateStartOfNextWeek > CURDATE()
GROUP BY tb_dish.name
ORDER BY total DESC;
/*Поки що найкраще*/
SELECT tb_order.*
FROM tb_order
WHERE dateStartOfWeek > CURDATE()
ORDER BY tb_order.id;



