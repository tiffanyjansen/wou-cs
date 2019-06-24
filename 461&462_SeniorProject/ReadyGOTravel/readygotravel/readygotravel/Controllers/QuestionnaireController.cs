using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using readygotravel.Abstract;
using readygotravel.Models;

namespace readygotravel.Controllers
{
    public class QuestionnaireController : Controller
    {
        //database access fields
        private DBContext db = new DBContext();
        private ITravelRepo repo;
        public QuestionnaireController(ITravelRepo travelRepo)
        {
            repo = travelRepo;
        }

        //initialize all lists and variables needed for generate questionairre results
        private List<State> climateStates = new List<State>();
        private List<State> geographyStates = new List<State>();
        private List<State> queryStates = new List<State>();
        private List<SelectListItem> SeasonList;
        private List<SelectListItem> WeatherList;
        private List<SelectListItem> TempList;
        private List<SelectListItem> ClimateList;
        private List<SelectListItem> GeoList;

        /// <summary>
        /// Returns a list of states that contain the user selected attributes
        /// </summary>
        /// <returns>Posts the list back to the index view page</returns>
        // GET: Questionnaire
        [HttpGet]
        public ActionResult Index()
        {
            //generate the needed lists for the drop downs on the view page
            GenerateLists();

            //store query string elements
            List<State> result = new List<State>();
            int temp1, temp2, intWeather;
            string season, weather, temperature, climate, geography;
            string[] tempSplit;
            int[] tempInts = new int[2];
            season = Request.QueryString["season"];
            weather = Request.QueryString["weather"];
            temperature = Request.QueryString["temperature"];
            climate = Request.QueryString["climate"];
            geography = Request.QueryString["geography"];
            
            //url string checking to make sure selections have been made before generating lists
            if (temperature != null && weather != null)
            {
                //breaks the webpage if user's enter junk into the query string for season, climate, or geography
                var SeasonMatch = SeasonList.Where(s => s.Value.Equals(season)).First().Value;
                var ClimateMatch = ClimateList.Where(c => c.Value.Contains(climate)).First().Value;
                var GeoMatch = GeoList.Where(g => g.Value.Contains(geography)).First().Value;

                //if weather or temp url query string is altered, show error page
                if (!Char.IsDigit(temperature[0]) || !Char.IsDigit(weather[0]))
                {
                    return RedirectToAction("Error", "Error");
                }
                else
                {
                    intWeather = Convert.ToInt32(weather);
                    //split temperatue string and convert to ints
                    tempSplit = temperature.Split('-');
                    tempInts = Array.ConvertAll(tempSplit, s => int.Parse(s));
                    temp1 = tempInts[0];
                    temp2 = tempInts[1];

                    //generate list for states with selected climate
                    if (climate != "no preference")
                    {
                        climateStates = db.Climates.Where(c => c.ClimateType == climate).SelectMany(c => c.States).ToList();
                    }
                    //generate list for states with selected geography
                    if (geography != "no preference")
                    {
                        geographyStates = db.Geographies.Where(g => g.GeographyType == geography).SelectMany(g => g.States).ToList();
                    }

                    //switch cases for each season to query the database or list
                    switch (season)
                    {
                        case "spring":
                            if (climate == "no preference")
                            {
                                foreach (State state in geographyStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = SpringWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = SpringNoPrefWeather(temp1, temp2);
                                }
                            }
                            if (geography == "no preference")
                            {
                                foreach (State state in climateStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = SpringWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = SpringNoPrefWeather(temp1, temp2);
                                }
                            }
                            if (climate == "no preference" && geography == "no preference")
                            {
                                if (weather != "0")
                                {
                                   result = SpringWeatherSelectedStatesDBOnly(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = SpringNoPrefWeatherStatesDBOnly(temp1, temp2);
                                }
                            }
                            else
                            {
                                foreach (State state in climateStates)
                                {
                                    if (geographyStates.Contains(state))
                                    {
                                        queryStates.Add(state);
                                    }
                                }
                                if (weather != "0")
                                {
                                    result = SpringWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = SpringNoPrefWeather(temp1, temp2);
                                }
                            }
                            ViewBag.Result = "springlist";
                            break;
                        case "summer":
                            if (climate == "no preference")
                            {
                                foreach (State state in geographyStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = SummerWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = SummerNoPrefWeather(temp1, temp2);
                                }
                            }
                            if (geography == "no preference")
                            {
                                foreach (State state in climateStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = SummerWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = SummerNoPrefWeather(temp1, temp2);
                                }
                            }
                            if (climate == "no preference" && geography == "no preference")
                            {
                                if (weather != "0")
                                {
                                    result = SummerWeatherSelectedStatesDBOnly(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = SummerNoPrefWeatherStatesDBOnly(temp1, temp2);
                                }
                            }
                            else
                            {
                                foreach (State state in climateStates)
                                {
                                    if (geographyStates.Contains(state))
                                    {
                                        queryStates.Add(state);
                                    }
                                }
                                if (weather != "0")
                                {
                                    result = SummerWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = SummerNoPrefWeather(temp1, temp2);
                                }
                            }
                            ViewBag.Result = "summerlist";
                            break;
                        case "fall":
                            if (climate == "no preference")
                            {
                                foreach (State state in geographyStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = FallWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = FallNoPrefWeather(temp1, temp2);
                                }

                            }
                            if (geography == "no preference")
                            {
                                foreach (State state in climateStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = FallWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = FallNoPrefWeather(temp1, temp2);
                                }
                            }
                            if (climate == "no preference" && geography == "no preference")
                            {
                                if (weather != "0")
                                {
                                    result = FallWeatherSelectedStatesDBOnly(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = FallNoPrefWeatherStatesDBOnly(temp1, temp2);
                                }
                            }
                            else
                            {
                                foreach (State state in climateStates)
                                {
                                    if (geographyStates.Contains(state))
                                    {
                                        queryStates.Add(state);
                                    }
                                }
                                if (weather != "0")
                                {
                                    result = FallWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = FallNoPrefWeather(temp1, temp2);
                                }
                            }
                            ViewBag.Result = "fallresult";
                            break;
                        case "winter":
                            if (climate == "no preference")
                            {
                                foreach (State state in geographyStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = WinterWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = WinterNoPrefWeather(temp1, temp2);
                                }

                            }
                            if (geography == "no preference")
                            {
                                foreach (State state in climateStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = WinterWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = WinterNoPrefWeather(temp1, temp2);
                                }
                            }
                            if (climate == "no preference" && geography == "no preference")
                            {
                                if (weather != "0")
                                {
                                    result = WinterWeatherSelectedStatesDBOnly(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = WinterNoPrefWeatherStatesDBOnly(temp1, temp2);
                                }
                            }
                            else
                            {
                                foreach (State state in climateStates)
                                {
                                    if (geographyStates.Contains(state))
                                    {
                                        queryStates.Add(state);
                                    }
                                }
                                if (weather != "0")
                                {
                                    result = WinterWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = WinterNoPrefWeather(temp1, temp2);
                                }
                            }
                            ViewBag.Result = "winterresult";
                            break;
                        case "no preference":
                            if (climate == "no preference")
                            {
                                foreach (State state in geographyStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = NoSeasonWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = NoSeasonNoPrefWeather(temp1, temp2);
                                }
                            }
                            if (geography == "no preference")
                            {
                                foreach (State state in climateStates)
                                {
                                    queryStates.Add(state);
                                }
                                if (weather != "0")
                                {
                                    result = NoSeasonWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = NoSeasonNoPrefWeather(temp1, temp2);
                                }
                            }

                            if (climate == "no preference" && geography == "no preference")
                            {
                                if (weather != "0")
                                {
                                    result = NoSeasonWeatherSelectedStatesDBOnly(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = NoSeasonNoPrefWeatherStatesDBOnly(temp1, temp2);
                                }
                            }
                            else
                            {
                                foreach (State state in climateStates)
                                {
                                    if (geographyStates.Contains(state))
                                    {
                                        queryStates.Add(state);
                                    }
                                }
                                if (weather != "0")
                                {
                                    result = NoSeasonWeatherSelected(intWeather, temp1, temp2);
                                }
                                else
                                {
                                    result = NoSeasonNoPrefWeather(temp1, temp2);
                                }
                            }
                            ViewBag.Result = "noprefresult";
                            break;
                        default:
                            break;
                    }
                    if (result.Count() == 0)
                    {
                        if (weather == "0")
                        {
                            ViewBag.Message = "I'm sorry, but there are no results for your given selections of " + season + " season, with no preference weather, " + temp1 + " to " + temp2 + " degree temperature, " + climate.ToLower() + " climate, and a geography selection of " + geography.ToLower() + ".";
                        }
                        else
                        {
                            ViewBag.Message = "I'm sorry, but there are no results for your given selections of " + season + " season, with " + db.Weather.Where(w => w.WeatherID == intWeather).FirstOrDefault().WeatherType.ToLower() + " weather, " + temp1 + " to " + temp2 + " degree temperature, " + climate.ToLower() + " climate, and a geography selection of " + geography.ToLower() + ".";
                        }
                    }
                    else
                    {
                        if (weather == "0")
                        {
                            ViewBag.Message = "Here are the results for your given selections of " + season + " season, with no preference weather, " + temp1 + " to " + temp2 + " degree temperature, " + climate.ToLower() + " climate, and a geography selection of " + geography.ToLower() + ".";
                        }
                        else
                        {
                            ViewBag.Message = "Here are the results for your given selections of " + season + " season, with " + db.Weather.Where(w => w.WeatherID == intWeather).FirstOrDefault().WeatherType.ToLower() + " weather, " + temp1 + " to " + temp2 + " degree temperature, " + climate.ToLower() + " climate, and a geography selection of " + geography.ToLower() + ".";
                        }
                    }
                }
            }

            return View(result);
        }

        private void GenerateLists()
        {
            SeasonList = new List<SelectListItem>()
            {
                new SelectListItem{ Value="no preference", Text="No Preference"},
                new SelectListItem{ Value="spring", Text="Spring"},
                new SelectListItem{ Value="summer", Text="Summer"},
                new SelectListItem{ Value="fall", Text="Fall"},
                new SelectListItem{ Value="winter", Text="Winter"}
            };
            ViewBag.SeasonList = SeasonList;

            WeatherList = new List<SelectListItem>()
            {

                new SelectListItem{ Value="0", Text="No Preference"},
                new SelectListItem{ Value="1", Text="Clear Skies"},
                new SelectListItem{ Value="2", Text="Cloudy"},
                new SelectListItem{ Value="3", Text="Humid"},
                new SelectListItem{ Value="4", Text="Rainy"},
                new SelectListItem{ Value="5", Text="Snowy"},
                new SelectListItem{ Value="6", Text="Sunny"}
            };
            ViewBag.WeatherList = WeatherList;

            TempList = new List<SelectListItem>()
            {

                new SelectListItem{ Value="0-120", Text="No Preference"},
                new SelectListItem{ Value="100-120", Text="100s or higher"},
                new SelectListItem{ Value="80-99", Text="80s to 90s"},
                new SelectListItem{ Value="60-79", Text="60s to 70s"},
                new SelectListItem{ Value="40-59", Text="40s to 50s"},
                new SelectListItem{ Value="0-39", Text="30s or less"}
            };
            ViewBag.TemperatureList = TempList;

            ClimateList = new List<SelectListItem>()
            {
                new SelectListItem{ Value="no preference", Text="No Preference"},
                new SelectListItem{ Value="Semiarid Steppe", Text="Semiarid"},
                new SelectListItem{ Value="Humid subtropical", Text="Humid subtropical"},
                new SelectListItem{ Value="Marine westcoast", Text="Marine westcoast"},
                new SelectListItem{ Value="Mediterranean", Text="Mediterranean"},
                new SelectListItem{ Value="Humid continentental warm summer", Text="Humid continentental warm summer"},
                new SelectListItem{ Value="Humid continentental cold summer", Text="Humid continentental cold summer"},
                new SelectListItem{ Value="Highland", Text="Highland"},
                new SelectListItem{ Value="Tropical wet/dry", Text="Tropical"},
                new SelectListItem{ Value="Desert", Text="Desert"},
                new SelectListItem{ Value="Tundra", Text="Tundra"}
            };
            ViewBag.ClimateList = ClimateList;

            GeoList = new List<SelectListItem>()
            {
                new SelectListItem{ Value="no preference", Text="No Preference"},
                new SelectListItem{ Value="Mountain", Text="Mountain"},
                new SelectListItem{ Value="River", Text="River"},
                new SelectListItem{ Value="Lake", Text="Lake"},
                new SelectListItem{ Value="Forest", Text="Forest"},
                new SelectListItem{ Value="Coast", Text="Coast"},
                new SelectListItem{ Value="Plains", Text="Plains"},
                new SelectListItem{ Value="Desert", Text="Desert"},
                new SelectListItem{ Value="Swamp", Text="Swamp"},
                new SelectListItem{ Value="Cave", Text="Cave"},
                new SelectListItem{ Value="Plateau", Text="Plateau"},
            };
            ViewBag.GeographyList = GeoList;
        }

        /// <summary>
        /// Queries the database for spring weather and temp from the climate/geography states list and sets the result to the returned list of states
        /// </summary>
        public List<State> SpringWeatherSelected(int intWeather, int temp1, int temp2)
        {
            return queryStates.Where(s => s.SpringWeather == intWeather).Where(s => s.SpringTemp >= temp1 && s.SpringTemp <= temp2).ToList();
        }

        /// <summary>
        /// Queries the database for spring temp from the climate/geography states list and sets the result to the returned list of states
        /// </summary>
        public List<State> SpringNoPrefWeather(int temp1, int temp2)
        {
            return queryStates.Where(s => s.SpringTemp >= temp1 && s.SpringTemp <= temp2).ToList();
        }

        /// <summary>
        /// Queries the database for spring weather and temp and sets the result to the returned list of states
        /// </summary>
        public List<State> SpringWeatherSelectedStatesDBOnly(int intWeather, int temp1, int temp2)
        {
            return repo.States.Where(s => s.SpringWeather == intWeather).Where(s => s.SpringTemp >= temp1 && s.SpringTemp <= temp2).ToList();
        }

        /// <summary>
        /// Queries the database for spring temp and sets the result to the returned list of states
        /// </summary>
        public List<State> SpringNoPrefWeatherStatesDBOnly(int temp1, int temp2)
        {
            return repo.States.Where(s => s.SpringTemp >= temp1 && s.SpringTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates summer states for weather/temp from climate/geo list
        /// </summary>
        public List<State> SummerWeatherSelected(int intWeather, int temp1, int temp2)
        {
            return queryStates.Where(s => s.SummerWeather == intWeather).Where(s => s.SummerTemp >= temp1 && s.SummerTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates summer states for temp from climate/geo list
        /// </summary>
        public List<State> SummerNoPrefWeather(int temp1, int temp2)
        {
            return queryStates.Where(s => s.SummerTemp >= temp1 && s.SummerTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates summer states for weather/temp 
        /// </summary>
        public List<State> SummerWeatherSelectedStatesDBOnly(int intWeather, int temp1, int temp2)
        {
            return db.States.Where(s => s.SummerWeather == intWeather).Where(s => s.SummerTemp >= temp1 && s.SummerTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates summer states for temp
        /// </summary>
        public List<State> SummerNoPrefWeatherStatesDBOnly(int temp1, int temp2)
        {
            return db.States.Where(s => s.SummerTemp >= temp1 && s.SummerTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates fall list
        /// </summary>
        public List<State> FallWeatherSelected(int intWeather, int temp1, int temp2)
        {
            return queryStates.Where(s => s.FallWeather == intWeather).Where(s => s.FallTemp >= temp1 && s.FallTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates fall list
        /// </summary>
        public List<State> FallNoPrefWeather(int temp1, int temp2)
        {
            return queryStates.Where(s => s.FallTemp >= temp1 && s.FallTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates fall list
        /// </summary>
        public List<State> FallWeatherSelectedStatesDBOnly(int intWeather, int temp1, int temp2)
        {
            return db.States.Where(s => s.FallWeather == intWeather).Where(s => s.FallTemp >= temp1 && s.FallTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates fall list
        /// </summary>
        public List<State> FallNoPrefWeatherStatesDBOnly(int temp1, int temp2)
        {
            return db.States.Where(s => s.FallTemp >= temp1 && s.FallTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates winter list
        /// </summary>
        public List<State> WinterWeatherSelected(int intWeather, int temp1, int temp2)
        {
            return queryStates.Where(s => s.WinterWeather == intWeather).Where(s => s.WinterTemp >= temp1 && s.WinterTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates winter list
        /// </summary>
        public List<State> WinterNoPrefWeather(int temp1, int temp2)
        {
            return queryStates.Where(s => s.WinterTemp >= temp1 && s.WinterTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates winter list
        /// </summary>
        public List<State> WinterWeatherSelectedStatesDBOnly(int intWeather, int temp1, int temp2)
        {
            return db.States.Where(s => s.WinterWeather == intWeather).Where(s => s.WinterTemp >= temp1 && s.WinterTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates winter list
        /// </summary>
        public List<State> WinterNoPrefWeatherStatesDBOnly(int temp1, int temp2)
        {
            return db.States.Where(s => s.WinterTemp >= temp1 && s.WinterTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates no season preference list
        /// </summary>
        public List<State> NoSeasonWeatherSelected(int intWeather, int temp1, int temp2)
        {
            return queryStates.Where(s => s.SpringWeather == intWeather || s.SummerWeather == intWeather || s.FallWeather == intWeather || s.WinterWeather == intWeather).Where(s => s.SpringTemp >= temp1 && s.SpringTemp <= temp2 || s.SummerTemp >= temp1 && s.SummerTemp <= temp2 || s.FallTemp >= temp1 && s.FallTemp <= temp2 || s.WinterTemp >= temp1 && s.WinterTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates no season preference list
        /// </summary>
        public List<State> NoSeasonNoPrefWeather(int temp1, int temp2)
        {
            return queryStates.Where(s => s.SpringTemp >= temp1 && s.SpringTemp <= temp2 || s.SummerTemp >= temp1 && s.SummerTemp <= temp2 || s.FallTemp >= temp1 && s.FallTemp <= temp2 || s.WinterTemp >= temp1 && s.WinterTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates no season preference list
        /// </summary>
        public List<State> NoSeasonWeatherSelectedStatesDBOnly(int intWeather, int temp1, int temp2)
        {
            return db.States.Where(s => s.SpringWeather == intWeather || s.SummerWeather == intWeather || s.FallWeather == intWeather || s.WinterWeather == intWeather).Where(s => s.SpringTemp >= temp1 && s.SpringTemp <= temp2 || s.SummerTemp >= temp1 && s.SummerTemp <= temp2 || s.FallTemp >= temp1 && s.FallTemp <= temp2 || s.WinterTemp >= temp1 && s.WinterTemp <= temp2).ToList();
        }

        /// <summary>
        /// Generates no season preference list
        /// </summary>
        public List<State> NoSeasonNoPrefWeatherStatesDBOnly(int temp1, int temp2)
        {
            return db.States.Where(s => s.SpringTemp >= temp1 && s.SpringTemp <= temp2 || s.SummerTemp >= temp1 && s.SummerTemp <= temp2 || s.FallTemp >= temp1 && s.FallTemp <= temp2 || s.WinterTemp >= temp1 && s.WinterTemp <= temp2).ToList();
        }

        public ActionResult Result()
        {
            return View();
        }

        /// <summary>
        /// Use Get to return a view that was passed a State.
        /// </summary>
        /// <param name="stateName">The name of the State that will be passed to the view.</param>
        /// <returns></returns>
        public ActionResult StateInfo(int? stateID)
        {
            if (stateID == null || stateID > 50)
            {
                return RedirectToAction("Index");
            }
            else
            {
                //Get the state from the db using the states name.
                State selectedState = db.States.Where(n => n.StateID.Equals((int)stateID)).FirstOrDefault();

                ViewBag.ActivityItems = GetActivityList(stateID);

                //Make crimeCheck to check what range of level of crime the state falls under.
                decimal crimeRate = selectedState.CrimeRate;
                ViewBag.crimeCheck = CheckCrime(crimeRate);

                //String array for food name items based on state selected
                string[] foodItemsList = new string[3];
                //Fill the Array with appropriate fooditems from the DB
                foodItemsList[0] = db.States.Where(s => s.StateID.Equals((int)stateID))
                              .Select(f => f.FoodItemOne).FirstOrDefault();
                foodItemsList[1] = db.States.Where(s => s.StateID.Equals((int)stateID))
                              .Select(f => f.FoodItemTwo).FirstOrDefault();
                foodItemsList[2] = db.States.Where(s => s.StateID.Equals((int)stateID))
                              .Select(f => f.FoodItemThree).FirstOrDefault();

                ViewBag.foodItems = foodItemsList;

                ViewBag.GeoList = GetGeographyList(stateID);

                return View(selectedState);
            }
        }

        //Returns the activity list for the selected state
        public string[] GetActivityList(int? stateID)
        {
            //Get the state from the db using the states name.
            State selectedState = repo.States.Where(n => n.StateID.Equals((int)stateID)).FirstOrDefault();

            //String array for activity name items based on state selected
            string[] activityItemList = new string[3];
            //Fill the Array with appropriate fooditems from the DB
            activityItemList[0] = selectedState.ActivityItemOne;
            activityItemList[1] = selectedState.ActivityItemTwo;
            activityItemList[2] = selectedState.ActivityItemThree;

            return activityItemList;
        }

        //Returns the geography list for the selected state
        public List<String> GetGeographyList(int? stateID)
        {
            //Int array for first three geo from selected state
            List<string> geoList = new List<string>();
            //fill array with first three geos
            geoList = repo.States.ToList().Where(s => s.StateID.Equals((int)stateID))
                               .SelectMany(c => c.Geographies)
                               .Select(t => t.GeographyType).ToList();

            return geoList;
        }

        //Returns the ViewBag number for the crimeCheck.
        public int CheckCrime(decimal crimeRate)
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

    }
}