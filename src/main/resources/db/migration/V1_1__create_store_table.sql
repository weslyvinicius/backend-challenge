CREATE TABLE IF NOT EXISTS store (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name varchar(100) not null,
  address varchar(100) not null
) DEFAULT CHARSET=UTF8;