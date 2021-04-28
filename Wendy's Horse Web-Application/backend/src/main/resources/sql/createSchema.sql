CREATE TABLE IF NOT EXISTS sport
(
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS horse
(
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  desc      VARCHAR(255),
  dob       DATE NOT NULL,
  sex       VARCHAR(6) NOT NULL,
  fav_sport BIGINT REFERENCES sport(id),
  dad_id    BIGINT REFERENCES horse(id),
  mom_id    BIGINT REFERENCES horse(id)
);
