using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using readygotravel.Models;
using readygotravel.Controllers;
using Moq;
using readygotravel.Abstract;
using readygotravel.Concrete;

namespace rgt_testing
{
    [TestClass]
    public class AdrianTests

    //Sprint 3 Tests
    //PBI 268 - Adrian Unit Test
    {
        [TestMethod]
        public void ConfirmsCorrectRemainderAmount_ShouldCalculateToTheDecimal()
        {
            //Arrange
            //Sum of total costs: 1679.37 + 1506.21 + 800.05 = 3985.63
            //User Amount - total costs: 5000.29 - 3985.63 = 1014.66
            Search testSearchItem = getTestObject();
            testSearchItem.MaxAmount = 5000.29m;
            decimal testAirportCost = 800.05m;
            decimal testHotelCost = 1506.21m;
            decimal testFoodCost = 1679.37m;

            //Act
            decimal testTotalRemaing = 5000.29m - 3985.63m;
            decimal testRemainingAmountFunction = GetRemainingAmount(testSearchItem,
                testAirportCost, testHotelCost, testFoodCost);

            //Assert
            Assert.AreEqual(testTotalRemaing, testRemainingAmountFunction);
            Assert.AreEqual(1014.66m, testRemainingAmountFunction);
        }

        [TestMethod]
        public void ConfirmsThatFunctionReturnsNegativeAmount_ShouldBeInitalMinusCosts()
        {
            //Arrange
            Search testSearchItem = getTestObject();
            testSearchItem.MaxAmount = 1m;
            decimal testAirportCost = 200m;
            decimal testHotelCost = 300m;
            decimal testFoodCost = 50m;

            //Act
            decimal testTotalRemaing = 1 - 550m;
            decimal testRemainingAmountFunction = GetRemainingAmount(testSearchItem,
                testAirportCost, testHotelCost, testFoodCost);

            //Assert
            Assert.AreEqual(testTotalRemaing, testRemainingAmountFunction);
            Assert.AreEqual(-549m, testRemainingAmountFunction);
        }

        [TestMethod]
        public void ConfirmsThatFunctionAddressesNegativeInput_ShouldCalculateCorrectly()
        {
            //Arrange
            Search testSearchItem = getTestObject();
            testSearchItem.MaxAmount = -100m;
            decimal testAirportCost = -200m;
            decimal testHotelCost = -300m;
            decimal testFoodCost = -50m;

            //Act
            decimal testTotalRemaing = -100 - 550m;
            decimal testRemainingAmountFunction = GetRemainingAmount(testSearchItem,
                testAirportCost, testHotelCost, testFoodCost);

            //Assert
            Assert.AreEqual(testTotalRemaing, testRemainingAmountFunction);
            Assert.AreEqual(-650m, testRemainingAmountFunction);
        }

        public Search getTestObject()
        {
            Search testResultObject = new Search();
            return testResultObject;
        }

        private decimal GetRemainingAmount(Search search, decimal flight, decimal hotel, decimal food)
        {
            decimal amount = search.MaxAmount;
            if (search.MaxAmount < 0)
            {
                amount = amount + (flight + hotel + food);
                amount = Math.Round(amount, 2);
            }
            else
            {
                amount = amount - (flight + hotel + food);
                amount = Math.Round(amount, 2);
            }
            return amount;
        }

        //Sprint 4 Tests
        //PBI 91 - Adrian Unit Test
        [TestMethod]
        public void ConfirmsThatFunctionReturns_FourBDSeasonalTempsAndWeathers_PerState()
        {
            //Arrage
            //Create the mock and the controller to be used
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.States).Returns(new State[]
            {
                new State {StateID = 1, LocationID = 1, SpringTemp=10, SummerTemp=20, FallTemp=30, WinterTemp=40, SpringWeather=1, SummerWeather=1, FallWeather=2, WinterWeather=2, Location = new Location {LocationID = 1, Name = "Oregon", CountryID = 1, State = true}, Weather = new Weather { WeatherID = 1, WeatherType = "Clear Skies"}, Weather1 = new Weather { WeatherID = 2, WeatherType= "Cloudy"}, Weather2 = new Weather { WeatherID = 1, WeatherType = "Humid"}, Weather3 = new Weather { WeatherID = 2, WeatherType= "Sunny"}},
                new State {StateID = 2, LocationID = 2, SpringTemp=25, SummerTemp=35, FallTemp=45, WinterTemp=55, SpringWeather=2, SummerWeather=2, FallWeather=3, WinterWeather=3,
                Location = new Location { LocationID = 2, Name = "New York", CountryID = 1, State = true },
                    Weather = new Weather { WeatherID = 1, WeatherType = "Clear Skies"}, Weather1 = new Weather { WeatherID = 2, WeatherType= "Cloudy"},Weather2 = new Weather { WeatherID = 1, WeatherType = "Humid"}, Weather3 = new Weather { WeatherID = 2, WeatherType= "Sunny"} }

            }.AsQueryable());
            TopLocationsController top = new TopLocationsController(mock.Object);

            //Act
            //Counter, Array to Hold Temps, Function to get temps for State Object
            int i = 0;
            int[] tempOregon = new int[4];
            string[] weatherOregon = new string[4];
            int[] tempNewYork = new int[4];
            string[] weatherNewYork = new string[4];
            top.CreateTempsList(tempOregon, weatherOregon, i, "Oregon");
            top.CreateTempsList(tempNewYork, weatherNewYork, i, "New York");

            //Assert
            Assert.AreEqual(tempOregon[0], 30);
            Assert.AreEqual(tempOregon[3], 20);
            Assert.AreNotEqual(tempNewYork[0], 60);
            Assert.AreEqual(tempNewYork[1], 55);
            Assert.AreEqual(weatherOregon[0], "Humid");
            Assert.AreEqual(weatherNewYork[2], "Clear Skies");
        }

        //PBI 92 - Adrian Unit Test
        [TestMethod]
        public void ConfirmsThatFunctionReturnsCorrectCrimeData_AndCrimeViewData()
        {
            //Arrage
            //Create the mock and the controller to be used
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.States).Returns(new State[]
            {
               new State {StateID = 1, LocationID = 1, CrimeRate = 100m, Location = new Location {LocationID = 1, Name = "Oregon", CountryID = 1, State = true}},
                new State {StateID = 2, LocationID = 2, CrimeRate = 700m,
                Location = new Location { LocationID = 2, Name = "New York", CountryID = 1, State = true },
                    }
            }
            .AsQueryable());
            TopLocationsController top = new TopLocationsController(mock.Object);

            //Act
            decimal[] crimeRate = new decimal[2];
            int[] crimeCheck = new int[2];
            top.CreateCrimeLists(crimeRate, crimeCheck, 0, "Oregon");
            top.CreateCrimeLists(crimeRate, crimeCheck, 1, "New York");

            //Assert
            Assert.AreEqual(crimeRate[0], 100m);
            Assert.AreEqual(crimeRate[1], 700m);
            Assert.AreEqual(crimeCheck[0], 1);
            Assert.AreEqual(crimeCheck[1], 4);
        }

        //PBI 274 - Adrian Unit Test
        [TestMethod]
        public void CheckToMakeSureFlightTypeReturnsCorrectCharacter_ShouldReturn_E_or_B()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.Searches).Returns(new Search[]
            {
                new Search { SearchID = 1, UserID = -1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 2, UserID = -1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Business",
                    StartAirport = 13, EndAirport = 18}
            }.AsQueryable());
            SearchesController testController = new SearchesController(mock.Object);

            //Act
            List<Search> testList = mock.Object.Searches.ToList();
            string testEconomy = testController.GetFlightType(testList.Where(s => s.SearchID == 1).Select(f => f.FlightType).FirstOrDefault());
            string testBusiness = testController.GetFlightType(testList.Where(s => s.SearchID == 2).Select(f => f.FlightType).FirstOrDefault());

            //Assert
            Assert.AreEqual(testEconomy, "E");
            Assert.AreEqual(testBusiness, "B");
            Assert.ThrowsException<ArgumentOutOfRangeException>(() => testController.GetFlightType(""));

        }

        //PBI 325 - Adrian Unit Test
        [TestMethod]
        public void TesttoConfirmThatGetAirPortCodeFunctionReturnsCorrectCodes_ShouldBeThreeLetterStrings()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.Searches).Returns(new Search[]
            {
                new Search { SearchID = 1, UserID = -1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Economy",
                    StartAirport = 13, EndAirport = 18},
                new Search { SearchID = 2, UserID = -1, StartDate = DateTime.UtcNow, EndDate= DateTime.UtcNow,
                NumTravelers = 2, MaxAmount = 5000, HotelStarValue = 2, FlightType = "Business",
                    StartAirport = 101, EndAirport = 132}
            }.AsQueryable());
            mock.Setup(m => m.Airports).Returns(new Airport[]
            {
                new Airport { AirportID = 13, AirportCode = "AAA", LocationID = 1},
                new Airport { AirportID = 18, AirportCode = "BBB", LocationID = 2},
                new Airport { AirportID = 101, AirportCode = "CCC", LocationID = 3},
                new Airport { AirportID = 132, AirportCode = "DDD", LocationID = 4}
            }.AsQueryable());
            SearchesController testAirController = new SearchesController(mock.Object);

            //    //Act
            List<Search> testAirList = mock.Object.Searches.ToList();
            string startOne = testAirController.GetAirportCode((int)testAirList.Where(s => s.SearchID == 1).Select(a => a.StartAirport).FirstOrDefault());
            string endOne = testAirController.GetAirportCode((int)testAirList.Where(s => s.SearchID == 1).Select(a => a.EndAirport).FirstOrDefault());
            string startTwo = testAirController.GetAirportCode((int)testAirList.Where(s => s.SearchID == 2).Select(a => a.StartAirport).FirstOrDefault());
            string endTwo = testAirController.GetAirportCode((int)testAirList.Where(s => s.SearchID == 2).Select(a => a.EndAirport).FirstOrDefault());
            string startThree = testAirController.GetAirportCode((int)testAirList.Where(s => s.SearchID == 3).Select(a => a.EndAirport).FirstOrDefault());

            //Assert
            Assert.AreEqual(startOne, "AAA");
            Assert.AreEqual(endOne, "BBB");
            Assert.AreEqual(startTwo, "CCC");
            Assert.AreEqual(endTwo, "DDD");
            Assert.AreEqual(startThree, null);
        }

        //PBI 375 - Adrian Unit Test
        [TestMethod]
        public void TesttoConfirmThatFoodItemsReturnStringResults_ShouldBe3StringsBasedOnState()
        {
            //Arrange
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.States).Returns(new State[]
                {
                 new State { StateID = 1, LocationID = 1, FoodItemOne = "Bread", FoodItemTwo = "Toast", FoodItemThree = "Raisin Bread", Location = new Location {LocationID = 1, Name = "Ohio", CountryID = 1, State = true} },
                 new State { StateID = 2, LocationID = 2, FoodItemOne = "Cheese", FoodItemTwo = "Cream Cheese", FoodItemThree = "Melted Cheese", Location = new Location {LocationID = 2, Name = "Montana", CountryID = 1, State = true}  }
                 }.AsQueryable());
            TopLocationsController testFoodItemsController = new TopLocationsController(mock.Object);

            //Act
            int i = 0;
            int j = 3;
            string[] food = new string[6];
            testFoodItemsController.CreateFoodItemsList(food, i, mock.Object.States.Where(n => n.LocationID == 1).Select(n => n.Location.Name).FirstOrDefault());
            testFoodItemsController.CreateFoodItemsList(food, j, mock.Object.States.Where(n => n.LocationID == 2).Select(n => n.Location.Name).FirstOrDefault());

            //Assert
            Assert.AreEqual(food[1], "Toast");
            Assert.AreEqual(food[3], "Cheese");
            Assert.AreEqual(food[5], "Melted Cheese");
            Assert.AreNotEqual(food[0], "Cheese");
        }
    }
}
