create table IF not exists conference (
    id int auto_increment primary key,
    conference_name varchar(250) not null unique,
    conference_topic varchar(250) not null,
    conference_date date not null unique,
    participants int not null
);