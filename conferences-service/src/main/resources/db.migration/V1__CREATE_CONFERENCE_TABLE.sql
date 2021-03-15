CREATE TABLE IF NOT EXISTS conference (
    id int primary key,
    conference_name varchar(250) not null unique,
    conference_topic varchar(250) not null,
    conference_date date not null unique,
    participants int not null
);