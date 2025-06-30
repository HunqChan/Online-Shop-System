-- Xóa database nếu đã tồn tại
IF DB_ID('swdproject') IS NOT NULL
BEGIN
    ALTER DATABASE swdproject SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE swdproject;
END
GO

-- Tạo database
CREATE DATABASE swdproject;
GO

USE swdproject;
GO

-- Bảng users
CREATE TABLE users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    active BIT NOT NULL,
    address VARCHAR(255),
    avatarUrl VARCHAR(255),
    createdAt DATETIME2(6),
    email VARCHAR(255) NOT NULL,
    fullName VARCHAR(255),
    googleId VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    role VARCHAR(255) NOT NULL,
    updatedAt DATETIME2(6),
    username VARCHAR(255) NOT NULL
);

-- Bảng product_categories
CREATE TABLE product_categories (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    active BIT NOT NULL,
    createdAt DATETIME2(6),
    description VARCHAR(255),
    imageUrl VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    updatedAt DATETIME2(6)
);

-- Bảng products
CREATE TABLE products (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    active BIT NOT NULL,
    createdAt DATETIME2(6),
    description VARCHAR(MAX),
    imageUrl VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    stock INT NOT NULL,
    updatedAt DATETIME2(6),
    category_id BIGINT
);

-- Bảng product_additional_images
CREATE TABLE product_additional_images (
    product_id BIGINT NOT NULL,
    image_url VARCHAR(255)
);

-- Bảng carts
CREATE TABLE carts (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    createdAt DATETIME2(6),
    sessionId VARCHAR(255),
    updatedAt DATETIME2(6),
    user_id BIGINT
);

-- Bảng cart_items
CREATE TABLE cart_items (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    createdAt DATETIME2(6),
    price NUMERIC(10,2) NOT NULL,
    quantity INT NOT NULL,
    updatedAt DATETIME2(6),
    cart_id BIGINT,
    product_id BIGINT NOT NULL,
    productName VARCHAR(255)
);

-- Bảng orders
CREATE TABLE orders (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    createdAt DATETIME2(6),
    guestEmail VARCHAR(255),
    guestPhone VARCHAR(255),
    orderNumber VARCHAR(255) NOT NULL,
    paymentMethod VARCHAR(255) NOT NULL,
    paymentStatus VARCHAR(255) NOT NULL,
    paymentTransactionId VARCHAR(255),
    shippingAddress VARCHAR(255),
    shippingCity VARCHAR(255),
    shippingDistrict VARCHAR(255),
    shippingFee NUMERIC(12,2) NOT NULL,
    shippingNote VARCHAR(255),
    shippingWard VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    subtotal NUMERIC(12,2) NOT NULL,
    tax NUMERIC(12,2) NOT NULL,
    total NUMERIC(12,2) NOT NULL,
    trackingNumber VARCHAR(255),
    updatedAt DATETIME2(6),
    user_id BIGINT
);

-- Bảng order_items
CREATE TABLE order_items (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    createdAt DATETIME2(6),
    price NUMERIC(10,2) NOT NULL,
    productName VARCHAR(255),
    quantity INT NOT NULL,
    updatedAt DATETIME2(6),
    order_id BIGINT NOT NULL,
    product_id BIGINT
);

-- Bảng provinces
CREATE TABLE provinces (
    id VARCHAR(20) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL
);

-- Bảng districts
CREATE TABLE districts (
    id VARCHAR(20) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    province_id BIGINT
);

-- Bảng wards
CREATE TABLE wards (
    id VARCHAR(20) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    district_id BIGINT
);
