CREATE TABLE IF NOT EXISTS payment (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  credit_card_number varchar(20) not null,
  status varchar(30) not null,
  order_id BIGINT not null,
  payment_date timestamp,
  constraint payment_id foreign key(order_id) references orders(id)
) DEFAULT CHARSET=UTF8;