using readygotravel.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using readygotravel.Controllers;
using System.Xml;
using System.Net;
using System.IO;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using readygotravel.Models.ViewModels;
using readygotravel.Concrete;
using readygotravel.Abstract;
using Newtonsoft.Json;

namespace readygotravel.Controllers
{
    public class TopLocationsController : Controller
    {
        //Code needed for dependency injection and DB access
        private DBContext db = new DBContext();
        private ITravelRepo repo;
        public TopLocationsController(ITravelRepo travelRepo)
        {
            repo = travelRepo;
        }

        // GET: Top US Locations
        /// <summary>
        /// This is the main function for the Top US locations view page
        /// </summary>
        /// <returns>Returns all lists, information, and users name for said view page</returns>
        public ActionResult Index()
        {
            //New person model for login, displays name if logged in, or admin if admin in the nav bar
            Person person = new Person();
            string idTemp = User.Identity.GetUserId();
            if (idTemp != null)
            {
                //Check the id of the current user if its not null
                int? id = db.People.Where(n => n.UserID.Equals(idTemp)).First().PersonID;
                //Id should never be null, guests are always -1, this is simply for error handling
                if (id == null)
                {
                    return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
                }
                person = db.People.Find(id);
                if (person == null)
                {
                    return HttpNotFound();
                }

                //Check if the logged in user is an Admin or not.
                if (new PeopleController(repo).IsAdminUser(User.Identity))
                {
                    ViewBag.Admin = 1;
                }
                else
                {
                    ViewBag.Admin = 0;
                }
            }
            //Create an array of the top 12 states to reference.
            string[] states = { "Wyoming","Hawaii", "Arizona", "New York", "California", "Maryland", "Massachusetts","Nevada","Illinois", "Louisiana", "Montana", "Washington"};

            //Create all arrays such as foodCost, foodItems amounts, avgHotel Ratings based on Searches, weather, geography, etc.
            decimal[] foodCostList = new decimal[12];
            string[] avgHotelList = new string[12];
            string[] foodItemsList = new string[36];
            int[] tempsList = new int[48];
            string[] weatherList = new string[48];
            List<string> climates = new List<string>();
            List<int> climateCount = new List<int>();
            decimal[] actualCrimeList = new decimal[12];
            int[] crimeCheck = new int[12];

            //climate counter and int for said tracker
            List<int> count = new List<int>();
            int z = 0;

            //For each of the 12 states, fill the foodcost list/AvgHotelRating List with only states with the specified names.
            for (int i = 0; i < 12; i++)
            {
                //Can not do a db linq query using an array, so used a work around.
                string state = states[i];

                //DB query to pull top state's foodcost data.
                foodCostList[i] = Math.Round(db.States
                    .Where(s => s.Location.Name.Contains(state))
                    .Select(f => f.FoodCost).FirstOrDefault(), 2);

                //Function and counter to pull the three fooditem names per state (Function Tested - Adrian)
                int j = i * 3;
                CreateFoodItemsList(foodItemsList, j, state);

                //Function to pull state's crimerate data (Function Tested - Adrian)
                CreateCrimeLists(actualCrimeList, crimeCheck, i, state);

                //Function to the 4 temperatures/weather per season per state (Function Tested - Adrian)
                int k = i * 4;
                CreateTempsList(tempsList, weatherList, k, state);

                //Loop to add all climates for all states, includes a counter geoCount that helps to track what climates belong to what states
                foreach (string climate in db.States.Where(s => s.Location.Name.Contains(state))
                    .SelectMany(c => c.Climates).Select(t => t.ClimateType).ToList())
                {
                    //This makes a list of all climates for all 12 states
                    climates.Add(climate);
                    //This tracks climates per state
                    climateCount.Add(i);
                    //This is a variable used for the view page to paste correct results in the foreach loop
                    count.Add(z);
                    z++;
                }

                //Setup list of Average searhed hotel star ratings.
                avgHotelList[i] = AverageSearchedHotelStarRating(states[i]);

            }

            //ViewBags have stored lists for view page.
            ViewBag.foodList = foodCostList;
            ViewBag.hotelList = avgHotelList;
            ViewBag.foodItems = foodItemsList;
            ViewBag.actualCrime = actualCrimeList;
            ViewBag.crimeCheck = crimeCheck;
            ViewBag.temps = tempsList;
            ViewBag.weather = weatherList;
            ViewBag.climates = climates;
            ViewBag.cCount = climateCount;
            ViewBag.count = count;
            ViewBag.states = states;

            //Also return person object to display name
            return View(person);
        }

        /// <summary>
        /// This will return the TopEuropeanLocations View with some information about the locations.
        /// </summary>
        /// <returns>The TopEurpeanLocations View.</returns>
        public ActionResult TopEuropeanLocations()
        {
            //New Person Model for Login, displays name if logged in, or admin if admin in the nav bar
            Person person = new Person();
            string idTemp = User.Identity.GetUserId();
            if (idTemp != null)
            {
                //Check the id of the current user if its not null
                int? id = db.People.Where(n => n.UserID.Equals(idTemp)).First().PersonID;
                //Id should never be null, guests are always -1, this is simply for error handling
                if (id == null)
                {
                    return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
                }
                person = db.People.Find(id);
                if (person == null)
                {
                    return HttpNotFound();
                }

                //Check if the logged in user is an Admin or not.
                if (new PeopleController(repo).IsAdminUser(User.Identity))
                {
                    ViewBag.Admin = 1;
                }
                else
                {
                    ViewBag.Admin = 0;
                }
            }
            
            //Create an array of the top 12 locations to reference.
            string[] locations = { "Paris","Florence", "Amsterdam", "London", "Barcelona", "Rome", "Reykjavik", "Edinburgh", "Prague","Galway","Nice", "Istanbul" };
            
            //Create an avgHotel Ratings array based on Searches
            string[] avgHotelList = new string[12];
            for(int i = 0; i < 12; i++)
            {
                //Setup list of Average searhed hotel star ratings.
                avgHotelList[i] = AverageSearchedHotelStarRating(locations[i]);

            }
            ViewBag.hotelList = avgHotelList;

            //Also return person object to display name
            return View(person);
        }

        //Adrian Test
        /// <summary>
        /// Function that builds food item list, 3 for each state
        /// </summary>
        /// <param name="foodItemsList">The array that holds all of the food strings</param>
        /// <param name="j">counter that grabs 3 foods per State</param>
        /// <param name="state">the current State for tracking</param>
        public void CreateFoodItemsList(string[] foodItemsList, int j, string state)
        {
            foodItemsList[j] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                         .Select(f => f.FoodItemOne).FirstOrDefault();
            foodItemsList[j + 1] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                     .Select(f => f.FoodItemTwo).FirstOrDefault();
            foodItemsList[j + 2] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                     .Select(f => f.FoodItemThree).FirstOrDefault();
        }

        /// <summary>
        /// This will give you the Average Searched Hotel Star Rating of a location.
        /// </summary>
        /// <param name="locationName">The name of the location that is being referenced.</param>
        /// <returns>The average searched hotel star rating.</returns>
        public string AverageSearchedHotelStarRating(string locationName)
        {
            //Try to find results for location searches.
            string AverageValue;
            try
            {
                //Avg the list of hotel star rating results by the selected state.
                AverageValue = Convert.ToString(Math.Round(repo.Locations.ToList()
                    .Where(s => s.Name == locationName)
                    .SelectMany(a => a.Airports)
                    .SelectMany(n => n.Searches1)
                    .SelectMany(r => r.Results)
                    .Select(r => r.AvgHotelStar)
                    .Average(), 2));
                return AverageValue;
            }

            //If null is returned because of no results for that state, catch and put in a default value.
            catch
            {
                AverageValue = "---";
            }
            return AverageValue;

        }
        /// <summary>
        /// This function pulls temperatures per state per season
        /// </summary>
        /// <param name="tempsList">The list of temperatures ran once per state</param>
        /// <param name="k">A counter that helps to capture each temperature</param>
        /// <param name="state">The current state info being pulled in the FOR loop in each view page</param>
        public void CreateTempsList(int[] tempsList, string[] weatherList, int k, string state)
        {
            tempsList[k] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                    .Select(t => t.FallTemp).FirstOrDefault();
            tempsList[k + 1] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                     .Select(t => t.WinterTemp).FirstOrDefault();
            tempsList[k + 2] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                     .Select(t => t.SpringTemp).FirstOrDefault();
            tempsList[k + 3] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                     .Select(t => t.SummerTemp).FirstOrDefault();
            weatherList[k] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                         .Select(w => w.Weather2.WeatherType).FirstOrDefault();
            weatherList[k + 1] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                     .Select(w => w.Weather3.WeatherType).FirstOrDefault();
            weatherList[k + 2] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                     .Select(w => w.Weather.WeatherType).FirstOrDefault();
            weatherList[k + 3] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                     .Select(w => w.Weather1.WeatherType).FirstOrDefault();
        }

        /// <summary>
        /// This function creates 2 Arrays for Crime information per state for the view
        /// </summary>
        /// <param name="actualCrimeList">The actual crime number from the DB per state</param>
        /// <param name="crimeCheck">The function to check the range for crime for display purposes (safe/not as safe, etc...)</param>
        /// <param name="i">Counter for the loop this function resides, to capture current crime info</param>
        /// <param name="state">Pull data based on the state in question</param>
        public void CreateCrimeLists(decimal[] actualCrimeList, int[] crimeCheck, int i, string state)
        {
            actualCrimeList[i] = repo.States.ToList().Where(s => s.Location.Name.Contains(state))
                    .Select(c => c.CrimeRate).FirstOrDefault();
            crimeCheck[i] = new QuestionnaireController(repo).CheckCrime(actualCrimeList[i]);
        }

        /// <summary>
        /// This function involves getting the hotel cost by star rating of a specified state.
        /// </summary>
        /// <param name="id">This represents the number id associated with the number of top locations.</param>
        /// <returns>A JsonResult containing the hotel cost by star rating and the location ID that is is associated with.
        /// </returns>
        public JsonResult CostForHotelStars(int? id)
        {
                int check = id ?? default(int);

                //These are the main/largest airports codes associated with the states in the top locations page
                //The states in order are: "WYOMING", "HAWAII", "ARIZONA", "NEW YORK", "CALIFORNIA", "MARYLAND", "MASSACHUSETTS", "NEVADA", "ILLINOIS", "LOUISIANA", "MONTANA", "WASHINGTON"}
                string[] AirportCode = { "JAC","HNL", "PHX", "JFK", "LAX", "BWI", "BOS",
                "LAS","ORD", "MSY", "BZN", "SEA"};

                //Call Function that has the day after a specified amount of days and the next day
                Tuple<DateTime, DateTime> days = GetDateAndNext(1, 1);

                List<decimal> costsByStar = new List<decimal>();

                //Run for every star value.
                for (int i = 1; i < 6; i++)
                {
                    List<decimal> hotelstuff = new SearchesController(repo).GetHotelData(days.Item1, days.Item2, 1, AirportCode[check - 1], i);
                    costsByStar.Add(hotelstuff.First());
                }

                var data = new { locationID = check, CostsByStar = costsByStar };

                //return costs for each hotel value based on location and star value in json format
                return Json(data, JsonRequestBehavior.AllowGet);   
        }

        /// <summary>
        /// Get a tuple that has the day after a specified amount of days and the next day.
        /// </summary>
        /// <param name="daysAfterToday">StartDate</param>
        /// <param name="daysAfterStart">EndDate</param>
        /// <returns></returns>
        public Tuple<DateTime,DateTime> GetDateAndNext(int daysAfterToday, int daysAfterStart)
        {
            DateTime startDate = DateTime.Now.AddDays(daysAfterToday);
            Tuple<DateTime, DateTime> dates = new Tuple<DateTime, DateTime>(startDate, startDate.AddDays(daysAfterStart));
            return dates;
        }

        /*
         * This method returns the "Display" View if the logged in user is an admin, otherwise it returns the "Index" View
         * The Display View shows a table with the Top Searched Locations* 
         */
        [Authorize]
        public ActionResult Display()
        {
            //If the user is an admin...
            if (new PeopleController(repo).IsAdminUser(User.Identity))
            {
                //Create a sorted list of top locations, find the total number of items, and display the top 10.
                List<TopSearched> topLocations = GetTopLocations();
                ViewBag.NumSearches = topLocations.Count();
                return View(topLocations.Take(10));
            }
            else
            {
                //Redirect the User to the Index method.
                return RedirectToAction("Index");
            }

        }

        /// <summary>
        /// This method gets the top locations searched. It looks for all the endStates that have been searched and then figures out how many times it was searched.It then returns the list of states in order from most searched to "least" searched. 
        ///Note: this does not consider places with no search history
        /// </summary>
        /// <returns>A sorted list of the Top Searched Locations</returns>
        private List<TopSearched> GetTopLocations()
        {
            //Query the end states from the Database
            var endStates = db.Searches
                .GroupBy(s => s.EndAirport)
                .Select(s => s.FirstOrDefault())
                .GroupBy(a => a.Airport1.LocationID)
                .Select(a => a.FirstOrDefault())
                .ToList();

            //Create a new List of "TopSearched" items.
            List<TopSearched> top = new List<TopSearched>();

            //Go trhough our state list, state by state.
            foreach (var state in endStates)
            {
                //Count the number of times it's been searched.
                int numSearches = db.Searches.Where(s => s.Airport1.LocationID == state.Airport1.LocationID)
                    .Select(s => s.Airport1.LocationID)
                    .Count();

                //Add the state with the number of times searched to our list to "TopSearched" items.
                top.Add(new TopSearched { EndState = state.Airport1.Location.Name, TimesSearched = numSearches });
            }

            //Sort the list so the top searched state is at the top of the table.
            List<TopSearched> sorted = top
                .OrderByDescending(t => t.TimesSearched)
                .ToList();

            //Return the sorted list.
            return sorted;
        }

         /// <summary>
         /// This method is for the Javascript file that adds 10 more rows to the table for the Admin Page. The table holds the Name of the State and the number of times that state was searched as an end state.
         /// </summary>
         /// <param name="id"></param>
         /// <returns>A Table of Json Results per above</returns>
        public JsonResult AddElements(int? id)
        {
            //Check if there was a number passed in or not. If not, set it to the default int.
            int check = id ?? default(int);

            //Get the list of top locations, skip the number passed in, take the next 10.
            var list = GetTopLocations().Skip(check).Take(10);

            //Create the data using this list. The list conntains "TopSearched" items.
            var data = new { EndState = list.Select(s => s.EndState).ToList(), TimesSearched = list.Select(s => s.TimesSearched).ToList() };

            //Return the Json to the Javascript to be added to the table. 
            return Json(data, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// This function gets a list of airports for a specified location id, specifically states
        /// </summary>
        /// <param name="id">The location id for the DB query</param>
        /// <returns>Json results with airport information including airport id and direction for state locations </returns>
        public JsonResult FillData(string id)
        {
            List<Airport> airports = db.Airports.Where(a => a.Location.Name == id).Select(s => s).ToList();
            List<AirportJS> sAirports = new List<AirportJS>();
            foreach (Airport airport in airports)
            {
                sAirports.Add(new AirportJS { AirportID = airport.AirportID, Direction = airport.Direction, AirportCode = airport.AirportCode });
            }

            //turn the list into a json object.
            string result = JsonConvert.SerializeObject(sAirports, Newtonsoft.Json.Formatting.None);

            //return the json object.
            return Json(result, JsonRequestBehavior.AllowGet);
        }
    }
}