CREATE TABLE ORDERS (
  id                BIGINT NOT NULL PRIMARY KEY,
  user_id           BIGINT NOT NULL,
  address           VARCHAR(255),
  order_date        TIMESTAMP
);

CREATE TABLE ORDER_LINES (
  id                BIGINT NOT NULL PRIMARY KEY,
  order_id          BIGINT REFERENCES ORDERS(id),
  item_id           BIGINT NOT NULL,
  quantity          NUMERIC,
  unit_price        NUMERIC
);
