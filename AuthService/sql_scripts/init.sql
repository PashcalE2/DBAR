create table client (
                        id serial primary key,
                        phone_number varchar not null,
                        email varchar not null,
                        login varchar unique not null,
                        password varchar not null
);

create table role (
                      id serial primary key,
                      name varchar not null
);

insert into role (name) values ('ROLE_CLIENT');
insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_FACTORY');
insert into role (name) values ('ROLE_SUPERVISOR');


create table permission (
                            client integer references client on delete cascade not null,
                            role integer references role,
                            primary key(client, role)
);