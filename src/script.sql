DROP DATABASE IF EXISTS LibraryManagement;
CREATE DATABASE IF NOT EXISTS LibraryManagement;
SHOW DATABASES ;
USE LibraryManagement;
#-------------------
DROP TABLE IF EXISTS Member;
CREATE TABLE IF NOT EXISTS Member(
    memberId VARCHAR (20) ,
    memberName VARCHAR (30) NOT NULL DEFAULT  'Unknown',
    address VARCHAR (30),
    nic VARCHAR (20),
    birthDay DATE ,
    contact VARCHAR (20),
    email VARCHAR (20),
    registerDate DATE ,
    registerFee DOUBLE ,
    CONSTRAINT PRIMARY KEY (memberId)
);
SHOW TABLES ;
DESCRIBE member;
#---------------------
DROP TABLE IF EXISTS Book;
CREATE TABLE IF NOT EXISTS Book(
    bookId VARCHAR (20),
    bookName VARCHAR (30) NOT NULL DEFAULT 'Unknown',
    writterName VARCHAR (30),
    bookLanguage VARCHAR (20),
    price DOUBLE ,
    bType VARCHAR (15),
    addDate Date,
    CONSTRAINT PRIMARY KEY (bookId)
    );
SHOW TABLES ;
DESCRIBE Book;
#--------------------------------
DROP TABLE IF EXISTS AvailableBook;
CREATE TABLE IF NOT EXISTS AvailableBook(
    bookId VARCHAR (20),
    bookName VARCHAR (30) NOT NULL DEFAULT 'Unknown',
    writterName VARCHAR (30),
    bookLanguage VARCHAR (20),
    price DOUBLE ,
    bType VARCHAR (15),
    addDate Date,
    CONSTRAINT PRIMARY KEY (bookId)
    );
SHOW TABLES ;
DESCRIBE AvailableBook;
#--------------------------------
DROP TABLE IF EXISTS Employee;
CREATE TABLE IF NOT EXISTS Employee(
    empId VARCHAR (20),
    empName VARCHAR (30),
    address VARCHAR (30),
    NIC VARCHAR (20),
    birthDay DATE ,
    contact VARCHAR (20),
    post VARCHAR (20),
    quali VARCHAR (30),
     CONSTRAINT PRIMARY KEY (empId)
    );
SHOW TABLES ;
DESCRIBE Employee;
#---------------------------------
DROP TABLE IF EXISTS Login;
CREATE TABLE IF NOT EXISTS Login(
    password VARCHAR (20),
    post VARCHAR (20),
    empId VARCHAR (20),
    CONSTRAINT PRIMARY KEY (password),
    CONSTRAINT FOREIGN KEY (empId) REFERENCES Employee(empId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE Login;
#------------------------------------
DROP TABLE IF EXISTS Supplier;
CREATE TABLE IF NOT EXISTS Supplier(
    supplierId VARCHAR (20),
    supplierName VARCHAR (30),
    address VARCHAR (30),
    contact VARCHAR (20),
    CONSTRAINT PRIMARY KEY (supplierId)
    );
    SHOW TABLES ;
DESCRIBE Supplier;
#---------------------------------
DROP TABLE IF EXISTS Attendence;
CREATE TABLE IF NOT EXISTS Attendence(
    attendenceNumber VARCHAR (20),
    attendDate DATE ,
    hours INT ,
    workingType VARCHAR (20),
    empId VARCHAR (20),
    empName VARCHAR (30),
    post VARCHAR (30),
    CONSTRAINT PRIMARY KEY (attendenceNumber),
    CONSTRAINT FOREIGN KEY (empId) REFERENCES Employee(empId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE Attendence;
#------------------------------------
DROP TABLE IF EXISTS IssueBook;
CREATE TABLE IF NOT EXISTS IssueBook(
    issueId VARCHAR (20),
    issueDate DATE ,
    endDate DATE ,
    bookId VARCHAR (20),
    memberId VARCHAR (20),
    empId VARCHAR (20),
    CONSTRAINT PRIMARY KEY (issueId),
    CONSTRAINT FOREIGN KEY (bookId) REFERENCES Book(bookId)  ,
    CONSTRAINT FOREIGN KEY (memberId) REFERENCES Member(memberId) ,
    CONSTRAINT FOREIGN KEY (empId) REFERENCES Employee(empId)
    );
SHOW TABLES ;
DESCRIBE IssueBook;
#------------------------------------
DROP TABLE IF EXISTS ReturnBook;
CREATE TABLE IF NOT EXISTS ReturnBook(
    returnId VARCHAR (20),
    issueId VARCHAR (20),
    issueDate DATE ,
    endDate DATE ,
    returnDate DATE ,
    bookId VARCHAR (20),
    memberId VARCHAR (20),
    empId VARCHAR (20),
    lateDates INT ,
    fines DOUBLE ,
    CONSTRAINT PRIMARY KEY (returnId),
    CONSTRAINT FOREIGN KEY (bookId) REFERENCES Book(bookId) ,
    CONSTRAINT FOREIGN KEY (memberId) REFERENCES Member(memberId) ,
    CONSTRAINT FOREIGN KEY (empId) REFERENCES Employee(empId)
    );
SHOW TABLES ;
DESCRIBE ReturnBook;
#--------------------------------
DROP TABLE IF EXISTS Orders;
CREATE TABLE IF NOT EXISTS Orders(
    orderId VARCHAR (20),
    bookName VARCHAR (30),
    qty INT,
    orderDate DATE,
    CONSTRAINT PRIMARY KEY (orderId)
    );
SHOW TABLES ;
DESCRIBE Orders;
#--------------------------------
DROP TABLE IF EXISTS PaymentDetail;
CREATE TABLE IF NOT EXISTS PaymentDetail(
     paymentId VARCHAR (20),
     empId VARCHAR (20),
     empName VARCHAR (30),
     post VARCHAR (30),
     workingHours INT,
     totalSal DOUBLE ,
     paymentDate DATE ,
     CONSTRAINT PRIMARY KEY (paymentId),
     CONSTRAINT FOREIGN KEY (empId) REFERENCES Employee(empId)
    );
SHOW TABLES ;
DESCRIBE PaymentDetail;
#--------------------------------
DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
    orderId VARCHAR (20),
    supplierId VARCHAR (20),
    supplierName VARCHAR (30),
    supAddress VARCHAR (40),
    contact VARCHAR (20),
    bookName VARCHAR (30),
    qty INT ,
    orderDate DATE,
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (supplierId) REFERENCES Supplier(supplierId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE OrderDetail;
#--------------------------------
DROP TABLE IF EXISTS LostBook;
CREATE TABLE IF NOT EXISTS LostBook(
    lostId VARCHAR (20),
    memberId VARCHAR (20),
    issueDate DATE ,
    bookId VARCHAR (20),
    bookName VARCHAR (30),
    bookPrice DOUBLE ,
    totalFines DOUBLE ,
    payDate DATE ,
    CONSTRAINT PRIMARY KEY (lostId)
    );
    SHOW TABLES ;
DESCRIBE LostBook;
#---------------------------------
INSERT INTO Login (password,post)VALUES ('A1234','Admin');


DROP DATABASE IF EXISTS Student;
CREATE DATABASE IF NOT EXISTS Student;
SHOW DATABASES ;
USE Student;
#-------------------
DROP TABLE IF EXISTS Logins;
CREATE TABLE IF NOT EXISTS Logins(
    userName  VARCHAR (20) ,
    password VARCHAR (30),
    CONSTRAINT PRIMARY KEY (userName)
);
SHOW TABLES ;
DESCRIBE Logins;
#---------------------
DROP TABLE IF EXISTS Registration;
CREATE TABLE IF NOT EXISTS Registration(
    regNo INT,
    firstName VARCHAR (50) ,
    lastName VARCHAR (50) ,
    dateOfBirth DATE,
    gender VARCHAR (50),
    address VARCHAR (50),
    email VARCHAR (50),
    mobilePhone INT,
    homePhone INT,
    parentName VARCHAR (50),
    nic VARCHAR (50),
    contactNo INT,
    CONSTRAINT PRIMARY KEY (regNo)
    );
SHOW TABLES ;
DESCRIBE Registration;
