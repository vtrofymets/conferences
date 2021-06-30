create table IF not exists conference (
    id identity primary key,
    name varchar(250) not null unique,
    topic varchar(250) not null,
    date_start date not null unique,
    date_end date not null unique,
    participants int not null
);