/* This script will create the database to be used in the project. */

CREATE TABLE [dbo].[Users] (
	[ID] INT IDENTITY(1,1) NOT NULL,
	[PIN] INT NOT NULL,
	[CheckingAmount] MONEY NOT NULL,
	[SavingsAmount] MONEY NOT NULL,

	CONSTRAINT [PK_dbo.Users] PRIMARY KEY CLUSTERED ([ID] ASC)
)