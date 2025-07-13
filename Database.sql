-- Xóa database nếu đã tồn tại
USE
    master;

IF
    DB_ID('swdproject') IS NOT NULL
    BEGIN
        ALTER
            DATABASE swdproject SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
        DROP
            DATABASE swdproject;
    END
GO

-- Tạo database

CREATE
    DATABASE swdproject;
GO

USE swdproject;
GO

-- Bảng settings
CREATE TABLE settings
(
    setting_id   INT IDENTITY (1,1) PRIMARY KEY,
    setting_type NVARCHAR(50)  NOT NULL,
    name         NVARCHAR(100) NOT NULL,
    description  NVARCHAR(MAX),
    is_deleted   BIT           NOT NULL DEFAULT 0
);

-- Bảng users
CREATE TABLE users
(
    user_id               INT IDENTITY (1,1) PRIMARY KEY,
    role_id               INT           NOT NULL,
    full_name             NVARCHAR(100) NOT NULL,
    password              VARCHAR(100),
    avatar_url            NVARCHAR(MAX),
    gender                BIT,
    email                 NVARCHAR(100) NOT NULL,
    phone_number          NVARCHAR(15),

    province_id           INT,
    province_name         NVARCHAR(100),
    district_id           INT,
    district_name         NVARCHAR(100),
    ward_code             NVARCHAR(20),
    ward_name             NVARCHAR(100),
    detail_address        NVARCHAR(255),

    created_at            DATETIME               DEFAULT GETDATE(),
    updated_at            DATETIME,
    is_deleted            BIT           NOT NULL DEFAULT 0,
    reset_password_token  NVARCHAR(MAX),
    reset_password_expiry DATETIME,

    CONSTRAINT fk_users_role_id FOREIGN KEY (role_id) REFERENCES settings (setting_id)
);

CREATE TABLE products
(
    id          BIGINT IDENTITY (1,1) PRIMARY KEY,
    active      BIT            NOT NULL,
    createdAt   DATETIME2(6),
    description VARCHAR(MAX),
    imageUrl    VARCHAR(255),
    name        VARCHAR(255)   NOT NULL,
    price       NUMERIC(12, 2) NOT NULL,
    stock       INT            NOT NULL,
    updatedAt   DATETIME2(6),
    image       VARCHAR(255)
);

CREATE TABLE carts
(
    id        BIGINT IDENTITY (1,1) PRIMARY KEY,
    createdAt DATETIME2(6),
    sessionId VARCHAR(255),
    updatedAt DATETIME2(6),
    user_id   BIGINT
);

CREATE TABLE cart_items
(
    id          BIGINT IDENTITY (1,1) PRIMARY KEY,
    createdAt   DATETIME2(6),
    price       NUMERIC(10, 2) NOT NULL,
    quantity    INT            NOT NULL,
    updatedAt   DATETIME2(6),
    cart_id     BIGINT,
    product_id  BIGINT         NOT NULL,
    productName VARCHAR(255)
);

CREATE TABLE orders
(
    id                   BIGINT IDENTITY (1,1) PRIMARY KEY,
    createdAt            DATETIME2(6),
    orderNumber          VARCHAR(255)   NOT NULL,
    paymentMethod        VARCHAR(255)   NOT NULL,
    paymentStatus        VARCHAR(255)   NOT NULL,
    paymentTransactionId VARCHAR(255),
    shippingAddress      VARCHAR(255),
    shippingFee          NUMERIC(12, 2) NOT NULL,
    status               VARCHAR(255)   NOT NULL,
    subtotal             NUMERIC(12, 2) NOT NULL,
    total                NUMERIC(12, 2) NOT NULL,
    updatedAt            DATETIME2(6),
    user_id              BIGINT
);

CREATE TABLE order_items
(
    id          BIGINT IDENTITY (1,1) PRIMARY KEY,
    createdAt   DATETIME2(6),
    price       NUMERIC(10, 2) NOT NULL,
    productName VARCHAR(255),
    quantity    INT            NOT NULL,
    updatedAt   DATETIME2(6),
    order_id    BIGINT         NOT NULL,
    product_id  BIGINT
);

-- Thêm dữ liệu mẫu vào bảng settings cho loại ROLE
INSERT INTO settings (setting_type, name, description)
VALUES ('ROLE', 'Admin', 'Administrator role'),
       ('ROLE', 'User', 'Regular user role'),
       ('ROLE', 'Staff', 'Staff role');

INSERT INTO users (role_id, full_name, password, avatar_url, gender,
                   email, phone_number, created_at, is_deleted)
VALUES
-- Admin
(1, 'Nguyen Van A', 'admin123', NULL, 1, 'admin@example.com', '0909123456', GETDATE(), 0),
-- Users
(2, 'Le Thi B', 'user123', NULL, 0, 'user1@example.com', '0909988776', GETDATE(), 0),
(2, 'Tran Van C', 'user456', NULL, 1, 'user2@example.com', '0911222333', GETDATE(), 0),
-- Staff
(3, 'Pham Thi D', 'staff123', NULL, 0, 'staff@example.com', '0988776655', GETDATE(), 0);

