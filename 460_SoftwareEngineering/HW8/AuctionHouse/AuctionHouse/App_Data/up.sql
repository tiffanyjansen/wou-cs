/* Create the Buyer Table */
CREATE TABLE [dbo].[Buyer]
(
	[ID]   INT IDENTITY(1,1) NOT NULL,
	[Name] NVARCHAR(30) NOT NULL,
	CONSTRAINT [PK_dbo.Buyer] PRIMARY KEY CLUSTERED (Name)
);

/* Create the Seller Table */
CREATE TABLE [dbo].[Seller]
(
	[ID]   INT IDENTITY(1,1) NOT NULL,
	[Name] NVARCHAR(30) NOT NULL,
	CONSTRAINT [PK_dbo.Seller] PRIMARY KEY CLUSTERED (Name)
);

/* Create the Item Table */
CREATE TABLE [dbo].[Item]
(
	[ID] INT IDENTITY(1001,1) NOT NULL, 
    [Name] NVARCHAR(30) NOT NULL, 
    [Description] NVARCHAR(100) NULL, 
    [Seller] NVARCHAR(30) NOT NULL,
	CONSTRAINT [PK_dbo.Item] PRIMARY KEY CLUSTERED (ID ASC),
    CONSTRAINT [FK_Item_Seller] FOREIGN KEY ([Seller]) REFERENCES [Seller]([Name])
);

/* Create the Bid Table */
CREATE TABLE [dbo].[Bid]
(
	[ID] INT IDENTITY(1,1) NOT NULL,
	[Item] INT NOT NULL, 
    [Buyer] NVARCHAR(30) NOT NULL, 
    [Price] MONEY NULL, 
    [Timestamp] DATETIME NOT NULL, 
	CONSTRAINT [PK_dbo.Bid] PRIMARY KEY CLUSTERED (ID ASC),
	CONSTRAINT [FK_Bid_Item] FOREIGN KEY ([Item]) REFERENCES [Item]([ID]), 
    CONSTRAINT [FK_Bid_Buyer] FOREIGN KEY ([Buyer]) REFERENCES [Buyer]([Name]) 
);

/* Fill Tables with Data */
-- Buyers
INSERT INTO [dbo].[Buyer](Name) VALUES
	('Jane Stone'),
	('Tom McMasters'),
	('Otto Vanderwall');

-- Sellers
INSERT INTO [dbo].[Seller](Name) VALUES
	('Gayle Hardy'),
	('Lyle Banks'),
	('Pearl Greene');

-- Items
INSERT INTO [dbo].[ITEM](Name, Description, Seller) VALUES 
	('Abraham Lincoln Hammer', 'A bench mallet fashioned from a broken rail-splitting maul in 1829 and owned by Abraham Lincoln', 'Pearl Greene'),
	('Albert Einsteins Telescope', 'A brass telescope owned by Albert Einstein in Germany, circa 1927', 'Gayle Hardy'),
	('Bob Dylan Love Poems', 'Five versions of an original unpublished, handwritten, love poem by Bob Dylan', 'Lyle Banks');

--Bids
INSERT INTO [dbo].[Bid](Item, Buyer, Price, Timestamp) VALUES
	(1001, 'Otto Vanderwall', 250000,'12/04/2017 09:04:22'),
	(1003, 'Jane Stone', 95000,'12/04/2017 08:44:03');