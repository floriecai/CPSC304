
drop table ListingPostedIsIn cascade constraints;
drop table Location cascade constraints;
drop table Admin cascade constraints;
drop table AmenitiesIncluded cascade constraints;
drop table RegisteredUser cascade constraints;
drop table Host cascade constraints;
drop table MakesReservation cascade constraints;
drop table Transaction cascade constraints;

create table Admin (admin integer, name CHAR(10));

create table Location (postalCode CHAR(10), city CHAR (20), country CHAR(20), primary key (postalCode));

create table RegisteredUser (email CHAR(30), userName CHAR(30), password CHAR(20), primary key (email));

create table Host (governmentId char(30), email char(30) unique, phoneNumber integer, primary key (governmentId),  
foreign key (email) references RegisteredUser);

create table ListingPostedIsIn (listingId CHAR(10), price REAL, capacity INTEGER, private CHAR(1), rating REAL, governmentId CHAR (30) NOT NULL, postalCode CHAR(10) NOT NULL, address CHAR(40), primary key (listingId), FOREIGN KEY (governmentId) REFERENCES Host, FOREIGN KEY(postalCode) REFERENCES Location);

create table AmenitiesIncluded (listingId CHAR(10), amenityId CHAR(5), tv CHAR(1), kitchen CHAR(1), internet CHAR(10), laundry CHAR(1), toiletries CHAR(1), primary key(listingId,amenityId), foreign key(listingId) references ListingPostedIsIn);

create table MakesReservation (reservationId CHAR(10), email CHAR(30) NOT NULL, listingId CHAR(10) NOT NULL, status CHAR(10), 
  checkindate DATE NOT NULL, checkoutdate DATE NOT NULL,  numberOfGuests INTEGER, PRIMARY KEY (reservationId), FOREIGN KEY (listingId) REFERENCES ListingPostedIsIn, 
  FOREIGN KEY (email) REFERENCES RegisteredUser);

CREATE TABLE Transaction(transactionId INTEGER, price REAL, time DATE, reservationId CHAR(10) NOT NULL, PRIMARY KEY (transactionId),  UNIQUE (reservationId), FOREIGN KEY (reservationId) REFERENCES MakesReservation);

insert into RegisteredUser values ('mario@nintendo.com', 'Mario', '12');
insert into RegisteredUser values ('sonic@sega.com', 'Sonic', '12');

insert into Host values('12', 'mario@nintendo.com', 1234);
insert into Host values('34', 'sonic@sega.com', 5678);

insert into Admin values(1, 'Lucian');
insert into Admin values(2, 'Brand');
insert into Admin values(3, 'Annie');

insert into Location values ('94133-415', 'San Francisco', 'USA');
insert into Location values ('T78 S6E', 'Toronto', 'Canada');
insert into Location values ('V4Q T4D', 'Vancouver', 'Canada');

insert into ListingPostedIsIn values('1', 90.0, 3, 'y', 4.5, '12', 'V4Q T4D', '200 Wow street');
insert into ListingPostedIsIn values('2', 50.0, 4, 'y', 3.5, '34', '94133-415', '100 Hey street');
insert into ListingPostedIsIn values('3', 85.0, 1, 'n', 2.5, '34', 'T78 S6E', '300 Lol street');
insert into ListingPostedIsIn values('4', 40.0, 4, 'n', 1.5, '12', '94133-415', '110 Hey street');

insert into AmenitiesIncluded values ('1', 'y', 'n', 'Wireless', 'n', 'y');
insert into AmenitiesIncluded values ('2', 'n', 'n', 'Cable', 'y', 'n');
insert into AmenitiesIncluded values ('3', 'y', 'n', 'Not', 'y', 'y');
insert into AmenitiesIncluded values ('4', 'n', 'n', 'Cable', 'y', 'n');

insert into MakesReservation values ('1', '2', 'OK', TO_DATE('2010/01/01', 'yyyy/mm/dd'), TO_DATE('2010/03/03', 'yyyy/mm/dd'), 2);

insert into Transaction values (1, 180.0, CURRENT_DATE, '1');
