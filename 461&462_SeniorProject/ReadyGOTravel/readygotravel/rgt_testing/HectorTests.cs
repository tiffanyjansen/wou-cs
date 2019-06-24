using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using readygotravel.Abstract;
using Moq;
using readygotravel.Controllers;
using readygotravel.Models;
using System.Linq;

namespace rgt_testing
{ 
    [TestClass]
    public class HectorTests
    {
        //PBI 267 - Hector Unit Test
        [TestMethod]
        public void CheckCrimeRate_ReturnsNumberRankingOfLowCrimeRate_1()
        {
            //Arrange
            decimal crimeRateValue = 100;
            int expectedCrimeRank = 1;

            //Act
            int CrimeRanking = CheckCrime(crimeRateValue);

            //Assert
            Assert.AreEqual(expectedCrimeRank, CrimeRanking);
        }

        [TestMethod]
        public void CheckCrimeRate_ReturnsNumberRankingOfMediumLowCrimeRate_2()
        {
            //Arrange
            decimal crimeRateValue = 300;
            int expectedCrimeRank = 2;

            //Act
            int CrimeRanking = CheckCrime(crimeRateValue);

            //Assert
            Assert.AreEqual(expectedCrimeRank, CrimeRanking);
        }

        [TestMethod]
        public void CheckCrimeRate_ReturnsNumberRankingOfMediumHighCrimeRate_3()
        {
            //Arrange
            decimal crimeRateValue = 450;
            int expectedCrimeRank = 3;

            //Act
            int CrimeRanking = CheckCrime(crimeRateValue);

            //Assert
            Assert.AreEqual(expectedCrimeRank, CrimeRanking);
        }

        [TestMethod]
        public void CheckCrimeRate_ReturnsNumberRankingOfHighCrimeRate_4()
        {
            //Arrange
            decimal crimeRateValue = 1000;
            int expectedCrimeRank = 4;

            //Act
            int CrimeRanking = CheckCrime(crimeRateValue);

            //Assert
            Assert.AreEqual(expectedCrimeRank, CrimeRanking);
        }

        private int CheckCrime(decimal crimeRate)
        {
            if (crimeRate > 550)
                return 4;
            else if (crimeRate > 400)
                return 3;
            else if (crimeRate > 250)
                return 2;
            else
                return 1;
        }
        
        //PBI 98 - Hector Unit Test
        [TestMethod]
        public void GetLocationName_ReturnLocationNameOfGivenID_locationName()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.Locations).Returns(new Location[]
            {
                new Location{ LocationID = 1, Name = "Paris", CountryID = 1, State = false },
                new Location{ LocationID = 2, Name = "Lyon", CountryID = 1, State = false },
                new Location{ LocationID = 3, Name = "MagicLand", CountryID = 2, State = false },
                new Location{ LocationID = 4, Name = "Moscow", CountryID = 2, State = false },
                new Location{ LocationID = 5, Name = "Other City", CountryID = 3, State = false },
                new Location{ LocationID = 6, Name = "Georgia", CountryID = 3, State = true },
                new Location{ LocationID = 7, Name = "London", CountryID = 1, State = false },
                new Location{ LocationID = 8, Name = "Jersey", CountryID = 1, State = true }
            }
            .AsQueryable());

            FavoritesController controller = new FavoritesController(mock.Object);
            //Act
            string location1 = controller.GetLocationName(1);
            string location2 = controller.GetLocationName(4);
            string location3 = controller.GetLocationName(8);
            string location4 = controller.GetLocationName(5);

            //Assert
            Assert.AreEqual(location1, "Paris");
            Assert.AreEqual(location2, "Moscow");
            Assert.AreEqual(location3, "Jersey");
            Assert.AreEqual(location4, "Other City");

        }
        
        //PBI 251 - Hector Unit Test
        [TestMethod]
        public void GetDateAndDateAfter_WillReturnSpecifiedDateAndTheNextSpecifiedDateAfter_TwoDates()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            int daysPast = 3;
            int skipDays = 2;
            DateTime date1 = DateTime.Now.AddDays(daysPast);
            DateTime date2 = date1.AddDays(skipDays);
            TopLocationsController testController = new TopLocationsController(mock.Object);

            //Act
            Tuple<DateTime, DateTime> dates = testController.GetDateAndNext(daysPast,skipDays);

            //Assert
            Assert.AreEqual(dates.Item1.ToString(), date1.ToString());
            Assert.AreEqual(dates.Item2.ToString(), date2.ToString());
        }
        
        //PBI 321 - Hector Unit Test
        [TestMethod]
        public void GetSearchListByUser_ShouldReturnSectionOfListByUser_ListOfSearches()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.Searches).Returns(new Search[]
            {
                new Search { SearchID = 1, UserID = 1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 2, UserID = 1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 3, UserID = 12, StartDate = DateTime.UtcNow, EndDate=DateTime.UtcNow, NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 4, UserID = 2, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 5, UserID = 1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 6, UserID = 1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Business",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 7, UserID = 3, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Business",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 8, UserID = 1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Business",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 9, UserID = 3, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Business",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 10, UserID = 5, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Business",
                    StartAirport = 13, EndAirport = 18}


            }.AsQueryable());
            SearchesController testController = new SearchesController(mock.Object);

            //Act
            //Note: The list is in descending order. 
            //The first parameter is the Usert ID to get the searches from, the second is amount that will be skipped in the beginning, the third is the amount of element to take from db.
            List<Search> searchList = testController.GetSearchListSectionByUser(1, 2, 3);

            //Assert
            Assert.AreEqual(searchList[0].SearchID, 5);
            Assert.AreEqual(searchList[1].SearchID, 2);
            Assert.AreEqual(searchList[2].SearchID, 1);


        }
        
        //PBI 329 - Hector Unit Test
        [TestMethod]
        public void CheckFavorites_ShouldReturnTrueIfFavorite_True()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.UserStates).Returns(new UserState[]
            {
                new UserState {PersonID = 1, LocationID = 4, International = false },
                new UserState {PersonID = 2, LocationID = 2, International = false },
                new UserState {PersonID = 1, LocationID = 1, International = false },
                new UserState {PersonID = 3, LocationID = 6, International = false },
                new UserState {PersonID = 3, LocationID = 1, International = true }

            }
            .AsQueryable());
            FavoritesController controller = new FavoritesController(mock.Object);
            //Act
            Boolean favoriteCheck = controller.FavoriteOfUserCheck(1, 4);
            Boolean favoriteCheck2 = controller.FavoriteOfUserCheck(1, 1);
            Boolean favoriteCheck3 = controller.FavoriteOfUserCheck(3, 1);

            //Assert
            Assert.AreEqual(favoriteCheck, true);
            Assert.AreEqual(favoriteCheck2, true);
            Assert.AreEqual(favoriteCheck3, true);

        }

        //PBI 393 - Hector Unit Test
        [TestMethod]
        public void GetAverageHotelRating_ShouldReturnAverageSearchedHotelStarRating_StringValueOfAverage()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.Results).Returns(new Result[]
            {
                new Result {ResultID = 1,SearchID = 1,AvgHotelStar=3.43M, AvgFlightAmount = 32333, AvgHotelAmount = 11322, AvgFoodCost= 322},
                new Result {ResultID = 2,SearchID = 2,AvgHotelStar=2.50M, AvgFlightAmount = 42333, AvgHotelAmount = 4222, AvgFoodCost = 332},
                new Result {ResultID = 3,SearchID = 3,AvgHotelStar=3.54M, AvgFlightAmount = 54533, AvgHotelAmount = 7622, AvgFoodCost = 112},
                new Result {ResultID = 4,SearchID = 4,AvgHotelStar=1.00M, AvgFlightAmount = 22233, AvgHotelAmount = 7772, AvgFoodCost = 32},
                new Result {ResultID = 5,SearchID = 5,AvgHotelStar=1.44M, AvgFlightAmount = 99933, AvgHotelAmount = 99822, AvgFoodCost = 22222}
            }
            .AsQueryable());

            mock.Setup(m => m.Searches).Returns(new Search[]
            {
                new Search { SearchID = 1, UserID = 1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 3, FlightType = "Economy",
                    StartAirport = 2, EndAirport = 1, Results = mock.Object.Results.Where(n=>n.SearchID == 1).ToList()},
                new Search { SearchID = 2, UserID = 3, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 4, EndAirport = 1, Results = mock.Object.Results.Where(n=>n.SearchID == 2).ToList()},
                new Search { SearchID = 3, UserID = 2, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 3, EndAirport = 1, Results = mock.Object.Results.Where(n=>n.SearchID == 3).ToList()},
                new Search { SearchID = 4, UserID = 1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 4, EndAirport = 3, Results = mock.Object.Results.Where(n=>n.SearchID == 4).ToList()},
                new Search { SearchID = 5, UserID = 3, StartDate = DateTime.UtcNow, EndDate=DateTime.UtcNow, NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 1, EndAirport = 2, Results = mock.Object.Results.Where(n=>n.SearchID == 5).ToList()}
            }
            .AsQueryable());

            mock.Setup(m => m.Airports).Returns(new Airport[]
            {
                new Airport {AirportID = 1, AirportCode="CDG",  LocationID=1, Searches1 = mock.Object.Searches.Where(n=>n.EndAirport == 1).ToList()},
                new Airport {AirportID = 2, AirportCode="PDX", LocationID=2, Searches1 = mock.Object.Searches.Where(n=>n.EndAirport == 2).ToList()},
                new Airport {AirportID = 3, AirportCode="SAW", LocationID=3, Searches1 = mock.Object.Searches.Where(n=>n.EndAirport == 3).ToList()},
                new Airport {AirportID = 4, AirportCode="HSV", LocationID=4, Searches1 = mock.Object.Searches.Where(n=>n.EndAirport == 3).ToList()}
            }
            .AsQueryable());

            mock.Setup(m => m.Locations).Returns(new Location[]
            {
                new Location {LocationID = 1, Name = "Paris",  CountryID=2, State = false, Airports=mock.Object.Airports.Where(n=>n.LocationID == 1).ToList() },
                new Location {LocationID = 2,  Name = "Oregon", CountryID=1, State = true, Airports=mock.Object.Airports.Where(n=>n.LocationID == 2).ToList()},
                new Location {LocationID = 3, Name = "Istanbul", CountryID=3, State = false, Airports=mock.Object.Airports.Where(n=>n.LocationID == 3).ToList() },
                new Location {LocationID = 4, Name = "Alabama", CountryID=1, State = true, Airports=mock.Object.Airports.Where(n=>n.LocationID == 4).ToList()}
            }
            .AsQueryable());

            TopLocationsController controller = new TopLocationsController(mock.Object);

            //Act
            string AverageAmount1 = controller.AverageSearchedHotelStarRating("Paris");
            string AverageAmount2 = controller.AverageSearchedHotelStarRating("Oregon");
            string AverageAmount3 = controller.AverageSearchedHotelStarRating("Istanbul");

            //Assert
            Assert.AreEqual(AverageAmount1, "3.16");
            Assert.AreEqual(AverageAmount2, "1.44");
            Assert.AreEqual(AverageAmount3, "1.00");
        }
        //PBI 380 - Hector Unit Test
        [TestMethod]
        public void GetCheckIfInternational_ShouldReturnBooleanToDetermineIfLocationIsInternational_false()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.Locations).Returns(new Location[]
            {
                new Location {LocationID = 1, Name = "Paris",  CountryID=2, State = false},
                new Location {LocationID = 2,  Name = "Oregon", CountryID=1, State = true},
                new Location {LocationID = 3, Name = "Istanbul", CountryID=3, State = false},
                new Location {LocationID = 4, Name = "Alabama", CountryID=1, State = true},
                new Location {LocationID = 5, Name = "Ohio", CountryID=1, State = true}
            }
            .AsQueryable());

            FavoritesController controller = new FavoritesController(mock.Object);

            //Act
            bool oregonCheck = controller.CheckIfInternational(2);
            bool alabamaCheck = controller.CheckIfInternational(4);
            bool OhioCheck = controller.CheckIfInternational(5);

            //Assert
            Assert.AreEqual(oregonCheck, false);
            Assert.AreEqual(alabamaCheck, false);
            Assert.AreEqual(OhioCheck, false);
        }
        [TestMethod]
        public void GetCheckIfInternational_ShouldReturnBooleanToDetermineIfLocationIsInternational_true()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.Locations).Returns(new Location[]
            {
                new Location {LocationID = 1, Name = "Paris",  CountryID=2, State = false},
                new Location {LocationID = 2,  Name = "Oregon", CountryID=1, State = true},
                new Location {LocationID = 3, Name = "Istanbul", CountryID=3, State = false},
                new Location {LocationID = 4, Name = "Alabama", CountryID=1, State = true},
                new Location {LocationID = 5, Name = "Ohio", CountryID=1, State = true},
                new Location {LocationID = 6, Name = "Galway", CountryID=23, State = false}
            }
            .AsQueryable());

            FavoritesController controller = new FavoritesController(mock.Object);

            //Act
            bool parisCheck = controller.CheckIfInternational(1);
            bool istanbulCheck = controller.CheckIfInternational(3);
            bool galwayCheck = controller.CheckIfInternational(6);

            //Assert
            Assert.AreEqual(parisCheck, true);
            Assert.AreEqual(istanbulCheck, true);
            Assert.AreEqual(galwayCheck, true);
        }
    }
}
