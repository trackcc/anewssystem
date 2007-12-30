
drop table a_security_dept if exists;

drop table a_security_user if exists;

drop table a_security_role if exists;

drop table a_security_resource if exists;

drop table a_security_menu if exists;

drop table a_security_role_user if exists;

drop table a_security_resource_role if exists;

drop table a_security_menu_role if exists;

create table a_security_dept (
    id bigint generated by default as identity(start with 1, increment by 1),
    parent_id bigint,
    name varchar(50),
    descn varchar(200),
    constraint pk_dept primary key(id),
    constraint fk_dept_parent_id foreign key(parent_id) references a_security_dept(id)
);


create table a_security_user (
    id bigint generated by default as identity(start with 1, increment by 1),
    dept_id bigint,
    username varchar(50),
    password varchar(50),
    status tinyint,
    code varchar(50),
    truename varchar(50),
    sex tinyint,
    birthday datetime,
    tel varchar(50),
    mobile varchar(50),
    email varchar(100),
    duty varchar(50),
    descn varchar(200),
    constraint pk_user primary key(id),
    constraint fk_user_dept_id foreign key(dept_id) references a_security_dept(id)
);


create table a_security_role (
    id bigint generated by default as identity(start with 1, increment by 1),
    name varchar(50),
    descn varchar(200),
    constraint pk_role primary key(id)
);


create table a_security_resource (
    id bigint generated by default as identity(start with 1, increment by 1),
    name varchar(50),
    res_type varchar(50),
    res_string varchar(200),
    descn varchar(200),
    constraint pk_resource primary key(id)
);


create table a_security_menu (
    id bigint generated by default as identity(start with 1, increment by 1),
    parent_id bigint,
    name varchar(50),
    seq int,
    title varchar(50),
    tip varchar(50),
    image varchar(50),
    forward varchar(50),
    target varchar(50),
    descn varchar(200),
    constraint pk_menu primary key(id),
    constraint fk_menu_parent_id foreign key(parent_id) references a_security_menu(id)
);


create table a_security_role_user (
    user_id bigint,
    role_id bigint,
    constraint pk_role_user primary key(user_id,role_id),
    constraint fk_role_user_user_id foreign key(user_id) references a_security_user(id),
    constraint fk_role_user_role_id foreign key(role_id) references a_security_role(id)
);


create table a_security_resource_role (
    role_id bigint,
    resource_id bigint,
    constraint pk_resource_role primary key(role_id,resource_id),
    constraint fk_resource_role_role_id foreign key(role_id) references a_security_role(id),
    constraint fk_resource_role_resource_id foreign key(resource_id) references a_security_resource(id)
);


create table a_security_menu_role (
    role_id bigint,
    menu_id bigint,
    constraint pk_menu_role primary key(role_id,menu_id),
    constraint fk_menu_role_role_id foreign key(role_id) references a_security_role(id),
    constraint fk_menu_role_menu_id foreign key(menu_id) references a_security_menu(id)
);
