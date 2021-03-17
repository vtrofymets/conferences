create table IF not exists talk (
    id int auto_increment primary key,
    conference_id int not null,
    title varchar(100) not null,
    description varchar(250) not null,
    speaker varchar(50) not null,
    type varchar(30) not null,
    foreign key (conference_id) references conference(id) on delete CASCADE
);