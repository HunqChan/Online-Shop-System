USE [master]
GO
/****** Object:  Database [swdproject]    Script Date: 7/3/2025 7:44:53 AM ******/
CREATE DATABASE [swdproject]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'swdproject', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.PM\MSSQL\DATA\swdproject.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'swdproject_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.PM\MSSQL\DATA\swdproject_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [swdproject] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [swdproject].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [swdproject] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [swdproject] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [swdproject] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [swdproject] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [swdproject] SET ARITHABORT OFF 
GO
ALTER DATABASE [swdproject] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [swdproject] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [swdproject] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [swdproject] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [swdproject] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [swdproject] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [swdproject] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [swdproject] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [swdproject] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [swdproject] SET  ENABLE_BROKER 
GO
ALTER DATABASE [swdproject] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [swdproject] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [swdproject] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [swdproject] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [swdproject] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [swdproject] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [swdproject] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [swdproject] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [swdproject] SET  MULTI_USER 
GO
ALTER DATABASE [swdproject] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [swdproject] SET DB_CHAINING OFF 
GO
ALTER DATABASE [swdproject] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [swdproject] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [swdproject] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [swdproject] SET QUERY_STORE = OFF
GO
USE [swdproject]
GO
/****** Object:  Table [dbo].[cart_items]    Script Date: 7/3/2025 7:44:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart_items](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[createdAt] [datetime2](6) NULL,
	[price] [numeric](10, 2) NOT NULL,
	[quantity] [int] NOT NULL,
	[updatedAt] [datetime2](6) NULL,
	[cart_id] [bigint] NULL,
	[product_id] [bigint] NOT NULL,
	[productName] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[carts]    Script Date: 7/3/2025 7:44:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[carts](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[createdAt] [datetime2](6) NULL,
	[sessionId] [varchar](255) NULL,
	[updatedAt] [datetime2](6) NULL,
	[user_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[dtproperties]    Script Date: 7/3/2025 7:44:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dtproperties](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[objectid] [int] NULL,
	[property] [varchar](64) NOT NULL,
	[value] [varchar](255) NULL,
	[uvalue] [nvarchar](255) NULL,
	[lvalue] [image] NULL,
	[version] [int] NULL,
	[lastupdated] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_items]    Script Date: 7/3/2025 7:44:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_items](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[createdAt] [datetime2](6) NULL,
	[price] [numeric](10, 2) NOT NULL,
	[productName] [varchar](255) NULL,
	[quantity] [int] NOT NULL,
	[updatedAt] [datetime2](6) NULL,
	[order_id] [bigint] NOT NULL,
	[product_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 7/3/2025 7:44:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[createdAt] [datetime2](6) NULL,
	[orderNumber] [varchar](255) NOT NULL,
	[paymentMethod] [varchar](255) NOT NULL,
	[paymentStatus] [varchar](255) NOT NULL,
	[paymentTransactionId] [varchar](255) NULL,
	[shippingAddress] [varchar](255) NULL,
	[shippingFee] [numeric](12, 2) NOT NULL,
	[status] [varchar](255) NOT NULL,
	[subtotal] [numeric](12, 2) NOT NULL,
	[total] [numeric](12, 2) NOT NULL,
	[updatedAt] [datetime2](6) NULL,
	[user_id] [bigint] NULL,
	[to_district_id] [int] NULL,
	[to_ward_code] [varchar](50) NULL,
	[service_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[products]    Script Date: 7/3/2025 7:44:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[products](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[active] [bit] NOT NULL,
	[createdAt] [datetime2](6) NULL,
	[description] [varchar](max) NULL,
	[imageUrl] [varchar](255) NULL,
	[name] [varchar](255) NOT NULL,
	[price] [numeric](12, 2) NOT NULL,
	[stock] [int] NOT NULL,
	[updatedAt] [datetime2](6) NULL,
	[image] [varchar](255) NULL,
	[weight] [int] NULL,
	[length] [int] NULL,
	[width] [int] NULL,
	[height] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[settings]    Script Date: 7/3/2025 7:44:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[settings](
	[setting_id] [int] IDENTITY(1,1) NOT NULL,
	[setting_type] [nvarchar](50) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[description] [nvarchar](max) NULL,
	[is_deleted] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[setting_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 7/3/2025 7:44:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NOT NULL,
	[full_name] [nvarchar](100) NOT NULL,
	[password] [varchar](100) NULL,
	[avatar_url] [nvarchar](max) NULL,
	[gender] [bit] NULL,
	[email] [nvarchar](100) NOT NULL,
	[phone_number] [nvarchar](15) NULL,
	[province_id] [int] NULL,
	[province_name] [nvarchar](100) NULL,
	[ward_code] [nvarchar](20) NULL,
	[ward_name] [nvarchar](100) NULL,
	[detail_address] [nvarchar](255) NULL,
	[created_at] [datetime] NULL,
	[updated_at] [datetime] NULL,
	[is_deleted] [bit] NOT NULL,
	[reset_password_token] [nvarchar](max) NULL,
	[reset_password_expiry] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[warehouses]    Script Date: 7/3/2025 7:44:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[warehouses](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NOT NULL,
	[address] [varchar](255) NOT NULL,
	[district_id] [int] NOT NULL,
	[ward_code] [varchar](50) NOT NULL,
	[phone] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[cart_items] ON 

INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (1, CAST(N'2025-06-30T23:12:55.8460000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:12:55.8460000' AS DateTime2), 1, 2, N'Qu?n Jeans Nam Xanh')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (2, CAST(N'2025-06-30T23:13:16.1020000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:13:16.1020000' AS DateTime2), 1, 2, N'Qu?n Jeans Nam Xanh')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (3, CAST(N'2025-06-30T23:13:33.5020000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 7, CAST(N'2025-06-30T23:13:41.1970000' AS DateTime2), 2, 2, N'Qu?n Jeans Nam Xanh')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (4, CAST(N'2025-06-30T23:15:48.1290000' AS DateTime2), CAST(229000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:15:48.1290000' AS DateTime2), 2, 6, N'Men Khaki Shorts')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (5, CAST(N'2025-06-30T23:21:21.4720000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:21:21.4720000' AS DateTime2), 3, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (6, CAST(N'2025-06-30T23:21:27.3490000' AS DateTime2), CAST(429000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:21:27.3490000' AS DateTime2), 3, 7, N'Pink Oversized Hoodie')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (7, CAST(N'2025-06-30T23:34:24.5650000' AS DateTime2), CAST(199000.00 AS Numeric(10, 2)), 2, CAST(N'2025-06-30T23:35:34.0780000' AS DateTime2), 4, 1, N'Men White T-Shirt')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (8, CAST(N'2025-06-30T23:35:51.8920000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:35:51.8920000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (9, CAST(N'2025-06-30T23:37:46.7380000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:37:46.7380000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (10, CAST(N'2025-06-30T23:37:46.9260000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:37:46.9260000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (11, CAST(N'2025-06-30T23:37:48.4440000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:37:48.4440000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (12, CAST(N'2025-06-30T23:37:48.5820000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:37:48.5820000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (13, CAST(N'2025-06-30T23:37:48.7270000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:37:48.7270000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (14, CAST(N'2025-06-30T23:37:48.8760000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:37:48.8760000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (15, CAST(N'2025-06-30T23:39:00.2980000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:39:00.2980000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (16, CAST(N'2025-06-30T23:39:00.4550000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:39:00.4550000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (17, CAST(N'2025-06-30T23:39:00.5690000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:39:00.5690000' AS DateTime2), 5, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (18, CAST(N'2025-06-30T23:39:03.4410000' AS DateTime2), CAST(229000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:39:03.4410000' AS DateTime2), 5, 6, N'Men Khaki Shorts')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (19, CAST(N'2025-06-30T23:40:20.0090000' AS DateTime2), CAST(319000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:40:20.0090000' AS DateTime2), 5, 10, N'Women Knitted Sweater')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (20, CAST(N'2025-06-30T23:41:27.0480000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:41:27.0480000' AS DateTime2), 6, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (21, CAST(N'2025-06-30T23:44:02.0910000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:44:02.0910000' AS DateTime2), 7, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (22, CAST(N'2025-06-30T23:47:22.0140000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-06-30T23:47:22.0140000' AS DateTime2), 9, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (23, CAST(N'2025-07-01T00:00:06.2780000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T00:00:06.2780000' AS DateTime2), 10, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (24, CAST(N'2025-07-01T00:03:03.4510000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T00:03:03.4510000' AS DateTime2), 11, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (25, CAST(N'2025-07-01T00:05:10.3860000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 3, CAST(N'2025-07-01T00:15:05.1340000' AS DateTime2), 11, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (26, CAST(N'2025-07-01T00:11:30.1200000' AS DateTime2), CAST(289000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T00:11:30.1200000' AS DateTime2), 11, 8, N'A-line Skirt')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (27, CAST(N'2025-07-01T00:15:00.2240000' AS DateTime2), CAST(359000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T00:15:00.2240000' AS DateTime2), 11, 5, N'Floral Maxi Dress')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (28, CAST(N'2025-07-01T00:18:04.1350000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T00:18:04.1350000' AS DateTime2), 12, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (29, CAST(N'2025-07-01T00:18:09.1970000' AS DateTime2), CAST(299000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T00:18:09.1970000' AS DateTime2), 12, 3, N'Plain Long Sleeve Shirt')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (30, CAST(N'2025-07-01T00:42:37.9200000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T00:42:37.9200000' AS DateTime2), 13, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (31, CAST(N'2025-07-01T10:11:02.0360000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 3, CAST(N'2025-07-01T10:11:10.8920000' AS DateTime2), 14, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (32, CAST(N'2025-07-01T10:11:05.4010000' AS DateTime2), CAST(299000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T10:11:05.4010000' AS DateTime2), 14, 3, N'Plain Long Sleeve Shirt')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (33, CAST(N'2025-07-01T10:39:15.0340000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T10:39:15.0340000' AS DateTime2), 15, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (34, CAST(N'2025-07-01T10:45:48.9990000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T10:45:48.9990000' AS DateTime2), 16, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (35, CAST(N'2025-07-01T10:50:43.3410000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T10:50:43.3410000' AS DateTime2), 17, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (36, CAST(N'2025-07-01T10:54:36.1170000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T10:54:36.1170000' AS DateTime2), 18, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (37, CAST(N'2025-07-01T11:02:58.5260000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T11:02:58.5260000' AS DateTime2), 19, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (38, CAST(N'2025-07-01T11:03:18.2850000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T11:03:18.2850000' AS DateTime2), 19, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (39, CAST(N'2025-07-01T11:03:18.4920000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T11:03:19.7220000' AS DateTime2), 19, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (40, CAST(N'2025-07-01T11:03:18.6650000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T11:03:18.6650000' AS DateTime2), 19, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (41, CAST(N'2025-07-01T11:03:43.5190000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T11:03:43.5190000' AS DateTime2), 20, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (42, CAST(N'2025-07-01T11:24:42.6280000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T11:24:42.6280000' AS DateTime2), 21, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (43, CAST(N'2025-07-01T11:29:10.0750000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T11:29:10.0750000' AS DateTime2), 22, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (44, CAST(N'2025-07-01T19:40:32.2590000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T19:40:32.2590000' AS DateTime2), 23, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (45, CAST(N'2025-07-01T19:44:40.4960000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T19:44:40.4960000' AS DateTime2), 24, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (46, CAST(N'2025-07-01T19:49:18.0180000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T19:49:18.0180000' AS DateTime2), 25, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (47, CAST(N'2025-07-01T19:53:32.7250000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T19:53:32.7250000' AS DateTime2), 26, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (48, CAST(N'2025-07-01T19:54:06.0490000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T19:54:06.0490000' AS DateTime2), 27, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (49, CAST(N'2025-07-01T19:57:30.8120000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T19:57:30.8120000' AS DateTime2), 28, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (50, CAST(N'2025-07-01T20:56:37.4030000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T20:56:37.4030000' AS DateTime2), 29, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (51, CAST(N'2025-07-01T21:41:58.2940000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T21:41:58.2940000' AS DateTime2), 30, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (52, CAST(N'2025-07-01T22:26:44.9480000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T22:26:44.9480000' AS DateTime2), 31, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (53, CAST(N'2025-07-01T22:33:29.8050000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T22:33:29.8050000' AS DateTime2), 32, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (54, CAST(N'2025-07-01T22:36:17.9500000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T22:36:17.9500000' AS DateTime2), 33, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (55, CAST(N'2025-07-01T22:47:11.9690000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T22:47:11.9690000' AS DateTime2), 34, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (56, CAST(N'2025-07-01T22:49:41.3530000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T22:49:41.3530000' AS DateTime2), 35, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (57, CAST(N'2025-07-01T22:53:47.8170000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T22:53:47.8170000' AS DateTime2), 36, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (58, CAST(N'2025-07-01T22:55:49.6950000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T22:55:49.6950000' AS DateTime2), 37, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (59, CAST(N'2025-07-01T22:58:11.2940000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T22:58:11.2940000' AS DateTime2), 37, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (60, CAST(N'2025-07-01T23:05:01.6520000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:05:01.6520000' AS DateTime2), 37, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (61, CAST(N'2025-07-01T23:14:25.7310000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:14:25.7310000' AS DateTime2), 38, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (62, CAST(N'2025-07-01T23:17:42.2670000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 5, CAST(N'2025-07-01T23:22:08.1070000' AS DateTime2), 39, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (63, CAST(N'2025-07-01T23:29:03.8240000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:29:03.8240000' AS DateTime2), 39, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (64, CAST(N'2025-07-01T23:32:17.7690000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:32:17.7690000' AS DateTime2), 40, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (65, CAST(N'2025-07-01T23:34:10.3450000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:34:10.3450000' AS DateTime2), 40, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (66, CAST(N'2025-07-01T23:34:45.7350000' AS DateTime2), CAST(299000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:34:45.7350000' AS DateTime2), 40, 3, N'Plain Long Sleeve Shirt')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (67, CAST(N'2025-07-01T23:41:26.3110000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:41:26.3110000' AS DateTime2), 41, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (68, CAST(N'2025-07-01T23:42:04.8600000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:42:04.8600000' AS DateTime2), 41, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (69, CAST(N'2025-07-01T23:42:05.3930000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:42:05.3930000' AS DateTime2), 41, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (70, CAST(N'2025-07-01T23:42:05.9080000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:42:05.9080000' AS DateTime2), 41, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (71, CAST(N'2025-07-01T23:42:13.9390000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-01T23:42:13.9390000' AS DateTime2), 41, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (72, CAST(N'2025-07-03T07:34:42.2220000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-03T07:34:42.2220000' AS DateTime2), 44, 2, N'Men Blue Jeans')
INSERT [dbo].[cart_items] ([id], [createdAt], [price], [quantity], [updatedAt], [cart_id], [product_id], [productName]) VALUES (73, CAST(N'2025-07-03T07:40:39.1810000' AS DateTime2), CAST(399000.00 AS Numeric(10, 2)), 1, CAST(N'2025-07-03T07:40:39.1810000' AS DateTime2), 45, 2, N'Men Blue Jeans')
SET IDENTITY_INSERT [dbo].[cart_items] OFF
SET IDENTITY_INSERT [dbo].[carts] ON 

INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (1, CAST(N'2025-06-30T23:12:55.8010000' AS DateTime2), N'8F2B6DC2DDCF8AAE4BD078CE68D93879', CAST(N'2025-06-30T23:12:55.8010000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (2, CAST(N'2025-06-30T23:13:32.0840000' AS DateTime2), N'C2F3A64C8EEE063B454F44642B501BAB', CAST(N'2025-06-30T23:13:32.0840000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (3, CAST(N'2025-06-30T23:21:21.4210000' AS DateTime2), N'DC7CDE92426272251D41CE502C32E404', CAST(N'2025-06-30T23:21:21.4210000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (4, CAST(N'2025-06-30T23:34:24.4920000' AS DateTime2), N'A1F24D9205378E24704C67C031C069EA', CAST(N'2025-06-30T23:34:24.4920000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (5, CAST(N'2025-06-30T23:35:51.8230000' AS DateTime2), N'EF0CA34045D14F35279B594C11C67110', CAST(N'2025-06-30T23:35:51.8230000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (6, CAST(N'2025-06-30T23:41:26.9810000' AS DateTime2), N'2237602B40ADD15E03D2A7BE6A1CA504', CAST(N'2025-06-30T23:41:26.9810000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (7, CAST(N'2025-06-30T23:44:02.0010000' AS DateTime2), N'D4FB00CFE86B226D5486F3F38EB4D36B', CAST(N'2025-06-30T23:44:02.0010000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (8, CAST(N'2025-06-30T23:46:43.0810000' AS DateTime2), N'0CCA0EAD7304438107985EE08959BC70', CAST(N'2025-06-30T23:46:43.0810000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (9, CAST(N'2025-06-30T23:47:21.9420000' AS DateTime2), N'009738C7090E26ACE13F477641C7086E', CAST(N'2025-06-30T23:47:21.9420000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (10, CAST(N'2025-07-01T00:00:06.2180000' AS DateTime2), N'A56C86FEF726DD318FC3966E6E6A0545', CAST(N'2025-07-01T00:00:06.2180000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (11, CAST(N'2025-07-01T00:03:03.3890000' AS DateTime2), N'B271B446745DE3A3F9D6EC8A3CEE0159', CAST(N'2025-07-01T00:03:03.3890000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (12, CAST(N'2025-07-01T00:18:04.0830000' AS DateTime2), N'5261F9D2F2693DCEAFC96339CE52DFB7', CAST(N'2025-07-01T00:18:04.0830000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (13, CAST(N'2025-07-01T00:42:37.8370000' AS DateTime2), N'6BA4D5E1677B32F814FF4259EE204B82', CAST(N'2025-07-01T00:42:37.8370000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (14, CAST(N'2025-07-01T10:11:01.8940000' AS DateTime2), N'72D62C5040EF8CBA2DDAA18235FC9DBE', CAST(N'2025-07-01T10:11:01.8940000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (15, CAST(N'2025-07-01T10:39:14.9580000' AS DateTime2), N'FADD34B66653450162D69E0DF35CC71C', CAST(N'2025-07-01T10:39:14.9580000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (16, CAST(N'2025-07-01T10:45:48.9230000' AS DateTime2), N'82C5DD0C1C5E61C81E75B1C3C62E9983', CAST(N'2025-07-01T10:45:48.9230000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (17, CAST(N'2025-07-01T10:50:43.2780000' AS DateTime2), N'6C6FA6F1753F631415A98477715F9E0B', CAST(N'2025-07-01T10:50:43.2780000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (18, CAST(N'2025-07-01T10:54:33.9240000' AS DateTime2), N'B9399F2283045E6817C4F05EEBBF5AA7', CAST(N'2025-07-01T10:54:33.9240000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (19, CAST(N'2025-07-01T11:02:58.4630000' AS DateTime2), N'051B18FD0C2255BB0E24B56F5CC41BE5', CAST(N'2025-07-01T11:02:58.4630000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (20, CAST(N'2025-07-01T11:03:42.0370000' AS DateTime2), N'EE4E734E639BB67B6D1A7EFB5151FAFB', CAST(N'2025-07-01T11:03:42.0370000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (21, CAST(N'2025-07-01T11:24:42.5080000' AS DateTime2), N'21740B80B9460071C8AC92D537C8BF66', CAST(N'2025-07-01T11:24:42.5080000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (22, CAST(N'2025-07-01T11:29:10.0100000' AS DateTime2), N'2D1E90736AB5CDFBE351265873706D0F', CAST(N'2025-07-01T11:29:10.0100000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (23, CAST(N'2025-07-01T19:40:32.0180000' AS DateTime2), N'5C4BA6AA6B86BB325BD0365940756179', CAST(N'2025-07-01T19:40:32.0180000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (24, CAST(N'2025-07-01T19:44:40.3650000' AS DateTime2), N'056F8004EA644ECE8B4DF480A43D0AD9', CAST(N'2025-07-01T19:44:40.3650000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (25, CAST(N'2025-07-01T19:49:17.8790000' AS DateTime2), N'1EC34AE9754211F1AA82C886BC43E790', CAST(N'2025-07-01T19:49:17.8790000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (26, CAST(N'2025-07-01T19:53:31.1270000' AS DateTime2), N'E94A2179C4E52A9EBF75445B755EAC9F', CAST(N'2025-07-01T19:53:31.1270000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (27, CAST(N'2025-07-01T19:54:05.9100000' AS DateTime2), N'53CF2EB6791C613673F4B3D8D5EC25A0', CAST(N'2025-07-01T19:54:05.9100000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (28, CAST(N'2025-07-01T19:57:30.7230000' AS DateTime2), N'2CF21BBFF737471D23AA074FCE3F1EBB', CAST(N'2025-07-01T19:57:30.7230000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (29, CAST(N'2025-07-01T20:56:37.2950000' AS DateTime2), N'4523A343396B5B1D8D7D7F0FBBDC3C38', CAST(N'2025-07-01T20:56:37.2950000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (30, CAST(N'2025-07-01T21:41:58.2030000' AS DateTime2), N'99448DD4BDEBB27E973E1A2D2B0B8782', CAST(N'2025-07-01T21:41:58.2030000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (31, CAST(N'2025-07-01T22:26:44.8870000' AS DateTime2), N'AECDCAE36E22295D375EE0F88E9C1CA3', CAST(N'2025-07-01T22:26:44.8870000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (32, CAST(N'2025-07-01T22:33:29.7220000' AS DateTime2), N'E9E908F1B6C7633F6DA8B735B1C63FFB', CAST(N'2025-07-01T22:33:29.7220000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (33, CAST(N'2025-07-01T22:36:17.8820000' AS DateTime2), N'C9727C31B04F26A2C36525E01B3C526A', CAST(N'2025-07-01T22:36:17.8820000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (34, CAST(N'2025-07-01T22:47:11.9200000' AS DateTime2), N'DC8CA02695B1704E6708093DFCA68F80', CAST(N'2025-07-01T22:47:11.9200000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (35, CAST(N'2025-07-01T22:49:41.3100000' AS DateTime2), N'5865E6EC6881D1732FE10A1B144E2AC9', CAST(N'2025-07-01T22:49:41.3100000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (36, CAST(N'2025-07-01T22:53:47.7700000' AS DateTime2), N'C02CF41D89EE9FBFEBDF8665AC313E2F', CAST(N'2025-07-01T22:53:47.7700000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (37, CAST(N'2025-07-01T22:55:49.6500000' AS DateTime2), N'28886B8D4690FD1F40AF30A9908B8B40', CAST(N'2025-07-01T22:55:49.6500000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (38, CAST(N'2025-07-01T23:14:25.6630000' AS DateTime2), N'31D1A2DA2FCCEDAFB36C96DDE6461FFB', CAST(N'2025-07-01T23:14:25.6630000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (39, CAST(N'2025-07-01T23:17:42.2040000' AS DateTime2), N'9171C52BABB05A69180B3C49325A681D', CAST(N'2025-07-01T23:17:42.2040000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (40, CAST(N'2025-07-01T23:32:17.6930000' AS DateTime2), N'B0017AC393E0E4B01D3B7B9B80AB2551', CAST(N'2025-07-01T23:32:17.6930000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (41, CAST(N'2025-07-01T23:41:26.2490000' AS DateTime2), N'1931B603985CEBEE8807175D322CA435', CAST(N'2025-07-01T23:41:26.2490000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (42, CAST(N'2025-07-02T00:07:37.7750000' AS DateTime2), N'5FD6761EE7AE519744CBE3DD61CBBB7A', CAST(N'2025-07-02T00:07:37.7750000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (43, CAST(N'2025-07-02T00:20:02.8370000' AS DateTime2), N'8FC161E68DF2DF9653A3BA60D787B841', CAST(N'2025-07-02T00:20:02.8370000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (44, CAST(N'2025-07-03T07:34:42.1520000' AS DateTime2), N'A30BA9E3814FE771CC8DAA570D62C4AA', CAST(N'2025-07-03T07:34:42.1520000' AS DateTime2), 0)
INSERT [dbo].[carts] ([id], [createdAt], [sessionId], [updatedAt], [user_id]) VALUES (45, CAST(N'2025-07-03T07:40:39.1340000' AS DateTime2), N'D34A1ECA6B40EBAFC85241580D139441', CAST(N'2025-07-03T07:40:39.1340000' AS DateTime2), 0)
SET IDENTITY_INSERT [dbo].[carts] OFF
SET IDENTITY_INSERT [dbo].[orders] ON 

INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (1, CAST(N'2025-06-30T23:36:00.8960000' AS DateTime2), N'2711f9fb8c', N'VNPAY', N'PENDING', NULL, N'dadada', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-06-30T23:36:00.8960000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (2, CAST(N'2025-06-30T23:37:04.8860000' AS DateTime2), N'd2519363bb', N'COD', N'PENDING', N'N/A', N'dadada', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-06-30T23:37:04.8860000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (3, CAST(N'2025-06-30T23:37:14.1220000' AS DateTime2), N'01a16a7b79', N'VNPAY', N'PENDING', NULL, N'dadada', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-06-30T23:37:14.1220000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (4, CAST(N'2025-06-30T23:37:57.0020000' AS DateTime2), N'5db2777c4a', N'VNPAY', N'PENDING', NULL, N'a', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(2793000.00 AS Numeric(12, 2)), CAST(2793000.00 AS Numeric(12, 2)), CAST(N'2025-06-30T23:37:57.0020000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (5, CAST(N'2025-06-30T23:39:09.0720000' AS DateTime2), N'6d6cb248db', N'VNPAY', N'PENDING', NULL, N'a', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(4219000.00 AS Numeric(12, 2)), CAST(4219000.00 AS Numeric(12, 2)), CAST(N'2025-06-30T23:39:09.0720000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (6, CAST(N'2025-06-30T23:40:32.4710000' AS DateTime2), N'0aaa5240f7', N'VNPAY', N'PENDING', NULL, N'adasdsa', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(4538000.00 AS Numeric(12, 2)), CAST(4538000.00 AS Numeric(12, 2)), CAST(N'2025-06-30T23:40:32.4710000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (7, CAST(N'2025-06-30T23:41:32.1650000' AS DateTime2), N'7dc88cf474', N'VNPAY', N'PENDING', NULL, N'2122', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-06-30T23:41:32.1700000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (8, CAST(N'2025-06-30T23:44:05.7790000' AS DateTime2), N'2b3a3aefce', N'VNPAY', N'PENDING', NULL, N'a', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-06-30T23:44:05.7830000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (9, CAST(N'2025-06-30T23:47:25.6020000' AS DateTime2), N'18b21fd230', N'VNPAY', N'PENDING', NULL, N'da', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-06-30T23:47:25.6030000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (10, CAST(N'2025-07-01T00:00:09.8390000' AS DateTime2), N'5c31f0ce', N'VNPAY', N'PENDING', NULL, N'a', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:00:09.8390000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (11, CAST(N'2025-07-01T00:03:46.6730000' AS DateTime2), N'352e672e', N'CARD', N'PENDING', NULL, N'a', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:03:46.6730000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (12, CAST(N'2025-07-01T00:05:15.9670000' AS DateTime2), N'f71f4cf8', N'CARD', N'PENDING', NULL, N'd?', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(798000.00 AS Numeric(12, 2)), CAST(798000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:05:15.9670000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (13, CAST(N'2025-07-01T00:12:08.7200000' AS DateTime2), N'b96734ca', N'CARD', N'PENDING', NULL, N'ad', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(1087000.00 AS Numeric(12, 2)), CAST(1087000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:12:08.7200000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (14, CAST(N'2025-07-01T00:15:11.9910000' AS DateTime2), N'e6da78ab', N'CARD', N'PENDING', NULL, N'adasdsa', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(2244000.00 AS Numeric(12, 2)), CAST(2244000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:15:11.9910000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (15, CAST(N'2025-07-01T00:17:01.8850000' AS DateTime2), N'eadc37c7', N'CARD', N'PENDING', NULL, N'adasdsa', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(2244000.00 AS Numeric(12, 2)), CAST(2244000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:17:01.8850000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (16, CAST(N'2025-07-01T00:18:15.8560000' AS DateTime2), N'fb62a81c', N'CARD', N'PENDING', NULL, N'sadasdsa', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(698000.00 AS Numeric(12, 2)), CAST(698000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:18:15.8560000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (17, CAST(N'2025-07-01T00:19:10.6670000' AS DateTime2), N'ae95f1f8', N'CARD', N'PENDING', NULL, N'adas', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(698000.00 AS Numeric(12, 2)), CAST(698000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:19:10.6670000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (18, CAST(N'2025-07-01T00:21:39.8280000' AS DateTime2), N'2967ec00', N'CARD', N'PENDING', NULL, N'a', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(698000.00 AS Numeric(12, 2)), CAST(698000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:21:39.8280000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (19, CAST(N'2025-07-01T00:42:43.9350000' AS DateTime2), N'd23af177', N'CARD', N'PENDING', NULL, N'da', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T00:42:43.9350000' AS DateTime2), 0, NULL, NULL, NULL)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (20, CAST(N'2025-07-01T22:50:13.8600000' AS DateTime2), N'998da3e5', N'COD', N'PENDING', N'N/A', N'qu?n 1', CAST(1.23 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399001.23 AS Numeric(12, 2)), CAST(N'2025-07-01T22:50:13.8600000' AS DateTime2), NULL, 1444, N'20308', 53321)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (21, CAST(N'2025-07-01T22:54:02.9330000' AS DateTime2), N'0f20bbcb', N'COD', N'PENDING', N'N/A', N'Hà N?i', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T22:54:02.9330000' AS DateTime2), NULL, 1447, N'20304', 100039)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (22, CAST(N'2025-07-01T22:54:12.7600000' AS DateTime2), N'66d57bab', N'CARD', N'PENDING', NULL, N'Hà N?i', CAST(0.00 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399000.00 AS Numeric(12, 2)), CAST(N'2025-07-01T22:54:12.7600000' AS DateTime2), NULL, 1447, N'20304', 100039)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (23, CAST(N'2025-07-01T23:21:52.3080000' AS DateTime2), N'99be5494', N'CARD', N'PENDING', NULL, N'1444, 20308', CAST(1.23 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399001.23 AS Numeric(12, 2)), CAST(N'2025-07-01T23:21:52.3090000' AS DateTime2), NULL, 1444, N'20308', 53321)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (24, CAST(N'2025-07-01T23:22:17.9480000' AS DateTime2), N'ee0e12ca', N'VNPAY', N'PENDING', NULL, N'1444, 20308', CAST(1.23 AS Numeric(12, 2)), N'PENDING', CAST(1995000.00 AS Numeric(12, 2)), CAST(1995001.23 AS Numeric(12, 2)), CAST(N'2025-07-01T23:22:17.9480000' AS DateTime2), NULL, 1444, N'20308', 53321)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (25, CAST(N'2025-07-01T23:22:22.5550000' AS DateTime2), N'214dab52', N'CARD', N'PENDING', NULL, N'1444, 20308', CAST(1.23 AS Numeric(12, 2)), N'PENDING', CAST(1995000.00 AS Numeric(12, 2)), CAST(1995001.23 AS Numeric(12, 2)), CAST(N'2025-07-01T23:22:22.5550000' AS DateTime2), NULL, 1444, N'20308', 53321)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (26, CAST(N'2025-07-01T23:42:27.9910000' AS DateTime2), N'c901a8ca', N'VNPAY', N'PENDING', NULL, N'1444, 20308', CAST(1.23 AS Numeric(12, 2)), N'PENDING', CAST(1995000.00 AS Numeric(12, 2)), CAST(1995001.23 AS Numeric(12, 2)), CAST(N'2025-07-01T23:42:27.9910000' AS DateTime2), NULL, 1444, N'20308', 53321)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (27, CAST(N'2025-07-01T23:42:33.1090000' AS DateTime2), N'344ddc52', N'CARD', N'PENDING', NULL, N'1444, 20308', CAST(1.23 AS Numeric(12, 2)), N'PENDING', CAST(1995000.00 AS Numeric(12, 2)), CAST(1995001.23 AS Numeric(12, 2)), CAST(N'2025-07-01T23:42:33.1090000' AS DateTime2), NULL, 1444, N'20308', 53321)
INSERT [dbo].[orders] ([id], [createdAt], [orderNumber], [paymentMethod], [paymentStatus], [paymentTransactionId], [shippingAddress], [shippingFee], [status], [subtotal], [total], [updatedAt], [user_id], [to_district_id], [to_ward_code], [service_id]) VALUES (28, CAST(N'2025-07-03T07:40:51.9940000' AS DateTime2), N'0492ffc8', N'CARD', N'PENDING', NULL, N'1444, 20308', CAST(1.23 AS Numeric(12, 2)), N'PENDING', CAST(399000.00 AS Numeric(12, 2)), CAST(399001.23 AS Numeric(12, 2)), CAST(N'2025-07-03T07:40:51.9940000' AS DateTime2), NULL, 1444, N'20308', 53321)
SET IDENTITY_INSERT [dbo].[orders] OFF
SET IDENTITY_INSERT [dbo].[products] ON 

INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (1, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Breathable cotton t-shirt', N'https://asda.scene7.com/is/image/Asda/essential-mens-holiday-clothes_1?scl=1&qlt=85&jpegSize=100', N'Men White T-Shirt', CAST(199000.00 AS Numeric(12, 2)), 50, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image1.jpg', 1000, 50, 30, 20)
INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (2, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Stretchable and stylish jeans', N'https://www.next.co.il/cms/resource/blob/998114/c087b788cdabef39d18a6bb72c8bf4b1/date-hero-mens-mb-data.jpg', N'Men Blue Jeans', CAST(399000.00 AS Numeric(12, 2)), 30, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image2.jpg', 1000, 50, 30, 20)
INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (3, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Elegant long sleeve shirt', N'https://luckybrand.bynder.com/match/WebName/140549_960_2/STRIPE_SHORT_SLEEVE_CAMP_COLLAR_SHIRT_960?preset=wbmd', N'Plain Long Sleeve Shirt', CAST(299000.00 AS Numeric(12, 2)), 40, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image3.jpg', 1000, 50, 30, 20)
INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (4, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Trendy bomber jacket', N'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTUtMXBVFaPmeB4OjPACAalo2ufkwexVfXxw&s', N'Black Bomber Jacket', CAST(499000.00 AS Numeric(12, 2)), 25, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image4.jpg', 1000, 50, 30, 20)
INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (5, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Lightweight floral maxi dress', N'https://tiemchupanh.com/wp-content/uploads/2022/01/1-15.jpg', N'Floral Maxi Dress', CAST(359000.00 AS Numeric(12, 2)), 20, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image5.jpg', 1000, 50, 30, 20)
INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (6, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Trendy casual khaki shorts', N'https://file.hstatic.net/200000665395/article/mau-content-ban-quan-ao-nam-3_d050401820344f7a9869341a494a7ffa.jpg', N'Men Khaki Shorts', CAST(229000.00 AS Numeric(12, 2)), 35, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image6.jpg', 1000, 50, 30, 20)
INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (7, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Oversized casual hoodie', N'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdIK1P3i8rsIWUuyK8WDHGZYlV7ntQzNgyUE6D70XWEMvb_pWSq5d74b2CHe-ZiC13PSs&usqp=CAU', N'Pink Oversized Hoodie', CAST(429000.00 AS Numeric(12, 2)), 18, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image7.jpg', 1000, 50, 30, 20)
INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (8, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Office-style A-line skirt', N'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDrpwgThfrmQlyOOC4E0zWkQZ-aYAc5LoSfHNfpOapLcjd4CIWb6PXqz-a-2m-0DHH6HM&usqp=CAU', N'A-line Skirt', CAST(289000.00 AS Numeric(12, 2)), 28, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image8.jpg', 1000, 50, 30, 20)
INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (9, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Stretchable sportswear set', N'https://m.media-amazon.com/images/I/81ZGhxZ-giL._UY1100_.jpg', N'Men Sportswear Set', CAST(379000.00 AS Numeric(12, 2)), 22, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image9.jpg', 1000, 50, 30, 20)
INSERT [dbo].[products] ([id], [active], [createdAt], [description], [imageUrl], [name], [price], [stock], [updatedAt], [image], [weight], [length], [width], [height]) VALUES (10, 1, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'Long sleeve knitted sweater', N'https://thevou.com/wp-content/uploads/2024/09/Old-Money-Mens-Clothing-Brands-696x1044.jpg', N'Women Knitted Sweater', CAST(319000.00 AS Numeric(12, 2)), 16, CAST(N'2025-06-30T22:34:14.4270000' AS DateTime2), N'image10.jpg', 1000, 50, 30, 20)
SET IDENTITY_INSERT [dbo].[products] OFF
SET IDENTITY_INSERT [dbo].[settings] ON 

INSERT [dbo].[settings] ([setting_id], [setting_type], [name], [description], [is_deleted]) VALUES (1, N'ROLE', N'Admin', N'Administrator role', 0)
INSERT [dbo].[settings] ([setting_id], [setting_type], [name], [description], [is_deleted]) VALUES (2, N'ROLE', N'User', N'Regular user role', 0)
INSERT [dbo].[settings] ([setting_id], [setting_type], [name], [description], [is_deleted]) VALUES (3, N'ROLE', N'Staff', N'Staff role', 0)
SET IDENTITY_INSERT [dbo].[settings] OFF
SET IDENTITY_INSERT [dbo].[users] ON 

INSERT [dbo].[users] ([user_id], [role_id], [full_name], [password], [avatar_url], [gender], [email], [phone_number], [province_id], [province_name], [ward_code], [ward_name], [detail_address], [created_at], [updated_at], [is_deleted], [reset_password_token], [reset_password_expiry]) VALUES (2, 1, N'Nguyen Van A', N'admin123', NULL, 1, N'admin@example.com', N'0909123456', NULL, NULL, NULL, NULL, NULL, CAST(N'2025-07-03T07:40:06.000' AS DateTime), NULL, 0, NULL, NULL)
INSERT [dbo].[users] ([user_id], [role_id], [full_name], [password], [avatar_url], [gender], [email], [phone_number], [province_id], [province_name], [ward_code], [ward_name], [detail_address], [created_at], [updated_at], [is_deleted], [reset_password_token], [reset_password_expiry]) VALUES (3, 2, N'Le Thi B', N'user123', NULL, 0, N'user1@example.com', N'0909988776', NULL, NULL, NULL, NULL, NULL, CAST(N'2025-07-03T07:40:06.000' AS DateTime), NULL, 0, NULL, NULL)
INSERT [dbo].[users] ([user_id], [role_id], [full_name], [password], [avatar_url], [gender], [email], [phone_number], [province_id], [province_name], [ward_code], [ward_name], [detail_address], [created_at], [updated_at], [is_deleted], [reset_password_token], [reset_password_expiry]) VALUES (4, 2, N'Tran Van C', N'user456', NULL, 1, N'user2@example.com', N'0911222333', NULL, NULL, NULL, NULL, NULL, CAST(N'2025-07-03T07:40:06.000' AS DateTime), NULL, 0, NULL, NULL)
INSERT [dbo].[users] ([user_id], [role_id], [full_name], [password], [avatar_url], [gender], [email], [phone_number], [province_id], [province_name], [ward_code], [ward_name], [detail_address], [created_at], [updated_at], [is_deleted], [reset_password_token], [reset_password_expiry]) VALUES (5, 3, N'Pham Thi D', N'staff123', NULL, 0, N'staff@example.com', N'0988776655', NULL, NULL, NULL, NULL, NULL, CAST(N'2025-07-03T07:40:06.000' AS DateTime), NULL, 0, NULL, NULL)
SET IDENTITY_INSERT [dbo].[users] OFF
SET IDENTITY_INSERT [dbo].[warehouses] ON 

INSERT [dbo].[warehouses] ([id], [name], [address], [district_id], [ward_code], [phone]) VALUES (1, N'Default Warehouse', N'72 Thành Thái, Phu?ng 14, Qu?n 10, TP.HCM', 1444, N'20308', N'0987654321')
SET IDENTITY_INSERT [dbo].[warehouses] OFF
ALTER TABLE [dbo].[settings] ADD  DEFAULT ((0)) FOR [is_deleted]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ((0)) FOR [is_deleted]
GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD  CONSTRAINT [fk_users_role_id] FOREIGN KEY([role_id])
REFERENCES [dbo].[settings] ([setting_id])
GO
ALTER TABLE [dbo].[users] CHECK CONSTRAINT [fk_users_role_id]
GO
USE [master]
GO
ALTER DATABASE [swdproject] SET  READ_WRITE 
GO
