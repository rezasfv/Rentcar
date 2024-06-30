-- Create database and schema
DROP DATABASE IF EXISTS rentcar;
CREATE DATABASE rentcar ENCODING = 'UTF8';
\c rentcar
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- Create domains
CREATE DOMAIN public.email AS VARCHAR(254)
  CONSTRAINT validEmail CHECK (VALUE ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$');

CREATE DOMAIN public.passwd AS VARCHAR(254)
  CONSTRAINT properPassword CHECK (VALUE ~* '[A-Za-z0-9._%!]{8,}');

CREATE DOMAIN public.phone_number AS VARCHAR(20)
  CONSTRAINT validPhone CHECK (VALUE ~* '^[0-9]+$');

CREATE DOMAIN public.address AS VARCHAR(255);

CREATE DOMAIN public.nationality AS VARCHAR(50);

CREATE DOMAIN public.license_plate AS VARCHAR(20)
  CONSTRAINT validLicensePlate CHECK (VALUE ~* '^[A-Z0-9]+$');

CREATE DOMAIN public.amount AS DECIMAL(10, 2)
  CONSTRAINT nonNegativeAmount CHECK (VALUE >= 0);

CREATE DOMAIN public.name_ AS VARCHAR(100)
  CONSTRAINT validName CHECK (VALUE <> '');

CREATE DOMAIN public.car_capacity AS INT
  CONSTRAINT validCarCapacity CHECK (VALUE > 0 AND VALUE <= 10);

-- Enum definitions
CREATE TYPE license_type AS ENUM (
    'B',
    'B1'
);

CREATE TYPE car_class AS ENUM (
    'small',
    'medium',
    'large',
    'SUV'
);

CREATE TYPE car_status AS ENUM (
    'available',
    'rented',
    'maintenance'
);

CREATE TYPE pay_status AS ENUM (
    'pending',
    'confirmed',
    'cancelled'
);

-- Create tables
CREATE TABLE public.DriversLicense (
    LicenseNumber VARCHAR(20) PRIMARY KEY,
    ExpirationDate DATE NOT NULL,
    TypeAccept license_type NOT NULL,
    IssuingDate DATE NOT NULL
);   

CREATE TABLE public.Customer (
    Costumer_ID SERIAL PRIMARY KEY,
    Email public.email NOT NULL,
    FirstName public.name_ NOT NULL,
    LastName public.name_ NOT NULL,
    Phone public.phone_number,
    DataBirth DATE NOT NULL,
    Address_Costumer public.address,
    Nationality public.nationality,
    Password_Costumer public.passwd NOT NULL,
    LicenseNumber VARCHAR(20) NOT NULL,
    FOREIGN KEY (LicenseNumber) REFERENCES public.DriversLicense(LicenseNumber)
);

CREATE TABLE public.Car (
    LicensePlate public.license_plate PRIMARY KEY,
    RentalRate public.amount NOT NULL,
    CurrentStatus car_status NOT NULL,
    ModelName VARCHAR(50) NOT NULL,
    BrandName VARCHAR(50) NOT NULL,
    Category car_class NOT NULL,
    Capacity public.car_capacity NOT NULL
);

CREATE TABLE public.Admin (
    Email public.email PRIMARY KEY,
    Password_admin public.passwd NOT NULL
);

CREATE TABLE public.RentalTransaction (
    Payment_ID SERIAL PRIMARY KEY,
    Amount public.amount NOT NULL,
    PaymentStatus pay_status NOT NULL,
    IssueDate TIMESTAMP NOT NULL,
    FromDate TIMESTAMP NOT NULL,
    ToDate TIMESTAMP NOT NULL,
    Costumer_ID SERIAL NOT NULL,
    LicensePlate VARCHAR(20) NOT NULL,
    FOREIGN KEY (Costumer_ID) REFERENCES public.Customer(Costumer_ID),
    FOREIGN KEY (LicensePlate) REFERENCES public.Car(LicensePlate)
);

-- Relationship tables
CREATE TABLE public.HoldsLicense (
    Costumer_ID SERIAL,
    LicenseNumber VARCHAR(20),
    PRIMARY KEY (Costumer_ID, LicenseNumber),
    FOREIGN KEY (Costumer_ID) REFERENCES public.Customer(Costumer_ID),
    FOREIGN KEY (LicenseNumber) REFERENCES public.DriversLicense(LicenseNumber)
);

CREATE TABLE public.Manage (
    Email public.email,
    LicensePlate public.license_plate,
    PRIMARY KEY (Email, LicensePlate),
    FOREIGN KEY (Email) REFERENCES public.Admin(Email),
    FOREIGN KEY (LicensePlate) REFERENCES public.Car(LicensePlate)
);


CREATE TABLE public.Reserve (
    Costumer_ID SERIAL,
    Payment_ID SERIAL,
    LicensePlate public.license_plate,
    PRIMARY KEY (Costumer_ID, Payment_ID, LicensePlate),
    FOREIGN KEY (Costumer_ID) REFERENCES public.Customer(Costumer_ID),
    FOREIGN KEY (Payment_ID) REFERENCES public.RentalTransaction(Payment_ID),
    FOREIGN KEY (LicensePlate) REFERENCES public.Car(LicensePlate)
);
