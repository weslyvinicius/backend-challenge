CREATE TABLE IF NOT EXISTS orders (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  address varchar(100) not null,
  confirmation_date timestamp not null,
  status varchar(80) not null
) DEFAULT CHARSET=UTF8;