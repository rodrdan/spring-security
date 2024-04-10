DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
username VARCHAR(50) NOT NULL PRIMARY KEY,
password CHAR(68) NOT NULL,
enabled SMALLINT NOT NULL
);

INSERT INTO users VALUES
('john', '{bcrypt}$2a$10$x/LNJ0.l.GMX5LqHsInKTOkQZTT/tTv9LEFPfA6L5Tw0VRsSI3UVW', 1),
('mary', '{bcrypt}$2a$10$rNarTNmhn7I3P9W.P.OZwefPqQigXRsqpiUR1nAoCO0xaCKTAwtW.', 1),
('susan', '{bcrypt}$2a$10$uaeNhhQs2MPF/VFI8T1Fuub1uM3HMHdbd83iUMIENW3s.3fTn92vi', 1)
;

CREATE TABLE authorities (
username VARCHAR(50) NOT NULL,
authority VARCHAR(50) NOT NULL,
UNIQUE (username, authority),
CONSTRAINT authorities_ibfk_1 FOREIGN KEY (username) REFERENCES users (username)
);

INSERT INTO authorities
VALUES
('john','ROLE_EMPLOYEE'),
('mary','ROLE_EMPLOYEE'),
('mary','ROLE_MANAGER'),
('susan','ROLE_EMPLOYEE'),
('susan','ROLE_MANAGER'),
('susan','ROLE_ADMIN');