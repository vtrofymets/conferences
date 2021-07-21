create table talk (
    id identity primary key,
    conference_id bigint not null,
    title varchar(100) not null,
    description varchar(250) not null,
    speaker varchar(50) not null,
    type varchar(30) not null,
    foreign key (conference_id) references conference(id) on delete CASCADE
);