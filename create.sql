
-- create the required tables
create table Account
(
  AccountNumber int not null,
  Name varchar(40) not null,
  primary key( AccountNumber )
);

create table Plan
(
  PlanName varchar(30) not null,
  PricePerMonth float not null,
  AllowedDataUsage int not null,
  type varchar(30) not null,
  primary key( PlanName )
);

create table Subscribe
(
  IMEI int not null,
  AccountNumber int not null,
  PlanName varchar(30) not null,
  primary key( IMEI ),
  foreign key( PlanName ) references Plan
);

create table Phone
(
  IMEI int not null,
  MobileNumber varchar(14) not null,
  Manufacturer varchar(30) not null,
  Model varchar(30) not null,
  primary key( IMEI )
);


--create table Owns
--(
-- MasterAccountNumber int not null,
--  DependentAccountNumber int not null,
--  foreign key( MasterAccountNumber ) references Account,
--  foreign key( DependentAccountNumber ) references Account
--);

create table Bill
(
  AccountNumber int not null,
  EndDate Date not null,
  StartDate Date not null,
  DueDate Date not null,
  primary key( AccountNumber, EndDate ),
  foreign key( AccountNumber ) references Account
);

create table Item
(
  AccountNumber int not null, 
  EndDate Date not null,
  ItemNumber int not null,
  Amount float not null,
  primary key( AccountNumber, EndDate, ItemNumber ),
  foreign key( AccountNumber, EndDate ) references Bill
);
