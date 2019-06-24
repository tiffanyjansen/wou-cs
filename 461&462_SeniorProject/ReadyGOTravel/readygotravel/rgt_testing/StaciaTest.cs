using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using readygotravel.Abstract;
using readygotravel.Concrete;
using readygotravel.Controllers;
using readygotravel.Models;

namespace rgt_testing
{
    [TestClass]
    public class StaciaTests
    {
        //Last few tests before AES 
        [TestMethod]
        public void SpringWeatherSelectedStatesDBOnly_ShouldReturnListofStatesOfGivenWeather_True()
        {
            //Arrange -- set up mock states to query for questionairre
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.States).Returns(new State[]
            {
                new State{StateID = 1, SpringWeather = 1, SpringTemp = 65},
                new State{StateID = 2, SpringWeather = 1, SpringTemp = 55},
                new State{StateID = 3, SpringWeather = 2, SpringTemp = 50},
                new State{StateID = 4, SpringWeather = 6, SpringTemp = 75},
            }.AsQueryable());
            QuestionnaireController controller = new QuestionnaireController(mock.Object);

            //Act -- set up expected and actual values
            int testStateIDa = 1;
            int testStateIDb = 2;
            List<State> result = new List<State>();
            int intWeather = 1;
            int temp1 = 50;
            int temp2 = 70;
            result = controller.SpringWeatherSelectedStatesDBOnly(intWeather, temp1, temp2);

            //Assert -- tests that expect and actual values are the same
            Assert.AreEqual(2, result.Count());
            Assert.AreEqual(testStateIDa, result[0].StateID);
            Assert.AreEqual(testStateIDb, result[1].StateID);
    }

        [TestMethod]
        public void SpringNoPrefWeatherStatesDBOnly_ShouldReturnListofStatesOfGivenWeather_True()
        {
            //Arrange -- set up mock states to query for questionairre
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.States).Returns(new State[]
            {
                new State{StateID = 1, SpringWeather = 1, SpringTemp = 65},
                new State{StateID = 2, SpringWeather = 1, SpringTemp = 55},
                new State{StateID = 3, SpringWeather = 2, SpringTemp = 50},
                new State{StateID = 4, SpringWeather = 6, SpringTemp = 75},
            }.AsQueryable());
            QuestionnaireController controller = new QuestionnaireController(mock.Object);

            //Act -- set up expected and actual values
            int testStateIDa = 1;
            int testStateIDb = 2;
            int testStateIDc = 3;
            List<State> result = new List<State>();
            int temp1 = 50;
            int temp2 = 70;
            result = controller.SpringNoPrefWeatherStatesDBOnly(temp1, temp2);

            //Assert -- tests that expect and actual values are the same
            Assert.AreEqual(3, result.Count());
            Assert.AreEqual(testStateIDa, result[0].StateID);
            Assert.AreEqual(testStateIDb, result[1].StateID);
            Assert.AreEqual(testStateIDc, result[2].StateID);
        }

        //PBI 261 - Stacia Unit Test Sprint 5
        [TestMethod]
        public void GetActivityList_ShouldReturnStringListForState1_True()
        {
            //Arrange -- sets up mock states to query activity list items
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.States).Returns(new State[]
            {
                new State{ StateID = 1, ActivityItemOne = "Hiking", ActivityItemTwo = "Bowling", ActivityItemThree = "Netflix" },
                new State{ StateID = 2, ActivityItemOne = "Fishing", ActivityItemTwo = "State Parks", ActivityItemThree = "Beaches" }
             }.AsQueryable());
            QuestionnaireController controller = new QuestionnaireController(mock.Object);

            //Act -- set up expected and actual values for method testing
            String activityOne = "Hiking";
            String activityTwo = "Bowling";
            String activityThree = "Netflix";
            string[] test = controller.GetActivityList(1);

            //Assert -- tests that expected and actual values are the same
            Assert.AreEqual(3, test.Count());
            Assert.AreEqual(activityOne, test[0]);
            Assert.AreEqual(activityTwo, test[1]);
            Assert.AreEqual(activityThree, test[2]);
        }

        [TestMethod]
        public void GetGeographyList_ShouldReturnStringListForState2_True()
        {
            //Arrange -- sets up mock states to query activity list items
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.States).Returns(new State[]
            {
                new State{ StateID = 1, Geographies = new List<Geography> { new Geography {GeographyType = "Forest" }, new Geography { GeographyType = "Lakes" }, new Geography {GeographyType = "Coast" }, new Geography {GeographyType = "Rivers" } } },
                new State{ StateID = 2, Geographies = new List<Geography> { new Geography {GeographyType = "Mountains" }, new Geography { GeographyType = "Desert" }, new Geography {GeographyType = "Mesa" }, new Geography {GeographyType = "Rivers" } } }
             }.AsQueryable());
            QuestionnaireController controller = new QuestionnaireController(mock.Object);

            //Act -- set up expected and actual values for method testing
            String geoOne = "Mountains";
            String geoTwo = "Desert";
            String geoThree = "Mesa";
            String geoFour = "Rivers";
            List<String> testList = controller.GetGeographyList(2);

            //Assert -- tests that expected and actual values are the same
            Assert.AreEqual(testList.Count(), 4);
            Assert.AreEqual(testList.First(), geoOne);
            Assert.AreEqual(testList.Skip(1).First(), geoTwo);
            Assert.AreEqual(testList.Skip(2).First(), geoThree);
            Assert.AreEqual(testList.Skip(3).First(), geoFour);
        }

        //PBI 319/320 - Stacia Unit Test Sprint 4
        [TestMethod]
        public void GetRemainingFlightsOnly_ShouldCalculateToTheDecimal_True()
        {
            //Arrange -- sets up search amount, flight, and food cost for testing
            SearchesController controller = new SearchesController(new EFTravelRepo());
            Search testSearch = new Search();
            testSearch.MaxAmount = 1000.00m;
            decimal testFlightCost = 500.57m;
            decimal testFoodCost = 200.18m;

            //Act -- set up expected and actual values for method testing
            decimal testRemaing = testSearch.MaxAmount - testFlightCost - testFoodCost;
            decimal testRemainingAmountFunction = controller.GetRemainingAmountFlightsOnly(testSearch,
                testFlightCost, testFoodCost);

            //Assert -- tests that expected and actual values are the same
            Assert.AreEqual(testRemaing, testRemainingAmountFunction);
            Assert.AreEqual(299.25m, testRemainingAmountFunction);
        }

        [TestMethod]
        public void GetRemainingFlightsOnly_ShouldCalculateNegativeValues_True()
        {
            //Arrange -- sets up search amount, flight, and food cost for testing
            SearchesController controller = new SearchesController(new EFTravelRepo());
            Search testSearch = new Search();
            testSearch.MaxAmount = 500.00m;
            decimal testFlightCost = 400.00m;
            decimal testFoodCost = 200.00m;

            //Act -- set up expected and actual values for method testing
            decimal testRemaing = testSearch.MaxAmount - testFlightCost - testFoodCost;
            decimal testRemainingAmountFunction = controller.GetRemainingAmountFlightsOnly(testSearch,
                testFlightCost, testFoodCost);

            //Assert -- tests that expected and actual values are the same
            Assert.AreEqual(testRemaing, testRemainingAmountFunction);
            Assert.AreEqual(-100.00m, testRemainingAmountFunction);
        }

        [TestMethod]
        public void GetRemainingFlightsOnly_ShouldCalculateStartingWithNegativeValues_True()
        {
            //Arrange -- sets up search amount, flight, and food cost for testing
            SearchesController controller = new SearchesController(new EFTravelRepo());
            Search testSearch = new Search();
            testSearch.MaxAmount = -100m;
            decimal testAirportCost = -200m;
            decimal testFoodCost = -50m;

            //Act -- set up expected and actual values for method testing
            decimal testTotalRemaing = -100 - 250m;
            decimal testRemainingAmountFunction = controller.GetRemainingAmountFlightsOnly(testSearch,
                testAirportCost, testFoodCost);

            //Assert -- tests that expected and actual values are the same
            Assert.AreEqual(testTotalRemaing, testRemainingAmountFunction);
            Assert.AreEqual(-350m, testRemainingAmountFunction);
        }

        [TestMethod]
        public void GetRemainingHotelsOnly_ShouldCalculateToTheDecimal_True()
        {
            //Arrange -- sets up search amount, hotel, and food cost for testing
            SearchesController controller = new SearchesController(new EFTravelRepo());
            Search testSearch = new Search();
            testSearch.MaxAmount = 1000.00m;
            decimal testHotelCost = 500.57m;
            decimal testFoodCost = 200.18m;

            //Act -- set up expected and actual values for method testing
            decimal testRemaing = testSearch.MaxAmount - testHotelCost - testFoodCost;
            decimal testRemainingAmountFunction = controller.GetRemainingAmountHotelsOnly(testSearch,
                testHotelCost, testFoodCost);

            //Assert -- tests that expected and actual values are the same
            Assert.AreEqual(testRemaing, testRemainingAmountFunction);
            Assert.AreEqual(299.25m, testRemainingAmountFunction);
        }

        [TestMethod]
        public void GetRemainingHotelsOnly_ShouldCalculateNegativeValues_True()
        {
            //Arrange -- sets up search amount, hotel, and food cost for testing
            SearchesController controller = new SearchesController(new EFTravelRepo());
            Search testSearch = new Search();
            testSearch.MaxAmount = 500.00m;
            decimal testHotelCost = 400.00m;
            decimal testFoodCost = 200.00m;

            //Act -- set up expected and actual values for method testing
            decimal testRemaing = testSearch.MaxAmount - testHotelCost - testFoodCost;
            decimal testRemainingAmountFunction = controller.GetRemainingAmountHotelsOnly(testSearch,
                testHotelCost, testFoodCost);

            //Assert -- tests that expected and actual values are the same
            Assert.AreEqual(testRemaing, testRemainingAmountFunction);
            Assert.AreEqual(-100.00m, testRemainingAmountFunction);
        }

        [TestMethod]
        public void GetRemainingAmountHotelsOnly_ShouldCalculateStartingWithNegativeValues_True()
        {
            //Arrange -- sets up search amount, hotel, and food cost for testing
            SearchesController controller = new SearchesController(new EFTravelRepo());
            Search testSearch = new Search();
            testSearch.MaxAmount = -100m;
            decimal testHotelCost = -200m;
            decimal testFoodCost = -50m;

            //Act -- set up expected and actual values for method testing
            decimal testTotalRemaing = -100 - 250m;
            decimal testRemainingAmountFunction = controller.GetRemainingAmountHotelsOnly(testSearch,
                testHotelCost, testFoodCost);

            //Assert -- tests that expected and actual values are the same
            Assert.AreEqual(testTotalRemaing, testRemainingAmountFunction);
            Assert.AreEqual(-350m, testRemainingAmountFunction);
        }

        //PBI 269 - Stacia Unit Test Sprint 3
        [TestMethod]
        public void GetHotelStarValue_ShouldReturnThree_True()
        {
            //Arrange -- set up strings for test and expected values
            string StarVals = "2-3"; //returns 3
            int ExpectedValue = 3;
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act -- set up the returned value from the method we are testing
            int ReturnedValue = controller.GetHotelStarValue(StarVals);

            //Assert -- actually run assertion tests on both values
            Assert.AreEqual(ExpectedValue, ReturnedValue);
        }

        [TestMethod]
        public void GetHotelStarValue_ShouldReturnZero_True()
        {
            //Arrange -- set up strings for test and expected values
            string NoPref = "no preference"; //returns 0
            int ExpectedValue = 0;
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act -- set up the returned value from the method we are testing
            int ReturnedValue = controller.GetHotelStarValue(NoPref);

            //Assert -- actually run assertion tests on both values
            Assert.AreEqual(ExpectedValue, ReturnedValue);
        }

        [TestMethod]
        public void GetHotelStarValue_ShouldReturn70_True()
        {
            //Arrange -- set up strings for test and expected values
            string TempRange = "60-70"; //returns 70
            int ExpectedValue = 70;
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act -- set up the returned value from the method we are testing
            int ReturnedValue = controller.GetHotelStarValue(TempRange);

            //Assert -- actually run assertion tests on both values
            Assert.AreEqual(ExpectedValue, ReturnedValue);
        }

        [TestMethod]
        public void GetHotelStarValue_ShouldReturnZeroStringWithDash_True()
        {
            //Arrange -- set up strings for test and expected values
            string DashNoNum = "no-preference"; //returns 0
            int ExpectedValue = 0;
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act -- set up the returned value from the method we are testing
            int ReturnedValue = controller.GetHotelStarValue(DashNoNum);

            //Assert -- actually run assertion tests on both values
            Assert.AreEqual(ExpectedValue, ReturnedValue);
        }

    }
}
