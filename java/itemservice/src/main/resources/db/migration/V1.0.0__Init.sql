CREATE TABLE ITEMS (
  id            BIGINT NOT NULL PRIMARY KEY,
  name          VARCHAR(50) NOT NULL,
  category      VARCHAR(50) NOT NULL,
  description   VARCHAR(255),
  rating        DOUBLE PRECISION,
  price         NUMERIC
);
