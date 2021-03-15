CREATE TABLE IF NOT EXISTS talk (
    id int primary key,
    conference_id int not null,
    talk_name varchar(250) not null,
    talk_description varchar(250) not null,
    speaker varchar(50) not null,
    talk_type varchar(30) not null,
    foreign key (conference_id) references conference(id)
);