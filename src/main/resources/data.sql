INSERT INTO user (id, creation_date, email, enabled, password, username)
 VALUES (1000, '2018-09-01 12:00:00', 'd_freshly_default@gmail.com', true , '$2a$10$ETBZ1t1O.qHYYduX3lz8reiChlZ29ghDOhF7PS/EFHoilTy76s1Ai', 'd_freshly_user');
INSERT INTO user (id, creation_date, email, enabled, password, username)
 VALUES (1001, '2018-09-01 12:00:00', 'd_enabled_default@gmail.com', true , '$2a$10$ETBZ1t1O.qHYYduX3lz8reiChlZ29ghDOhF7PS/EFHoilTy76s1Ai', 'd_enabled_user');

INSERT INTO user_roles(user_id, roles) VALUES (1000, 'user');
INSERT INTO user_roles(user_id, roles) VALUES (1001, 'user');

insert into subject(id, name) values (1000, 'polski');
insert into subject(id, name) values (1001, 'matma');
insert into subject(id, name) values (1002, 'wf');
insert into subject(id, name) values (1003, 'przyroda');
insert into subject(id, name) values (1004, 'religia');
insert into subject(id, name) values (1005, 'utk');
insert into subject(id, name) values (1006, 'pai');
insert into subject(id, name) values (1007, 'twbi');
insert into subject(id, name) values (1008, 'pimlsk');
insert into subject(id, name) values (1009, 'wos');
insert into subject(id, name) values (1010, 'fizyka');
insert into subject(id, name) values (1011, 'eie');
insert into subject(id, name) values (1012, 'biologia');
insert into subject(id, name) values (1013, 'chemia');

insert into task_type(id, name) values (1000, 'test');
insert into task_type(id, name) values (1001, 'homework');
insert into task_type(id, name) values (1002, 'housework');

insert into task(id, date, name, state, type_id, author_id, subject_id) VALUES (1000, '2018-10-01', 'sprawdzian z wesela', 'UNDONE',1000, 1001, 1000);
insert into task(id, date, name, state, type_id, author_id, subject_id) VALUES (1001, '2018-10-01', 'sprawdzian z poteg', 'UNDONE',1000, 1001, 1000);
insert into task(id, date, name, state, type_id, author_id, subject_id) VALUES (1002, '2018-10-02', 'sprawdzian z chlopow', 'UNDONE',1000, 1001, 1000);
insert into task(id, date, name, state, content, type_id, author_id, subject_id) VALUES (1003, '2018-10-02', 'praca domowa z jadra ciemnosci', 'UNDONE','Charakterysytka Kurtza i Marlowa',1001, 1001, 1000);