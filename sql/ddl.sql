drop table if exists member CASCADE;
create table member
(
    id bigint not null,
    email varchar(64) not null,
    first_name varchar(10) not null,
    last_name varchar(10) not null,
    pass_word varchar(100) not null,
    pass_word_confirm varchar(100) not null,
    primary key (id)) engine=InnoDB
);