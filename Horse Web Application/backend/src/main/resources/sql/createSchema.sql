

CREATE TYPE IF NOT EXISTS SEXENUM AS ENUM ('male', 'female');


CREATE TABLE IF NOT EXISTS sport
(
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  desc      VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS horse
(
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  desc      VARCHAR(255),
  dob       DATE NOT NULL,
  sex       SEXENUM NOT NULL,
  fav_sport BIGINT REFERENCES sport(id),
  dad_id    BIGINT REFERENCES horse(id),
  mom_id    BIGINT REFERENCES horse(id)
);
