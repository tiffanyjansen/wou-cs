--Requests Table
CREATE TABLE [dbo].[Requests]
(
	[RequestID]        INT IDENTITY (1,1)    NOT NULL,
    [DateOfRequest]		DateTime	NOT NULL,
	[IPAddress]		NVARCHAR(100)	NOT NULL,
	[Browser]		NVARCHAR(100)	NOT NULL,
	[SpecialSite]		NVARCHAR(100)		NOT NULL,
    CONSTRAINT [PK_dbo.Requests] PRIMARY KEY CLUSTERED ([RequestID] ASC)
);