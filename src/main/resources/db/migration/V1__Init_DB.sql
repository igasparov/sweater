create sequence messages_seq start with 1 increment by 1;
create sequence users_seq start with 1 increment by 1;

create table messages (
    id long not null,
    filename varchar(255),
    tag varchar(255),
    text varchar(2048) not null,
    user_id bigint, primary key (id)
    );

create table user_role (
    user_id bigint not null,
    roles varchar(255)
    );

create table users (
    id bigint not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null unique,
    primary key (id)
    );

alter table if exists messages
    add constraint message_user_fk
    foreign key (user_id) references users;

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references users;