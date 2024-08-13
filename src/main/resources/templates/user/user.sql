create table user (
    idx int not null auto_increment primary key,
    mid varchar(20) not null,
    name varchar(20) not null,
    age int default 20,
    address varchar(50)
);