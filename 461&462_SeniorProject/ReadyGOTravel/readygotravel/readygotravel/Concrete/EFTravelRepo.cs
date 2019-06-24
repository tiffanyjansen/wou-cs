using readygotravel.Abstract;
using readygotravel.Models;
using System.Collections.Generic;

namespace readygotravel.Concrete
{
    public class EFTravelRepo : ITravelRepo
    {
        private DBContext db = new DBContext();

        public IEnumerable<Airport> Airports {
            get { return db.Airports; }
        }
        public IEnumerable<Climate> Climates {
            get { return db.Climates; }
        }
        public IEnumerable<Continent> Continents {
            get { return db.Continents; }
        }
        public IEnumerable<Country> Countries {
            get { return db.Countries; }
        }
        public IEnumerable<Currency> Currencies { 
            get { return db.Currencies; }
        }
        public IEnumerable<Geography> Geographies {
            get { return db.Geographies; }
        }
        public IEnumerable<Location> Locations {
            get {return db.Locations; }
        }
        public IEnumerable<Person> People {
            get { return db.People; }
        }
        public IEnumerable<Result> Results {
            get { return db.Results; }
        }
        public IEnumerable<Search> Searches {
            get { return db.Searches; }
        }
        public IEnumerable<State> States {
            get { return db.States; }
        }
        public IEnumerable<UserState> UserStates {
            get { return db.UserStates; }
        }
        public IEnumerable<Weather> Weather {
            get { return db.Weather;  }
        }
    }
}