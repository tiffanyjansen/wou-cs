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
    public class TiffanyTests
    {
        //PBI: 266 Sprint 3 Tests
        [TestMethod]
        [DataRow(2.1D, 2.0D)]
        [DataRow(2.2D, 2.25D)]
        [DataRow(2.3D, 2.25D)]
        [DataRow(2.4D, 2.5D)]
        [DataRow(2.5D, 2.5D)]
        [DataRow(2.6D, 2.5D)]
        [DataRow(2.7D, 2.75D)]
        [DataRow(2.8D, 2.75D)]
        [DataRow(2.9D, 3D)]
        public void RoundToFourths__ShouldRoundTenthsToNearestQuarter_True(double test, double expected)
        {
            //Arrange -> Convert String Values to Decimal Values
            decimal testDecimal = Convert.ToDecimal(test);
            decimal expectedDecimal = Convert.ToDecimal(expected);
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act -> Run the method and catch the return value.
            decimal returnedDecimal = controller.RoundToFourths(testDecimal);

            //Assert
            Assert.AreEqual(expectedDecimal, returnedDecimal);
        }

        [TestMethod]
        [DataRow(2.11D, 2.0D)]
        [DataRow(2.12D, 2.0D)]
        [DataRow(2.13D, 2.25D)]
        [DataRow(2.14D, 2.25D)]
        [DataRow(2.15D, 2.25D)]
        [DataRow(2.35D, 2.25D)]
        [DataRow(2.36D, 2.25D)]
        [DataRow(2.37D, 2.25D)]
        [DataRow(2.38D, 2.5D)]
        [DataRow(2.39D, 2.5D)]
        [DataRow(2.59D, 2.5D)]
        [DataRow(2.61D, 2.5D)]
        [DataRow(2.62D, 2.5D)]
        [DataRow(2.63D, 2.75D)]
        [DataRow(2.64D, 2.75D)]
        [DataRow(2.65D, 2.75D)]
        [DataRow(2.85D, 2.75D)]
        [DataRow(2.86D, 2.75D)]
        [DataRow(2.87D, 2.75D)]
        [DataRow(2.88D, 2.75D)]
        [DataRow(2.89D, 3D)]
        [DataRow(2.91D, 3D)]
        [DataRow(2.92D, 3D)]
        public void RoundToFourths__ShouldRoundHundredthsToNearestQuarter_True(double test, double expected)
        {
            //Arrange -> Convert String Values to Decimal Values
            decimal testDecimal = Convert.ToDecimal(test);
            decimal expectedDecimal = Convert.ToDecimal(expected);
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act -> Run the method and catch the return value.
            decimal returnedDecimal = controller.RoundToFourths(testDecimal);

            //Assert
            Assert.AreEqual(expectedDecimal, returnedDecimal);
        }

        [TestMethod]
        [DataRow(-2.1D, -2.0D)]
        [DataRow(-2.2D, -2.25D)]
        [DataRow(-2.3D, -2.25D)]
        [DataRow(-2.4D, -2.5D)]
        [DataRow(-2.5D, -2.5D)]
        [DataRow(-2.6D, -2.5D)]
        [DataRow(-2.7D, -2.75D)]
        [DataRow(-2.8D, -2.75D)]
        [DataRow(-2.9D, -3D)]
        public void RoundToFourths__ShouldRoundNegativesToNearestQuarter_True(double test, double expected)
        {
            //Arrange -> Convert String Values to Decimal Values
            decimal testDecimal = Convert.ToDecimal(test);
            decimal expectedDecimal = Convert.ToDecimal(expected);
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act -> Run the method and catch the return value.
            decimal returnedDecimal = controller.RoundToFourths(testDecimal);

            //Assert
            Assert.AreEqual(expectedDecimal, returnedDecimal);
        }

        [TestMethod]
        [DataRow(2.1D, 2.25D)]
        [DataRow(2.2D, 2.5D)]
        [DataRow(2.3D, 2.5D)]
        [DataRow(2.4D, 2.75D)]
        [DataRow(2.5D, 2.75D)]
        [DataRow(2.6D, 2.75D)]
        [DataRow(2.7D, 3D)]
        [DataRow(2.8D, 3D)]
        [DataRow(2.9D, 3.25D)]
        public void RoundToFourths__ShouldRoundTenthsToNearestQuarter_False(double test, double notExpected)
        {
            //Arrange -> Convert String Values to Decimal Values
            decimal testDecimal = Convert.ToDecimal(test);
            decimal notExpectedDecimal = Convert.ToDecimal(notExpected);
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act -> Run the method and catch the return value.
            decimal returnedDecimal = controller.RoundToFourths(testDecimal);

            //Assert
            Assert.AreNotEqual(notExpectedDecimal, returnedDecimal);
        }

        //Sprint 4 Tests
        //PBI: 326 & 327

        [TestMethod]
        public void GetLocationsByCountry_ShouldReturnListOfCitiesWithOneAsCountry_True()
        {
            //Arrage
            //Create the mock and the controller to be used
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.Locations).Returns(new Location[]
            {
                new Location{ LocationID = 1, Name = "Paris", CountryID = 1 },
                new Location{ LocationID = 2, Name = "Lyon", CountryID = 1 },
                new Location{ LocationID = 3, Name = "Georgia", CountryID = 2 },
                new Location{ LocationID = 4, Name = "Alabama", CountryID = 2 },
                new Location{ LocationID = 5, Name = "City", CountryID = 3 },
                new Location{ LocationID = 6, Name = "OtherCity", CountryID = 3 },
                new Location{ LocationID = 7, Name = "Name", CountryID = 1 },
                new Location{ LocationID = 8, Name = "LocationName", CountryID = 1 }
            }.AsQueryable());
            SearchesController controller = new SearchesController(mock.Object);

            //Act
            //Run the method passing in 1 as the CountryID we are looking for.
            List<Location> locations = controller.GetLocationsByCountry(1);

            //Assert
            Assert.AreEqual(locations.Count, 4);
            Assert.AreEqual(locations.First().Name, "Paris");
            Assert.AreEqual(locations.Skip(1).First().Name, "Lyon");
            Assert.AreEqual(locations.Skip(2).First().Name, "Name");
            Assert.AreEqual(locations.Skip(3).First().Name, "LocationName");
        }

        [TestMethod]
        public void GetCities_ShouldReturnListOfCitiesWithCountry_True()
        {
            //Arrage
            //Create the mock and the controller to be used
            Mock<ITravelRepo> mock = new Mock<ITravelRepo>();
            mock.Setup(m => m.Locations).Returns(new Location[]
            {
                new Location{ LocationID = 1, Name = "Paris", Country = new Country{ CountryName = "CountryName"} },
                new Location{ LocationID = 2, Name = "Lyon", Country = new Country{ CountryName = "CountryName"}  },
                new Location{ LocationID = 3, Name = "Georgia", Country = new Country{ CountryName = "Country"} },
                new Location{ LocationID = 4, Name = "Alabama", Country = new Country{ CountryName = "Country"}  },
                new Location{ LocationID = 5, Name = "City", Country = new Country{ CountryName = "Name"}  },
                new Location{ LocationID = 6, Name = "OtherCity", Country = new Country{ CountryName = "Name"}  },
                new Location{ LocationID = 7, Name = "Name", Country = new Country{ CountryName = "CountryName"}  },
                new Location{ LocationID = 8, Name = "LocationName", Country = new Country{ CountryName = "CountryName"}  }
            }.AsQueryable());

            SearchesController controller = new SearchesController(mock.Object);

            //Act
            //Run the method passing in 1 as the CountryID we are looking for.
            List<string> locations = controller.GetLocations("CountryName");

            //Assert
            Assert.AreEqual(locations.Count, 4);
            Assert.AreEqual(locations.First(), "Paris");
            Assert.AreEqual(locations.Skip(1).First(), "Lyon");
            Assert.AreEqual(locations.Skip(2).First(), "Name");
            Assert.AreEqual(locations.Skip(3).First(), "LocationName");
        }

        [TestMethod]
        [DataRow("12", 12)]
        [DataRow("52", 52)]
        [DataRow("true", -1)]
        [DataRow("52", 52)]
        [DataRow("47", 47)]
        [DataRow("string", -1)]
        [DataRow("number", -1)]
        public void GetAirportID_ShouldReturnInt_True(string inputString, int expected)
        {
            //Arrange
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act
            int returnInt = controller.GetAirportID(inputString);

            //Assert
            Assert.AreEqual(returnInt, expected);
        }

        [TestMethod]
        [DataRow(0, 1)]
        [DataRow(1, 1)]
        [DataRow(2, 1)]
        [DataRow(3, 2)]
        [DataRow(4, 2)]
        [DataRow(5, 3)]
        [DataRow(6, 3)]
        [DataRow(7, 4)]
        [DataRow(8, 4)]
        [DataRow(9, 5)]
        [DataRow(10, 5)]
        public void GetNumRooms_ShouldReturnRoundedValue_True(int travelers, int expected)
        {
            //Arrange
            SearchesController controller = new SearchesController(new EFTravelRepo());

            //Act
            int returned = controller.GetNumRooms(travelers);

            //Assert
            Assert.AreEqual(returned, expected);
			
        }
    }
}

