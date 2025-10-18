create database stock_analyzer;
use stock_analyzer;

create table stock(
    ticker varchar(6) primary key not null,
    sector varchar(20) not null,
    price decimal(10,2) not null,
    date date not null
);

create table user(
    UserID int primary key auto_increment,
    Name varchar(20),
    LastName varchar(40),
    Email varchar(40),
    Contact varchar(13)
);

create table address(
    AdressID int primary key auto_increment,
    UserID int,
    Cep int,
    Number int,
    Complement varchar(255)
);

create table contact(
    ContactID int primary key auto_increment,
    UserID int,
    ContactNumber varchar(13)
);

create table email(
    EmailID int primary key auto_increment,
    UserID int,
    Email varchar(255)
);

