DROP TABLE Admin CASCADE CONSTRAINTS;
DROP TABLE RegisteredUser CASCADE CONSTRAINTS;
DROP TABLE Host CASCADE CONSTRAINTS;
DROP TABLE Verifies CASCADE CONSTRAINTS;
DROP TABLE ListingPostedIsIn CASCADE CONSTRAINTS;
DROP TABLE Location CASCADE CONSTRAINTS;
DROP TABLE AmenitiesIncluded CASCADE CONSTRAINTS;
DROP TABLE TransactionIdAndEmail CASCADE CONSTRAINTS;
DROP TABLE Transaction CASCADE CONSTRAINTS;
DROP TABLE CreditCardTransaction CASCADE CONSTRAINTS;
DROP TABLE CreditCardInfo CASCADE CONSTRAINTS;
DROP TABLE PayPalTransaction CASCADE CONSTRAINTS;
DROP TABLE MakesReservation CASCADE CONSTRAINTS;
DROP SEQUENCE listing_seq;
DROP SEQUENCE resv_seq;
DROP SEQUENCE trans_seq;
DROP SEQUENCE amenities_seq; 

CREATE TABLE Admin (
	adminId INTEGER, 
	name VARCHAR(40), 
	password VARCHAR (40),
	PRIMARY KEY (adminId));

CREATE TABLE RegisteredUser (
	email VARCHAR (30), 
	name VARCHAR (40), 
	password VARCHAR(40),
	PRIMARY KEY (email));

CREATE TABLE Host (
	governmentId VARCHAR (30), 
	email VARCHAR (30), 
	phoneNumber CHAR(10),
	PRIMARY KEY (governmentId),
	UNIQUE (email),
	FOREIGN KEY (email) REFERENCES RegisteredUser ON DELETE CASCADE);

CREATE TABLE Verifies (
	adminId INTEGER, 
	governmentId VARCHAR(20),
	PRIMARY KEY (adminId, GovernmentId),
	FOREIGN KEY (adminId) REFERENCES Admin ON DELETE CASCADE,
	FOREIGN KEY (governmentId) REFERENCES Host ON DELETE CASCADE);
	
CREATE TABLE Location (
	postalCode CHAR(10), 
	city VARCHAR (30), 
	country VARCHAR(30),
	PRIMARY KEY (postalCode));
	
	
CREATE SEQUENCE listing_seq
  START WITH 0
  INCREMENT BY 1
  MINVALUE 0
  CACHE 1000;

CREATE TABLE ListingPostedIsIn (
	listingId NUMBER, 
	price REAL NOT NULL,
	capacity INTEGER, 
	private CHAR(1),
	rating REAL, 
	governmentId VARCHAR (30) NOT NULL, 
	postalCode CHAR(10) NOT NULL, 
	address VARCHAR(40),
	PRIMARY KEY (listingId),
	FOREIGN KEY(governmentId) REFERENCES Host ON DELETE CASCADE,
	FOREIGN KEY(postalCode) REFERENCES Location,
	CHECK (price > 0));

  
  
CREATE SEQUENCE amenities_seq
  START WITH 0
  INCREMENT BY 1
   MINVALUE 0
  CACHE 1000;

CREATE TABLE AmenitiesIncluded (
	amenitiesId NUMBER,
	listingId NUMBER, 
	tv CHAR(1) DEFAULT 'F' NOT NULL, 
	kitchen CHAR(1) DEFAULT 'F' NOT NULL, 
	internet CHAR(1) DEFAULT 'F' NOT NULL, 
	laundry CHAR(1) DEFAULT 'F' NOT NULL, 
	toiletries CHAR(1) DEFAULT 'F' NOT NULL,
	PRIMARY KEY(listingId),
	FOREIGN KEY(listingId) REFERENCES ListingPostedIsIn ON DELETE CASCADE); 
	
	
CREATE SEQUENCE resv_seq
  START WITH 0
  INCREMENT BY 1
  MINVALUE 0
  CACHE 1000;

CREATE TABLE Transaction(
	transactionId NUMBER, 
	price REAL NOT NULL,
	time DATE, 
	listingId NUMBER NOT NULL,
	PRIMARY KEY (transactionId),
	FOREIGN KEY (listingId) REFERENCES ListingPostedIsIn,
	CHECK (price >= 0.00));

CREATE SEQUENCE trans_seq
  START WITH 0
  INCREMENT BY 1
  MINVALUE 0
  CACHE 1000;

CREATE TABLE MakesReservation (
	reservationId NUMBER, 
	listingId NUMBER NOT NULL, 
	checkindate DATE NOT NULL, 
	checkoutdate DATE NOT NULL,  
	numberOfGuests INTEGER,
	transactionId NUMBER NOT NULL,
    PRIMARY KEY (reservationId),
    FOREIGN KEY (listingId) REFERENCES ListingPostedIsIn ON DELETE CASCADE,
    FOREIGN KEY (transactionId) REFERENCES Transaction ON DELETE CASCADE);


CREATE TABLE TransactionIdAndEmail (transactionId NUMBER, email VARCHAR (30) NOT NULL,
	PRIMARY KEY (transactionId),
	FOREIGN KEY (transactionId) REFERENCES Transaction ON DELETE CASCADE,
	FOREIGN KEY (email) REFERENCES RegisteredUser ON DELETE CASCADE);

CREATE TABLE CreditCardInfo(cardNumber CHAR (15), company CHAR (20), cardHolderName VARCHAR (20), expirationDate VARCHAR(20),             
	PRIMARY KEY (cardNumber));

CREATE TABLE CreditCardTransaction(transactionId NUMBER, cardNumber CHAR(15),
	PRIMARY KEY (transactionId),
	FOREIGN KEY (cardNumber) REFERENCES CreditCardInfo,
	FOREIGN KEY (transactionId) REFERENCES Transaction);

CREATE TABLE PayPalTransaction(email VARCHAR(30), transactionId NUMBER,
	PRIMARY KEY (transactionId),
	FOREIGN KEY (transactionId) REFERENCES Transaction ON DELETE CASCADE,
	FOREIGN KEY (email) REFERENCES RegisteredUser);

INSERT INTO Admin VALUES (001, 'cpsc304', 'cpsc304');
INSERT INTO Admin VALUES (002, 'florie', 'cai');
INSERT INTO Admin VALUES (003, 'felipe', 'osorio');
INSERT INTO Admin VALUES (004, 'gustav', 'staprans');
INSERT INTO Admin VALUES (005, 'Simon', 'Feng');
INSERT INTO Admin VALUES (006, 'Shadow', 'Chui');

INSERT INTO RegisteredUser VALUES ('staprans@gmail.com', 'gustav', 'staprans');
INSERT INTO RegisteredUser VALUES ('floriecai@hotmail.com', 'felipe', 'cai');
INSERT INTO RegisteredUser VALUES ('felipe@gmail.com', 'florie', 'cai');
INSERT INTO RegisteredUser VALUES ('simon@gmail.com', 'simon', 'feng');
insert into RegisteredUser VALUES ('mario@nintendo.com', 'Mario', 'Luigi');
insert into RegisteredUser VALUES ('sonic@sega.com', 'Sonic', 'Tails');
insert into RegisteredUser VALUES ('merpo@merpo.com', 'merpo', 'cai');
INSERT INTO RegisteredUser VALUES ('3beicat@sina.com', 'Dazhao', 'Song');

INSERT INTO Location VALUES ('V6T0B3', 'Vancouver', 'Canada');
INSERT INTO Location VALUES ('V6T2S2', 'Vancouver', 'Canada');
INSERT INTO Location VALUES ('V6T1Z9', 'Richmond', 'Canada');
INSERT INTO Location VALUES ('V6T4Q6', 'Burnaby', 'Canada');
insert into Location values ('94133-415', 'San Francisco', 'USA');
insert into Location values ('T78 S6E', 'Toronto', 'Canada');
insert into Location values ('V4Q T4D', 'Vancouver', 'Canada');
INSERT INTO Location values ('94118-276', 'San Francisco', 'USA');

insert into Host VALUES ('12', 'mario@nintendo.com', '777896548');
insert into Host VALUES ('34', 'sonic@sega.com', '1235678910');
INSERT INTO Host VALUES ('00001', 'staprans@gmail.com', '1111111111');
INSERT INTO Host VALUES ('00002', 'floriecai@hotmail.com', '2222222222');
INSERT INTO Host VALUES ('00003', 'felipe@gmail.com', '3333333333');
INSERT INTO Host VALUES ('00004', 'simon@gmail.com', '4444444444');
INSERT INTO Host VALUES ('00005', 'simonfengqy@126.com', '123467890');

insert into ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values( listing_seq.nextval, 90.0, 3, 'y', 4.5, '12', 'V4Q T4D', '200 Wow street');
insert into ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values( listing_seq.nextval, 50.0, 4, 'y', 3.5, '34', '94133-415', '100 Hey street');
insert into ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values( listing_seq.nextval, 85.0, 1, 'n', 2.5, '34', 'T78 S6E', '300 Lol street');
insert into ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values( listing_seq.nextval, 40.0, 4, 'n', 1.5, '12', '94133-415', '110 Hey street');
INSERT INTO ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values( listing_seq.nextval, 99.99, 4, 'T', 4.0, '00001', 'V6T0B3', '123 Moop Lane');
INSERT INTO ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values( listing_seq.nextval, 199.99, 6, 'F', 5.0, '00002', 'V6T0B3', '321 Mooped Lane');
INSERT INTO ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values( listing_seq.nextval, 299.99, 8, 'T', 4.0, '00003', 'V6T2S2', '789 Shoop Lane');
INSERT INTO ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values( listing_seq.nextval, 399.99, 10, 'F', 4.5, '00004', 'V6T4Q6', '987 Shooped Lane')
;
INSERT INTO ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values (listing_seq.nextval, 166.9, 4, 'y', 3.86, '00028', 'T78 S6E', '527 Hudson Street');

INSERT INTO ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values (listing_seq.nextval, 225.8, 6, 'y', 4.25, '00037', 'V6T4Q6', '480 Simons Lane');
INSERT INTO ListingPostedIsIn (listingId, price, capacity, private, rating, governmentId, postalCode, address) values (listing_seq.nextval, 249.9, 4, 'N', 4.66, '00588', '94118-276', '230 Bobbo Avenue');


INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 1, 'F', 'F', 'F', 'F', 'F');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 2, 'T', 'F', 'T', 'F', 'F');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 3, 'T', 'T', 'F', 'T', 'F');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 4, 'T', 'T', 'F', 'T', 'F');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 5, 'F', 'T', 'F', 'T', 'F');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 6, 'T', 'T', 'F', 'T', 'F');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 7, 'T', 'T', 'T', 'T', 'T');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 8, 'T', 'F', 'F', 'T', 'F');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 9, 'T', 'T', 'T', 'T', 'F');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 10, 'F', 'T', 'T', 'F', 'T');
INSERT INTO AmenitiesIncluded (amenitiesId, listingId, tv, kitchen, internet, laundry, toiletries) values (amenities_seq.nextval, 11, 'F', 'T', 'F', 'F', 'T');

insert into Verifies VALUES (3, '00001');
insert into Verifies VALUES (6, '00004');
INSERT into Verifies VALUES (3, '12');
insert into Verifies VALUES (1, '00002');
insert into Verifies VALUES (5, '34');

insert into Transaction values (trans_seq.nextval, 200.0, TO_DATE('2015-01-01', 'YYYY-MM-DD'), 2);
insert into 

insert into MakesReservation values (resv_seq.nextval, 2, TO_DATE('2015-02-01', 'YYYY-MM-DD'), TO_DATE('2015-02-05', 'YYYY-MM-DD'), 2, 1);

insert into Transaction (transactionId, price, time, listingId) values(trans_seq.nextval, 199.0, TRUNC(SYSDATE), 6);

insert into MakesReservation values (resv_seq.nextval, 6, TO_DATE('2015-03-06', 'YYYY-MM-DD'), TO_DATE('2015-03-07', 'YYYY-MM-DD'), 4, 2);

insert into Transaction (transactionId, price, time, listingId) values(trans_seq.nextval, 199.0, TO_DATE('2015-01-01', 'YYYY-MM-DD'), 6);

insert into MakesReservation values (resv_seq.nextval, 6, TO_DATE('2015-04-06', 'YYYY-MM-DD'), TO_DATE('2015-04-07', 'YYYY-MM-DD'), 4, 2);

insert into TransactionIdAndEmail (transactionId, email) values (1, 'sonic@sega.com');

insert into TransactionIdAndEmail (transactionId, email)  values (2, 'mario@nintendo.com');

insert into TransactionIdAndEmail  (transactionId, email) values (3, 'sonic@sega.com');
