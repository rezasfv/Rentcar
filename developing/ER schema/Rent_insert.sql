\c rentcar
-- Insert sample data into DriversLicense table
INSERT INTO public.DriversLicense (LicenseNumber, ExpirationDate, TypeAccept, IssuingDate)
VALUES 
('SC178902', '2025-12-31', 'B', '2020-01-01'),
('DL876792', '2026-06-30', 'B1', '2021-05-15'),
('LP716390', '2029-10-31', 'B', '2019-11-01'),
('RT361941', '2028-08-31', 'B1', '2020-08-31'),
('EL329729', '2025-04-30', 'B', '2020-02-01');

-- Insert sample data into Customer table
INSERT INTO public.Customer (Email, FirstName, LastName, Phone, DataBirth, Address_Costumer, Nationality, Password_Costumer, LicenseNumber)
VALUES 
('johndoe@gmail.com', 'John', 'Doe', '123456789', '1990-01-01', '123 Main St', 'USA', 'bjhkvw725', 'SC178902'),
('janes85@gmail.com', 'Jane', 'Smith', '987654321', '1985-05-15', '456 Elm St', 'UK', 'hbf7akjy', 'DL876792'),
('alicejohnson@gmail.com', 'Alice', 'Johnson', '554658451', '1988-10-30', '789 Oak St', 'Canada', 'bjh93hbf', 'LP716390'),
('emily.williams4@gmail.com', 'Emily', 'Williams', '762891764', '1995-08-25', '456 Pine St', 'Canada', 'passtyh4', 'RT361941'),
('sophiabrown795@gmail.com', 'Sophia', 'Brown', '7781635655', '1988-10-30', '789 Maple St', 'Australia', 'kjeword25', 'EL329729');

-- Insert sample data into Car table
INSERT INTO public.Car (LicensePlate, RentalRate, CurrentStatus, ModelName, BrandName, Category, Capacity)
VALUES 
('HR23791B', 50.00, 'available', 'Corolla', 'Toyota', 'medium', 5),
('T6237FX5', 70.00, 'available', 'Civic', 'Honda', 'small', 4),
('BF82HYH6', 100.00, 'available', 'Explorer', 'Ford', 'SUV', 7),
('3877DBHY', 80.00, 'available', 'Golf', 'Volkswagen', 'medium', 5),
('JHF76287', 120.00, 'available', 'X5', 'BMW', 'large', 7),
('PQ63R789', 60.00, 'available', 'Camry', 'Toyota', 'medium', 5),
('S75TU012', 70.00, 'available', 'Accord', 'Honda', 'medium', 5),
('V56WX345', 90.00, 'available', 'Equinox', 'Chevrolet', 'medium', 5),
('Y45ZA678', 100.00, 'available', 'Altima', 'Nissan', 'medium', 5),
('BC76D901', 80.00, 'available', 'Jetta', 'Volkswagen', 'medium', 5);

-- Insert sample data into Admin table
INSERT INTO public.Admin (Email, Password_admin)
VALUES 
('Admin@gmail.com', 'admin123');


-- Insert sample data into RentalTransaction table
INSERT INTO public.RentalTransaction (Amount, PaymentStatus, IssueDate, FromDate, ToDate, Costumer_ID, LicensePlate)
VALUES 
(303.07, 'pending', '2024-01-25', '2024-02-01', '2024-02-10', 1, 'T6237FX5'),
(280.65, 'confirmed', '2024-01-15', '2024-02-05', '2024-02-15', 2, 'HR23791B'),
(120.24, 'confirmed', '2024-02-05', '2024-03-03', '2024-03-08', 3, '3877DBHY'),
(1650.87, 'pending', '2024-02-18', '2024-03-07', '2024-04-14', 4, 'HR23791B'),
(390.60, 'cancelled', '2024-02-27', '2024-03-12', '2024-03-29', 5, 'JHF76287'),
(110.00, 'pending', '2024-04-14', '2024-04-03', '2024-04-08', 1, 'BC76D901'),
(85.00, 'confirmed', '2024-03-12', '2024-04-05', '2024-04-15', 2, 'S75TU012'),
(130.00, 'pending', '2024-03-27', '2024-04-07', '2024-04-14', 3, 'V56WX345'),
(195.00, 'confirmed', '2024-04-03', '2024-04-12', '2024-04-19', 4, 'T6237FX5'),
(120.00, 'pending', '2024-04-05', '2024-05-06', '2024-05-13', 5, '3877DBHY');

