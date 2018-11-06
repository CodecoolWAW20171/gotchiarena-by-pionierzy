DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
  id       SERIAL       NOT NULL PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS pokemon;
CREATE TABLE pokemon (
  id      SERIAL NOT NULL PRIMARY KEY,
  user_id INTEGER,
  name    VARCHAR(255),
  speed   INTEGER,
  defense INTEGER,
  attack  INTEGER
);

ALTER TABLE ONLY pokemon
  DROP CONSTRAINT IF EXISTS fk_account_id;
ALTER TABLE ONLY pokemon
  ADD CONSTRAINT fk_account_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

-- user
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public to "GotchiArena_user";