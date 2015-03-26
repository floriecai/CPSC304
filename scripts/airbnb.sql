DROP TABLE Admin CASCADE CONSTRAINTS;
DROP TABLE RegisteredUser CASCADE CONSTRAINTS;
DROP TABLE Host CASCADE CONSTRAINTS;
DROP TABLE Verifies CASCADE CONSTRAINTS;
DROP TABLE ListingPostedIsIn CASCADE CONSTRAINTS;
DROP TABLE Location CASCADE CONSTRAINTS;
DROP TABLE AmenitiesIncluded CASCADE CONSTRAINTS;
DROP TABLE ListingIdAndEmail CASCADE CONSTRAINTS;
DROP TABLE FulfillsReservation CASCADE CONSTRAINTS;
DROP TABLE WrittenReview CASCADE CONSTRAINTS;
DROP TABLE Transaction CASCADE CONSTRAINTS;
DROP TABLE CreditCardTransaction CASCADE CONSTRAINTS;
DROP TABLE CreditCardInfo CASCADE CONSTRAINTS;
DROP TABLE PayPalTransaction CASCADE CONSTRAINTS;
DROP TABLE Refund CASCADE CONSTRAINTS;
DROP TABLE MakesReservation CASCADE CONSTRAINTS;

CREATE TABLE Admin (adminId INTEGER, name VARCHAR(40), password VARCHAR (40),
	PRIMARY KEY (adminId));

CREATE TABLE RegisteredUser (email VARCHAR (40), name VARCHAR (40), password VARCHAR(40),
	PRIMARY KEY (email));

CREATE TABLE Host (governmentId VARCHAR (30), email VARCHAR (40), phoneNumber CHAR(10), name VARCHAR(40),
	PRIMARY KEY (governmentId), 
	UNIQUE (email),
	FOREIGN KEY (email) REFERENCES RegisteredUser ON DELETE CASCADE ON UPDATE CASCADE);


CREATE TABLE Verifies (adminId INTEGER, governmentId VARCHAR(30),
	PRIMARY KEY (governmentId),
	FOREIGN KEY (adminId) REFERENCES Admin ON DELETE SET DEFAULT ON UPDATE CASCADE,
	FOREIGN KEY (governmentId) REFERENCES Host ON DELETE CASCADE ON UPDATE CASCADE);
	
CREATE TABLE Location (postalCode CHAR(6), city VARCHAR (30), country VARCHAR(30),
	PRIMARY KEY (postalCode)); 

CREATE TABLE ListingPostedIsIn (
	listingId CHAR (10), 
	price REAL, capacity INTEGER, 
	private CHAR(1),
	rating REAL, 
	governmentId VARCHAR (30) NOT NULL, 
	postalCode CHAR(6) NOT NULL, 
	address VARCHAR(40),
	PRIMARY KEY (listingId),
	FOREIGN KEY(governmentId) REFERENCES Host ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(postalCode) REFERENCES Location ON UPDATE CASCADE,
	CHECK (price > 0));    

CREATE TABLE AmenitiesIncluded (listingId CHAR(10),
	amenityId CHAR(5),
	tv CHAR(1), 
	kitchen CHAR(1), 
	internet CHAR(1), 
	laundry CHAR(1), 
	toiletries CHAR(1),
	PRIMARY KEY(listingId, amenityId),
	FOREIGN KEY(listingId) REFERENCES ListingPostedIsIn ON DELETE CASCADE ON UPDATE CASCADE); 


CREATE TABLE Transaction(transactionId CHAR(10), 
	price REAL, 
	day DATE, 
	listingId CHAR(10) NOT NULL,
	reservationId CHAR(10) NOT NULL,
	PRIMARY KEY (transactionId),
	FOREIGN KEY (listingId) REFERENCES ListingPostedIsIn ON DELETE SET DEFAULT ON UPDATE CASCADE,
	FOREIGN KEY (reservationId) REFERENCES MakesReservation ON DELETE SET DEFAULT ON UPDATE CASCADE,
	CHECK (price >= 0.00)); 

CREATE TABLE MakesReservation (reservationId CHAR(10), 
	listingId CHAR(10) NOT NULL, 
	status CHAR(10), 
	checkindate DATE NOT NULL,
	checkoutdate DATE NOT NULL, 
	numberOfGuests INTEGER, 
	transactionId CHAR(10) NOT NULL,
	PRIMARY KEY (reservationId),
    	FOREIGN KEY (listingId) REFERENCES ListingPostedIsIn ON DELETE CASCADE ON UPDATE CASCADE,
    	FOREIGN KEY (transactionId) REFERENCES Transaction ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE ListingIdAndEmail (listingId CHAR(10), email VARCHAR (40) NOT NULL,
	PRIMARY KEY (listingId),
	FOREIGN KEY (listingId) REFERENCES ListingPostedIsIn ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (email) REFERENCES RegisteredUser ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE FulfillsReservation(reservationId CHAR (10), 
	email VARCHAR (40) NOT NULL, 
	reviewId CHAR(10),
	PRIMARY KEY (reservationId),
	FOREIGN KEY (reservationId) REFERENCES MakesReservation ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (email) REFERENCES RegisteredUser ON DELETE SET DEFAULT ON UPDATE CASCADE,
	FOREIGN KEY (reviewId) REFERENCES WrittenReview ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE WrittenReview(reviewId CHAR(10), 
	ratingStars INTEGER, 
	description VARCHAR (4000), 
	day DATE, 
	reservationId CHAR(10) NOT NULL,
	PRIMARY KEY (reviewId),
	UNIQUE (reservationId),
	FOREIGN KEY (reservationId) REFERENCES FulfillsReservation ON DELETE CASCADE ON UPDATE CASCADE, 
	CHECK (ratingStars >= 1 AND ratingStars <=5));

CREATE TABLE CreditCardInfo(cardNumber CHAR (15), 
	company CHAR (20), 
	cardHolderName CHAR (20), 
	expirationDate DATE,             
	PRIMARY KEY (cardNumber));

CREATE TABLE CreditCardTransaction(transactionId CHAR(10), cardNumber CHAR(15),
	PRIMARY KEY (transactionId),
	FOREIGN KEY (cardNumber) REFERENCES CreditCardInfo ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (transactionId) REFERENCES Transaction ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE PayPalTransaction(email VARCHAR(40), transactionId CHAR(10),
	PRIMARY KEY (transactionId),
	FOREIGN KEY (transactionId) REFERENCES Transaction ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (email) REFERENCES RegisteredUser ON DELETE SET DEFAULT ON UPDATE CASCADE);

CREATE TABLE Refund (transactionId CHAR(10), cancellationfee REAL, time DATE,
	PRIMARY KEY(transactionId),
	FOREIGN KEY (transactionId) REFERENCES Transaction ON DELETE CASCADE ON UPDATE CASCADE,
	CHECK (cancellationfee >= 0)); 
