CREATE TABLE accounts (
    id serial NOT NULL PRIMARY KEY,
    username text,
    password text
);

CREATE TABLE pokemon (
    id serial NOT NULL PRIMARY KEY,
    user_id integer,
    name text,
    speed integer,
    defense integer,
    attack integer


);



ALTER TABLE ONLY pokemon
    ADD CONSTRAINT fk_account_id FOREIGN KEY (user_id) REFERENCES accounts(id) ON DELETE CASCADE;
