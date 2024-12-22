create sequence conf_seq start 1 increment 1;

create table conference
(
    id           bigint primary key,
    name         varchar(250) not null unique,
    topic        varchar(250) not null,
    date_start   date         not null unique,
    date_end     date         not null unique,
    participants int          not null
);