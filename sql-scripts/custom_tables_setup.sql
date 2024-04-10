DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS members;

--
-- Custom table 'members' instead of 'users'
--

CREATE TABLE members (
  user_id VARCHAR(50) NOT NULL,
  pw CHAR(68) NOT NULL,
  active SMALLINT NOT NULL,
  PRIMARY KEY (user_id)
);

INSERT INTO members VALUES
('john', '{bcrypt}$2a$10$x/LNJ0.l.GMX5LqHsInKTOkQZTT/tTv9LEFPfA6L5Tw0VRsSI3UVW', 1),
('mary', '{bcrypt}$2a$10$rNarTNmhn7I3P9W.P.OZwefPqQigXRsqpiUR1nAoCO0xaCKTAwtW.', 1),
('susan', '{bcrypt}$2a$10$uaeNhhQs2MPF/VFI8T1Fuub1uM3HMHdbd83iUMIENW3s.3fTn92vi', 1)
;

--
-- Custom table 'roles' instead of 'authorities'
--

CREATE TABLE roles (
user_id VARCHAR(50) NOT NULL,
role VARCHAR(50) NOT NULL,
UNIQUE (user_id, role),
CONSTRAINT authorities_ibfk_1 FOREIGN KEY (user_id) REFERENCES members (user_id)
);

INSERT INTO roles
VALUES
('john','ROLE_EMPLOYEE'),
('mary','ROLE_EMPLOYEE'),
('mary','ROLE_MANAGER'),
('susan','ROLE_EMPLOYEE'),
('susan','ROLE_MANAGER'),
('susan','ROLE_ADMIN');


