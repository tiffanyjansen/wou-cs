using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Xml;
using System.Web.Mvc;
using Newtonsoft.Json;
using readygotravel.Models;
using readygotravel.Models.ViewModels;
using Microsoft.AspNet.Identity;
using readygotravel.Abstract;
using System.Web.Script.Serialization;

namespace readygotravel.Controllers
{

    public class SearchesController : Controller
    {
        //The fields for the controller. (db = Database, userDB = ASP.NET User Database, repo = Repository)
        private DBContext db = new DBContext();
        private ApplicationDbContext userDB = new ApplicationDbContext();
        private ITravelRepo repo;

        /// <summary>
        /// The constructor for the SearchesController so testing won't be such a pain in the butt.
        /// </summary>
        /// <param name="travelRepo">The repository, mostly to make testing easier</param>
        public SearchesController(ITravelRepo travelRepo)
        {
            repo = travelRepo;
        }

        /// <summary>
        /// The result page. This method does all the 
        /// </summary>
        /// <param name="id">The ID of the Result object created in the Search View</param>
        /// <param name="left">The amount of money left afterr the flight/hotel costs are removed</param>
        /// <returns>The Result View</returns>
        public ActionResult Result(int? id, decimal? left)
        {
            if (id == null)
            {
                return RedirectToAction("Create");
            }
            if (left == null)
            {
                return RedirectToAction("Create");
            }

            Result result = db.Results.Find(id);

            //Check if a logged in user made the search that has this result. Note: UserID of -1 means it has no user.
            if(result.Search.UserID != -1)
            {
                //Check if user is logged in.
                if (Request.IsAuthenticated)
                {
                    //Get logged in users id.
                    string id2 = User.Identity.GetUserId();

                    //Get peopleID from UserId.
                    int peopleID = db.People.Where(n => n.UserID.Equals(id2)).Select(n => n.PersonID).First();

                    //Note: a UserID by a Search is associated with a PersonID by a Person.
                    //Check if the Search associtate with this Result is relate to the current logged in user.
                    if (result.Search.UserID != peopleID)
                        return RedirectToAction("Create");
                }
                else
                    return RedirectToAction("Create");
            }

            //Make check to see if Adding favorites is an option. Check: if the user is associated with the end state that was searched (in UserState table).
            if (Request.IsAuthenticated)
            {
                //Get logged in users id.
                string id2 = User.Identity.GetUserId();

                //Get peopleID from UserId.
                int peopleID = db.People.Where(n => n.UserID.Equals(id2)).Select(n => n.PersonID).First();//Get logged in users id.

                //Check if user and state is column in UserState table.
                ViewBag.HasFavorite = new FavoritesController(repo).FavoriteOfUserCheck(peopleID, result.Search.Airport1.Location.LocationID);

            }

            ViewBag.Remaining = left;
            if (result.AvgFlightAmount == null)
            {
                ViewBag.Total = result.AvgHotelAmount + result.AvgFoodCost;
            }
            else if (result.AvgHotelAmount == null)
            {
                ViewBag.Total = result.AvgFlightAmount + result.AvgFoodCost;
            }
            else
            {
                ViewBag.Total = result.AvgFlightAmount + result.AvgHotelAmount + result.AvgFoodCost;
            }

            //Makes sure that the average flight amount is not set to 0 or -1 as this would indicate errors
            if (result.AvgFlightAmount != (decimal)0.00 && result.AvgFlightAmount != (decimal)-1.00)
            {
                if (result.AvgFlightAmount == null)
                {
                    if (result.AvgHotelAmount == (decimal)0.00)
                    {
                        ViewBag.Result = 5;
                        ViewBag.Message = "We're sorry.";
                        ViewBag.Message2 = "There were no hotels given your specific parameters.";
                    }
                    //Compare dollar amount available and update the ViewBag results accordingly. 
                    else if (left >= 0)
                    {
                        ViewBag.Result = 0;
                        ViewBag.Message = "You can stay there for: ";
                        ViewBag.Message2 = "And you have this much remaining: ";
                    }
                    else if (left >= -500)
                    {
                        ViewBag.Result = 1;
                        ViewBag.Message = "You can almost stay if you had amount this much more: ";
                    }
                    else
                    {
                        ViewBag.Result = 2;
                        ViewBag.Message = "You are too broke and can't stay on that budget, you would need this much more: ";
                    }
                }
                else
                {
                    //Compare dollar amount available and update the ViewBag results accordingly. 
                    if (left >= 0)
                    {
                        ViewBag.Result = 0;
                        ViewBag.Message = "You can travel there for: ";
                        ViewBag.Message2 = "And you have this much remaining: ";
                    }
                    else if (left >= -500)
                    {
                        ViewBag.Result = 1;
                        ViewBag.Message = "You can almost go if you had amount this much more: ";
                    }
                    else
                    {
                        ViewBag.Result = 2;
                        ViewBag.Message = "You are too broke and can't go on that budget, you would need this much more: ";
                    }
                }


                //Check if the decimal value is 0, if so skip this.
                decimal num = RoundToFourths(result.AvgHotelStar);
                //Get the whole number of the rounded number
                decimal integral = Math.Truncate(num);
                //Get the decimal value of the rounded number
                decimal @decimal = num - integral;

                //Set the whole number and decimal to the View.
                ViewBag.Stars = integral;
                ViewBag.Decimal = @decimal;
            }
            else if (result.AvgFlightAmount == -1)
            {
                ViewBag.Result = 3;
                ViewBag.Message = "Oops your ending location was the same as your starting location!";
                ViewBag.Message2 = "Please update your search options and try again.";
            }
            else
            {
                ViewBag.Result = 4;
                ViewBag.Message = "No flights were available that matched your search criteria.";
                ViewBag.Message2 = "Please update your search options and try again.";
            }

            ViewBag.NumDays = (result.Search.EndDate - result.Search.StartDate).Days;

            decimal[] currencies = GetCurrencyExchanges(result);
            ViewBag.Exchange = currencies[0];
            ViewBag.ExchangeStart = currencies[1];
            if (result.Search.Airport != null)
            {
                ViewBag.CurrencyStart = result.Search.Airport.Location.Country.Currency.CurrencyName;
            }
            ViewBag.Symbol = result.Search.Airport1.Location.Country.Currency.CurrencySymbol;
            ViewBag.Currency = result.Search.Airport1.Location.Country.Currency.CurrencyName;

            //Present information to user
            return View(result);
        }

        /// <summary>
        /// Calls the currency API to get the current currency exchange.
        /// </summary>
        /// <param name="result">The result we want the exchanges for</param>
        /// <returns>an array with the currency exchanges needed.</returns>
        public decimal[] GetCurrencyExchanges(Result result)
        {
            //Get the currency of the ending country
            Currency endCurrency = result.Search.Airport1.Location.Country.Currency;

            //Get the API Key from the AppSecrets File.
            string currencyKey = System.Web.Configuration.WebConfigurationManager.AppSettings["currencyKey"];

            decimal[] currencyExchanges = new decimal[2];
            string currencyURL = "";
            string startKey = "";
            string endKey = "";

            if (result.Search.Airport == null)
            {

                if (endCurrency.CurrencyCode == "USD")
                {
                    currencyExchanges[0] = 1;
                    currencyExchanges[1] = 1;
                    return currencyExchanges;
                }
                else
                {
                    //Construct the Url for the currency API
                    currencyURL = "https://api.currconv.com/api/v7/convert?q=" + "USD" + "_" + endCurrency.CurrencyCode + "&compact=ultra&apiKey=" + currencyKey;
                    startKey = "USD" + "_" + endCurrency.CurrencyCode;
                    //Get the response string from the site so I can start accessing it.
                    string responseString = GetResponseString(currencyURL);

                    //The results.
                    if(responseString != null)
                    { 
                        var results = new JavaScriptSerializer().Deserialize<Dictionary<string, decimal>>(responseString);
                        currencyExchanges[0] = results[startKey];
                        currencyExchanges[1] = 1;
                    }
                    else
                    {
                        currencyExchanges[0] = 0;
                        currencyExchanges[1] = 0;
                    }

                    return currencyExchanges;
                }
            }
            else
            {
                Currency startCurrency = result.Search.Airport.Location.Country.Currency;

                if (startCurrency.CurrencyCode == "USD" && endCurrency.CurrencyCode == "USD")
                {
                    currencyExchanges[0] = 1;
                    currencyExchanges[1] = 1;
                    return currencyExchanges;
                }
                else if (startCurrency.CurrencyCode == "USD")
                {
                    //Construct the Url for the currency API
                    currencyURL = "https://api.currconv.com/api/v7/convert?q=" + startCurrency.CurrencyCode + "_" + endCurrency.CurrencyCode + "&compact=ultra&apiKey=" + currencyKey;
                    startKey = startCurrency.CurrencyCode + "_" + endCurrency.CurrencyCode;
                    //Get the response string from the site so I can start accessing it.
                    string responseString = GetResponseString(currencyURL);

                    //The results.
                    if(responseString != null)
                    {                     
                        var results = new JavaScriptSerializer().Deserialize<Dictionary<string, decimal>>(responseString);

                        currencyExchanges[0] = results[startKey];
                        currencyExchanges[1] = 1;
                    }
                    else
                    {
                        currencyExchanges[0] = 0;
                        currencyExchanges[1] = 0;
                    }
                    return currencyExchanges;
                }
                else if (endCurrency.CurrencyCode == "USD")
                {
                    //Construct the Url for the currency API
                    currencyURL = "https://free.currconv.com/api/v7/convert?q=" + endCurrency.CurrencyCode + "_" + startCurrency.CurrencyCode + "&compact=ultra&apiKey=" + currencyKey;
                    endKey = endCurrency.CurrencyCode + "_" + startCurrency.CurrencyCode;
                    //Get the response string from the site so I can start accessing it.
                    string responseString = GetResponseString(currencyURL);

                    
                    //The results.
                    if (responseString != null)
                    {
                        var results = new JavaScriptSerializer().Deserialize<Dictionary<string, decimal>>(responseString);

                        currencyExchanges[0] = 1;
                        currencyExchanges[1] = results[endKey];
                    }
                    else
                    {
                        currencyExchanges[0] = 0;
                        currencyExchanges[1] = 0;
                    }
                    
                    return currencyExchanges;
                }
                else if (endCurrency.Equals(startCurrency))
                {
                    currencyURL = "https://api.currconv.com/api/v7/convert?q=" + startCurrency.CurrencyCode + "_" + endCurrency.CurrencyCode + "&compact=ultra&apiKey=" + currencyKey;
                    startKey = "USD" + "_" + endCurrency.CurrencyCode;
                    //Get the response string from the site so I can start accessing it.
                    string responseString = GetResponseString(currencyURL);

                    //The results.
                    if (responseString != null)
                    {
                        var results = new JavaScriptSerializer().Deserialize<Dictionary<string, decimal>>(responseString);
                        currencyExchanges[0] = results[startKey];
                        currencyExchanges[1] = 1;
                    }
                    else
                    {
                        currencyExchanges[0] = 0;
                        currencyExchanges[1] = 0;
                    }
                    
                    return currencyExchanges;
                }
                else
                {
                    //Construct the Url for the currency API
                    currencyURL = "https://api.currconv.com/api/v7/convert?q=" + "USD" + "_" + endCurrency.CurrencyCode + "," + startCurrency.CurrencyCode + "_" + endCurrency.CurrencyCode + "&compact=ultra&apiKey=" + currencyKey;
                    startKey = "USD" + "_" + endCurrency.CurrencyCode;
                    endKey = startCurrency.CurrencyCode + "_" + endCurrency.CurrencyCode;
                    //Get the response string from the site so I can start accessing it.
                    string responseString = GetResponseString(currencyURL);

                    //The results.
                    if (responseString != null)
                    {
                        var results = new JavaScriptSerializer().Deserialize<Dictionary<string, decimal>>(responseString);

                        currencyExchanges[0] = results[startKey];
                        currencyExchanges[1] = results[endKey];
                    }
                    else
                    {
                        currencyExchanges[0] = 0;
                        currencyExchanges[1] = 0;
                    }
                    
                    return currencyExchanges;
                }
            }
        }
        
        /// <summary>
        /// This method rounds whatever decimal number to the nearest "fourth"
        /// </summary>
        /// <param name="num">The decimal value we wish to have rounded</param>
        /// <returns>The rounded value of the decimal passed in.</returns>
        public decimal RoundToFourths(decimal num)
        {
            bool positive = true;
            //if num is negative, make it positive and reset the bool value.
            if (num < 0)
            {
                num = num * (-1);
                positive = false;
            }

            decimal integral = Math.Truncate(num);
            decimal @decimal = num - integral;

            //If the decimal value is less than 0.12 set the decimal value to 0.
            if (@decimal <= (decimal)0.12)
                @decimal = 0;
            //If the decimal value is less than 0.37 but bigger than 0.12 set the decimal value to 0.25.
            else if (@decimal <= (decimal)0.37)
                @decimal = (decimal)0.25;
            //If the decimal value is less than 0.62 but bigger than 0.37 set the decimal value to 0.5.
            else if (@decimal <= (decimal)0.62)
                @decimal = (decimal)0.5;
            //If the decimal value is less than 0.88 but bigger than 0.62 set the decimal value to 0.75.
            else if (@decimal <= (decimal)0.88)
                @decimal = (decimal)0.75;
            //If the decimal value is bigger than 0.88 set the decimal value to 1.
            else
                @decimal = 1;

            if (@decimal == 1)
            {
                integral++;
                @decimal--;
            }

            //If the number was negative, switch it back to negative.
            if (!positive)
                return (-1) * (integral + @decimal);

            return integral + @decimal;
        }

        // GET: Searches/Create
        /// <summary>
        /// This is the original search method. It will allow users to search for both Flights and Hotels.
        /// </summary>
        /// <param name="id">The potential state being passed in.</param>
        /// <returns>The Create View</returns>
        public ActionResult Create(string id)
        {
            try {
                if (id == null)
                    GenerateLists(null);
                else if (repo.Locations.Where(l => l.Name == id).Select(l => l).First() == null)
                    GenerateLists(null);
                else
                    GenerateLists(id);

                return View();
            }
            catch
            {
                return RedirectToAction("Error404","Error");
            }
        }

        /// <summary>
        /// This method gets all of the states in the database and puts them into a list.
        /// </summary>
        /// <returns>A list of all the states in the database.</returns>
        public List<string> GetLocations(string countryName)
        {
            return repo.Locations
                .Where(l => l.Country.CountryName == countryName)
                .Select(l => l.Name).ToList();
        }

        // POST: Searches/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        /// <summary>
        /// This method is the one after the create page has it's inputs put in.
        /// </summary>
        /// <param name="searchVM">Search ViewModel</param>
        /// <param name="StartAir">Starting Airport ID</param>
        /// <param name="EndAir">Ending Airport ID</param>
        /// <param name="HotelStarValue">Star Value selected</param>
        /// <param name="FlightType">Flight Type selected</param>
        /// <returns>The Result View if there were no errors, otherwise the Create page again.</returns>
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "StartDate,EndDate,NumTravelers,MaxAmount")] SearchDashBoardVM searchVM, string StartAir, string EndAir, string HotelStarValue, string FlightType)
        {
            int id = FindPersonID();

            //Find the corresponding numbers or symbols for the given inputs of the star value and flight type.
            int star = GetHotelStarValue(HotelStarValue);
            string type = GetFlightType(FlightType);

            if (StartAir == null | EndAir == null)
            {
                ViewBag.ErrorMessage = "Please Select Your Locations and Airports";
                GenerateLists(null);
                return View();
            }

            int startAirportID = GetAirportID(StartAir);
            int endAirportID = GetAirportID(EndAir);

            //Check for errors
            if (startAirportID == -1 | endAirportID == -1)
            {
                GenerateLists(null);
                ViewBag.Error = "There was an issue with the JavaScript functionality, please try again.";
                return View();
            }

            if(searchVM.MaxAmount <= 0)
            {
                GenerateLists(null);
                return View();
            }

            //Create the search (hopefully...)
            Search search = new Search { UserID = id, NumTravelers = searchVM.NumTravelers, StartDate = searchVM.StartDate, EndDate = searchVM.EndDate, MaxAmount = searchVM.MaxAmount, StartAirport = (int)startAirportID, EndAirport = (int)endAirportID, HotelStarValue = star, FlightType = type };

            try
            {
                db.Searches.Add(search);
                db.SaveChanges();
            }
            catch (Exception)
            {
                ViewBag.Error = "There was an issue adding the search to the database, please try again.";
                GenerateLists(null);
                return View();
            }

            if (ModelState.IsValid)
            {
                //Get the starting airport code from the DB
                string startAirPort = GetAirportCode((int)search.StartAirport);

                //Get the airport code for the ending airport
                string endAirPort = GetAirportCode(search.EndAirport);

                //Do the flight API stuff and get the average cost of flights.
                decimal flightInfo = GetFlightData(search.StartDate, search.EndDate, search.NumTravelers, startAirPort, endAirPort, type);

                //Do the hotel API stuff and get the average cost of hotels.                
                List<decimal> hotelInfo = GetHotelData(search.StartDate, search.EndDate, search.NumTravelers, endAirPort, star);
                decimal hotelCost = hotelInfo.First();
                decimal avgStar = hotelInfo.LastOrDefault();

                decimal amount = (decimal)0;
                Result result;

                //Get the foodCost based on the ending state
                if (db.Airports.Where(a => a.AirportID == search.EndAirport).Select(a => a).FirstOrDefault().Location.State)
                {
                    decimal foodCost = Math.Round(db.Airports
                        .Where(a => a.AirportID == search.EndAirport)
                        .Select(a => a.Location.States.FirstOrDefault().FoodCost)
                        .FirstOrDefault(), 2);

                    //Determine total days on vacation
                    TimeSpan totalDays = search.EndDate - search.StartDate;

                    //Calcate foodCost by mulitiplying # of Days * # of Travelers * food cost average by state per day per person
                    decimal totalFoodCost = Math.Round(foodCost * search.NumTravelers * Convert.ToDecimal(totalDays.TotalDays), 2);

                    //Save Result to DB
                    result = new Result { SearchID = search.SearchID, AvgFlightAmount = flightInfo, AvgHotelAmount = hotelCost, AvgFoodCost = totalFoodCost, AvgHotelStar = avgStar };

                    //Calculate how much money is left over
                    amount = GetRemainingAmount(search, flightInfo, hotelCost, totalFoodCost);
                }
                else
                {
                    amount = GetRemainingAmount(search, flightInfo, hotelCost, (decimal)0);
                    result = new Result { SearchID = search.SearchID, AvgFlightAmount = flightInfo, AvgHotelAmount = hotelCost, AvgHotelStar = avgStar };
                }
                db.Results.Add(result);
                db.SaveChanges();

                //Now pass results as decimals to the results action
                return RedirectToAction("Result", "Searches", new { id = result.ResultID, left = amount });
            }

            ViewBag.ErrorMessage = "Please Select Your Locations and Airports";
            GenerateLists(null);
            return View(searchVM);
        }

        /// <summary>
        /// This method gets the airport code of the airport passed in.
        /// </summary>
        /// <param name="airport">The ID of the airport</param>
        /// <param name="international">The bool value of whether it's not in the US or if it is.</param>
        /// <returns>The airport code for the given airport</returns>
        //Adrian Test
        public string GetAirportCode(int airport)
        {
            return repo.Airports
                .Where(a => a.AirportID == airport)
                .Select(a => a.AirportCode).FirstOrDefault();
        }

        // GET: Searches/FlightsOnly
        /// <summary>
        /// This is the view for doing a flights only search.
        /// </summary>
        /// <param name="id">The state name if applicable</param>
        /// <returns>The flights only view page.</returns>
        [HttpGet]
        public ActionResult FlightsOnly(string id)
        {
            try { 
                if (id == null)
                    GenerateLists(null);
                else if (repo.Locations.Where(l => l.Name == id).Select(l => l).First() == null)
                    GenerateLists(null);
                else
                    GenerateLists(id);

                return View();
            }
            catch
            {
                return RedirectToAction("Error404", "Error");
            }
        }

        // POST: Searches/FlightsOnly
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        /// <summary>
        ///  This method is the one after the flights only page has it's inputs put in.
        /// </summary>
        /// <param name="searchVM">The Search ViewModel</param>
        /// <param name="StartAir">Starting Airport ID</param>
        /// <param name="EndAir">Ending Airport ID</param>
        /// <param name="FlightType">Selected Flight Type</param>
        /// <returns>The Result View if no errors, otherwise refreshes the page.</returns>
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult FlightsOnly([Bind(Include = "StartDate,EndDate,NumTravelers,MaxAmount")] SearchDashBoardVM searchVM, string StartAir, string EndAir, string FlightType)
        {

            int id = FindPersonID();

            //Find the corresponding numbers or symbols for the given inputs of the star value and flight type.
            string type = GetFlightType(FlightType);

            if (StartAir == null | EndAir == null)
            {
                ViewBag.ErrorMessage = "Please Select Your Locations and Airports";
                GenerateLists(null);
                return View();
            }

            int startAirportID = GetAirportID(StartAir);
            int endAirportID = GetAirportID(EndAir);

            //Check for errors
            if (startAirportID == -1 | endAirportID == -1)
            {
                GenerateLists(null);
                ViewBag.Error = "There was an issue with the JavaScript functionality, please try again.";
                return View();
            }

            if (searchVM.MaxAmount <= 0)
            {
                GenerateLists(null);
                return View();
            }

            //Create the search (hopefully...)
            Search search = new Search { UserID = id, NumTravelers = searchVM.NumTravelers, StartDate = searchVM.StartDate, EndDate = searchVM.EndDate, MaxAmount = searchVM.MaxAmount, StartAirport = (int)startAirportID, EndAirport = (int)endAirportID, FlightType = type };

            try
            {
                db.Searches.Add(search);
                db.SaveChanges();
            }
            catch (Exception)
            {
                ViewBag.Error = "There was an issue adding the search to the database, please try again.";
                GenerateLists(null);
                return View();
            }

            if (ModelState.IsValid)
            {
                //Get the starting airport code from the DB
                string startAirPort = GetAirportCode((int)search.StartAirport);

                //Get the airport code for the ending airport
                string endAirPort = GetAirportCode(search.EndAirport);

                //Do the flight API stuff and get the average cost of flights.
                decimal flightInfo = GetFlightData(search.StartDate, search.EndDate, search.NumTravelers, startAirPort, endAirPort, type);

                decimal amount = (decimal)0;
                Result result = new Models.Result();

                //Get the foodCost based on the ending state
                if (db.Airports.Where(a => a.AirportID == search.EndAirport).Select(a => a).FirstOrDefault().Location.State)
                {
                    decimal foodCost = GetFoodCost(search);

                    //Determine total days on vacation
                    TimeSpan totalDays = search.EndDate - search.StartDate;

                    //Calcate foodCost by multiplying # of Days * # of Travelers * food cost average by state per day per person
                    decimal totalFoodCost = Math.Round(foodCost * search.NumTravelers * Convert.ToDecimal(totalDays.TotalDays), 2);

                    //Save Result to DB
                    result = new Result { SearchID = search.SearchID, AvgFlightAmount = flightInfo, AvgFoodCost = totalFoodCost };

                    //Calculate how much money is left over
                    amount = GetRemainingAmountFlightsOnly(search, flightInfo, totalFoodCost);
                }
                else
                {
                    amount = GetRemainingAmountFlightsOnly(search, flightInfo, (decimal)0);
                    result = new Result { SearchID = search.SearchID, AvgFlightAmount = flightInfo };
                }
                db.Results.Add(result);
                db.SaveChanges();

                //Now pass results as decimals to the results action
                return RedirectToAction("Result", "Searches", new { id = result.ResultID, left = amount });
            }

            GenerateLists(null);
            ViewBag.ErrorMessage = "Please Select Your Locations and Airports";
            return View(searchVM);
        }

        /// <summary>
        /// This method gets the food cost for the search and rounds it to the nearest hundredth.
        /// </summary>
        /// <param name="search">The search we want the food cost for</param>
        /// <returns>The dollar value of the food cost.</returns>
        public decimal GetFoodCost(Search search)
        {
            decimal foodCost = Math.Round(db.Airports
                        .Where(a => a.AirportID == search.EndAirport)
                        .Select(a => a.Location.States.FirstOrDefault().FoodCost)
                        .FirstOrDefault(), 2);

            return foodCost;
        }

        // GET: Searches/HotelsOnly
        /// <summary>
        /// This is the view for doing a hotels only search.
        /// </summary>
        /// <param name="id">The state name if applicable</param>
        /// <returns>The hotels only view page.</returns>
        public ActionResult HotelsOnly(string id)
        {
            try
            {
                if (id == null)
                    GenerateLists(null);
                else if (db.Locations.Where(l => l.Name == id).Select(l => l).First() == null)
                    GenerateLists(null);
                else
                    GenerateLists(id);

                return View();
            }
            catch
            {
                return RedirectToAction("Error404", "Error");
            }
        }

        // POST: Searches/HotelsOnly
        /// <summary>
        ///  This method is the one after the hotls only page has it's inputs put in.
        /// </summary>
        /// <param name="searchVM">The Search ViewModel</param>
        /// <param name="EndAir">Ending Airport ID</param>
        /// <param name="HotelStarValue">Selected Star Value</param>
        /// <returns>The Result View if no errors, otherwise refreshes the page.</returns>
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult HotelsOnly([Bind(Include = "StartDate,EndDate,NumTravelers,MaxAmount")] SearchDashBoardVM searchVM, string EndAir, string HotelStarValue)
        {

            int id = FindPersonID();

            //Find the corresponding numbers or symbols for the given inputs of the star value and flight type.
            int star = GetHotelStarValue(HotelStarValue);

            if (EndAir == null)
            {
                ViewBag.ErrorMessage = "Please Select Your Location and Airport";
                GenerateLists(null);
                return View();
            }

            int endAirportID = GetAirportID(EndAir);

            //Check for errors
            if (endAirportID == -1)
            {
                GenerateLists(null);
                ViewBag.Error = "There was an issue with the JavaScript functionality, please try again.";
                return View();
            }

            if (searchVM.MaxAmount <= 0)
            {
                GenerateLists(null);
                return View();
            }

            //Create the search (hopefully...)
            Search search = new Search { UserID = id, NumTravelers = searchVM.NumTravelers, StartDate = searchVM.StartDate, EndDate = searchVM.EndDate, MaxAmount = searchVM.MaxAmount, EndAirport = (int)endAirportID, HotelStarValue = star };

            try
            {
                db.Searches.Add(search);
                db.SaveChanges();
            }
            catch (Exception)
            {
                ViewBag.Error = "There was an issue adding the search to the database, please try again.";
                GenerateLists(null);
                return View();
            }

            if (ModelState.IsValid)
            {
                //Get the airport code for the ending airport
                string endAirPort = GetAirportCode(search.EndAirport);

                //Do the hotel API stuff and get the average cost of hotels.                
                List<decimal> hotelInfo = GetHotelData(search.StartDate, search.EndDate, search.NumTravelers, endAirPort, star);
                decimal hotelCost = hotelInfo.First();
                decimal avgStar = hotelInfo.LastOrDefault();

                decimal amount = (decimal)0;
                Result result;

                //Get the foodCost based on the ending state
                if (db.Airports.Where(a => a.AirportID == search.EndAirport).Select(a => a).FirstOrDefault().Location.State)
                {
                    decimal foodCost = GetFoodCost(search);

                    //Determine total days on vacation
                    TimeSpan totalDays = search.EndDate - search.StartDate;

                    //Calcate foodCost by mulitiplying # of Days * # of Travelers * food cost average by state per day per person
                    decimal totalFoodCost = Math.Round(foodCost * search.NumTravelers * Convert.ToDecimal(totalDays.TotalDays), 2);

                    //Save Result to DB
                    result = new Result { SearchID = search.SearchID, AvgHotelAmount = hotelCost, AvgFoodCost = totalFoodCost, AvgHotelStar = avgStar };

                    //Calculate how much money is left over
                    amount = GetRemainingAmountHotelsOnly(search, hotelCost, totalFoodCost);
                }
                else
                {
                    amount = GetRemainingAmountHotelsOnly(search, hotelCost, (decimal)0);
                    result = new Result { SearchID = search.SearchID, AvgHotelAmount = hotelCost, AvgHotelStar = avgStar };
                }
                db.Results.Add(result);
                db.SaveChanges();

                //Now pass results as decimals to the results action
                return RedirectToAction("Result", "Searches", new { id = result.ResultID, left = amount });
            }

            ViewBag.ErrorMessage = "Please Select Your Location and Airport";
            GenerateLists(null);
            return View(searchVM);
        }

        /// <summary>
        /// This method generates all of the ViewModels necessary for the create, hotels only, and flights only views.
        /// </summary>
        /// <param name="endLocal">The End Location, if applicable.</param>
        private void GenerateLists(string endLocal)
        {
            //Create the list of stars
            List<string> starList = new List<string>();
            starList.Add("No Preference");
            starList.Add("0-1");
            starList.Add("1-2");
            starList.Add("3-4");
            starList.Add("4-5");
            ViewBag.Stars = starList;

            //Create the list of flight types
            List<string> typeList = new List<string>();
            typeList.Add("Economy");
            typeList.Add("Business");
            ViewBag.Flight = typeList;

            //Create the list of continents
            ViewBag.Continents = repo.Continents.Select(c => c.ContinentName).ToList();

            //Create the list of countries.            
            ViewBag.Countries = GetCountries(repo.Continents.FirstOrDefault());

            ViewBag.Cities = GetLocations(GetCountries(repo.Continents.FirstOrDefault()).FirstOrDefault());
            string StrLocation = (GetLocations(GetCountries(repo.Continents.FirstOrDefault()).FirstOrDefault())).FirstOrDefault();
            ViewBag.Airports = repo.Airports.Where(a => a.Location.Name == StrLocation).OrderBy(a => a.AirportCode).Select(a => a).ToList();

            if (endLocal == null)
            {
                ViewBag.EndCities = GetLocations(GetCountries(repo.Continents.FirstOrDefault()).FirstOrDefault());               
                ViewBag.EndAirports = repo.Airports.Where(a => a.Location.Name == StrLocation).OrderBy(a => a.AirportCode).Select(a => a).ToList();
            }                
            else
            {
                Location endLocation = repo.Locations.Where(l => l.Name == endLocal).Select(l => l).FirstOrDefault();
                List<string> EndLocations = new List<string>();
                EndLocations.Add(endLocation.Name);
                foreach (string name in GetLocations(GetCountries(repo.Continents.FirstOrDefault()).FirstOrDefault()))
                {
                    if (endLocation.Name != name)
                    {
                        EndLocations.Add(name);
                    }
                }
                ViewBag.EndCities = EndLocations;
                ViewBag.EndAirports = repo.Airports.Where(a => a.Location.Name == endLocal).OrderBy(a => a.AirportCode).Select(a => a).ToList();
            }                
        }

        /// <summary>
        /// This method gets the list of countries
        /// </summary>
        /// <param name="continent">The continent we want the countries from</param>
        /// <returns>The list of country names</returns>
        private List<string> GetCountries(Continent continent)
        {
            return repo.Countries
                .Where(c => c.Continent == continent)
                .Select(c => c.CountryName).ToList();
        }

        /// <summary>
        /// This method gets the person who is logged in, ID #.
        /// </summary>
        /// <returns>The ID number of the perosn logged in, or -1.</returns>
        private int FindPersonID()
        {
            //Get the username of the user that is logged in.
            string userName = User.Identity.Name;

            //Find the userID for the person logged in.
            string userID = userDB.Users
                .Where(u => u.UserName == userName)
                .Select(un => un.Id).FirstOrDefault();

            //Find the personID for the person logged in.
            int? personID = repo.People
                .Where(p => p.UserID == userID)
                .Select(u => u.PersonID).FirstOrDefault();

            //If there is no user logged in, set the userID to -1, otherwise set it to the user logged in.
            if (personID == null || personID == 0)
            {
                return -1;
            }
            else
            {
                return (int)personID;
            }
        }

        /// <summary>
        /// This method gets the remaining budget after a search has been made.
        /// </summary>
        /// <param name="search">The search that was made</param>
        /// <param name="flight">The avg flight cost</param>
        /// <param name="hotel">The avg hotl cost</param>
        /// <param name="food">The avg food cost</param>
        /// <returns>Remaining budget</returns>
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

        /// <summary>
        /// This method gets the remaining budget after a search has been made.
        /// </summary>
        /// <param name="search">The search that was made</param>
        /// <param name="flight">The avg flight cost</param>
        /// <param name="food">The avg food cost</param>
        /// <returns>Remaining budget</returns>
        public decimal GetRemainingAmountFlightsOnly(Search search, decimal flight, decimal food)
        {
            decimal amount = search.MaxAmount;
            if (search.MaxAmount < 0)
            {
                amount = amount + (flight + food);
                amount = Math.Round(amount, 2);
            }
            else
            {
                amount = amount - (flight + food);
                amount = Math.Round(amount, 2);
            }
            return amount;
        }

        /// <summary>
        /// This method gets the remaining budget after a search has been made.
        /// </summary>
        /// <param name="search">The search that was made</param>
        /// <param name="hotel">The avg hotel cost</param>
        /// <param name="food">The avg food cost</param>
        /// <returns>Remaining budget</returns>
        public decimal GetRemainingAmountHotelsOnly(Search search, decimal hotel, decimal food)
        {
            decimal amount = search.MaxAmount;
            if (search.MaxAmount < 0)
            {
                amount = amount + (hotel + food);
                amount = Math.Round(amount, 2);
            }
            else
            {
                amount = amount - (hotel + food);
                amount = Math.Round(amount, 2);
            }
            return amount;
        }

        /// <summary>
        /// This method gets the largest value of the star value selected.
        /// </summary>
        /// <param name="HotelStarValue">The string passed back from the dropdown</param>
        /// <returns>The largest star value, or 0.</returns>
        public int GetHotelStarValue(string HotelStarValue)
        {
            //Split the string into pieces
            string[] stars = HotelStarValue.Split('-');
            string last = stars.Last();
            int n;
            //If the user selected a number sequence convert the number to an int
            if (int.TryParse(last, out n))
            {
                return Convert.ToInt32(last);
            }
            //Else return 0
            else
            {
                return 0;
            }
        }

        /// <summary>
        /// This method returns the AirportID part of the stuff returned from the JavaScript
        /// </summary>
        /// <param name="air">The string returned from the JavaScript</param>
        /// <returns>the ID of the airport passed back by the JavaScript</returns>
        public int GetAirportID(string air)
        {
            int n;
            if (int.TryParse(air, out n))
            {
                return Convert.ToInt32(air);
            }
            else
            {
                return -1; //there must have been an issue.
            }
        }

        /// <summary>
        /// Function to check flight type string and convert to Character used for API
        /// </summary>
        public string GetFlightType(string FlightType)
        {
            if (FlightType == "Economy")
            {
                return "E";
            }
            else if (FlightType == "Business")
            {
                return "B";
            }
            else
            {
                throw new ArgumentOutOfRangeException("Flight must be either Economy or Business");
            }
        }

        /// <summary>
        /// This method calls the Flight API.
        /// </summary>
        /// <param name="startDate">The start date of travel</param>
        /// <param name="endDate">The end date of travel</param>
        /// <param name="travelers">The number of travelers going</param>
        /// <param name="startAirPort">Start Airport Code</param>
        /// <param name="endAirPort">End Airport Code</param>
        /// <param name="type">Flight Type</param>
        /// <returns>The avg flight cost</returns>
        private decimal GetFlightData(DateTime startDate, DateTime endDate, int travelers, string startAirPort, string endAirPort, string type)
        {
            //Build URL for GET request to Airport API, including my key and url parameters
            string flightID = System.Web.Configuration.WebConfigurationManager.AppSettings["flightID"];
            string flightKey = System.Web.Configuration.WebConfigurationManager.AppSettings["flightKey"];
            string urlInfo = "http://developer.goibibo.com/api/search/?app_id=" + flightID + "&app_key=" + flightKey + "&format=json&source=" + startAirPort + "&destination=" + endAirPort + "&dateofdeparture=" + startDate.Year + startDate.Month.ToString("00") + startDate.Day.ToString("00") + "&dateofarrival=" + endDate.Year + endDate.Month.ToString("00") + endDate.Day.ToString("00") + "&seatingclass=" + type + "&adults=" + travelers + "&children=0&infants=0&counter=0";

            //Get the response string by accessing the site and reading it all the way through.
            string responseString = GetResponseString(urlInfo);

            //Convert string into a json object
            var results = JsonConvert.DeserializeObject<dynamic>(responseString);

            //Int for totalling flights
            int sum = 0;

            //If the results returned data and has values
            try
            {
                if (results.data.onwardflights.HasValues)
                {
                    //Counter
                    int i = 0;
                    //Loop through the array inside json
                    foreach (var value in results.data.onwardflights)
                    {
                        //Add flight data and taxes to the total sum for all flights
                        sum = sum + Convert.ToInt32(results.data.onwardflights[i].fare.totalfare)
                            + Convert.ToInt32(results.data.onwardflights[i].fare.totaltaxes);
                        i++;
                    }
                    //Divide by the total number of flights to get an average
                    sum = sum / results.data.onwardflights.Count;
                }
            }
            catch
            {
                sum = -1;
            }

            decimal cost = Convert.ToDecimal(sum);

            //Sum will be 0 if there were no flights available, -1 if there was an error
            if (sum != 0 && sum != -1)
            {
                cost = cost / 100;
            }
            //Return the average, rounded to 2 decimal places.
            return Math.Round(cost, 2);
        }

        /// <summary>
        /// This method calls the Hotel API
        /// </summary>
        /// <param name="startDate">The start date of travel</param>
        /// <param name="endDate">The end date of travel</param>
        /// <param name="travelers">The number of travelers going</param>
        /// <param name="location">The End Location Airport Code</param>
        /// <param name="star">The largest star value we want</param>
        /// <returns>The average hotel cost and star value based on input</returns>
        public List<decimal> GetHotelData(DateTime startDate, DateTime endDate, int travelers, string location, int? star)
        {
            int numRooms = GetNumRooms(travelers);

            //Get the API Key from the AppSecrets File.
            string hotelKey = System.Web.Configuration.WebConfigurationManager.AppSettings["hotelKey"];

            //Construct the Url for the hotel API
            string hotelURL = "http://api.hotwire.com/v1/search/hotel?apikey=" + hotelKey + "&dest=" + location + "&rooms=" + numRooms + "&adults=" + travelers + "&children=0&startdate=" + startDate.Month.ToString("00") + "/" + startDate.Day.ToString("00") + "/" + startDate.Year + "&enddate=" + endDate.Month.ToString("00") + "/" + endDate.Day.ToString("00") + "/" + startDate.Year;

            //Get the response string from the site so I can start accessing it.
            string responseString = GetResponseString(hotelURL);

            //Turn the string into XML Format.
            XmlDocument doc = new XmlDocument();
            doc.LoadXml(responseString);

            //Get all the elements by the "total price" tag.
            XmlNodeList priceList = doc.GetElementsByTagName("TotalPrice");
            XmlNodeList starList = doc.GetElementsByTagName("StarRating");

            //Make things easy to access.
            decimal totalPrice = 0;
            decimal totalStar = 0;
            List<decimal> retList = new List<decimal>();

            //Check if star value passed in is null or 0.
            if (star == null | star == 0)
            {
                //Average out all the prices and star ratings and return that average.                
                if (priceList.Count == 0)
                {
                    retList.Add(0);
                    return retList;
                }
                for (int i = 0; i < priceList.Count; i++)
                {
                    decimal price = Convert.ToDecimal(priceList.Item(i).InnerXml);
                    decimal starValue = Convert.ToDecimal(starList.Item(i).InnerXml);
                    totalPrice += price;
                    totalStar += starValue;
                }
                //Round price to 2 decimal places so it's actually a price.
                decimal hotelPrice = Math.Round((totalPrice / priceList.Count), 2);
                retList.Add(hotelPrice);

                //Round starValue to 2 decimal places so it's cleaner.
                decimal StarAvg = Math.Round((totalStar / starList.Count), 2);
                retList.Add(StarAvg);

                return retList;
            }
            //Check the starList to the starvalue passed in.
            else
            {
                //counter
                int j = 0;

                for (int i = 0; i < starList.Count; i++)
                {
                    decimal starValue = Convert.ToDecimal(starList.Item(i).InnerXml);
                    if (starValue >= star - 1 && starValue <= star)
                    {
                        decimal price = Convert.ToDecimal(priceList.Item(i).InnerXml);
                        decimal stars = Convert.ToDecimal(starList.Item(i).InnerXml);
                        totalPrice += price;
                        totalStar += stars;
                        j++;
                    }
                }
                if (j == 0)
                {
                    retList.Add(0);
                    return retList;
                }
                //Round price to 2 decimal places so it's actually a price.
                decimal hotelPrice = Math.Round((totalPrice / j), 2);
                retList.Add(hotelPrice);

                //Round starValue to 2 decimal places so it's cleaner.
                decimal StarAvg = Math.Round((totalStar / j), 2);
                retList.Add(StarAvg);

                //return the list
                return retList;
            }
        }

        /// <summary>
        /// Return the number of rooms needed for the hotel.
        /// </summary>
        /// <param name="travelers">The number of travelers</param>
        /// <returns>The number of rooms necessary for the trip</returns>
        public int GetNumRooms(int travelers)
        {
            if (travelers % 2 == 0)
            {
                if (travelers == 0)
                {
                    return 1;
                }
                return travelers / 2;
            }
            else
                return (travelers + 1) / 2;
        }

        /// <summary>
        /// This method gets the response string for the APIs.
        /// </summary>
        /// <param name="url">The url for the APIs</param>
        /// <returns>The Json or XML object from the API</returns>
        private string GetResponseString(string url)
        {
            try
            {
                //Make a request using my urlInfo, then grab response information 
                WebRequest request = WebRequest.Create(url);
                WebResponse response = request.GetResponse();
                Stream information = response.GetResponseStream();
                StreamReader reader = new StreamReader(information);

                //Grab full response information, read to the end of the data and put it into a string
                return reader.ReadToEnd();
            }
            catch (Exception)
            {
                return null;
            }
        }

        /// <summary>
        /// This method gets all of the airports connected to the city/state name passed from 
        /// the JavaScript file.
        /// </summary>
        /// <param name="id">The name of the country selected.</param>
        /// <returns>A Json object.</returns>
        [HttpGet]
        public JsonResult FindAirports(string id)
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

        /// <summary>
        /// This method gets all of the cities connected to the country name passed from 
        /// the JavaScript file.
        /// </summary>
        /// <param name="id">The name of the country selected.</param>
        /// <returns>A Json object.</returns>
        [HttpGet]
        public JsonResult FindCities(string id)
        {
            Country country = repo.Countries //Get the country
                .Where(c => c.CountryName.Equals(id))
                .Select(c => c).FirstOrDefault();

            List<CountryCityJS> cityStates = new List<CountryCityJS>();
            List<Location> locations = GetLocationsByCountry(country.CountryID);

            foreach (Location location in locations)
            {
                cityStates.Add(new CountryCityJS { ID = location.LocationID, Name = location.Name });
            }

            string result = JsonConvert.SerializeObject(cityStates, Newtonsoft.Json.Formatting.None,
                    new JsonSerializerSettings() { ReferenceLoopHandling = ReferenceLoopHandling.Ignore });

            return Json(result, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// This method gets all of the countries connected to the continent name passed from 
        /// the JavaScript file.
        /// </summary>
        /// <param name="id">The name of the continent selected.</param>
        /// <returns>A Json object.</returns>
        [HttpGet]
        public JsonResult FindCountries(string id)
        {
            Debug.WriteLine("We made it to the countries stuff");

            Continent continent = repo.Continents
                .Where(c => c.ContinentName == id)
                .Select(c => c).FirstOrDefault();

            List<CountryCityJS> countries = new List<CountryCityJS>();

            foreach (Country country in continent.Countries.ToList())
            {
                countries.Add(new CountryCityJS { ID = country.CountryID, Name = country.CountryName });
            }

            string result = JsonConvert.SerializeObject(countries, Newtonsoft.Json.Formatting.Indented,
                new JsonSerializerSettings { ReferenceLoopHandling = ReferenceLoopHandling.Ignore });

            return Json(result, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// This is the method that gets the cities by passing in the country ID. I will be testing this method as 
        /// part of Sprint 4.
        /// </summary>
        /// <param name="id">The ID of the country</param>
        /// <returns>A list of the International Cities in the Country</returns>
        public List<Location> GetLocationsByCountry(int id)
        {
            return repo.Locations
                    .Where(l => l.CountryID == id)
                    .Select(l => l).ToList();
        }

        /// <summary>
        /// This will just set a viewBag for searches count and then just return the DeleteAll view.
        /// </summary>
        /// <returns>A DeleteAll view.</returns>
        [Authorize]
        public ActionResult DeleteAll()
        {
            //Get logged in users id.
            string id = User.Identity.GetUserId();

            //Get peopleID from UserId.
            int peopleID = repo.People.Where(n => n.UserID.Equals(id)).Select(n => n.PersonID).First();

            //Get searches done by peopleID.
            var searches = repo.Searches.Where(n => n.UserID == peopleID).ToList();

            ViewBag.searchesCount = searches.Count();
            return View();
        }

        /// <summary>
        /// This will delete all searches and results relating to the user that is signed in.
        /// </summary>
        /// <returns>It will just return a redirect to the Searches/UserSearches.cshtml page.</returns>
        [Authorize]
        [HttpPost, ActionName("DeleteAll")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteAllConfirmed()
        {
            //Get logged in users id.
            string id = User.Identity.GetUserId();

            //Get peopleID from UserId.
            int peopleID = repo.People.Where(n => n.UserID.Equals(id)).Select(n => n.PersonID).First();

            //Get searches done by peopleID.
            var searches = db.Searches.Where(n => n.UserID == peopleID).ToList();

            var results = db.Results.ToList();

            //Deletes everyting in the list of searches for a user.
            for (int i = 0; i < searches.Count(); i++)
            {
                //The result associated with a search will need to be deleted before a search.
                if (results.Where(n => n.SearchID == searches[i].SearchID).Any())
                    db.Results.Remove(results.Where(n => n.SearchID == searches[i].SearchID).First());
                db.Searches.Remove(searches[i]);
            }

            db.SaveChanges();
            return RedirectToAction("UserSearches", "Searches");
        }

        /// <summary>
        /// This method was already here. Not sure what it does.
        /// </summary>
        /// <param name="disposing">true or false?</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        /// <summary>
        /// This will get the list of searches done by the current user and then pass them to the view to display them.
        /// </summary>
        /// <returns>The UserSearches view with a list of searches</returns>
        [Authorize]
        public ActionResult UserSearches()
        {
            //Get logged in users id.
            string id = User.Identity.GetUserId();

            //Get peopleID from UserId.
            int peopleID = repo.People.Where(n => n.UserID.Equals(id)).Select(n => n.PersonID).First();

            //Get searches done by peopleID.
            var searches = repo.Searches.Where(n => n.UserID == peopleID).OrderByDescending(n => n.SearchID);

            //Have the total number of searches.
            ViewBag.TotalSearches = searches.Count();

            return View(searches.Take(3).ToList());
        }

        /// <summary>
        /// This will just gather all search related information from a user and then return it as a JsonResult.
        /// </summary>
        /// <param name="id">This is the starting point of the next 10 searches in relation to the database.</param>
        /// <returns>A JsonResult with the information from a list of searches.</returns>
        [Authorize]
        public JsonResult AddSearches(int? id)
        {

            int check = id ?? default(int);

            //Get logged in users id.
            string id2 = User.Identity.GetUserId();

            //Get peopleID from UserId.
            int peopleID = repo.People.Where(n => n.UserID.Equals(id2)).Select(n => n.PersonID).First();

            //Get searches done by peopleID.
            var moreSearches = GetSearchListSectionByUser(peopleID, check, 10);

            //Setting up lists involved in displaying.
            var startDates = moreSearches.Select(n => n.StartDate.ToString("MM/dd/yy")).ToList();
            var endDates = moreSearches.Select(n => n.EndDate.ToString("MM/dd/yy")).ToList();
            var numTravelers = moreSearches.Select(n => n.NumTravelers).ToList();
            var maxAmounts = moreSearches.Select(n => n.MaxAmount).ToList();

            List<String> startAirports = new List<string>();
            for(int i = 0; i < moreSearches.Count; i++)
            {
                startAirports.Add(AirportCheck(moreSearches[i]));
            }
            var endAirports = moreSearches.Select(n => n.Airport1).Select(n => n.Location).Select(n => n.Name).ToList();
            List<String> startAirportCodes = new List<string>();
            for (int i = 0; i < moreSearches.Count; i++)
            {
                startAirportCodes.Add(AirportCodeCheck(moreSearches[i]));
            }


            var endAirportCodes = moreSearches.Select(n => n.Airport1).Select(n => n.AirportCode).ToList();
            var hotelStarValues = moreSearches.Select(n => n.HotelStarValue).ToList();
            List<String> startCountry = new List<string>();
            for (int i = 0; i < moreSearches.Count; i++)
            {
                startCountry.Add(CountryCheck(moreSearches[i]));
            }


            var endCountry = moreSearches.Select(n => n.Airport1).Select(n => n.Location).Select(n => n.Country).Select(n => n.CountryName);

            //Setting up result related variables for display.
            var averageFlightAmounts = moreSearches.Select(n => n.Results.Select(n2 => n2.AvgFlightAmount));
            var averateHotelAmounts = moreSearches.Select(n => n.Results.Select(n2 => n2.AvgHotelAmount));
            var averageFoodCosts = moreSearches.Select(n => n.Results.Select(n2 => n2.AvgFoodCost));
            var averageHotelStars = moreSearches.Select(n => n.Results.Select(n2 => n2.AvgHotelStar));


            //Data that will be returned to javascript file.
            var data = new { StartDates = startDates, EndDates = endDates, NumTravelers = numTravelers, MaxAmounts = maxAmounts, StartAirports = startAirports, EndAirports = endAirports, StartAirportCodes = startAirportCodes, EndAirportCodes = endAirportCodes, HotelStarValues = hotelStarValues, AverageFlightAmounts = averageFlightAmounts, AverageHotelAmounts = averateHotelAmounts, AverageFostCosts = averageFoodCosts, AverageHotelStars = averageHotelStars, StartCountry = startCountry, EndCountry = endCountry };

            return Json(data, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// This method checks to see if there is a start airport in the search or not
        /// </summary>
        /// <param name="search">The search we are checking</param>
        /// <returns>The starting location, or null</returns>
        public string AirportCheck(Search search)
        {
            if (search.Airport == null)
                return null;
            else
                return search.Airport.Location.Name;

        }

        /// <summary>
        /// This method checks to see if there is a start airport in the search or not
        /// </summary>
        /// <param name="search">The search we are checking</param>
        /// <returns>The starting airport code, or null</returns>
        public string AirportCodeCheck(Search search)
        {
            if (search.Airport == null)
                return null;
            else
                return search.Airport.AirportCode;

        }

        /// <summary>
        /// This method checks to see if there is a start airport in the search or not
        /// </summary>
        /// <param name="search">The search we are checking</param>
        /// <returns>The starting country name, or null</returns>
        public string CountryCheck(Search search)
        {
            if (search.Airport == null)
                return null;
            else
                return search.Airport.Location.Country.CountryName;

        }

        /// <summary>
        /// This will return a specified section of a search list by a user.
        /// </summary>
        /// <param name="peopleID">The ID of a person who made searches.</param>
        /// <param name="skipAmount">The amount of items it will skip over in the list of searches by a user.</param>
        /// <param name="size">The amount of itmes taken from a search list.</param>
        /// <returns></returns>
        public List<Search> GetSearchListSectionByUser(int peopleID, int skipAmount, int size)
        {
            return repo.Searches.Where(n => n.UserID == peopleID).OrderByDescending(n => n.SearchID).Skip(skipAmount).Take(size).ToList();
        }
    }
}