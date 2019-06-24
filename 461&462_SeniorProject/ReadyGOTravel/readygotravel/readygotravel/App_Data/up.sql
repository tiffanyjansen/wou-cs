/* Create Tables:
	* ASP Tables for Users ~ so we don't have to worry about security so much
	* Our Tables */

/*ASP Tables for the Users */
/* AspNetUsers Table */
CREATE TABLE [dbo].[AspNetUsers] (
    [Id]                   NVARCHAR (128) NOT NULL,
    [Email]                NVARCHAR (256) NULL,
    [EmailConfirmed]       BIT            NOT NULL,
    [PasswordHash]         NVARCHAR (MAX) NULL,
    [SecurityStamp]        NVARCHAR (MAX) NULL,
    [PhoneNumber]          NVARCHAR (MAX) NULL,
    [PhoneNumberConfirmed] BIT            NOT NULL,
    [TwoFactorEnabled]     BIT            NOT NULL,
    [LockoutEndDateUtc]    DATETIME       NULL,
    [LockoutEnabled]       BIT            NOT NULL,
    [AccessFailedCount]    INT            NOT NULL,
    [UserName]             NVARCHAR (256) NOT NULL,
    CONSTRAINT [PK_dbo.AspNetUsers] PRIMARY KEY CLUSTERED ([Id] ASC)
);

GO
CREATE UNIQUE NONCLUSTERED INDEX [UserNameIndex]
    ON [dbo].[AspNetUsers]([UserName] ASC);

/* AspNetRoles Table */
CREATE TABLE [dbo].[AspNetRoles] (
    [Id]   NVARCHAR (128) NOT NULL,
    [Name] NVARCHAR (256) NOT NULL,
    CONSTRAINT [PK_dbo.AspNetRoles] PRIMARY KEY CLUSTERED ([Id] ASC)
);

GO
CREATE UNIQUE NONCLUSTERED INDEX [RoleNameIndex]
    ON [dbo].[AspNetRoles]([Name] ASC);

/* AspNetUserClaims Table */
CREATE TABLE [dbo].[AspNetUserClaims] (
    [Id]         INT IDENTITY (1, 1) NOT NULL,
    [UserId]     NVARCHAR (128) NOT NULL,
    [ClaimType]  NVARCHAR (MAX) NULL,
    [ClaimValue] NVARCHAR (MAX) NULL,
    CONSTRAINT [PK_dbo.AspNetUserClaims] PRIMARY KEY CLUSTERED ([Id] ASC),
    CONSTRAINT [FK_dbo.AspNetUserClaims_dbo.AspNetUsers_UserId] FOREIGN KEY ([UserId]) REFERENCES [dbo].[AspNetUsers] ([Id]) ON DELETE CASCADE
);

GO
CREATE NONCLUSTERED INDEX [IX_UserId]
    ON [dbo].[AspNetUserClaims]([UserId] ASC);

/* AspNetUserLogins Table */
CREATE TABLE [dbo].[AspNetUserLogins] (
    [LoginProvider] NVARCHAR (128) NOT NULL,
    [ProviderKey]   NVARCHAR (128) NOT NULL,
    [UserId]        NVARCHAR (128) NOT NULL,
    CONSTRAINT [PK_dbo.AspNetUserLogins] PRIMARY KEY CLUSTERED ([LoginProvider] ASC, [ProviderKey] ASC, [UserId] ASC),
    CONSTRAINT [FK_dbo.AspNetUserLogins_dbo.AspNetUsers_UserId] FOREIGN KEY ([UserId]) REFERENCES [dbo].[AspNetUsers] ([Id]) ON DELETE CASCADE
);

GO
CREATE NONCLUSTERED INDEX [IX_UserId]
    ON [dbo].[AspNetUserLogins]([UserId] ASC);

/* AspNetUserRoles Table */
CREATE TABLE [dbo].[AspNetUserRoles] (
    [UserId] NVARCHAR (128) NOT NULL,
    [RoleId] NVARCHAR (128) NOT NULL,
    CONSTRAINT [PK_dbo.AspNetUserRoles] PRIMARY KEY CLUSTERED ([UserId] ASC, [RoleId] ASC),
    CONSTRAINT [FK_dbo.AspNetUserRoles_dbo.AspNetRoles_RoleId] FOREIGN KEY ([RoleId]) REFERENCES [dbo].[AspNetRoles] ([Id]) ON DELETE CASCADE,
    CONSTRAINT [FK_dbo.AspNetUserRoles_dbo.AspNetUsers_UserId] FOREIGN KEY ([UserId]) REFERENCES [dbo].[AspNetUsers] ([Id]) ON DELETE CASCADE
);

GO
CREATE NONCLUSTERED INDEX [IX_UserId]
    ON [dbo].[AspNetUserRoles]([UserId] ASC);

GO
CREATE NONCLUSTERED INDEX [IX_RoleId]
    ON [dbo].[AspNetUserRoles]([RoleId] ASC);


/* Our Tables */
/* Person Table */
CREATE TABLE [dbo].[People]
(
	[PersonID] INT IDENTITY(1,1) NOT NULL,
	[UserID] NVARCHAR(128) NOT NULL,
	[FirstName] NVARCHAR(128) NOT NULL,
	[LastName] NVARCHAR(128) NOT NULL,

	CONSTRAINT [PK_dbo.People] PRIMARY KEY CLUSTERED ([PersonID] ASC),
	CONSTRAINT [FK_dbo.People] FOREIGN KEY (UserID) REFERENCES [dbo].[AspNetUsers] (Id)
);

/* Continent Table */
CREATE TABLE [dbo].[Continents]
(
	[ContinentID] INT IDENTITY(1,1) NOT NULL,
	[ContinentName] NVARCHAR(MAX) NOT NULL,

	CONSTRAINT [PK_dbo.Continents] PRIMARY KEY CLUSTERED ([ContinentID] ASC)
)

/* Currency Table */
CREATE TABLE [dbo].[Currencies]
(
	[CurrencyID] INT IDENTITY(1,1) NOT NULL,
	[CurrencyCode] NVARCHAR(3) NOT NULL,
	[CurrencyName] NVARCHAR(MAX) NOT NULL,
	[CurrencySymbol] NVARCHAR(3) NOT NULL,

	CONSTRAINT [PK_dbo.Currencies] PRIMARY KEY CLUSTERED ([CurrencyID] ASC)
)

/* Country Table*/
CREATE TABLE [dbo].[Countries]
(
	[CountryID] INT IDENTITY(1,1) NOT NULL,
	[CountryName] NVARCHAR(MAX) NOT NULL,
	[ContinentID] INT NOT NULL,
	[CurrencyID] INT NOT NULL,

	CONSTRAINT [PK_dbo.Countries] PRIMARY KEY CLUSTERED ([CountryID] ASC),
	CONSTRAINT [FK_dbo.Countries] FOREIGN KEY (ContinentID) REFERENCES [dbo].[Continents] (ContinentID),
	CONSTRAINT [FK_dbo.Countries1] FOREIGN KEY (CurrencyID) REFERENCES [dbo].[Currencies] (CurrencyID)
)

/* Locations Table */
CREATE TABLE [dbo].[Locations]
(
	[LocationID] INT IDENTITY(1,1) NOT NULL,
	[Name] NVARCHAR(MAX) NOT NULL,
	[CountryID] INT NOT NULL,
	[State] BIT NOT NULL,
	
	CONSTRAINT [PK_dbo.Locations] PRIMARY KEY CLUSTERED ([LocationID] ASC),
	CONSTRAINT [FK_dbo.Locations] FOREIGN KEY (CountryID) REFERENCES [dbo].[Countries] (CountryID)
)

/* U.S. State Weather Table */
CREATE TABLE [dbo].[Weather]
(
	[WeatherID] INT IDENTITY(1,1) NOT NULL,
	[WeatherType] NVARCHAR(32)  NOT NULL,

	CONSTRAINT [PK_dbo.Weather] PRIMARY KEY CLUSTERED ([WeatherID] ASC)
)

/* U.S. State Table */
CREATE TABLE [dbo].[States]
(
	[StateID] INT IDENTITY(1,1) NOT NULL,
	[LocationID] INT NOT NULL,
	[FoodCost] MONEY NOT NULL,
	[FoodItemOne] NVARCHAR(MAX) NOT NULL,
	[FoodItemTwo] NVARCHAR(MAX) NOT NULL,
	[FoodItemThree] NVARCHAR(MAX) NOT NULL,
	[ActivityItemOne] NVARCHAR(MAX) NOT NULL,
	[ActivityItemTwo] NVARCHAR(MAX) NOT NULL,
	[ActivityItemThree] NVARCHAR(MAX) NOT NULL,
	[SpringTemp] INT NOT NULL,
	[SummerTemp] INT NOT NULL,
	[FallTemp] INT NOT NULL,
	[WinterTemp] INT NOT NULL,
	[SpringWeather] INT NOT NULL,
	[SummerWeather] INT NOT NULL,
	[FallWeather] INT NOT NULL,
	[WinterWeather] INT NOT NULL,
	[CrimeRate] DECIMAL(6,1) NOT NULL,

	CONSTRAINT [PK_dbo.States] PRIMARY KEY CLUSTERED ([StateID] ASC),
	CONSTRAINT [FK_dbo.States] FOREIGN KEY (LocationID) REFERENCES [dbo].[Locations] (LocationID),
	CONSTRAINT [FK_dbo.States1] FOREIGN KEY (SpringWeather) REFERENCES [dbo].[Weather] (WeatherID),
	CONSTRAINT [FK_dbo.States2] FOREIGN KEY (SummerWeather) REFERENCES [dbo].[Weather] (WeatherID),
	CONSTRAINT [FK_dbo.States3] FOREIGN KEY (FallWeather) REFERENCES [dbo].[Weather] (WeatherID),
	CONSTRAINT [FK_dbo.States4] FOREIGN KEY (WinterWeather) REFERENCES [dbo].[Weather] (WeatherID)
)

/*Pople to State Table*/
CREATE TABLE [dbo].[UserState]
(
	[UserStateID] INT IDENTITY(1,1) NOT NULL,
	[PersonID] INT NOT NULL,	
	[LocationID] INT NULL,
	[International] BIT NULL,

	CONSTRAINT [PK_dbo.UserState] PRIMARY KEY CLUSTERED ([UserStateID] ASC),
	CONSTRAINT [FK_dbo.UserState] FOREIGN KEY (PersonID) REFERENCES [dbo].[People] (PersonID),
	CONSTRAINT [FK_dbo.UserState2] FOREIGN KEY (LocationID) REFERENCES [dbo].[Locations] (LocationID),
)

/*U.S. State Climate Table*/
CREATE TABLE [dbo].[Climates]
(
	[ClimateID] INT IDENTITY(1,1) NOT NULL,
	[ClimateType] NVARCHAR(32)  NOT NULL,

	CONSTRAINT [PK_dbo.Climates] PRIMARY KEY CLUSTERED ([ClimateID] ASC)
)

/*U.S. State Geography Table*/
CREATE TABLE [dbo].[Geographies]
(
	[GeographyID] INT IDENTITY(1,1) NOT NULL,
	[GeographyType] NVARCHAR(32)  NOT NULL,

	CONSTRAINT [PK_dbo.Geographies] PRIMARY KEY CLUSTERED ([GeographyID] ASC)
)

/* U.S State/Climate Table */
CREATE TABLE [dbo].[StateClimates]
(
	[StateID] INT NOT NULL,
	[ClimateID] INT NOT NULL,

	CONSTRAINT [FK_dbo.StateClimates] FOREIGN KEY (StateID) REFERENCES [dbo].[States] (StateID),
	CONSTRAINT [FK_dbo.StateClimates1] FOREIGN KEY (ClimateID) REFERENCES [dbo].[Climates] (ClimateID),
	UNIQUE(StateID, ClimateID)
)

/* U.S. State/Geography Table */
CREATE TABLE [dbo].[StateGeographies]
(
	[StateID] INT NOT NULL,
	[GeographyID] INT NOT NULL,

	CONSTRAINT [FK_dbo.StateGeographies] FOREIGN KEY (StateID) REFERENCES [dbo].[States] (StateID),
	CONSTRAINT [FK_dbo.StateGeographies1] FOREIGN KEY (GeographyID) REFERENCES [dbo].[Geographies] (GeographyID),
	UNIQUE(StateID, GeographyID)
)

/* U.S. Airport Table */
CREATE TABLE [dbo].[Airports]
(
	[AirportID] INT IDENTITY(1,1) NOT NULL,
	[AirportCode] NVARCHAR(5) NOT NULL,
	[Direction] NVARCHAR(5) NULL,
	[LocationID] INT NOT NULL,

	CONSTRAINT [PK_dbo.Airports] PRIMARY KEY CLUSTERED ([AirportID] ASC),
	CONSTRAINT [FK_dbo.Airports] FOREIGN KEY (LocationID) REFERENCES [dbo].[Locations] (LocationID)
)

/* Search Table */
CREATE TABLE [dbo].[Searches]
(
	[SearchID] INT IDENTITY(1,1) NOT NULL,
	[UserID] INT NOT NULL,
	[StartDate] DATETIME NOT NULL,
	[EndDate] DATETIME NOT NULL,
	[NumTravelers] INT NOT NULL,
	[MaxAmount] MONEY NOT NULL,
	[HotelStarValue] INT,
	[FlightType] NVARCHAR(2),
	[StartAirport] INT,
	[EndAirport] INT NOT NULL,

	CONSTRAINT [PK_dbo.Searches] PRIMARY KEY CLUSTERED ([SearchID] ASC),
    CONSTRAINT [FK_dbo.Searches] FOREIGN KEY (UserID) REFERENCES [dbo].[People] (PersonID),
    CONSTRAINT [FK_dbo.Searches2] FOREIGN KEY (StartAirport) REFERENCES [dbo].[Airports] (AirportID),
    CONSTRAINT [FK_dbo.Searches3] FOREIGN KEY (EndAirport) REFERENCES [dbo].[Airports] (AirportID)
);

/* Result Table */
CREATE TABLE [dbo].[Results]
(
	[ResultID] INT IDENTITY(1,1) NOT NULL,
	[SearchID] INT NOT NULL,
	[AvgHotelStar] DECIMAL(3,2) NOT NULL,
	[AvgFlightAmount] MONEY,
	[AvgHotelAmount] MONEY,
	[AvgFoodCost] MONEY NOT NULL,

	CONSTRAINT [PK_dbo.Results] PRIMARY KEY CLUSTERED ([ResultID] ASC),
	CONSTRAINT [FK_dbo.Results] FOREIGN KEY (SearchID) REFERENCES [dbo].[Searches] (SearchID)
);

/*
	Seeding: 
	* 1 AspNetUser ~ to be used to make the person have an "account" associated with it
	* 1 Person ~ to be used when someone is not logged in
*/

INSERT INTO [dbo].[AspNetUsers] (Id, Email, EmailConfirmed, PhoneNumberConfirmed, TwoFactorEnabled, LockoutEnabled, AccessFailedCount, UserName) VALUES
('0', 'user@users.com', 1, 1, 1, 1, 1, 'user');

/* Make it so I can make a random user with ID = -1 */
SET IDENTITY_INSERT [dbo].[People] ON; 

INSERT INTO [dbo].[People] (PersonID, UserID, FirstName, LastName) VALUES
(-1, '0', 'User', 'Test');

/* Turn it back off so we can only do it once. */
SET IDENTITY_INSERT [dbo].[People] OFF;

/*********************************************************UP DATA***********************************************************/

INSERT INTO [dbo].[Continents] (ContinentName) VALUES
('Americas'),('Europe'),('Africa'),('Asia'),('Oceania');

INSERT INTO [dbo].[Currencies] (CurrencyCode, CurrencyName, CurrencySymbol) VALUES
('ALL', 'Albanian Lek', 'Lek'), ('XCD', 'East Caribbean Dollar', '$'), ('EUR', 'Euro', '€'), ('BBD', 'Barbadian Dollar', '$'),
('BTN', 'Bhutanese Ngultrum', 'BTN'), ('BND', 'Brunei Dollar', '$'), ('XAF', 'Central African CFA Franc', 'XAF'), ('CUP', 'Cuban Peso', '$'),
('USD', 'United States Dollar', '$'), ('FKP', 'Falkland Islands Pound', '£'), ('GIP', 'Gibraltar Pound', '£'), ('HUF', 'Hungarian Forint', 'Ft'),
('IRR', 'Iranian Rial', '﷼'), ('JMD', 'Jamaican Dollar', 'J$'), ('AUD', 'Australian Dollar', '$'), ('LAK', 'Lao Kip', '₭'), ('LYD', 'Libyan Dinar', 'LYD'),
('MKD', 'Macedonian Denar', 'ден'), ('XOF', 'West African CFA Franc', 'XOF'), ('NZD', 'New Zealand Dollar', '$'), ('OMR', 'Omani Rial', '﷼'),
('PGK', 'Papua New Guinean Kina', 'PGK'), ('RWF', 'Rwandan Franc', 'RWF'), ('WST', 'Samoan Tala', 'WST'), ('RSD', 'Serbian Dinar', 'Дин'),
('SEK', 'Swedish Krona', 'kr'), ('TZS', 'Tanzanian Shilling', 'TSh'), ('AMD', 'Armenian Dram', 'AMD'), ('BSD', 'Bahamian Dollar', '$'),
('BAM', 'Bosnia And Herzegovina Konvertibilna Marka', 'KM'), ('CVE ', 'Cape Verdean Escudo', 'CVE'), ('CNY', 'Chinese Yuan', '¥'),
('CRC', 'Costa Rican Colon', '₡'), ('CZK', 'Czech Koruna', 'Kč'), ('ERN', 'Eritrean Nakfa', 'ERN'), ('GEL', 'Georgian Lari', 'GEL'),
('HTG', 'Haitian Gourde', 'HTG'), ('INR', 'Indian Rupee', '₹'), ('JOD', 'Jordanian Dinar', 'JOD'), ('KRW', 'South Korean Won', '₩'),
('LBP', 'Lebanese Lira', '£'), ('MWK', 'Malawian Kwacha', 'MWK'), ('MRO', 'Mauritanian Ouguiya', 'MRO'), ('MZN', 'Mozambican Metical', 'MZN'),
('ANG', 'Netherlands Antillean Gulden', 'ƒ'), ('PEN', 'Peruvian Nuevo Sol', 'S/'), ('QAR', 'Qatari Riyal', '﷼'), ('STD', 'Sao Tome And Principe Dobra', 'STD'),
('SLL', 'Sierra Leonean Leone', 'SLL'), ('SOS', 'Somali Shilling', 'S'), ('SDG', 'Sudanese Pound', 'SDG'), ('SYP', 'Syrian Pound', '£'),
('AOA', 'Angolan Kwanza', 'AOA'), ('AWG', 'Aruban Florin', 'ƒ'), ('BHD', 'Bahraini Dinar', 'BHD'), ('BZD', 'Belize Dollar', 'BZ$'),
('BWP', 'Botswana Pula', 'P'), ('BIF', 'Burundi Franc', 'BIF'), ('KYD', 'Cayman Islands Dollar', '$'), ('COP', 'Colombian Peso', '$'),
('DKK', 'Danish Krone', 'kr'), ('GTQ', 'Guatemalan Quetzal', 'Q'), ('HNL', 'Honduran Lempira', 'L'), ('IDR', 'Indonesian Rupiah', 'Rp'),
('ILS', 'Israeli New Sheqel', '₪'), ('KZT', 'Kazakhstani Tenge', 'лв'), ('KWD', 'Kuwaiti Dinar', 'KWD'), ('LSL', 'Lesotho Loti', 'LSL'),
('MYR', 'Malaysian Ringgit', 'RM'), ('MUR', 'Mauritian Rupee', '₨'), ('MNT', 'Mongolian Tugrik', '₮'), ('MMK', 'Myanma Kyat', 'MMK'),
('NGN', 'Nigerian Naira', '₦'), ('PAB', 'Panamanian Balboa', 'B/'), ('PHP', 'Philippine Peso', '₱'), ('RON', 'Romanian Leu', 'lei'),
('SAR', 'Saudi Riyal', '﷼'), ('SGD', 'Singapore Dollar', '$'), ('ZAR', 'South African Rand', 'R'), ('SRD', 'Surinamese Dollar', '$'),
('TWD', 'New Taiwan Dollar', 'NT$'), ('TOP', 'Paanga', 'TOP'), ('VEF', 'Venezuelan Bolivar', 'VEF'), ('DZD', 'Algerian Dinar', 'DZD'),
('ARS', 'Argentine Peso', '$'), ('AZN', 'Azerbaijani Manat', 'ман'), ('BYR', 'Belarusian Ruble', 'p'), ('BOB', 'Bolivian Boliviano', '$b'),
('BGN', 'Bulgarian Lev', 'лв'), ('CAD', 'Canadian Dollar', '$'), ('CLP', 'Chilean Peso', '$'), ('CDF', 'Congolese Franc', 'CDF'),
('DOP', 'Dominican Peso', 'RD$'), ('FJD', 'Fijian Dollar', '$'), ('GMD', 'Gambian Dalasi', 'GMD'), ('GYD', 'Guyanese Dollar', '$'),
('ISK', 'Icelandic Króna', 'kr'), ('IQD', 'Iraqi Dinar', 'IQD'), ('JPY', 'Japanese Yen', '¥'), ('KPW', 'North Korean Won', '₩'),
('LVL', 'Latvian Lats', 'Ls'), ('CHF', 'Swiss Franc', 'Fr'), ('MGA', 'Malagasy Ariary', 'MGA'), ('MDL', 'Moldovan Leu', 'MDL'),
('MAD', 'Moroccan Dirham', 'MAD'), ('NPR', 'Nepalese Rupee', '₨'), ('NIO', 'Nicaraguan Cordoba', 'C$'), ('PKR', 'Pakistani Rupee', '₨'),
('PYG', 'Paraguayan Guarani', 'Gs'), ('SHP', 'Saint Helena Pound', '£'), ('SCR', 'Seychellois Rupee', '₨'), ('SBD', 'Solomon Islands Dollar', '$'),
('LKR', 'Sri Lankan Rupee', '₨'), ('THB', 'Thai Baht', '฿'), ('TRY', 'Turkish New Lira', 'TRY'), ('AED', 'UAE Dirham', 'AED'),
('VUV', 'Vanuatu Vatu', 'VUV'), ('YER', 'Yemeni Rial', '﷼'), ('AFN', 'Afghan Afghani', '؋'), ('BDT', 'Bangladeshi Taka', 'BDT'),
('BRL', 'Brazilian Real', 'R$'), ('KHR', 'Cambodian Riel', '៛'), ('KMF', 'Comorian Franc', 'KMF'), ('HRK', 'Croatian Kuna', 'kn'),
('DJF', 'Djiboutian Franc', 'DJF'), ('EGP', 'Egyptian Pound', '£'), ('ETB', 'Ethiopian Birr', 'ETB'), ('XPF', 'CFP Franc', 'XPF'),
('GHS', 'Ghanaian Cedi', 'GHS'), ('GNF', 'Guinean Franc', 'GNF'), ('HKD', 'Hong Kong Dollar', '$'), ('XDR', 'Special Drawing Rights', 'XDR'),
('KES', 'Kenyan Shilling', 'KSh'), ('KGS', 'Kyrgyzstani Som', 'лв'), ('LRD', 'Liberian Dollar', '$'), ('MOP', 'Macanese Pataca', 'MOP'),
('MVR', 'Maldivian Rufiyaa', 'MVR'), ('MXN', 'Mexican Peso', '$'), ('NAD', 'Namibian Dollar', '$'), ('NOK', 'Norwegian Krone', 'kr'),
('PLN', 'Polish Zloty', 'zł'), ('RUB', 'Russian Ruble', 'руб'), ('SZL', 'Swazi Lilangeni', 'SZL'), ('TJS', 'Tajikistani Somoni', 'TJS'),
('TTD', 'Trinidad and Tobago Dollar', 'TT$'), ('UGX', 'Ugandan Shilling', 'USh'), ('UYU', 'Uruguayan Peso', '$U'), ('VND', 'Vietnamese Dong', '₫'),
('TND', 'Tunisian Dinar', 'TND'), ('UAH', 'Ukrainian Hryvnia', '₴'), ('UZS', 'Uzbekistani Som', 'лв'), ('TMT', 'Turkmenistan Manat', 'TMT'),
('GBP', 'British Pound', '£'), ('ZMW', 'Zambian Kwacha', 'ZMW'), ('BTC', 'Bitcoin', 'BTC'), ('BYN', 'New Belarusian Ruble', 'p');

INSERT INTO [dbo].[Countries] (CountryName, ContinentID, CurrencyID) VALUES
/*America*/
('United States', 1, 9),

/* European Countries */
('Albania', 2, 1),('Armenia', 2, 28),('Austria', 2, 15),('Azerbaijan', 2, 86),('Belarus', 2, 87),('Belgium', 2, 3),
('Bosnia and Herzegovina', 2, 30),('Bulgaria', 2, 89),('Croatia', 2, 124),('Cyprus', 2, 3),('Czech Republic', 2, 34),('Denmark', 2, 61),('Estonia', 2, 3),
('Faroe Islands', 2, 61),('Finland', 2, 3),('France', 2, 3),('Georgia', 2, 36),('Germany', 2, 3),('Gibraltar', 2, 11),('Greece', 2, 3),('Guernsey', 2, 153),
('Hungary', 2, 12),('Iceland', 2, 97),('Ireland', 2, 3),('Isle of Man', 2, 153),('Italy', 2, 3),('Jersey', 2, 153),('Kosovo', 2, 3),('Latvia', 2, 3),('Lithuania', 2, 102),
('Luxembourg', 2, 3),('Malta', 2, 3),('Montenegro', 2, 3),('Netherlands', 2, 3),('North Macedonia', 2, 18),('Norway', 2, 140),('Poland', 2, 141),('Portugal', 2, 3),
('Republic of Moldova', 2, 104),('Romania', 2, 76),('Russia', 2, 142),('Serbia', 2, 25),('Slovakia', 2, 3), ('Slovenia', 2, 3),('Spain', 2, 3),('Sweden', 2, 26),
('Switzerland', 2, 102),('Turkey', 2, 115),('Ukraine', 2, 150),('United Kingdom', 2, 153),

/* African Countries */
('Algeria', 3, 84), ('Angola', 3, 53), ('Benin', 3, 19), ('Botswana', 3, 57), ('Burkina Faso', 3, 19), ('Burundi', 3, 58), ('Cameroon', 3, 7), ('Cape Verde', 3, 31), 
('Central African Republic', 3, 7), ('Chad', 3, 7), ('Comoros', 3, 123), ('Côte d`Ivoire', 3, 19), ('Democratic Republic of the Congo', 3, 92), ('Djibouti', 3, 125), ('Egypt', 3, 126), 
('Equatorial Guinea', 3, 7), ('Eritrea', 3, 35), ('Eswatini', 3, 143), ('Ethiopia', 3, 127), ('Gabon', 3, 7), ('Gambia', 3, 95), ('Ghana', 3, 129), ('Guinea', 3, 130), ('Guinea-Bissau', 3, 19), 
('Kenya', 3, 133), ('Lesotho', 3, 68), ('Liberia', 3, 135), ('Libya', 3, 17), ('Madagascar', 3, 103), ('Malawi', 3, 42), ('Mali', 3, 19), ('Mauritania', 3, 43), ('Mauritius', 3, 70), ('Mayotte', 3, 3), 
('Morocco', 3, 105), ('Mozambique', 3, 44), ('Namibia', 3, 139), ('Niger', 3, 19), ('Nigeria', 3, 73), ('Republic of the Congo', 3, 7), ('Réunion', 3, 3), ('Rwanda', 3, 23), ('Saint Helena', 3, 153), 
('São Tomé and Príncipe', 3, 48), ('Senegal', 3, 19), ('Seychelles', 3, 111), ('Sierra Leone', 3, 49), ('Somalia', 3, 50), ('South Africa', 3, 79), ('South Sudan', 3, 51), ('Sudan', 3, 51), 
('Tanzania', 3, 27), ('Togo', 3, 19), ('Tunisia', 3, 149), ('Uganda', 3, 146), ('Zambia', 3, 154), ('Zimbabwe', 3, 9), 

/*American/Oceanic Countries*/
('Anguilla', 1, 3), ('Antigua and Barbuda', 1, 3), ('Argentina', 1, 85), ('Aruba', 1, 54), ('Bahamas', 1, 29), ('Barbados', 1, 4), ('Belize', 1, 56), ('Bermuda', 1, 9), 
('Bolivia', 1, 8), ('Brazil', 1, 121), ('British Virgin Islands', 1, 9), ('Canada', 1, 90), ('Caribbean Netherlands', 1, 3), ('Cayman Islands', 1, 59), ('Chile', 1, 91), ('Colombia', 1, 60), 
('Costa Rica', 1, 33), ('Cuba', 1, 8), ('Curaçao', 1, 45), ('Dominica', 1, 2), ('Dominican Republic', 1, 93), ('Ecuador', 1, 9), ('El Salvador', 1, 9), ('Falkland Islands', 1, 10), 
('French Guiana', 1, 3), ('Greenland', 1, 61), ('Grenada', 1, 2), ('Guadeloupe', 1, 3), ('Guatemala', 1, 62), ('Guyana', 1, 96), ('Haiti', 1, 37), ('Honduras', 1, 63), ('Jamaica', 1, 14), 
('Martinique', 1, 3), ('Mexico', 1, 138), ('Montserrat', 1, 2), ('Nicaragua', 1, 107), ('Panama', 1, 74), ('Paraguay', 1, 109), ('Peru', 1, 46), ('Puerto Rico', 1, 9), ('Saint Barthélemy', 1, 3), 
('Saint Kitts and Nevis', 1, 2), ('Saint Lucia', 1, 2), ('Saint Pierre and Miquelon', 1, 3), ('Saint Vincent and the Grenadines', 1, 2), ('Sint Maarten', 1, 45), 
('Suriname', 1, 80), ('Trinidad and Tobago', 1, 145), ('Turks and Caicos Islands', 1, 9), ('Uruguay', 1, 147), ('US Virgin Islands', 1, 9), ('Venezuela', 1, 83),

/*Asian Countries*/
('Afghanistan', 4, 119),('Bahrain', 4, 55),('Bangladesh', 4, 120),('Bhutan', 4, 5),('Brunei', 4, 6),('Cambodia', 4, 122),('China', 4, 32),('East Timor', 4, 9),('India', 4, 38),('Indonesia', 4, 64),
('Iran', 4, 13),('Iraq', 4, 98),('Israel', 4, 65),('Japan', 4, 99),('Jordan', 4, 39),('Kazakhstan', 4, 66),('Kuwait', 4, 67),('Kyrgyzstan', 4, 134),('Laos', 4, 16),('Lebanon', 4, 41),('Macau', 4, 136),
('Malaysia', 4, 69),('Maldives', 4, 137),('Mongolia', 4, 71),('Myanmar', 4, 72),('Nepal', 4, 106),('North Korea', 4, 100),('Oman', 4, 21),('Pakistan', 4, 108),('Philippines', 4, 75),('Qatar', 4, 47),
('Saudi Arabia', 4, 77),('Singapore', 4, 78),('South Korea', 4, 40),('Sri Lanka', 4, 113),('Syria', 4, 52),('Taiwan', 4, 81),('Tajikistan', 4, 144),('Thailand', 4, 114),('Turkmenistan', 4, 152),
('United Arab Emirates', 4, 116),('Uzbekistan', 4, 151),('Vietnam', 4, 148),('Yemen', 4, 118),

/*Oceania Countries*/
('American Samoa', 5, 9),('Australia', 5, 15),('Christmas Island', 5, 15),('Cocos Islands', 5, 15),('Cook Islands', 5, 20),('Easter Island', 5, 91),('Federated States of Micronesia', 5, 9),
('Fiji', 5, 94),('French Polynesia', 5, 128),('Guam', 5, 9),('Kiribati', 5, 15),('Marshall Islands', 5, 9),('Nauru', 5, 15),('New Caledonia', 5, 128),('New Zealand', 5, 20),('Niue', 5, 20),('Norfolk Island', 5, 15),
('Northern Mariana Islands', 5, 9),('Palau', 5, 9),('Papua New Guinea', 5, 22),('Samoa', 5, 24),('Solomon Islands', 5, 112),('Tonga', 5, 82),('Tuvalu', 5, 15),('Vanuatu', 5, 117),
('Wallis and Futuna', 5, 128);

INSERT INTO [dbo].[Locations] (Name,  CountryID,  State) VALUES
/* US States */
('Alabama', 1, 1), ('Alaska', 1, 1), ('Arizona', 1, 1), ('Arkansas', 1, 1), ('California', 1, 1), ('Colorado', 1, 1), ('Connecticut', 1, 1),
('Delaware', 1, 1), ('Florida', 1, 1), ('Georgia', 1, 1), ('Hawaii', 1, 1), ('Idaho', 1, 1), ('Illinois', 1, 1), ('Indiana', 1, 1), 
('Iowa', 1, 1), ('Kansas', 1, 1), ('Kentucky', 1, 1), ('Louisiana', 1, 1), ('Maine', 1, 1), ('Maryland', 1, 1), ('Massachusetts', 1, 1), 
('Michigan', 1, 1), ('Minnesota', 1, 1), ('Mississippi', 1, 1), ('Missouri', 1, 1), ('Montana', 1, 1), ('Nebraska', 1, 1), ('Nevada', 1, 1), 
('New Hampshire', 1, 1), ('New Jersey', 1, 1), ('New Mexico', 1, 1), ('New York', 1, 1), ('North Carolina', 1, 1), ('North Dakota', 1, 1), 
('Ohio', 1, 1), ('Oklahoma', 1, 1), ('Oregon', 1, 1), ('Pennsylvania', 1, 1), ('Rhode Island', 1, 1), ('South Carolina', 1, 1), 
('South Dakota', 1, 1), ('Tennessee', 1, 1), ('Texas', 1, 1), ('Utah', 1, 1), ('Vermont', 1, 1), ('Virginia', 1, 1), ('Washington', 1, 1), 
('West Virginia', 1, 1), ('Wisconsin', 1, 1), ('Wyoming', 1, 1),

/* European Cities */
('Tirana',2,0),('Gyumri',3,0),('Yerevan',3,0),('Graz',4,0),('Innsbruck',4,0),('Linz',4,0),('Salzburg',4,0),
('Vienna',4,0),('Baku',5,0),('Ganja',5,0),('Lankaran',5,0),('Nakhchivan (city)',5,0),('Qabala',5,0),('Gomel',6,0),
('Grodno',6,0),('Minsk',6,0),('Antwerp',7,0),('Brussels',7,0),('Liège',7,0),('Banja Luka',8,0),('Mostar',8,0),
('Sarajevo',8,0),('Tuzla',8,0),('Burgas',9,0),('Plovdiv',9,0),('Sofia',9,0),('Varna',9,0),('Dubrovnik ',10,0),
('Pula',10,0),('Split',10,0),('Zadar',10,0),('Zagreb',10,0),('Larnaca',11,0),('Paphos',11,0),('Tymbou',11,0),
('Brno',12,0),('Karlovy Vary',12,0),('Ostrava',12,0),('Pardubice',12,0),('Prague',12,0),('Aalborg',13,0),('Aarhus',13,0),
('Billund',13,0),('Copenhagen',13,0),('Tallinn',14,0),('Tartu',14,0),('Vágar ',15,0),('Helsinki',16,0),('Oulu',16,0),
('Rovaniemi',16,0),('Tampere',16,0),('Turku',16,0),('Lyon',17,0),('Marseille',17,0),('Nice',17,0),('Paris',17,0),('Toulouse',17,0),
('Batumi',18,0),('Kutaisi',18,0),('Tbilisi',18,0),('Berlin',19,0),('Düsseldorf',19,0),('Frankfurt',19,0),('Hamburg',19,0),('Munich',19,0),
('Gibraltar',20,0),('Athens',21,0),('Chania',21,0),('Heraklion',21,0),('Rhodes',21,0),('Thessaloniki',21,0),('Alderney',22,0),('Guernsey',22,0),
('Budapest',23,0),('Debrecen',23,0),('Győr-Pér',23,0),('Hévíz',23,0),('Pécs-Pogány',23,0),('Akureyri',24,0),('Reykjavik',24,0),('Cork',25,0),
('Dublin',25,0),('Galway',25,0),('Kerry',25,0),('Knock',25,0),('Shannon',25,0),('Isle of Man',26,0), ('Bergamo',27,0),('Milan',27,0),('Florence',27,0),('Rome',27,0),
('Venice',27,0),('Jersey',28,0),('Pristina',29,0),('Riga',30,0),('Ventspils',30,0),('Kaunas',31,0),('Palanga',31,0),('Šiauliai',31,0),
('Vilnius',31,0),('Luxembourg City',32,0),('Luqa',33,0),('Podgorica',34,0),('Tivat',34,0),('Amsterdam',35,0),('Eindhoven',35,0),('Groningen',35,0),
('Maastricht',35,0),('Rotterdam',35,0),('Ohrid',36,0),('Skopje',36,0),('Bergen',37,0),('Oslo',37,0),('Stavanger',37,0),('Tromsø',37,0),
('Trondheim',37,0),('Gdańsk',38,0),('Katowice',38,0),('Kraków',38,0),('Warsaw',38,0),('Wrocław',38,0),('Faro',39,0),('Funchal',39,0),
('Lisbon',39,0),('Ponta Delgada',39,0),('Porto',39,0),('Chişinău',40,0),('Bucharest',41,0),('Cluj-Napoca',41,0),('Iași',41,0),('Sibiu',41,0),
('Timișoara',41,0),('Moscow',42,0),('Novosibirsk',42,0),('Saint Petersburg',42,0),('Sochi',42,0),('Yekaterinburg',42,0),('Belgrade',43,0),
('Niš',43,0),('Bratislava',44,0),('Košice',44,0),('Piešťany',44,0),('Poprad',44,0),('Sliač',44,0),('Ljubljana',45,0),('Maribor',45,0),
('Portorož',45,0),('Barcelona',46,0),('Gran Canaria',46,0),('Madrid',46,0),('Málaga',46,0),('Palma de Mallorca',46,0),('Gothenburg',47,0),
('Luleå',47,0),('Malmö',47,0),('Stockholm',47,0),('Stockholm',47,0),('Basel-Mulhouse-Freiburg',48,0),('Bern',48,0),('Geneva',48,0),
('St. Gallen',48,0),('Zurich',48,0),('Adana',49,0),('Ankara',49,0),('Antalya',49,0),('Istanbul',49,0),('İzmir',49,0),('Kharkiv',50,0),('Kiev',50,0),
('Lviv',50,0),('Odessa',50,0),('Zaporizhia',50,0),('Birmingham',51,0),('Edinburgh',51,0),('Glasgow',51,0),('London',51,0),('Manchester',51,0),

/* African Cities */
('Bejaia',52,0),('Algiers',52,0),('Luanda',53,0),('Lubango',53,0),('Cotonou',54,0),('Gaborone',55,0),('Maun',55,0),('Francistown',55,0),
('Kasane',55,0),('Bobo-Dioulasso',56,0),('Ouagadougou',56,0),('Bujumbura',57,0),('Douala',58,0),('Yaoundé',58,0),('Boa Vista Island',59,0),
('Sal Island',59,0),('Praia-Santiago Island',59,0),('Sao Vicente Island',59,0),('Bangui',60,0),('N`Djamena',61,0),('Moroni',62,0),('Abidjan',63,0),
('Goma',64,0),('Kinshasa',64,0),('Kisangani',64,0),('Lubumbashi',64,0),('Djibouti City',65,0),('Cairo',66,0),('Alexandria',66,0),('Hurghada',66,0),
('Sharm el-Sheikh',66,0),('Marsa Alam',66,0),('Malabo',67,0),('Asmara',68,0),('Manzini',69,0),('Addis Ababa',70,0),('Dire Dawa',70,0),
('Franceville',71,0),('Libreville',71,0),('Port-Gentil',71,0),('Banjul',72,0),('Accra',73,0),('Tamale',73,0),('Conakry',74,0),('Bissau',75,0),
('Bubaque',75,0),('Eldoret',76,0),('Mombasa',76,0),('Kisumu',76,0),('Nairobi',76,0),('Maseru',77,0),('Monrovia',78,0),('Benghazi',79,0),
('Sabha',79,0),('Tripoli',79,0),('Tajoura',79,0),('Antananarivo',80,0),('Nosy Be',80,0),('Toamasina',80,0),('Mahajanga',80,0),('Antsiranana',80,0),
('Blantyre',81,0),('Lilongwe',81,0),('Bamako',82,0),('Nouakchott',83,0),('Nouadhibou',83,0),('Plaine Magnien',84,0),('Dzaoudzi',85,0),
('Casablanca',86,0),('Marrakech',86,0),('Agadir',86,0),('Fes',86,0),('Rabat',86,0),('Maputo',87,0),('Nampula',87,0),('Pemba',87,0),('Beira',87,0),
('Tete',87,0),('Windhoek',88,0),('Walvis Bay',88,0),('Niamey',89,0),('Lagos',90,0),('Abuja',90,0),('Kano',90,0),('Port Harcourt',90,0),
('Brazzaville',91,0),('Pointe-Noire',91,0),('Saint-Denis',92,0),('Kigali',93,0),('Saint Helena',94,0),('São Tomé',95,0),('Dakar',96,0),
('Ndiass',96,0),('Victoria',97,0),('Freetown',98,0),('Bosaso',99,0),('Galkayo',99,0),('Garowe',99,0),('Hargeisa',99,0),('Mogadishu',99,0),
('Cape Town',100,0),('Durban',100,0),('Johannesburg',100,0),('Nelspruit',100,0),('Juba',101,0),('Khartoum',102,0),('Port Sudan',102,0),
('Dar es Salaam',103,0),('Hai District',103,0),('Unguja-Zanzibar',103,0),('Lomé',104,0),('Tunis',105,0),('Enfidha',105,0),('Djerba',105,0),
('Monastir',105,0),('Sfax',105,0),('Entebbe',106,0),('Livingstone',107,0),('Lusaka',107,0),('Ndola',107,0),('Harare',108,0),
('Victoria Falls',108,0),('Bulawayo',108,0),

/*American Cities (Non US)*/
('The Valley',109,0),('Antigua',110,0),('Buenos Aires',111,0),('Córdoba',111,0),('Mendoza',111,0),('San Carlos de Bariloche',111,0),
('Oranjestad',112,0),('Nassau',113,0),('Freeport',113,0),('Marsh Harbour',113,0),('Christ Church',114,0),('Belize City',115,0),
('St. George`s, Bermuda',116,0),('La Paz',117,0),('Santa Cruz de la Sierra',117,0),('São Paulo',118,0),('Rio de Janeiro',118,0),('Brasília',118,0),
('Campinas',118,0),('Belo Horizonte',118,0),('Road Town',119,0),('Calgary',120,0),('Edmonton',120,0),('Halifax',120,0),('Montreal',120,0),
('Ottawa',120,0),('Toronto',120,0),('Vancouver',120,0),('Winnipeg',120,0),('Kralendijk-Bonaire',121,0),('Oranjestad-Sint Eustatius',121,0),
('Saba',121,0),('Cayman Brac',122,0),('Georgetown',122,0),('Santiago de Chile',123,0),('Puerto Montt',123,0),('Antofagasta',123,0),
('Punta Arenas',123,0),('Concepción',123,0),('Bogotá',124,0),('Medellín',124,0),('Cartagena de Indias',124,0),('Cali',124,0),
('Barranquilla',124,0),('Liberia',125,0),('San José de Costa Rica',125,0),('Havana',126,0),('Santa Clara',126,0),('Varadero',126,0),
('Holguín',126,0),('Willemstad',127,0),('Roseau',128,0),('Punta Cana',129,0),('Santo Domingo',129,0),('Santiago de los Caballeros',129,0),
('San Felipe de Puerto Plata',129,0),('La Romana',129,0),('Quito',130,0),('Guayaquil',130,0),('Cuenca',130,0),('Manta',130,0),
('San Salvador',131,0),('Mount Pleasant',132,0),('Cayenne',133,0),('Kangerlussuaq',134,0),('Nuuk',134,0),('Ilulissat',134,0),('Narsarsuaq',134,0),
('Grenada',135,0),('Pointe-à-Pitre',136,0),('Flores',137,0),('Guatemala City',137,0),('Georgetown',138,0),('Cap-Haïtien',139,0),
('Port-au-Prince',139,0),('La Ceiba',140,0),('Roatán',140,0),('San Pedro Sula',140,0),('Tegucigalpa',140,0),('Kingston',141,0),
('Montego Bay',141,0),('Fort-de-France',142,0),('Cancún',143,0),('Guadalajara',143,0),('Los Cabos',143,0),('Mexico City',143,0),
('Monterrey',143,0),('Puerto Vallarta',143,0),('Tijuana',143,0),('Brades',144,0),('Managua',145,0),('Bluefields',145,0),('Corn Island',145,0),
('Puerto Cabezas',145,0),('Bocas del Toro',146,0),('David, Chiriquí',146,0),('Panama City',146,0),('Asunción',147,0),('Ciudad del Este',147,0),
('Arequipa',148,0),('Cusco',148,0),('Lima',148,0),('Aguadilla',149,0),('San Juan',149,0),('St. Jean',150,0),('Saint Kitts',151,0),
('Vieux Fort Quarter',152,0),('Saint-Pierre',153,0),('Kingstown',154,0),('Canouan',154,0),('Philipsburg',155,0),('Paramaribo',156,0),
('Port of Spain',157,0),('Tobago',157,0),('Providenciales',158,0),('Montevideo',159,0),('Punta del Este',159,0),('Saint Thomas',160,0),
('Saint Croix',160,0),('Caracas',161,0),('Maracaibo',161,0),('Valencia',161,0),

/*Asian Cities*/
('Herat',162,0),('Kabul',162,0),('Kandahar',162,0),('Mazar-i-Sharif',162,0),('Manama',163,0),('Chittagong',164,0),('Dhaka',164,0),
('Sylhet',164,0),('Paro',165,0),('Bandar Seri Begawan',166,0),('Phnom Penh',167,0),('Siem Reap',167,0),('Sihanoukville',167,0),
('Beijing',168,0),('Chengdu',168,0),('Chongqing',168,0),('Guangzhou',168,0),('Hangzhou',168,0),('Hong Kong',168,0),('Kunming',168,0),
('Shanghai',168,0),('Shenzhen',168,0),('Xi`an',168,0),('Dili',169,0),('Ahmedabad',170,0),('Bangalore',170,0),('Chennai',170,0),
('Cochin',170,0),('Goa',170,0),('Hyderabad',170,0),('Jaipur',170,0),('Kolkata',170,0),('Mumbai',170,0),('Pune',170,0),
('Denpasar',171,0),('Jakarta',171,0),('Makassar',171,0),('Medan',171,0),('Surabaya',171,0),('Isfahan',172,0),('Mashhad',172,0),
('Shiraz',172,0),('Tehran',172,0),('Tehran',172,0),('Al Najaf',173,0),('Baghdad',173,0),('Basra',173,0),('Erbil',173,0),
('Mosul',173,0),('Eilat',174,0),('Tel Aviv',174,0),('Chiba',175,0),('Fukuoka',175,0),('Hiroshima',175,0),('Kagoshima',175,0),
('Nagasaki',175,0),('Nagoya',175,0),('Osaka',175,0),('Sapporo',175,0),('Sendai',175,0),('Tokyo',175,0),('Amman',176,0),
('Aqaba',176,0),('Aktau',177,0),('Almaty',177,0),('Astana',177,0),('Kuwait City',178,0),('Bishkek',179,0),('Osh',179,0),
('Luang Prabang',180,0),('Pakse',180,0),('Vientiane',180,0),('Beirut',181,0),('Macau',182,0),('Johor Bahru',183,0),
('Kota Kinabalu',183,0),('Kuala Lumpur',183,0),('Kuching',183,0),('Penang',183,0),('Addu City',184,0),('Hulhule Island',184,0),
('Ulan Bator',185,0),('Mandalay',186,0),('Naypyidaw',186,0),('Yangon',186,0),('Kathmandu',187,0),('Nijgadh',187,0),
('Pokhara',187,0),('Siddharthanagar',187,0),('Pyongyang',188,0),('Muscat',189,0),('Salalah',189,0),('Islamabad',190,0),
('Karachi',190,0),('Lahore',190,0),('Multan',190,0),('Peshawar',190,0),('Angeles',191,0),('Davao City',191,0),
('Iloilo City',191,0),('Lapu-Lapu',191,0),('Manila',191,0),('Doha',192,0),('Dammam',193,0),('Jeddah',193,0),('Medina',193,0),
('Riyadh',193,0),('Singapore',194,0),('Busan',195,0),('Cheongju',195,0),('Incheon',195,0),('Jeju',195,0),('Seoul',195,0),
('Colombo',196,0),('Hambantota',196,0),('Aleppo',197,0),('Damascus',197,0),('Latakia',197,0),('Kaohsiung',198,0),
('Taichung',198,0),('Taipei',198,0),('Taoyuan',198,0),('Dushanbe',199,0),('Khujand',199,0),('Bangkok',200,0),('Chiang Mai',200,0),
('Hat Yai',200,0),('Krabi',200,0),('Phuket',200,0),('Ashgabat',201,0),('Mary',201,0),('Turkmenbashi',201,0),('Abu Dhabi',202,0),
('Al Ain',202,0),('Dubai',202,0),('Ras Al Khaimah',202,0),('Sharjah',202,0),('Namangan',203,0),('Tashkent',203,0),
('Da Nang',204,0),('Hanoi',204,0),('Ho Chi Minh City',204,0),('Nha Trang',204,0),('Phu Quoc',204,0),('Aden',205,0),
('Sana`a',205,0),

/*Oceania Cities*/
('Pago Pago',206,0),('Adelaide',207,0),('Brisbane',207,0),('Melbourne',207,0),('Perth',207,0),('Sydney',207,0),
('Christmas Island',208,0),('Keeling',209,0),('Rarotonga',210,0),('Hanga Roa',211,0),('Chuuk',212,0),('Kosrae',212,0),
('Pohnpei',212,0),('Yap',212,0),('Nadi',213,0),('Suva',213,0),('Papeete',214,0),('Hagåtña',215,0),('Kiritimati',216,0),
('Tarawa',216,0),('Kwajalein',217,0),('Majuro',217,0),('Yaren',218,0),('Nouméa',219,0),('Auckland',220,0),('Christchurch',220,0),
('Dunedin',220,0),('Queenstown',220,0),('Wellington',220,0),('Alofi',221,0),('Norfolk Island',222,0),('Rota Island',223,0),
('Saipan Island',223,0),('Tinian Island',223,0),('Koror',224,0),('Port Moresby',225,0),('Apia',226,0),('Honiara',227,0),
('Nukuʻalofa',228,0),('Vavaʻu',228,0),('Funafuti',229,0),('Luganville',230,0),('Port Vila',230,0),('Futuna',231,0),
('Wallis',231,0);

INSERT INTO [dbo].[Weather] (WeatherType) VALUES
('Clear Skies'), ('Cloudy'), ('Humid'), ('Rainy'), ('Snowy'), ('Sunny');

/*	Weather Id + Type
	1 - Clear Skies
	2 - Cloudy
	3 - Humid
	4 - Rainy
	5 - Snowy
	6 - Sunny */

INSERT INTO [dbo].[States] (LocationID, FoodCost, FoodItemOne, FoodItemTwo, FoodItemThree, ActivityItemOne, ActivityItemTwo, ActivityItemThree, SpringTemp, SummerTemp, FallTemp, WinterTemp, SpringWeather, SummerWeather, FallWeather, WinterWeather, CrimeRate) VALUES
(1, 34.85,'Fried Chicken', 'Ribs', 'Baked Grits', 'U.S. Space & Rocket Center', 'Birmingham Civil Rights Institute', 'USS Alabama Battleship Memorial Park', 77, 91, 78, 59, 4, 3, 2, 4, 524.2), 
(2, 43.05, 'Grilled Salmon', 'King Crab', 'Reindeer Sausage', 'Denali National Park and Preserve', 'Glacier Bay National Park and Preserve', 'Northern Lights', 44, 64, 41, 25, 6, 2, 2, 6, 829), 
(3, 39.36, 'Chimichangas', 'Sonoran Hot Dogs', 'Carne Seca Plate', 'Grand Canyon', 'Monument Valley', 'Antelope Canyon', 85, 104, 88, 68, 6, 6, 6, 6, 508),
(4, 35.26, 'Hubcap Burger', 'Eggplant Casserole', 'Barbecue Pork', 'Hot Springs National Park', 'Blanchard Springs Caverns', 'Crystal Bridges Museum of American Art', 72, 84, 66, 43, 2, 3, 2, 4, 554.9), 
(5, 45.92, 'Carne Asada Burrito', 'Fish Tacos', 'In-N-Out Double-Double', 'Disneyland', 'Death Valley National Park', 'Golden Gate Bridge', 72, 84, 66, 43, 6, 6, 6, 2, 449.3), 
(6, 42.23, 'Lamb Fondue', 'Rocky Mountain Oysters', 'Lobster Macaroni and Cheese', 'Rocky Mountain National Park', 'Mesa Verde National Park', 'Aspen', 62, 86, 65, 45, 1, 6, 1, 1, 368.1), 
(7, 44.28, 'White Clam Pizza', 'Ricotta Gnocchi', 'Steamed Cheeseburger', 'Mark Twain House & Museum', 'Mohegan Sun', 'Mystic Seaport Museum', 57, 80, 63, 39, 1, 3, 2, 2, 228), 
(8, 41.00, 'Chicken and Slippery Dumplings', 'Blue Claw Crabs', 'Grotto Pizza', 'Winterthur', 'Cape Henlopen','Hagley Museum', 65, 85, 68, 45, 2, 3, 2, 1, 453.4), 
(9, 40.59, 'Stone Crabs', 'Cuban Sandwich', 'Gator Tail', 'Disneyworld', 'Universal Orlando Resort', 'Florida Keys', 80, 91, 80, 65, 1, 3, 1, 1, 408), 
(10, 37.72, 'Chicken & Dumplings', 'Fried Chicken', 'Fried Green Tomatoes', 'Rock City Gardens', 'World of Coca-Cola', 'Georgia Aquarium', 64, 88, 73, 54, 2, 3, 1, 2, 357.2), 
(11, 47.56, 'Poke', 'Garlic Shrimp', 'Salted Dried Beef', 'Hawaii Volcanoes National Park', 'Pearl Harbor National Memorial', 'Kualoa Ranch & Zipline', 83, 88, 86, 80, 6, 3, 3, 6, 250.6), 
(12, 38.13, 'Ruby Red Trout', 'Finger Steaks', 'Idaho Spuds', 'Craters of the Moon National Monument & Preserve', 'Shoshone Falls Park', 'Oregon Trail', 63, 87, 64, 40, 2, 6, 2, 2, 226.4),
(13, 41.00, 'Bacon-Wrapped Chorizo-Stuffed Dates', 'Deep-Dish Pizza', 'Baby Back Ribs', 'Millennium Park', 'The Field Museum', 'Wrigley Field', 58, 82, 62, 34, 2, 3, 2, 4, 438.8), 
(14, 36.90, 'Biscuits and Gravy', 'Roast Beef', 'German Sausages', 'Indiana Dunes National Park', 'The Children''s Museum of Indianapolis', 'Indianapolis Zoo', 62, 83, 64, 38, 2, 3, 2, 2, 399), 
(15, 36.49, 'Breaded Pork Tenderloin', 'Taco Pizza', 'Panino', 'Adventureland Park', 'Amana Colonies', 'Maquoketa Caves State Park', 61, 84, 62, 33, 2, 3, 2, 4, 293.4), 
(16, 36.49, 'Barbecue Ribs', 'Chili and Cinnamon Rolls', 'Barbecue Hot Wings', 'Tallgrass Prairie National Preserve', 'Botanica, The Wichita Gardens', 'Dodge City', 66, 87, 67, 42, 1, 3, 1, 1, 413), 
(17, 35.67, 'Benedictine Sandwhich', 'Goetta', 'Burgoo Stew', 'Kentucky Bourbon Trail', 'Mammoth Cave National Park', 'Louisville Slugger Museum & Factory', 65, 85, 67, 43, 4, 3, 2, 2,225.8), 
(18, 36.90, 'Jambalaya', 'Ya-Ka-Mein', 'Fried Seafood Sub Sandwhich', 'French Quarter', 'Garden District', 'St. Louis Cathedral', 78, 90, 79, 63, 2, 3, 1, 1, 557), 
(19, 40.18, 'Clambake Oysters', 'Lobster Pie', 'Baked Beans & Hot Dogs','Acadia National Park', 'Casco Bay', 'Old Port', 52, 77, 57, 31, 2, 1, 2, 2,121), 
(20, 44.69, 'Rockfish', 'Crab Cakes', 'Lean Top Roast Beef','U.S. Naval Academy', 'Inner Harbour', 'Ocean City Beach', 64, 87, 68, 44, 2, 3, 1, 1, 500.2), 
(21, 43.46, 'Scallops', 'Corned Beef', 'Clam Chowder', 'USS Constitution', 'Freedom Trail', 'Martha''s Vineyard', 55, 79, 61, 38, 2, 6, 1, 1, 358), 
(22, 38.13, 'Detroit-Style Pizza', 'Meat Hand Pies', 'Greek Salad', 'Mackinac Island', 'Sleeping Bear Dunes National Lakeshore', 'Isle Royale National Park', 57, 80, 60, 32, 1, 6, 1, 2, 450), 
(23, 39.77, 'Swedish Meatballs', 'Wild Rice Soup', 'Chicken Casserole', 'Mall of America', 'The Boundary Waters Canoe Area Wilderness', 'Voyageurs National Park', 57, 82, 58, 28, 2, 4, 1, 6, 238.3), 
(24, 34.44, 'Catfish and Hush Puppies', 'Crawfish', 'Fried Chicken', 'Vicksburg National Military Park', 'Elvis Presley Birthplace', 'Beau Rivage Resort & Casino', 76, 91, 77, 58, 2, 3, 1, 1, 285.7),
(25, 36.08, 'Salami Sandwich', 'St. Louis-Style Pizza', 'Toasted Ravioli', 'Gateway Arch', 'Forest Park', 'Titanic Museum', 66, 86, 68, 42, 2, 3, 1, 2, 530.3), 
(26, 38.95, 'Chicken-Fried Steak', 'Prime Rib', 'Bison-Based Stew', 'Glacier National Park', 'Lake McDonald', 'Whitefish Mountain Resort', 57, 82, 58, 34, 2, 6, 1, 2, 377.1), 
(27, 36.90, 'Biscuits and Gravy', 'Macaroni and Cheese', 'Corned Beef Hash', 'Chimney Rock', 'Carhenge', 'Scotts Bluff National Monument', 62, 86, 63, 35, 2, 3, 1, 2, 305.9),
(28, 40.18, 'Elk Chops', 'Fry Bread Indian Tacos', 'Eggs Benedict', 'Las Vegas Strip', 'Hoover Dam', 'Red Rock Canyon National Conservation Area', 79, 101, 80, 59, 6, 6, 6, 6, 555.9), 
(29, 43.05, 'Lobster and Steamers ', 'French Toast', 'Gravy Covered French Fries', 'Mount Washington', 'Franconia Notch State Park', 'Mount Washington Cog Railway', 56, 80, 60, 34, 2, 1, 1, 2, 198.7), 
(30, 45.92, 'South Jersey Pizza', 'Sliders', 'Pork Roll Sandwich', 'Jersey Shore', 'Liberty State Park', 'Atlantic City Broadwalk', 61, 84, 65, 41, 2, 6, 1, 1, 228.8),
(31, 38.54, 'Blue Corn Pancakes', 'Green Chile Cheeseburger', 'Carne Adovada',  'White Sands National Monument', 'Chaco Culture National Historical Park', 'Carlsbad Caverns National Park', 65, 84, 66, 45, 1, 6, 1, 1, 783.5), 
(32, 46.33, 'Coal Oven Pizza', 'Hot Dogs', 'Bagels and Lox', 'Statue of Liberty', 'Central Park', 'Empire State Building', 60, 82, 65, 41, 2, 3, 1, 2, 356.7), 
(33, 36.90,'Lexington-Style Barbecue', 'She-Crab Bisque', 'Shrimp and Grits', 'Biltmore', 'Outer Banks', 'Tweetsie Railroad', 71, 87, 71, 53, 2, 3, 2, 4,363.7),  
(34, 37.72, 'Tater Tot Casserole ', 'Cheese Buttons', 'Knoephla Soup', 'Theodore Roosevelt National Park', 'Scandinavian Heritage Association', 'Fort Abraham Lincoln State Park', 55, 81, 56, 25, 4, 1, 2, 6, 281.3), 
(35, 36.08, 'Polish-Style Sausage Sandwhiches', 'Cincinnati Chili', 'Goetta Hash', 'Cedar Point', 'Cuyahoga Valley National Park', 'Cincinnati Zoo & Botanical Garden', 62, 83, 64, 39, 2, 3, 2, 6, 297.5),
(36, 36.49, 'Fried-Onion Burger', 'Pho Beef Broth and Rice Noodles', 'Chicken Waffles', 'National Cowboy & Western Heritage Museum', 'Bricktown', 'Beavers Bend State Park', 71, 91, 73, 52, 1, 3, 1, 1, 456.2),  
(37, 40.59, 'Veggie Burger', 'Albacore Tuna Melt', 'Bacon Maple Bar', 'Crater Lake National Park', 'Tillamook Cheese Factory', 'Enchanted Forest', 61, 79, 64, 48, 4, 6, 4, 4, 281.8), 
(38, 40.18, 'Scrapple', 'Philly Cheesesteak', 'Mushroom Strudel', 'Philadelphia Museum of Art', 'Hersheypark', 'Liberty Bell', 63, 85, 67, 43, 2, 3, 1, 4, 313.3), 
(39, 40.59, 'Pizza Strips', 'Clam Cakes', 'Johnnycakes', 'The Breakers', 'WaterFire', 'Ocean Drive Historic District',  58, 80, 63, 39, 2, 6, 1, 4, 232.2), 
(40, 36.49, 'Fried Seafood', 'Grits', 'Barbecue', 'Magnolia Plantation and Gardens', 'Broadway at the Beach', 'Fort Sumter National Monument', 76, 91, 76, 58, 2, 3, 1, 4, 506.2), 
(41, 35.67, 'Hot Beef Commercial', 'Indian Tacos', 'Meat-and-Vegetable Pies', 'Mount Rushmore National Memorial', 'Badlands National Park', 'Custer State Park', 55, 81, 56, 24, 2, 6, 2, 2, 433.6), 
(42, 36.49, 'Dry Ribs', 'Fried Pickles', 'Country Ham and Red-Eye Gravy', 'Graceland', 'Dollywood', 'Ruby Falls', 72, 90, 74, 52, 2, 3, 1, 4, 651.5),  
(43, 39.77, 'Brisket', 'Puffy Tacos', 'Chili', 'Big Bend National Park', 'San Antonio River Walk', 'The Alamo', 79, 95, 81, 63, 1, 3, 6, 1, 438.9), 
(44, 39.77, 'Potato Tots', 'Corn Chowder', 'Wild Game Chili', 'Zion National Park', 'Bryce Canyon National Park', 'Great Salt Lake', 61, 87, 64, 40, 2, 6, 1, 2, 238.9), 
(45, 41.82, 'Chicken Pot Pie', 'Chili Dogs', 'Slow-Roasted Lamb Shanks', 'Killington Ski Area', 'Sugarbush Resort', 'Shelburne Farms', 54, 78, 57, 30, 2, 6, 1, 2, 165.8),  
(46, 41.82, 'Rappahannock Oysters', 'Ham & Biscuits', 'Brunswick Stew', 'Shenandoah National Park', 'Appalachian Trail Access', 'Arlington National Cemetery', 69, 88, 71, 49, 2, 3, 1, 2, 208.2), 
(47, 43.05, 'Smoked Sockeye Salmon', 'Oyster Stew', 'Beechers Mac & Cheese', 'Museum of Pop Culture', 'Space Needle', 'Mount St. Helens', 59, 74, 60, 47, 4, 6, 4, 4, 304.5), 
(48, 36.08, 'Pepperoni Rolls', 'Baked Steak and Gravy', 'Buckwheat Pancakes', 'The Greenbrier', 'New River Gorge National Park', 'Blackwater Falls State Park', 66, 83, 67, 45, 2, 3, 1, 2, 350.7),  
(49, 38.13, 'Bratwurst', 'Roesti and Cheese Fondue', 'Swedish Pancakes', 'Milwaukee Art Museum', 'Devil''s Lake State Park', 'The House on the Rock', 53, 77, 58, 31, 2, 6, 1, 2, 319.9), 
(50, 39.36, 'Spicy Elk Sausage', 'Butterflied Trout', 'Biscuits and Gravy', 'Devils Tower National Monument', 'Jackson Hole', 'Yellowstone National Park', 55, 79, 59, 39, 2, 6, 1, 2, 237.5);

INSERT INTO [dbo].[Climates] (ClimateType) VALUES
('Semiarid Steppe'), ('Humid subtropical'), ('Marine westcoast'), ('Mediterranean'), ('Humid continental warm summer'), ('Humid continental cold summer'), ('Highland'), ('Tropical wet/dry'), ('Desert'), ('Tundra');

INSERT INTO [dbo].[Geographies] (GeographyType) VALUES
('Mountain'), ('River'), ('Lake'), ('Forest'), ('Coast'), ('Plain'), ('Desert'), ('Mesa'), ('Swamp'), ('Plateau'), ('Valley'), ('Bay'), ('Marsh'), ('Bayou'), ('Canyon'), ('Cave');

/* 1: Mountain 2: River 3: Lake 4: Forest 5: Coast 6: Prairie 7: Desert 8: Mesa 9: Swamp 10: Plateau */

INSERT INTO [dbo].[StateClimates] (StateID, ClimateID) VALUES
(1, 2), (2, 10), (3, 1), (3, 7), (3, 9), (4, 2), (5, 4), (5, 7), (5, 9), (6, 1), (6, 7), (6, 9),  (7, 5), (8, 2), (9, 2), (9, 8), (10, 2), (11, 8), (12, 1), (12, 7), (13, 5), (13, 2), (14, 5), (14, 2), (15, 5), (15, 6), (16, 1), (16, 2), (16, 5), (17, 2), (17, 5), (18, 2), (19, 6), (20, 2), (20, 5), (21, 6), (22, 5), (22, 6), (23, 6), (24, 2), (25, 2), (25, 5), (26, 1), (26, 7), (27, 1), (27, 5), (27, 6), (28, 1), (28, 7), (28, 9), (29, 5), (29, 6), (30, 2), (30, 5), (31, 1), (31, 7), (31, 9), (32, 5), (32, 6), (33, 2), (33, 5), (34, 1), (34, 6), (35, 2), (35, 5), (36, 1), (36, 2), (37, 3), (37, 1), (37, 7), (38, 5), (39, 5), (40, 2), (41, 1), (41, 6), (42, 2), (42, 5), (43, 1), (43, 2), (44, 1), (44, 7), (44, 9), (45, 5), (45, 6), (46, 2), (46, 5), (47, 1), (47, 3), (47, 7), (48, 5), (49, 5), (49, 6), (50, 1), (50, 7);

INSERT INTO [dbo].[StateGeographies] (StateID, GeographyID) VALUES
(1, 1), (1, 2), (1, 3), (1, 5), (1, 14), (1, 11), 
(2, 1), (2, 2), (2, 5), (2, 12), (2, 3), (2, 4),
(3, 1), (3, 2), (3, 7), (3, 8), (3, 10), (3, 15),
(4, 1), (4, 2), (4, 10), (4, 4), (4, 3), (4, 14),
(5, 1), (5, 2), (5, 3), (5, 4), (5, 5), (5, 7), (5, 6), 
(6, 1), (6, 2), (6, 6), (6, 7), (6, 8), (6, 3),
(7, 2), (7, 5), (7, 4), (7, 1), (7, 16), (7, 12),
(8, 2), (8, 5), (8, 4), (8, 12), (8, 9), (8, 10), (8, 6),
(9, 2), (9, 5), (9, 3), (9, 9), (9, 4), (9, 12),
(10, 1), (10, 2), (10, 5), (10, 4), (10, 9), (10, 3),
(11, 1), (11, 5), (11, 15), (11, 2), (11, 11), (11, 4),
(12, 1), (12, 2), (12, 3), (12, 4), (12, 7), (12, 15),
(13, 2), (13, 3), (13, 4), (13, 6), (13, 9), (13, 16),
(14, 2), (14, 3), (14, 4), (14, 6), (14, 9), (14, 16),
(15, 2), (15, 3), (15, 4), (15, 6), (15, 10), (15, 16),
(16, 6), (16, 4), (16, 2), (16, 1), (16, 3), (16, 15), 
(17, 2), (17, 4), (17, 3), (17, 10), (17, 16), (17, 9),
(18, 2), (18, 3), (18, 5), (18, 4), (18, 9), (18, 14),
(19, 1), (19, 2), (19, 3), (19, 4), (19, 5), (19, 12),
(20, 1), (20, 2), (20, 5), (20, 4), (20, 16), (20, 12),
(21, 1), (21, 2), (21, 3), (21, 4), (21, 5), (21, 12),
(22, 2), (22, 3), (22, 4), (22, 1), (22, 12), (22, 16),
(23, 1), (23, 2), (23, 4), (23, 3), (23, 16), (23, 11),
(24, 2), (24, 3), (24, 4), (24, 6), (24, 5), (24, 9),
(25, 2), (25, 3), (25, 4), (25, 10), (25, 16), (25, 1),
(26, 1), (26, 2), (26, 3), (26, 4), (26, 6), (26, 16),
(27, 2), (27, 6), (27, 1), (27, 4), (27, 13), (27, 3), 
(28, 1), (28, 8), (28, 3), (28, 2), (28, 7), (28, 16),
(29, 1), (29, 2), (29, 3), (29, 4), (29, 5), (29, 11),
(30, 2), (30, 5), (30, 4), (30, 9), (30, 10), (30, 1),
(31, 1), (31, 2), (31, 4), (31, 6), (31, 7), (31, 15),
(32, 1), (32, 2), (32, 3), (32, 4), (32, 5), (32, 11),
(33, 1), (33, 2), (33, 3), (33, 4), (33, 5), (33, 9),
(34, 2), (34, 3), (34, 6), (34, 4), (34, 11), (34, 15), 
(35, 2), (35, 3), (35, 4), (35, 10), (35, 16), (35, 6),
(36, 2), (36, 3), (36, 4), (36, 10), (36, 6), (36, 16),
(37, 1), (37, 2), (37, 3), (37, 4), (37, 5), (37, 7), 
(38, 1), (38, 2), (38, 4), (38, 16), (38, 6), (38, 11),
(39, 2), (39, 5), (39, 4), (39, 12), (39, 13), (39, 9),
(40, 1), (40, 2), (40, 3), (40, 4), (40, 5), (40, 9), 
(41, 1), (41, 2), (41, 3), (41, 4), (41, 6), (41, 16),
(42, 1), (42, 2), (42, 4), (42, 16), (42, 9), (42, 10),
(43, 1), (43, 2), (43, 4), (43, 7), (43, 6), (43, 5), 
(44, 1), (44, 2), (44, 3), (44, 4), (44, 7), (44, 8), (44, 10),
(45, 1), (45, 2), (45, 3), (45, 4), (45, 11), (45, 13),
(46, 1), (46, 2), (46, 5), (46, 4), (46, 16), (46, 12),
(47, 1), (47, 2), (47, 4), (47, 5), (47, 6), (47, 10), (47, 7), 
(48, 1), (48, 2), (48, 4), (48, 10), (48, 16), (48, 15),
(49, 2), (49, 3), (49, 4), (49, 6), (49, 16), (49, 11),
(50, 1), (50, 2), (50, 4), (50, 16), (50, 6), (50, 10);


/* US International Airports */
INSERT INTO [dbo].[Airports] (AirportCode, LocationID) VALUES
/* The States with only 1 International Airport */
('LIT', 4), ('DEN', 6), ('BDL', 7), ('ILG', 8), ('BOI', 12), 
('DSM', 15), ('ICT', 16), ('BWI', 20), ('BOS', 21), ('OMA', 27),
('LAS', 28), ('OKC', 36), ('PDX', 37), ('PVD', 39), ('FSD', 41),
('SLC', 44), ('BTV', 45), ('CRW', 48);

INSERT INTO [dbo].[Airports] (AirportCode, Direction, LocationID) VALUES
/* Alabama's International Airports */
('HSV', 'N', 1),
('BHM', 'C', 1),
/* Alaska's International Airports */
('FAI', 'N', 2), 
('ANC', 'C', 2),
('PML', 'SW', 2), ('JNU', 'S', 2), ('KTN', 'SE', 2),
/* Arizona's International Airports */
('IFP', 'NW', 3),
('PHX', 'C', 3), 
('YUM', 'SW', 3), ('TUS', 'SE', 3),
/* California's International Airports */
('SFO', 'NW', 5), ('SJC', 'N', 5), ('SMF', 'NE', 5),
('LAX', 'WC', 5), ('FAT', 'C', 5), ('SBD', 'EC', 5),
('SAN', 'SW', 5), ('ONT', 'S', 5), ('PSP', 'SE', 5), 
/* Florida's International Airports */
('PNS', 'NW', 9), ('TLH', 'N', 9), ('DAB', 'NE', 9),  
('TPA', 'WC', 9), ('MCO', 'C', 9), ('FLL', 'EC', 9),
('RSW', 'SW', 9), ('EYW', 'S', 9), ('MIA', 'SE', 9),
/* Georgia's International Airports */
('ATL', 'NW', 10), 
('SAV', 'EC', 10),
/* Hawaii's International Airports */
('HNL', 'NW', 11), 
('KOA', 'SW', 11), ('ITO', 'SE', 11),
/* Illinois's International Airports */
('MLI', 'NW', 13), ('RFD', 'N', 13), ('ORD', 'NE', 13),
('MDW', 'WC', 13), ('PIA', 'C', 13),
/* Indiana's International Airports */
('SBN', 'NW', 14), ('FWA', 'NE', 14),
('IND', 'C', 14),
/* Kentucky's International Airports */
('CVG', 'N', 17), 
('SDF', 'C', 17),
/* Louisiana's International Airports */
('AEX', 'N', 18),
('MSY', 'S', 18),
/* Maine's International Airports */
('BGR', 'EC', 19),
('PWM', 'S', 19),
/* Michigan's International Airports */
('SAW', 'NW', 22), ('MBS', 'N', 22), ('CIU', 'NE', 22),
('GRR', 'WC', 22), ('LAN', 'C', 22), ('FNT', 'EC', 22), 
('AZO', 'SW', 22), ('DTW', 'S', 22),
/* Minnesota's International Airports */
('INL', 'N', 23), ('DLH', 'NE', 23),
('MSP', 'C', 23), 
('RST', 'S', 23),
/* Mississippi's International Airports */
('JAN', 'C', 24), 
('GPT', 'S', 24),
/* Missouri's International Airports */
('MCI', 'NW', 25),
('STL', 'WC', 25),
/* Montana's International Airports */
('GPI', 'NW', 26), ('GTF', 'N', 26),
('MSO', 'WC', 26),
('BZN', 'S', 26), ('BIL', 'SE', 26), 
/* New Hampshire's International Airports */
('MHT', 'S', 29), ('PSM', 'SE', 29),
/* New Jersey's International Airports */
('EWR', 'N', 30), 
('ACY', 'S', 30),
/* New Mexico's International Airports */
('ABQ', 'NW', 31), 
('ROW', 'EC', 31),
/* New York's International Airports */
('PBG', 'NE', 32), ('ART', 'NW', 32),
('BUF', 'WC', 32), ('SYR', 'C', 32), ('ALB', 'EC', 32),
('SWF', 'S', 32), ('JFK', 'SE', 32), 
/* North Carolina's International Airports */
('GSO', 'N', 33), ('RDU', 'NE', 33),
('CLT', 'C', 33), ('ILM', 'EC', 33),
/* North Dakota's International Airports */
('ISN', 'NW', 34), ('MOT', 'N', 34), ('GFK', 'NE', 34),
('DIK', 'SW', 34), ('FAR', 'SE', 34),
/* Ohio's International Airports */
('CLE', 'N', 35),
('CMH', 'C', 35),
('DAY', 'SW', 35),
/* Pennsylvania's International Airports */
('ERI', 'NW', 38), ('AVP', 'NE', 38),
('ABE', 'EC', 38),
('PIT', 'SW', 38), ('MDT', 'S', 38), ('PHL', 'SE', 38), 
/* South Carolina's International Airports */
('GSP', 'NW', 40),
('MYR', 'EC', 40),
('CHS', 'S', 40),  
/* Tennessee's International Airports */
('BNA', 'C', 42), 
('MEM', 'SW', 42),
/* Texas's International Airports */
('LBB', 'NW', 43), ('AMA', 'N', 43), ('DFW', 'NE', 43), 
('ELP', 'WC', 43), ('MAF', 'C', 43), ('SAT', 'EC', 43),
('MFE', 'SE', 43), ('BRO', 'SW', 43),
/* Virginia's International Airports */
('IAD', 'N', 46), ('DCA', 'NE', 46),
('RIC', 'C', 46), 
('ORF', 'SE', 46),
/* Washington's International Airports */
('BLI', 'NW', 47),
('SEA', 'WC', 47), ('GEG', 'EC', 47),
/* Wisconsin's International Airports */
('GRB', 'NE', 49),
('ATW', 'EC', 49),
('MKE', 'SE', 49),
/* Wyoming's International Airports */
('JAC', 'WC', 50), ('CPR', 'C', 50);

/* Other Country's Airports */
INSERT INTO [dbo].[Airports] (AirportCode, LocationID) VALUES
/* European Airports */
('TIA',51),('LWN',52),('EVN',53),('GRZ',54),('INN',55),('LNZ',56),('SZG',57),('VIE',58),('GYD',59),
('KVD',60),('LLK',61),('NAJ',62),('GBB',63),('GME',64),('GNA',65),('MSQ',66),('ANR',67),('BRU',68),
('LGG',69),('BNX',70),('OMO',71),('SJJ',72),('TZL',73),('BOJ',74),('PDV',75),('SOF',76),('VAR',77),
('DBV',78),('PUY',79),('SPU',80),('ZAD',81),('ZAG',82),('LCA',83),('PFO',84),('ECN',85),('BRQ',86),
('KLV',87),('OSR',88),('PED',89),('PRG',90),('AAL',91),('AAR',92),('BLL',93),('CPH',94),('TLL',95),
('TAY',96),('FAE',97),('HEL',98),('OUL',99),('RVN',100),('TMP',101),('TKU',102),('LYS',103),('MRS',104),
('NCE',105),('CDG',106),('TLS',107),('BUS',108),('KUT',109),('TBS',110),('TXL',111),('DUS',112),('FRA',113),('HAM',114),
('MUC',115),('GIB',116),('ATH',117),('CHQ',118),('HER',119),('RHO',120),('SKG',121),('ACI',122),('GCI',123),('BUD',124),
('DEB',125),('QGY',126),('SOB',127),('QPJ',128),('AEY',129),('KEF',130),('ORK',131),('DUB',132),('SNN',133),('KIR',134),
('NOC',135),('SNN',136),('IOM',137),('BGY',138),('MXP',139),('LIN',140),('FCO',141),('VCE',142),('JER',143),('PRN',144),
('RIX',145),('VNT',146),('KUN',147),('PLQ',148),('SQQ',149),('VNO',150),('LUX',151),('MLA',152),('TGD',153),('TIV',154),
('AMS',155),('EIN',156),('GRQ',157),('MST',158),('RTM',159),('OHD',160),('SKP',161),('BGO',162),('OSL',163),('SVG',164),
('TOS',165),('TRD',166),('GDN',167),('KTW',168),('KRK',169),('WAW',170),('WRO',171),('FAO',172),('FNC',173),('LIS',174),
('PDL',175),('OPO',176),('KIV',177),('OTP',178),('CLJ',179),('IAS',180),('SBZ',181),('TSR',182),('DME',183),('OVB',184),
('LED',185),('AER',186),('SVX',187),('BEG',188),('INI',189),('BTS',190),('KSC',191),('PZY',192),('TAT',193),('SLD',194),
('LJU',195),('MBX',196),('POW',197),('BCN',198),('LPA',199),('MAD',200),('AGP',201),('PMI',202),('GOT',203),('LLA',204),
('MMX',205),('ARN',206),('BMA',207),('BSL',208),('BRN',209),('GVA',210),('ACH',211),('ZRH',212),('ADA',213),('ESB',214),
('AYT',215),('SAW',216),('ADB',217),('HRK',218),('KBP',219),('LWO',220),('ODS',221),('OZH',222),('BHX',223),('EDI',224),
('GLA',225),('LHR',226),('MAN',227),

/* African Airports */
('BJA',228),('ALG',229),('LAD',230),('SSD',231),('COO',232),('GBE',233),('MUB',234),('FRW',235),('BBK',236),('BOY',237),
('OUA',238),('BJM',239),('DLA',240),('NSI',241),('BVC',242),('SID',243),('RAI',244),('VXE',245),('BGF',246),('NDJ',247),
('HAH',248),('ABJ',249),('GOM',250),('FIH',251),('FKI',252),('FBM',253),('JIB',254),('CAI',255),('HBE',256),('HRG',257),
('SSH',258),('RMF',259),('SSG',260),('ASM',261),('SHO',262),('ADD',263),('DIR',264),('MVB',265),('LBV',266),('POG',267),
('BJL',268),('ACC',269),('TML',270),('CKY',271),('OXB',272),('BQE',273),('EDL',274),('MBA',275),('KIS',276),('NBO',277),
('MSU',278),('ROB',279),('BEN',280),('SEB',281),('TIP',282),('MJI',283),('TNR',284),('NOS',285),('TMM',286),('MJN',287),
('DIE',288),('BLZ',289),('LLW',290),('BKO',291),('NKC',292),('NDB',293),('MRU',294),('DZA',295),('CMN',296),('RAK',297),
('AGA',298),('FEZ',299),('RBA',300),('MPM',301),('APL',302),('POL',303),('BEW',304),('TET',305),('WDH',306),('WVB',307),
('NIM',308),('LOS',309),('ABV',310),('KAN',311),('PHC',312),('BZV',313),('PNR',314),('RUN',315),('KGL',316),('HLE',317),
('TMS',318),('DKR',319),('DSS',320),('SEZ',321),('FNA',322),('BSA',323),('GBY',324),('GGR',325),('HGA',326),('MGQ',327),
('CPT',328),('DUR',329),('JNB',330),('MQP',331),('JUB',332),('KRT',333),('PZU',334),('DAR',335),('JRO',336),('ZNZ',337),
('LFW',338),('TUN',339),('NBE',340),('DJE',341),('MIR',342),('SFA',343),('EBB',344),('LVI',345),('LUN',346),('NLA',347),
('HRE',348),('VFA',349),('BUQ',350),

/*American Airports (Non US)*/
('AXA',351),('ANU',352),('AEP',353),('COR',354),('MDZ',355),('BRC',356),('AUA',357),('NAS',358),('FPO',359),('MHH',360),
('BGI',361),('BZE',362),('BDA',363),('LPB',364),('VVI',365),('GRU',366),('GIG',367),('BSB',368),('VCP',369),('CNF',370),
('EIS',371),('YYC',372),('YEG',373),('YHZ',374),('YUL',375),('YOW',376),('YYZ',377),('YVR',378),('YWG',379),('BON',380),
('EUX',381),('SAB',382),('CYB',383),('GCM',384),('SCL',385),('PMC',386),('ANF',387),('PUQ',388),('CCP',389),('BOG',390),
('MDE',391),('CTG',392),('CLO',393),('BAQ',394),('LIR',395),('SJO',396),('HAV',397),('SNU',398),('VRA',399),('HOG',400),
('CUR',401),('DOM',402),('PUJ',403),('SDQ',404),('STI',405),('POP',406),('LRM',407),('UIO',408),('GYE',409),('CUE',410),
('MEC',411),('SAL',412),('MPN',413),('CAY',414),('SFJ',415),('GOH',416),('JAV',417),('UAK',418),('GND',419),('PTP',420),
('FRS',421),('GUA',422),('GEO',423),('CAP',424),('PAP',425),('LCE',426),('RTB',427),('SAP',428),('TGU',429),('KIN',430),
('MBJ',431),('FDF',432),('CUN',433),('GDL',434),('SJD',435),('MEX',436),('MTY',437),('PVR',438),('TIJ',439),('MNI',440),
('MGA',441),('BEF',442),('RNI',443),('PUZ',444),('BOC',445),('DAV',446),('PTY',447),('ASU',448),('AGT',449),('AQP',450),
('CUZ',451),('LIM',452),('BQN',453),('SJU',454),('SBH',455),('SKB',456),('UVF',457),('FSP',458),('SVD',459),('CIW',460),
('SXM',461),('PBM',462),('POS',463),('TAB',464),('PLS',465),('MVD',466),('PDP',467),('STT',468),('STX',469),('CCS',470),
('MAR',471),('VLN',472),

/*Asian Airports*/
('HEA',473),('KBL',474),('KDH',475),('MZR',476),('BAH',477),('CGP',478),('DAC',479),('ZYL',480),('PBH',481),('BWN',482),
('PNH',483),('REP',484),('KOS',485),('PEK',486),('CTU',487),('CKG',488),('CAN',489),('HGH',490),('HKG',491),('KMG',492),
('PVG',493),('SZX',494),('XIY',495),('DIL',496),('AMD',497),('BLR',498),('MAA',499),('COK',500),('GOI',501),('HYD',502),
('JAI',503),('CCU',504),('BOM',505),('PNQ',506),('DPS',507),('CGK',508),('UPG',509),('KNO',510),('SUB',511),('IFN',512),
('MHD',513),('SYZ',514),('THR',515),('IKA',516),('NJF',517),('BGW',518),('BSR',519),('EBL',520),('OSM',521),('VDA',522),
('TLV',523),('NRT',524),('FUK',525),('HIJ',526),('KOJ',527),('NGS',528),('NGO',529),('KIX',530),('CTS',531),('SDJ',532),
('HND',533),('AMM',534),('AQJ',535),('SCO',536),('ALA',537),('TSE',538),('KWI',539),('FRU',540),('OSS',541),('LPQ',542),
('PKZ',543),('VTE',544),('BEY',545),('MFM',546),('JHB',547),('BKI',548),('KUL',549),('KCH',550),('PEN',551),('GAN',552),
('MLE',553),('ULN',554),('MDL',555),('NYT',556),('RGN',557),('KTM',558),('NIJ',559),('PKR',560),('BWA',561),('FNJ',562),
('MCT',563),('SLL',564),('ISB',565),('KHI',566),('LHE',567),('MUX',568),('PEW',569),('CRK',570),('DVO',571),('ILO',572),
('CEB',573),('MNL',574),('DOH',575),('DMM',576),('JED',577),('MED',578),('RUH',579),('SIN',580),('PUS',581),('CJJ',582),
('ICN',583),('CJU',584),('GMP',585),('CMB',586),('HRi',587),('ALP',588),('DAM',589),('LTK',590),('KHH',591),('RMQ',592),
('TSA',593),('TPE',594),('DYU',595),('LBD',596),('DMK',597),('CNX',598),('HDY',599),('KBV',600),('HKT',601),('ASB',602),
('MYP',603),('KRW',604),('AUH',605),('AAN',606),('DXB',607),('RKT',608),('SHJ',609),('NMA',610),('TAS',611),('DAD',612),
('HAN',613),('SGN',614),('CXR',615),('PQC',616),('ADE',617),('SAH',618),

/*Oceania Airports*/
('PPG',619),('ADL',620),('BNE',621),('MEL',622),('PER',623),('SYD',624),('XCH',625),('CCK',626),('RAR',627),('IPC',628),
('TKK',629),('KSA',630),('PNI',631),('YAP',632),('NAN',633),('SUV',634),('PPT',635),('GUM',636),('CXI',637),('TRW',638),
('KWA',639),('MAJ',640),('INU',641),('NOU',642),('AKL',643),('CHC',644),('DUD',645),('ZQN',646),('WLG',647),('IUE',648),
('NLK',649),('ROP',650),('SPN',651),('TIQ',652),('ROR',653),('POM',654),('APW',655),('HIR',656),('TBU',657),('VAV',658),
('FUN',659),('SON',660),('VLI',661),('FUT',662),('WLS',663);