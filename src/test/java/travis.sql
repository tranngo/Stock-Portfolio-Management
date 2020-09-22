USE stocks;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id int NOT NULL AUTO_INCREMENT,
    username varchar(255),
    password varchar(255),
    PRIMARY KEY(id)
);

INSERT INTO users (username, password) VALUES("serena", "jtdj34jd3unr3cncr");
INSERT INTO users (username, password) VALUES("federer", "jdfoejw438fjhdffef");
INSERT INTO users (username, password) VALUES("djokovicGotKickedOut34", "t8r3jcrejcynho39j3");
